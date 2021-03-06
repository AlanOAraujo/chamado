package br.com.pagga.chamado.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.model.Perfil;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class PerfilDAOTest {

	private static EntityManager em;
	private static PerfilDAO perfilDAO;
	private static long idSalvo;

	public static void main(String[] args) {
		conectar();
		em.getTransaction().begin();

		try {
			testeSave();
			testeFindById();
			testeFindAll();
			testeFindByDescricao();
			testeRemoveById();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new BusinessException("Erro executando testes | " + e.getMessage());
		} finally {
			em.close();
		}

	}

	private static void testeSave() {
		Perfil perfil = new Perfil("TESTE");
		
		perfilDAO.save(perfil);
		if(perfil.getId() != null && perfil.getId() >0) {
			idSalvo = perfil.getId();
			System.out.println("Perfil salvo com sucesso...");
		} else {
			throw new BusinessException("Erro ao salvar perfil...");
		}
	}

	private static void testeFindById() {
		Perfil perfil = perfilDAO.findById(idSalvo);
		if(perfil == null) {
			throw new BusinessException("Perfil não encontrado!");
		}
		System.out.println("Perfil encontrado!");
	}

	private static void testeFindAll() {
		List<Perfil> perfis = perfilDAO.findAll();
		if (perfis.isEmpty()) {
			throw new BusinessException("Perfil não encontrado!");
		}
		System.out.println("Encontrado(s) " + perfis.size() + " perfil(s)");
	}

	private static void testeFindByDescricao() {
		List<Perfil> perfil = perfilDAO.findByDescricao("TESTE");
		if(perfil == null) {
			throw new BusinessException("Perfil não encontrado!");
		}
		System.out.println("Perfil encontrado!");
	}

	private static void testeRemoveById() {
		perfilDAO.removeById(idSalvo);
		if (perfilDAO.findById(idSalvo) != null) {
			throw new BusinessException("Perfil inexistente...");
		}
		System.out.println("Perfil removido com sucesso!");
	}

	private static void conectar() {
		em = JPAUtil.createEntityManager();
		perfilDAO = new PerfilDAO(em);
	}
}
