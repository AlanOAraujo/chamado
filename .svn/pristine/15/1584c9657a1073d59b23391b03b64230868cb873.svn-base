package br.com.pagga.chamado.service;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.dao.PerfilDAO;
import br.com.pagga.chamado.dao.UsuarioDAO;
import br.com.pagga.chamado.dao.UsuarioPerfilDAO;
import br.com.pagga.chamado.model.UsuarioPerfil;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class UsuarioPerfilServiceTeste {
	private static EntityManager em;
	private static UsuarioPerfilDAO usuarioPerfilDAO;
	private static UsuarioPerfilService usuarioPerfilService;
	private static UsuarioDAO usuarioDAO;
	private static PerfilDAO perfilDAO;
	private static long idSalvo;

	public static void main (String args[]) {
		iniciar();
		em.getTransaction().begin();
		try {
			testeCadastroUsuarioPerfil();
			testeRemoverPerfilUsuario();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new BusinessException("Erro executando teste");
//			e.printStackTrace();
		} finally {
			em.close();
		}
	}
	
	private static void testeCadastroUsuarioPerfil() {
		UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
		usuarioPerfil.setUsuario(usuarioDAO.findById(1));
		usuarioPerfil.setPerfil(perfilDAO.findById(1));
		usuarioPerfilService.cadastroUsuarioPerfil(usuarioPerfil);
		
		if (usuarioPerfil.getId() == null) {
			throw new BusinessException("Erro ao cadastrar perfil de usuário...");
		}
		idSalvo = usuarioPerfil.getId();
		
		System.out.println("Perfil de usuário cadastrado com sucesso!");
		
	}
	
	private static void testeRemoverPerfilUsuario () {
		UsuarioPerfil usuarioPerfil = usuarioPerfilDAO.findById(idSalvo);
		usuarioPerfilService.removerPerfilUsuario(usuarioPerfil);
		if (usuarioDAO.findById(idSalvo) != null) {
			throw new BusinessException("Erro ao excluir perfil de usuário...");
		}
	}
	
	private static void iniciar() {
		em = JPAUtil.createEntityManager();
		usuarioPerfilDAO = new UsuarioPerfilDAO(em);
		usuarioPerfilService = new UsuarioPerfilService(usuarioPerfilDAO);
		usuarioDAO = new UsuarioDAO(em);
		perfilDAO = new PerfilDAO(em);
	}
	
}
