package br.com.pagga.chamado.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pagga.chamado.dao.UsuarioDAO;
import br.com.pagga.chamado.model.Atributo;
import br.com.pagga.chamado.model.PerfilRotinaAtributo;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.model.UsuarioPerfil;
import br.com.pagga.chamado.security.UsuarioLogado;
import br.com.pagga.chamado.service.exception.BusinessException;

public class LoginService {

	private UsuarioDAO usuarioDAO;
	
	@Inject
	private UsuarioService usuarioService; 

	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Inject
	public LoginService(UsuarioDAO usuarioDAO, UsuarioLogado usuarioLogado) {
		this.usuarioDAO = usuarioDAO;
	}

	public Usuario autentificaLogin(Usuario usuario) {
		
		if (StringUtils.isBlank(usuario.getCpf())) {
			throw new BusinessException("Campo cpf precisa ser preenchido");
		}
		
		if (StringUtils.isBlank(usuario.getSenha())) {
			throw new BusinessException("Campo senha precisa ser preenchida");
		}
		
		Usuario userAuthenticated = usuarioDAO.autentificaLogin(usuario);
		
		if(userAuthenticated == null) {
			logger.error("Usuário não encontrado, verificar se o CPF e Senha estão corretos");
			throw new BusinessException("Usuário não encontrado, verificar se o CPF e Senha estão corretos");
		}
		
		if(!userAuthenticated.isSituacao()) {
			logger.error("Usuário Inativo");
			throw new BusinessException("Usuário Inativo");
		}
		
		return userAuthenticated;
		
	}
	
	public Usuario senhaEsquecida(String emailUsuario, String cpfUsuario) throws IOException {
		
		Usuario usuarioSolicitante = usuarioDAO.findByCPF(cpfUsuario);
		
		//String gRecaptchaResponse = gRecaptcha;
		
		if(!emailUsuario.equals(usuarioSolicitante.getEmail()))
			throw new BusinessException("E-mail informado, esta diferente do e-mail registrado");
		
		if(!usuarioSolicitante.isSituacao())
			throw new BusinessException("Usuário Inativo, não será possível redefinir senha");
		
		try {
			
			String nomeUsuario = usuarioSolicitante.getNome();
			String senhaRandomica = gerarSenha();
				
			//boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
			
			usuarioSolicitante.setSenha(senhaRandomica);
			
			usuarioService.esqueceSenha(usuarioSolicitante);
			
			String senhaUsuario = usuarioSolicitante.getSenha();
			
			EmailService.emailEsqueceSenha(emailUsuario, nomeUsuario, senhaUsuario, cpfUsuario);
			
			return usuarioSolicitante;
			
		} catch (Exception e) {
			throw new BusinessException("Erro ao redefinir a senha");
		}
		
	}
	
	private static String gerarSenha() {
        String[] caracteres = { "1", "2", "3", "4", "5", "6", "7", "8",
                "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
                "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z", "?", "!", "@", "$", "&"};
       
        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int posicao = (int) (Math.random() * caracteres.length);
            senha.append(caracteres[posicao]);
        }
        return senha.toString();
    }
	
	public static List<Atributo> autentificarPerfil(UsuarioLogado usuarioLogado) {
		
		List<UsuarioPerfil> usuarioPerfils = usuarioLogado.getUsuario().getUsuarioPerfilList();
		
		List<Atributo> codAtributos = new ArrayList<>();
		
		for (int i = 0; i < usuarioPerfils.size(); i++) {
			
			List<PerfilRotinaAtributo> perfilRotinaAtributos = usuarioPerfils.get(i).getPerfil().getPerfilRotinaAtributo();
			
			for (int j = 0; j < perfilRotinaAtributos.size(); j++) {
				
				codAtributos.add(perfilRotinaAtributos.get(j).getAtributo());
				
			}
		}
		
		return codAtributos;
		
	}
	
}
