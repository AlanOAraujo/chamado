package br.com.pagga.chamado.model;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.dao.PerfilDAO;
import br.com.pagga.chamado.dao.PerfilRotinaAtributoDAO;
import br.com.pagga.chamado.service.PerfilRotinaAtributoService;
import br.com.pagga.chamado.service.PerfilService;
import br.com.pagga.chamado.util.JPAUtil;

public class PerfilTest {
	
	public static void main (String [] args) {
		
		EntityManager entityManager = JPAUtil.createEntityManager();
		
		Perfil perfil = new PerfilDAO(entityManager).findById(13);
		
		entityManager.close();
		
		entityManager = JPAUtil.createEntityManager();
		
		PerfilDAO perfilDAO = new PerfilDAO(entityManager);
		
		PerfilRotinaAtributoDAO perfilRotinaAtributoDAO = new PerfilRotinaAtributoDAO(entityManager);
		
		PerfilRotinaAtributoService perfilRotinaAtributoService = new PerfilRotinaAtributoService(perfilRotinaAtributoDAO);
		
		PerfilService perfilService = new PerfilService(perfilDAO, perfilRotinaAtributoService);
		
		/*******************Cadastrar Perfil***********************/
		
		/*perfil = Perfil.create("Tecnico TI");
				
		entityManager.getTransaction().begin();
		
		try {
			
			perfilService.cadastrarPerfil(perfil);
			
			entityManager.getTransaction().commit();
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			entityManager.getTransaction().rollback();
		}*/
		
		/*********************************************************/
		
		/*******************Alterar Perfil***********************/
		
		/*perfil = perfilDAO.findById(1);
		
		perfil.setDescricao("Usuário");
				
		entityManager.getTransaction().begin();
		try {
			
			perfilService.alterarPerfil(perfil);
			
			entityManager.getTransaction().commit();
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			entityManager.getTransaction().rollback();
		}
		*/
		
		/*********************************************************/
		
		/********************Remover Perfil***********************/
		
		
		perfil = perfilDAO.findById(14);
		
		entityManager.getTransaction().begin();
		try {
			
			perfilService.removePerfil(perfil);
			
			entityManager.getTransaction().commit();
			
		}catch (Exception e) {
			
			e.printStackTrace();
			
			entityManager.getTransaction().rollback();
		}
		
		/**********************************************************/
	}

}
