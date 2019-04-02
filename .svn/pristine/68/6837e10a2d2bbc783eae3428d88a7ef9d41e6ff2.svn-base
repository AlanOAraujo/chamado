package br.com.pagga.chamado.model;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.dao.UsuarioDAO;
import br.com.pagga.chamado.service.UsuarioService;
import br.com.pagga.chamado.util.JPAUtil;

@SuppressWarnings("unused")
public class UsuarioTest {

	public static void main(String[] args) {

		EntityManager entityManager = JPAUtil.createEntityManager();

		Usuario usuario = new UsuarioDAO(entityManager).findById(1);
		
		entityManager.close();
		
		entityManager = JPAUtil.createEntityManager();
		
		UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);

		//UsuarioService usuarioService = new UsuarioService(usuarioDAO);
		
		
		/***********************Criar novo Usuario******************************/
		
		/*usuario = Usuario.create("Bob", "1111111", "123456", "bobo@bbo");
		
		entityManager.getTransaction().begin();
		
		try {
				
			usuarioService.alterarUsuario(usuario);

			entityManager.getTransaction().commit();
		}
		catch ( Exception ex ) {

			ex.printStackTrace();

			entityManager.getTransaction().rollback();
		}
		*/
		
		/************************************************************************/
		
		/*************************Alterar Usuario*****************************/
		
		/*usuario.setCpf("121321321321321321");

		entityManager.getTransaction().begin();

		try {

			usuarioService.alterarUsuario(usuario);

			entityManager.getTransaction().commit();
		}
		catch ( Exception ex ) {

			ex.printStackTrace();

			entityManager.getTransaction().rollback();
		}*/

		/****************************************************************************/
		
		/***************************Alterar Situação*********************************/
		
		/*usuario = usuarioDAO.findById(1);
		
		entityManager.getTransaction().begin();
		
		try {
				
			String ativo = req.getParam("sit");
			
				if ativo
					usuario.ativa
				else
					usuario.inativa
				
			usuarioService.situacaoUsuario(usuario);

			entityManager.getTransaction().commit();
		}
		catch ( Exception ex ) {

			ex.printStackTrace();

			entityManager.getTransaction().rollback();
		}
		*/
		/****************************************************************************/
	}
}
