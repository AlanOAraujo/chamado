package br.com.pagga.chamado.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.model.Atributo;
import br.com.pagga.chamado.model.Perfil;
import br.com.pagga.chamado.model.PerfilRotinaAtributo;
import br.com.pagga.chamado.model.Rotina;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class PerfilRotinaAtributoDAOTest {
	
	private static PerfilRotinaAtributoDAO perfilRotinaAtributoDAO;
	private static EntityManager entityManager;
	private static PerfilRotinaAtributo perfilRotinaAtributo;
	
	private static long idPerfilRotinaAtributo;
	
	public static void main(String[] args) {
		
		conectar();
		
		entityManager.getTransaction().begin();
		
		try {
			
			estanciandoObjetoPerfilRotinaAtributo();
			
			salvandoPerfilRotinaAtributo();
			
			buscandoTodosPerfilRotinaAtributo();
			
			removerPerfilRotinaAtributo();

			buscandoTodosPerfilRotinaAtributo();
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.getMessage();
			entityManager.getTransaction().rollback();		
		}
	}

	public static void conectar() {
		entityManager = JPAUtil.createEntityManager();
		perfilRotinaAtributoDAO = new PerfilRotinaAtributoDAO(entityManager);
	}
	
	public static void estanciandoObjetoPerfilRotinaAtributo() {
		
		PerfilDAO perfilDAO = new PerfilDAO(entityManager);
		RotinaDAO rotinaDAO = new RotinaDAO(entityManager);
		AtributoDAO atributoDAO = new AtributoDAO(entityManager);
		
		Perfil perfil = perfilDAO.findById(1);
		Rotina rotina = rotinaDAO.findById(1);
		Atributo atributo = atributoDAO.findById(1);
		
		perfilRotinaAtributo = PerfilRotinaAtributo.createPerfilRotinaAtributo(perfil, rotina, atributo);
		
	}
	
	public static void salvandoPerfilRotinaAtributo() {
		
		perfilRotinaAtributoDAO.save(perfilRotinaAtributo);
		
		if ( perfilRotinaAtributo.getId() != null && perfilRotinaAtributo.getId() > 0 ) {
			System.out.println( "Perfil_Rotina_Atributo salvo com sucesso" + perfilRotinaAtributo);
			idPerfilRotinaAtributo = perfilRotinaAtributo.getId();
		}
		else {
			throw new BusinessException("Problemas ao salvar o Perfil_Rotina_Atributo");
		}
		
	}
	
	public static void removerPerfilRotinaAtributo() {
		
		PerfilRotinaAtributo perfilRotinaAtributoRemover = perfilRotinaAtributoDAO.findById(idPerfilRotinaAtributo);
		
		perfilRotinaAtributoDAO.remove(perfilRotinaAtributoRemover);
		
		if (perfilRotinaAtributoDAO.findById(idPerfilRotinaAtributo) != null) {
			throw new BusinessException("Algo deu errado ao excluir este relacionamento");
		} else {
			System.out.println("Relacionamento Excluido com sucesso!");

		}
		
		System.out.println("Perfil_Rotina_Atributo removido");
		
	}
	
	public static void removerPerfilRotinaAtributoPorPerfil() {
		PerfilDAO perfilDAO = new PerfilDAO(entityManager);
		Perfil perfil = perfilDAO.findById(4);
		perfilRotinaAtributoDAO.removeByPerfil(perfil);
		
	}
	
	public static void buscandoTodosPerfilRotinaAtributo() {
		List<PerfilRotinaAtributo> list = perfilRotinaAtributoDAO.findAll();
		
		if(list.isEmpty())
			throw new BusinessException("Não foi encontrado nenhum Perfil_Rotina");
		else	
			list.stream().forEach(System.out::println);
	}
	
	public static void buscandoPerfilRotinaAtributoPorID() {
		
		PerfilRotinaAtributo perfilRotinaAtributoFindID = perfilRotinaAtributoDAO.findById(idPerfilRotinaAtributo);
		
		if(perfilRotinaAtributoFindID == null) 
			throw new BusinessException("ID não encontrado!");
		else	
			System.out.println("ID Do ATRIBUTO: " + perfilRotinaAtributoDAO.findById(perfilRotinaAtributo.getId()));
	}
	
}
