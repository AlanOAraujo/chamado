package br.com.pagga.chamado.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.model.Perfil;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.model.UsuarioPerfil;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class UsuarioPerfilDAOTest {
	
	private static EntityManager em;
	private static UsuarioPerfilDAO usuarioPerfilDAO;
	private static UsuarioDAO usuarioDAO;
	private static PerfilDAO perfilDAO;
	private static long idSalvo;

	public static void main(String[] args) {
		conectar();
		em.getTransaction().begin();
		try {
			testeSave();
			testeFindById();
			testeFindAll();
			testeRemove();
//			testeRemoveById();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new BusinessException("Erro executando testes | " + e.getMessage());
		} finally {
			em.close();
		}
	}
	
	private static void testeSave() {
		UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
		Usuario usuario = usuarioDAO.findById(1);
		Perfil perfil = perfilDAO.findById(1);
		
		usuarioPerfil.setUsuario(usuario);
		usuarioPerfil.setPerfil(perfil);
		
		usuarioPerfilDAO.save(usuarioPerfil);
		idSalvo = usuarioPerfil.getId();
		
		if(usuarioPerfil.getId() == null) {
			throw new BusinessException("Erro ao cadastrar perfil de usuário...");
		}
		System.out.println("Perfil de usuário cadastrado com sucesso!");
	}
	
	private static void testeFindById() {
		UsuarioPerfil usuarioPerfil = usuarioPerfilDAO.findById(idSalvo);
		if(usuarioPerfil == null) {
			throw new BusinessException("Perfil de usuário não encontrado...");
		}
		System.out.println("Perfil de usuário encontrado!");
	}
	
	private static void testeFindAll() {
		List<UsuarioPerfil> usuarioPerfilList = usuarioPerfilDAO.findAll();
		if (usuarioPerfilList.isEmpty()) {
			throw new BusinessException("Perfil de usuário não encontrado...");
		}
		System.out.println("Encontrado(s) " + usuarioPerfilList.size() + " perfil(s) de usuário(s)");
	}
	
	private static void testeRemove() {
		testeSave();
		UsuarioPerfil usuarioPerfil= usuarioPerfilDAO.findById(idSalvo);
		usuarioPerfilDAO.remove(usuarioPerfil);
		
		if (usuarioPerfilDAO.findById(idSalvo) != null) {
			throw new BusinessException("Falha ao remover perfil de usuário!");
		}
		System.out.println("Perfil de usuário removido!");
	}
	
//	private static void testeRemoveById() {
//		testeSave();
//		List<UsuarioPerfil> usuarioPerfilList = usuarioPerfilDAO.findAll();
//		if(usuarioPerfilList != null && usuarioPerfilList.size() > 0) {
//			usuarioPerfilDAO.removeById(usuarioPerfilList.get(usuarioPerfilList.size() - 1).getId());
//		}
//	}
	
	private static void conectar() {
		em = JPAUtil.createEntityManager();
		usuarioPerfilDAO = new UsuarioPerfilDAO(em);
		usuarioDAO = new UsuarioDAO(em);
		perfilDAO = new PerfilDAO(em);
	}

}
