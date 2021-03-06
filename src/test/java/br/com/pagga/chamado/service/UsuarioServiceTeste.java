package br.com.pagga.chamado.service;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.dao.UsuarioDAO;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class UsuarioServiceTeste {
	private static UsuarioService usuarioService;
	private static EntityManager em;
	
	public static void main (String args[]) {
		iniciar();
		em.getTransaction().begin();
		try {
//		cadastraUsuarioTeste();
//		alteraUsuarioTeste();
//		ativaUsuarioTeste();
		inativaUsuarioTeste();
		em.getTransaction().commit();
		} catch (Exception e) {
			throw new BusinessException("Erro executando teste...");
		} finally {
			em.close();
		}
		
	}
	
	private static void cadastraUsuarioTeste () {
		Usuario usuario;
		
		usuario = Usuario.create("Nome do Usuário", "38200426874", "221445", "email0@mail");	//Sucesso
//		usuarioService.cadastraUsuario(usuario);
//		
//		usuario = Usuario.create(null, "38200426874", "221445", "email1@mail");					//Sem nome
//		usuarioService.cadastraUsuario(usuario);
//		
//		usuario = Usuario.create("Nome do Usuário", null, "221445", "email2@mail");				//Sem CPF
//		usuarioService.cadastraUsuario(usuario);
//		
//		usuario = Usuario.create("Nome do Usuário", "38200426874", "221445", null);				//Sem email
//		usuarioService.cadastraUsuario(usuario);
//		
//		usuario = Usuario.create("Nome do Usuário", "22222222222", "221445", "email3@mail");	//CPF Invalido
//		usuarioService.cadastraUsuario(usuario);
//		
//		usuario = Usuario.create("Nome do Usuário", "22222222222", "221445", "email4@mail");	//CPF Já Cadastrado
//		usuarioService.cadastraUsuario(usuario);
//		
//		usuario = Usuario.create("Nome do Usuário", "38200426874", "221445", "email0@mail");	//Email Já Cadastrado
//		usuarioService.cadastraUsuario(usuario);
	}
	
	private static void alteraUsuarioTeste () {
		Usuario usuario = new UsuarioDAO(JPAUtil.createEntityManager()).findByCPF("38200426874");
		
		usuario.setNome("Nome Alterado");					// Sucesso
		
		usuario.setCpf("35523165464");						// CPF Alterado
		
		usuarioService.alterarUsuario(usuario);
	}
	
	private static void ativaUsuarioTeste () {
		Usuario usuario = new UsuarioDAO(JPAUtil.createEntityManager()).findByCPF("38200426874");
		usuarioService.ativaUsuario(usuario);						// Sucesso ???
	}
	
	private static void inativaUsuarioTeste () {
		Usuario usuario = new UsuarioDAO(JPAUtil.createEntityManager()).findByCPF("38200426874");
		usuarioService.inativaUsuario(usuario);					// Sucesso ???
	}
	
	private static void iniciar () {
		em = JPAUtil.createEntityManager();
		UsuarioDAO usuarioDAO = new UsuarioDAO(em);
		//usuarioService = new UsuarioService(usuarioDAO);
	}
	
}
