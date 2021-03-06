package br.com.pagga.chamado.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.pagga.chamado.dao.UsuarioDAO;
import br.com.pagga.chamado.dao.UsuarioPerfilDAO;
import br.com.pagga.chamado.dao.filter.UsuarioFilter;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.model.UsuarioPerfil;
import br.com.pagga.chamado.security.UsuarioLogado;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.service.role.ControlePermissoes;
import br.com.pagga.chamado.util.StringUtil;

public class UsuarioService {

	private UsuarioDAO usuarioDAO;
	private UsuarioPerfilDAO usuarioPerfilDAO;
	private UsuarioLogado usuarioLogado;
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Inject
	public UsuarioService(UsuarioDAO usuarioDAO, UsuarioPerfilDAO usuarioPerfilDAO, UsuarioLogado usuarioLogado) {
		this.usuarioDAO = usuarioDAO;
		this.usuarioPerfilDAO = usuarioPerfilDAO;
		this.usuarioLogado = usuarioLogado;
	}

	public void cadastraUsuario( Usuario usuario ) {
		
		String permissaoCadUsr = ControlePermissoes.permissaoCadUsr(usuarioLogado);
		
		if(permissaoCadUsr == " " && !permissaoCadUsr.equals("CAD_USR")) {
			throw new BusinessException("Você não possui permissão para cadastrar usuário");
		}else {
			usuario.setSituacao(true);
			
			String usuarioCPF = usuario.getCpf().replaceAll("[^0-9]", "");
	
			usuario.setCpf(usuarioCPF);
			
			validaUsuario(usuario);
			
			usuarioDAO.save(usuario);
			
			if(usuario.getUsuarioPerfilList() != null && usuario.getUsuarioPerfilList().size() > 0) {
				for(UsuarioPerfil usuarioPerfil : usuario.getUsuarioPerfilList()) {
					usuarioPerfil.setUsuario(usuario);
					usuarioPerfilDAO.save(usuarioPerfil);
				}
			}
			
			logger.info("Usuario {} salvo com sucesso!", usuario.getNome());
		}
	}
	
	public void alterarUsuario( Usuario usuario ) {
		
		Usuario usuarioCadastrado = usuarioDAO.findById(usuario.getId());
			
		validaUsuario(usuarioCadastrado);
		
		String permissaoAltSenha = ControlePermissoes.permissaoAtrSnh(usuarioLogado);
		String permissaoAltEmail = ControlePermissoes.permissaoAtrEml(usuarioLogado);
		String permissaoAltNome = ControlePermissoes.permissaoAtrNom(usuarioLogado);
		String permissaoPrfAcs = ControlePermissoes.permissaoPrfAcs(usuarioLogado);
		String permissaoEdtUsr = ControlePermissoes.permissaoEdtUsr(usuarioLogado);

		if(!usuarioCadastrado.getSenha().equals(usuario.getSenha())) {
			if(permissaoAltSenha == " " && !permissaoAltSenha.equals("ATR_SNH")) {
				throw new BusinessException("Você não possui permissão para altera senha de usuário");
			}else {
				usuarioCadastrado.setSenha(usuario.getSenha());
			}
		}
		
		if(!usuarioCadastrado.getEmail().equals(usuario.getEmail())) {
			if(permissaoAltEmail == " " && !permissaoAltEmail.equals("ATR_EML")) {
				throw new BusinessException("Você não possui permissão para altera e-mail de usuário");
			}else {
				usuarioCadastrado.setEmail(usuario.getEmail());
			}
		}
		
		if(!usuarioCadastrado.getNome().equals(usuario.getNome())) {
			if(permissaoAltNome == " " && !permissaoAltNome.equals("ATR_NOM")) {
				throw new BusinessException("Você não possui permissão para altera nome de usuário");
			}else {
				usuarioCadastrado.setNome(usuario.getNome());
			}
		}

		if(permissaoEdtUsr == " " && !permissaoEdtUsr.equals("EDT_USR")) {
			throw new BusinessException("Você não possui permissão para alterar usuário");
		}else {
			usuarioDAO.save(usuarioCadastrado);
		}
		
		boolean usuarioTemPerfil = usuario.getUsuarioPerfilList() != null && usuario.getUsuarioPerfilList().size() > 0;
		boolean usuarioCadastradoTemPerfil = usuarioCadastrado.getUsuarioPerfilList() != null && usuarioCadastrado.getUsuarioPerfilList().size() > 0;
		
		if(permissaoPrfAcs == " " && !permissaoPrfAcs.equals("PRF_ACS")) {
			throw new BusinessException("Você não possui permissão para Adicionar/Remover perfil de acesso ao usuário");
		}else {	
			if (usuarioTemPerfil) {
				if (usuarioCadastradoTemPerfil) {
					for (UsuarioPerfil usuarioPerfil : usuario.getUsuarioPerfilList()) {
						boolean encontrado = false;
						for (UsuarioPerfil usuarioPerfilCadastrado : usuarioCadastrado.getUsuarioPerfilList()) {
							if (usuarioPerfilCadastrado.getPerfil().getId() == usuarioPerfil.getPerfil().getId()) {
								encontrado = true;
							}
						}
						if (!encontrado) {
							usuarioPerfil.setUsuario(usuario);
							usuarioPerfilDAO.save(usuarioPerfil);
						}
					}
				} else {
					for (UsuarioPerfil usuarioPerfil : usuario.getUsuarioPerfilList()) {
						usuarioPerfil.setUsuario(usuario);
						usuarioPerfilDAO.save(usuarioPerfil);
					}
				}
			} else {
				if (usuarioCadastradoTemPerfil) {
					for (UsuarioPerfil usuarioPerfilCadastrado : usuarioCadastrado.getUsuarioPerfilList()) {
						usuarioPerfilDAO.remove(usuarioPerfilCadastrado);
					}
				}
			}
			if (usuarioCadastradoTemPerfil) {
				if (usuarioTemPerfil) {
					for (UsuarioPerfil usuarioPerfilCadastrado : usuarioCadastrado.getUsuarioPerfilList()) {
						boolean encontrado = false;
						for (UsuarioPerfil usuarioPerfil : usuario.getUsuarioPerfilList()) {
							if (usuarioPerfilCadastrado.getPerfil().getId() == usuarioPerfil.getPerfil().getId()) {
								encontrado = true;
							}
						}
						if (!encontrado) {
							usuarioPerfilDAO.remove(usuarioPerfilCadastrado);
						}
					}
				}
			}
		}
		
		logger.info("Usuario {} alterado com sucesso!", usuario.getNome());
	}
	
	private void validaUsuario(Usuario usuario) {
		
		CPFValidator validador = new CPFValidator(); 
		
		if ( StringUtils.isBlank(usuario.getNome())) {
			throw new BusinessException("Nome do usuário é obrigatório");
		}
		
		if (StringUtils.isBlank(usuario.getCpf())) { 
			throw new BusinessException("CPF do usuário é obrigatório");
		}
		
		try {
			validador.assertValid(usuario.getCpf());
		} catch (InvalidStateException e) {
			throw new BusinessException("CPF inválido");
		}
		
		if (StringUtils.isBlank(usuario.getEmail())) {
			throw new BusinessException("Email do usuário é obrigatório");
		}
		
		Usuario findByCPF = usuarioDAO.findByCPF(usuario.getCpf());
		Usuario findByEmail = usuarioDAO.findByEmail(usuario.getEmail());
		
		if ((usuario.getId() == null && findByCPF != null) || ( findByCPF != null && usuario.getId() != findByCPF.getId())) {
			throw new BusinessException(String.format("CPF %s já cadastrado", usuario.getCpf()));
		}
		
		if ((usuario.getId() == null && findByEmail != null) || ( findByEmail != null && usuario.getId() != findByEmail.getId())) {
			throw new BusinessException(String.format("Email %s já cadastrado", usuario.getEmail()));
		}
		
		if(usuario.getDataCadastro() == null) {
			usuario.setDataCadastro(new Date());
		}
		
	}
	
	public List<Usuario> buscaPorNome(String nome) {
		try {
			return usuarioDAO.findByNome(nome);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ArrayList<>();
	}
	
	public List<Usuario> paginacaoUsuario(int pageNumber,  int pageSize, String filtro) {
		
		String permissaoCadUsr = ControlePermissoes.permissaoCadUsr(usuarioLogado);
		
		if(permissaoCadUsr == " " && !permissaoCadUsr.equals("CAD_USR")) {
			return new ArrayList<>();
		}else {
			UsuarioFilter usuarioFilter = aplicaFiltro(filtro);
			return usuarioDAO.listUsuario(pageNumber, pageSize, usuarioFilter);
		}	
	}
	
	public Long countUsuario(String filtro) {
		
		try {
			
			UsuarioFilter usuarioFilter = aplicaFiltro(filtro);
			
			return usuarioDAO.countUsuario(usuarioFilter);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return 0l;
	}
	
	private UsuarioFilter aplicaFiltro(String filtro) {
		
		UsuarioFilter usuarioFilter = new UsuarioFilter();
		
		if ( !StringUtil.isNullOrEmpty(filtro) && filtro.matches("[a-zA-Z_].*")) {
			
			if(!StringUtil.isNullOrEmpty(filtro) && filtro.matches("^[A-Za-z0-9+_.-]+@(.+)$") ) {
				usuarioFilter.withEmail(filtro);
			}else {
				usuarioFilter.withNome(filtro);
			}
		}else if(!StringUtil.isNullOrEmpty(filtro) && filtro.matches("[0-9]+|(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)")){
			if(filtro.matches("([0-9]{11}|[0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})")) {
				
				usuarioFilter.withCpf(filtro);
				
			}else if (filtro.matches("[0-9]")) {
				
				long idLong = Long.parseLong(filtro);
				usuarioFilter.withId(idLong);
				
			}
			
		}
		
		return usuarioFilter;
	}
	
	public void esqueceSenha(Usuario usuario) {
		
		Usuario usuarioRedefinir = usuarioDAO.findById(usuario.getId());
		
		validaUsuario(usuarioRedefinir);
		
		try {
			usuarioDAO.save(usuarioRedefinir);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
	
	public void alterarSenha(Long idUsuario, String senha) {
		
		String permissaoAltSenha = ControlePermissoes.permissaoAtrSnh(usuarioLogado);
		
		Usuario usuarioAlterarSenha = usuarioDAO.findById(idUsuario);
		
		validaUsuario(usuarioAlterarSenha);
		
		if(!senha.equals(usuarioAlterarSenha.getSenha())) {
			if(permissaoAltSenha == " " && !permissaoAltSenha.equals("ATR_SNH")) {
				throw new BusinessException("Você não possui permissão para altera senha de usuário");
			}else {
				usuarioAlterarSenha.setSenha(senha);
				usuarioDAO.save(usuarioAlterarSenha);
			}
		}else {
			throw new BusinessException("Esta senha já foi usado por "+usuarioAlterarSenha.getNome()+" informe uma senha diferente!");
		}
	}
	
	public void ativaUsuario(Usuario usuario) {
		
		String permissaoStuUsr = ControlePermissoes.permissaoStuUsr(usuarioLogado);
		
		if(permissaoStuUsr == " " && !permissaoStuUsr.equals("STU_USR")) {
			throw new BusinessException("Você não possui permissão para alterar situação do usuário");
		}else {
			Usuario usuarioSituacao = usuarioDAO.findById(usuario.getId());
			
			usuario.ativarUsuario(usuarioSituacao);
			
			this.situacaoUsuario(usuarioSituacao);
			
			logger.info("Usuario {} ativo!", usuarioSituacao.getNome());
		}
		
	}
	
	public void inativaUsuario(Usuario usuario) {
		
		String permissaoStuUsr = ControlePermissoes.permissaoStuUsr(usuarioLogado);
		
		if(permissaoStuUsr == " " && !permissaoStuUsr.equals("STU_USR")) {
			throw new BusinessException("Você não possui permissão para alterar situação do usuário");
		}else {
			Usuario usuarioSituacao = usuarioDAO.findById(usuario.getId());
			
			usuario.inativaUsuario(usuarioSituacao);
			
			this.situacaoUsuario(usuarioSituacao);
			
			logger.info("Usuario {} inativo!", usuario.getNome());
		}
	}
	
	private void situacaoUsuario( Usuario usuario ) {
		
		usuario.setSituacao(!usuario.isSituacao());
		usuarioDAO.save(usuario);
	}
	
}
