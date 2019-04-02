package br.com.pagga.chamado.model;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.dao.PerfilDAO;
import br.com.pagga.chamado.dao.UsuarioDAO;
import br.com.pagga.chamado.dao.UsuarioPerfilDAO;
import br.com.pagga.chamado.service.UsuarioPerfilService;
import br.com.pagga.chamado.util.JPAUtil;

@SuppressWarnings("unused")
public class UsuarioPerfilTest {
	
	public static void main (String [] args) {
		
		EntityManager entityManager = JPAUtil.createEntityManager();
		
		UsuarioPerfil usuarioPerfil = new UsuarioPerfilDAO(entityManager).findById(8);
		
		entityManager.close();
		
		entityManager = JPAUtil.createEntityManager();
		
		UsuarioPerfilDAO usuarioPerfilDAO = new UsuarioPerfilDAO(entityManager);
		
		UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);
		
		PerfilDAO perfilDAO = new PerfilDAO(entityManager);
		
		UsuarioPerfilService usuarioPerfilService = new UsuarioPerfilService(usuarioPerfilDAO);
		
		
		/***************Cadastrar UsuarioPerfil******************/
		/*
		usuarioPerfil = 
				UsuarioPerfil.createUsuarioPerfil(usuarioDAO.findByCPF("22222"), perfilDAO.findByDescricao("Tecnico TI"));
		
		entityManager.getTransaction().begin();
		
		try {
			usuarioPerfilService.cadastroUsuarioPerfil(usuarioPerfil);
			entityManager.getTransaction().commit();
		}
		catch (Exception ex) {

			ex.printStackTrace();

			entityManager.getTransaction().rollback();
		
		}
		*/
		
		/********************************************************/
		
		/********************Remover Perfil***********************/
		
		usuarioPerfil = usuarioPerfilDAO.findById(9);
		
		entityManager.getTransaction().begin();
		
		try {
				
			usuarioPerfilService.removerPerfilUsuario(usuarioPerfil);

			entityManager.getTransaction().commit();
		}
		catch ( Exception ex ) {

			ex.printStackTrace();

			entityManager.getTransaction().rollback();
		}
		
		/*********************************************************/
		
	}

}
