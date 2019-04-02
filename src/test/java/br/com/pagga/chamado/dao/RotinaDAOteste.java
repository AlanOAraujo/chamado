package br.com.pagga.chamado.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.model.Rotina;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class RotinaDAOteste {
	
	private static EntityManager entityManager;
	private static RotinaDAO rotinaDAO;
	private static Rotina rotina;
	
	private static long idRotina;
	
	public static void main(String[] args) {
		
		conectar();
		
		entityManager.getTransaction().begin();
		
		try {
			estanciandoObjetoRotina();
			
			salvandoRotina();
			
			buscarRotinaPorID();
			
			buscandoRotinaPorNome();
			
			buscaPorTodasAsRotinasDaBaseDeDados();
			
			removerRotina();
			
			buscaPorTodasAsRotinasDaBaseDeDados();
			
			entityManager.getTransaction().commit();
			
		} catch (Exception ex) {
			entityManager.getTransaction().rollback();
			throw new BusinessException("Erro executando testes | " + ex.getMessage());
		}finally {
			entityManager.close();
		}
		
	}
	
	public static void conectar() {
		entityManager = JPAUtil.createEntityManager();
		rotinaDAO = new RotinaDAO(entityManager);
	}
	
	public static void estanciandoObjetoRotina() {
		rotina = Rotina.createRotina("Cadastrar Urgêrcias");
	}
	
	public static void salvandoRotina() {

		rotinaDAO.save(rotina);
		
		if(rotina.getId() != null && rotina.getId() > 0) {
			System.out.println("Rotina Salva com sucesso!");
			idRotina = rotina.getId();
		}else {
			throw new BusinessException("Problemas ao salvar esta Rotina!");
		}
	}
	
	public static void removerRotina() {
		
		Rotina rotinaRemover = rotinaDAO.findById(idRotina);
		
		rotinaDAO.remove(rotinaRemover);
		
		if(rotinaDAO.findById(idRotina) != null)
			throw new BusinessException("Algo deu errado ao remover a Rotina");
		else
			System.out.println("Rotina Excluida");
	}
	
	public static void buscandoRotinaPorNome() {
		if(rotina.getNome() == "")
			throw new BusinessException("Não possui nome, por favor verificar!");
		else	
			System.out.println("NOME DO ROTINA: " + rotinaDAO.findByName(rotina.getNome()));
	}
	
	public static void buscarRotinaPorID() {
		
		Rotina rotinaFindID = rotinaDAO.findById(idRotina);
		
		if(rotinaFindID == null || rotina.getId() <= 0)
			throw new BusinessException("ID não encontrado!");
		else	
			System.out.println("ID DA ROTINA: " + rotinaDAO.findById(rotina.getId()));
	}
	
	public static void buscaPorTodasAsRotinasDaBaseDeDados() {
		List<Rotina> rotinas = rotinaDAO.findAll();
		
		if(rotinas.isEmpty())
			throw new BusinessException("Erro ao apresentar todas as rotinas");
		else
			rotinas.stream().forEach(System.out::println);
	}
	
}
