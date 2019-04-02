package br.com.pagga.chamado.model;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.dao.AtributoDAO;
import br.com.pagga.chamado.dao.PerfilDAO;
import br.com.pagga.chamado.dao.PerfilRotinaAtributoDAO;
import br.com.pagga.chamado.dao.RotinaDAO;
import br.com.pagga.chamado.service.PerfilRotinaAtributoService;
import br.com.pagga.chamado.util.JPAUtil;

public class PerfilRotinaAtributoTest {
	
	public static void main(String[] args) {
		
	EntityManager entityManager = JPAUtil.createEntityManager();
	
	PerfilRotinaAtributo perfilRotinaAtributo = new PerfilRotinaAtributoDAO(entityManager).findById(2);
	
	entityManager.close();
	
	entityManager = JPAUtil.createEntityManager();
	
	PerfilRotinaAtributoDAO perfilRotinaAtributoDAO = new PerfilRotinaAtributoDAO(entityManager);
	
	PerfilDAO perfilDAO = new PerfilDAO(entityManager);
	RotinaDAO rotinaDAO = new RotinaDAO(entityManager);
	AtributoDAO atributoDAO = new AtributoDAO(entityManager);
	
	PerfilRotinaAtributoService perfilRotinaAtributoService = new PerfilRotinaAtributoService(perfilRotinaAtributoDAO);
	
	
	/********************Cadastrar PerfilRA***************/
	
	perfilRotinaAtributo = PerfilRotinaAtributo.createPerfilRotinaAtributo(
			perfilDAO.findById(12), rotinaDAO.findById(4), atributoDAO.findById(6));
	
	entityManager.getTransaction().begin();
	
	try {
		perfilRotinaAtributoService.cadastrarPerfilRotinaAtributo(perfilRotinaAtributo);	
		entityManager.getTransaction().commit();
	}
	catch (Exception ex) {
		
		ex.printStackTrace();
		
		entityManager.getTransaction().rollback();
	
	}
	
	/*****************************************************/
	
	
	/*****************Remover PerfilRA********************/
	
	/*perfilRotinaAtributo = perfilRotinaAtributoDAO.findById(6);
	
	entityManager.getTransaction().begin();
	
	try {
		perfilRotinaAtributoService.removerPerfilRotinaAtributo(perfilRotinaAtributo);	
		entityManager.getTransaction().commit();
	}
	catch (Exception ex) {
		
		ex.printStackTrace();
		
		entityManager.getTransaction().rollback();
	
	}*/
	
	/*****************************************************/
	
	
	}
}
