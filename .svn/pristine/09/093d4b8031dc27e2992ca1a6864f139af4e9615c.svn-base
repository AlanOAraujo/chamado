package br.com.pagga.chamado.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class UsuarioDAOTest {
	
	private static EntityManager em;
	private static UsuarioDAO usuarioDAO;
	
	private static long idSalvo;

	public static void main(String[] args) {
		conectar();
		
		em.getTransaction().begin();
		try {
			//testePagination();
			testeFindAll();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new BusinessException("Erro executando testes | " + e.getMessage());
		} finally {
			em.close();
		}
	}
	
	private static void testeSave() {
		Usuario usuario = Usuario.create("Bruce", "30200426874", "221445", "bruce@mail");
		
		usuarioDAO.save(usuario);
		
		if ( usuario.getId() != null && usuario.getId() > 0 ) {
			System.out.println( "usuário salvo com sucesso" );
			idSalvo = usuario.getId();
		}
		else {
			throw new BusinessException("Problemas ao salvar o usuario");
		}
	}
	
	private static void testeFindById() {
		Usuario usuario = usuarioDAO.findById(idSalvo);
		if(usuario == null) {
			throw new BusinessException("Usuario não encontrado!");
		}
		System.out.println("Usuario encontrado!");
	}
	
	private static void testeFindAll() {
		List<Usuario> usuarios = usuarioDAO.findAll();
		
		if ( usuarios.isEmpty() ) {
			throw new BusinessException("Falha ao localizar usuarios");
		}
		
		System.out.println(usuarios.size() + "usuário(s) encontrado(s)");
	}
	
	private static void testeRemove() {
		Usuario usuario = usuarioDAO.findById(idSalvo);
		usuarioDAO.remove(usuario);
		
		if(usuarioDAO.findById(idSalvo) != null) {
			throw new BusinessException("Falha, usuário não removido!");
		}
		
		System.out.println("Usuário removido!");
	}
	
	
	/*private static void testePagination() {
		List<Usuario> usuarios = usuarioDAO.listUsuario(2,2,"alan");
		
		if ( usuarios.isEmpty() ) {
			throw new BusinessException("Falha ao localizar usuarios");
		}
		
		System.out.println(usuarios.size() + " usuário(s) encontrado(s) na paginação");
	}*/
	
//	private static void testeRemoveById() {
//		List<Usuario> usuarios = usuarioDAO.findAll();
//		usuarioDAO.removeById(usuarios.get(usuarios.size() - 1).getId());
//		System.out.println("Usuário " + usuarios.get(usuarios.size() - 1).getNome() + " removido...");
//		
////		usuarioDAO.removeById(Long.valueOf(12));
//	}
	
	private static void conectar() {
		em = JPAUtil.createEntityManager();
		usuarioDAO = new UsuarioDAO(em);
	}
	
}
