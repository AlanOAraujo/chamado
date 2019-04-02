package br.com.pagga.chamado.service;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.dao.PerfilDAO;
import br.com.pagga.chamado.dao.PerfilRotinaAtributoDAO;
import br.com.pagga.chamado.model.Perfil;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class PerfilServiceTeste {
	
	private static EntityManager em;
	private static PerfilDAO perfilDAO;
	private static PerfilService perfilService;
	private static long idSalvo;
	
	public static void main (String args[]) {
		
		iniciar();
		try {
			em.getTransaction().begin();
			cadastrarPerfilTeste();
			removePerfilTeste();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new BusinessException("Erro executando teste | " + e.getMessage());
		} finally {
			em.close();
		}
	}
	
	private static void cadastrarPerfilTeste () {
		Perfil perfil;
		
//		perfil = new Perfil("");											//Perfil sem descricao
//		perfilService.cadastrarPerfil(perfil);
		
		perfil = new Perfil("Perfil Teste");								// Sucesso
		perfilService.cadastrarPerfil(perfil);
		
//		perfil = new Perfil("Analista");									// Perfil já cadastrado
//		perfilService.cadastrarPerfil(perfil);
		
		if (perfil.getId() == null) {
			throw new BusinessException("Erro ao cadastrar perfil...");
		}
		
		idSalvo = perfil.getId();
		System.out.println("Perfil cadastrado com sucesso!");
	}

	private static void removePerfilTeste () {
		Perfil perfil = perfilDAO.findById(idSalvo);
		
		perfilService.removePerfil(perfil);
		
		if (perfilDAO.findById(idSalvo) != null) {
			throw new BusinessException("Erro ao remover perfil...");
		}
		
		System.out.println("Perfil removido com sucesso!");
	}
	
	private static void iniciar () {
		em = JPAUtil.createEntityManager();
		perfilDAO = new PerfilDAO(em);
		PerfilRotinaAtributoDAO perfilRotinaAtributoDAO = new PerfilRotinaAtributoDAO(JPAUtil.createEntityManager());
		PerfilRotinaAtributoService perfilRotinaAtributoService = new PerfilRotinaAtributoService(perfilRotinaAtributoDAO);
		
		perfilService = new PerfilService(perfilDAO, perfilRotinaAtributoService);
	}
}
