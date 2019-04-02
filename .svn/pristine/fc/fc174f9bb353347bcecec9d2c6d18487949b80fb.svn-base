package br.com.pagga.chamado.service;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.dao.AtributoDAO;
import br.com.pagga.chamado.dao.PerfilDAO;
import br.com.pagga.chamado.dao.PerfilRotinaAtributoDAO;
import br.com.pagga.chamado.model.Atributo;
import br.com.pagga.chamado.model.Perfil;
import br.com.pagga.chamado.model.PerfilRotinaAtributo;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class PerfilRotinaAtributoServiceTeste {
	
	private static EntityManager em;
	private static PerfilRotinaAtributoService perfilRotinaAtributoService;
	private static PerfilRotinaAtributoDAO perfilRotinaAtributoDAO;
	private static AtributoDAO atributoDAO;
	private static PerfilDAO perfilDAO;
	private static long idSalvo;

	public static void main (String args[]) {
		
		iniciar();
		em.getTransaction().begin();

		try {
			cadastrarPerfilRotinaAtributoTeste();
			removerPerfilRotinaAtributoTeste();
			cadastrarPerfilRotinaAtributoTeste();
			removerTodasAsRotinasDoPerfil();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new BusinessException("Erro executando teste.");
		} finally {
			em.close();
		}
		
	}
	
	private static void cadastrarPerfilRotinaAtributoTeste () {
		PerfilRotinaAtributo perfilRotinaAtributo = new PerfilRotinaAtributo();
		Perfil perfil = perfilDAO.findById(1);
		Atributo atributo = atributoDAO.findById(1);
		
		perfilRotinaAtributo.setPerfil(perfil);
		perfilRotinaAtributo.setAtributo(atributo);
		perfilRotinaAtributo.setRotina(atributo.getRotina());
		
		perfilRotinaAtributoService.cadastrarPerfilRotinaAtributo(perfilRotinaAtributo);
		
		if (perfilRotinaAtributo.getId() == null) {
			throw new BusinessException("Erro ao cadastrar...");
		}
		
		idSalvo = perfilRotinaAtributo.getId();
	}
	
	private static void removerPerfilRotinaAtributoTeste () {
		PerfilRotinaAtributo perfilRotinaAtributo = perfilRotinaAtributoDAO.findById(idSalvo);
		perfilRotinaAtributoService.removerPerfilRotinaAtributo(perfilRotinaAtributo);
		
		PerfilRotinaAtributo check = perfilRotinaAtributoDAO.findById(idSalvo);
		if (check != null) {
			throw new BusinessException("Erro ao excluir...");
		}
		
		System.out.println("Registro excluído com sucesso!");
	}
	
	private static void removerTodasAsRotinasDoPerfil () {
		perfilRotinaAtributoService.removerTodasAsRotinasDoPerfil(perfilDAO.findById(1));
		List<PerfilRotinaAtributo> perfilRotinaAtributoList = perfilRotinaAtributoDAO.findAll();
		
		if (perfilRotinaAtributoList != null && !perfilRotinaAtributoList.isEmpty()) {
			throw new BusinessException("Erro ao excluir registros...");
		}
		
		System.out.println("Registros excluidos com sucesso");
	}
	
	private static void iniciar () {
		em = JPAUtil.createEntityManager();
		perfilRotinaAtributoDAO = new PerfilRotinaAtributoDAO(em);
		perfilRotinaAtributoService = new PerfilRotinaAtributoService(perfilRotinaAtributoDAO);
		perfilDAO = new PerfilDAO(em);
		atributoDAO = new AtributoDAO(em);
	}
	
}
