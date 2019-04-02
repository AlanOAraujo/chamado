package br.com.pagga.chamado.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.model.Atributo;
import br.com.pagga.chamado.model.Rotina;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class AtributoDAOTest {

	private static EntityManager entityManager;
	private static AtributoDAO atributoDAO;
	private static Atributo atributo;
	
	private static long idAtributo;
	
	public static void main(String[] args) {
		
		conectar();
		
		entityManager.getTransaction().begin();

		try {
			
			estanciandoObjetoAtributo();
			
			salvandoAtributo();
			
			buscarAtributoPorCodigo();

			buscarAtributoPorID();
			
			buscarAtributoPorName();
			
			buscaTodosOsAtributos();
			
			removerAtributo();
			
			buscaTodosOsAtributos();
			
			entityManager.getTransaction().commit();
		}catch (Exception ex) {
			
			entityManager.getTransaction().rollback();
			throw new BusinessException("Erro executando testes | " + ex.getMessage());
		
		}finally {

			entityManager.close();
			
		}
		
	}
	
	public static void conectar() {
		
		entityManager = JPAUtil.createEntityManager();
		atributoDAO = new AtributoDAO(entityManager);
	}
	
	public static void estanciandoObjetoAtributo() {
		RotinaDAO rotinaDAO = new RotinaDAO(entityManager);
		Rotina rotina = rotinaDAO.findById(2);
		atributo = Atributo.createAtributo("Cachorro louco", "CCR_LUC", rotina);
	}
	
	public static void salvandoAtributo() {
		
		atributoDAO.save(atributo);
		
		if(atributo.getId() != null || atributo.getId() > 0) {
			System.out.println("Atributo criado com sucesso " + atributo);
			idAtributo = atributo.getId();
		} else {
			throw new BusinessException("Algo deu errado ao salvar atributo");
		}
		
	}
	
	public static void removerAtributo() {
		
		Atributo atributoRemove = atributoDAO.findById(idAtributo);
		
		atributoDAO.remove(atributoRemove);
		
		if(atributoDAO.findById(idAtributo) != null)
			throw new BusinessException("Algo deu errado ao remover o Atributo");
		else
			System.out.println("Atributo Excluida");
		
	}
	
	public static void buscarAtributoPorID() {
		
		Atributo atributoFindId = atributoDAO.findById(idAtributo);
		
		if(atributoFindId == null) 
			throw new BusinessException("ID não encontrado!");
		else	
			System.out.println("ID Do ATRIBUTO: " + atributoDAO.findById(idAtributo));
		}
	
	public static void buscarAtributoPorCodigo() {
		
		String atributoBuscaCod = atributo.getCodAtributo();
		
		if(atributoBuscaCod == "")
			throw new BusinessException("Codigo não informado por favor verificar");
		else
			System.out.println(atributoDAO.findByCod(atributoBuscaCod));
		
	}
	
	public static void buscarAtributoPorName() {
		
		String atributoBuscaPorNome = atributo.getNomeAtributo();
		
		if(atributoBuscaPorNome == "")
			throw new BusinessException("Nome não informado por favor verificar");
		else
			System.out.println(atributoDAO.findByNome(atributoBuscaPorNome));
	}
	
	public static void buscaTodosOsAtributos() {
		
		List<Atributo> atributos = atributoDAO.findAll();
		
		if(atributos.isEmpty())
			throw new BusinessException("Atributos não encontrado");
		else	
			atributos.stream().forEach(System.out::println);
	}
	
}
