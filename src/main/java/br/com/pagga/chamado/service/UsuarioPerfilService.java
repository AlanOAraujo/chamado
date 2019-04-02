package br.com.pagga.chamado.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pagga.chamado.dao.UsuarioPerfilDAO;
import br.com.pagga.chamado.model.UsuarioPerfil;

public class UsuarioPerfilService {
	
	private UsuarioPerfilDAO usuarioPerfilDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioPerfilService.class);
		
	@Inject
	public UsuarioPerfilService(UsuarioPerfilDAO usuperDAO) {
		this.usuarioPerfilDAO = usuperDAO;
	}
	
	public void cadastroUsuarioPerfil(UsuarioPerfil usuarioPerfil) {
		
		usuarioPerfilDAO.save(usuarioPerfil);
		logger.info("Usuario {} agora faz parte do Perfil {}",
				usuarioPerfil.getUsuario().getNome(), usuarioPerfil.getPerfil().getDescricao());
	}
	
	public void removerPerfilUsuario(UsuarioPerfil usuarioPerfil) {
		
		usuarioPerfilDAO.removeById(usuarioPerfil.getId());
		logger.info("Usuario {} foi removido do Perfil {}",
				usuarioPerfil.getUsuario().getNome(), usuarioPerfil.getPerfil().getDescricao());
		
	}
}
