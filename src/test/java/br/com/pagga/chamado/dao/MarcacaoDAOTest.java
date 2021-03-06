package br.com.pagga.chamado.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.model.Chamado;
import br.com.pagga.chamado.model.MarcacaoChamado;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class MarcacaoDAOTest {

	private static EntityManager entityManager;
	private static MarcacaoChamadoDAO marcacaoChamadoDAO;
	
	public static void main(String[] args) {
		
		conectar();
		
		entityManager.getTransaction().begin();
		
		buscarPorDescricaoEspecifica();
		
		try {
			/*estanciarMarcacaoAoChamado();
			buscarTodasAsMarcacoes();*/
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		
	}
	
	public static void conectar() {
		
		entityManager = JPAUtil.createEntityManager();
		marcacaoChamadoDAO = new MarcacaoChamadoDAO(entityManager);
		
	}
	
	/*public static void estanciarMarcacaoAoChamado() {
		
		ChamadoDAO chamadoDAO = new ChamadoDAO(entityManager);
		Chamado chamado = chamadoDAO.findById(1);
		
		MarcacaoChamado marcacaoChamado1 = MarcacaoChamado.create("TI", chamado);
		
		MarcacaoChamado marcacaoChamado2 = MarcacaoChamado.create("#CadeMeusPontos", chamado); 
		
		MarcacaoChamado marcacaoChamado3 = MarcacaoChamado.create("#QUeroCafeMais", chamado);
		
		MarcacaoChamado marcacaoChamado4 = MarcacaoChamado.create("Beneficios", chamado);
		
		marcacaoChamadoDAO.save(marcacaoChamado1);
		marcacaoChamadoDAO.save(marcacaoChamado2);
		marcacaoChamadoDAO.save(marcacaoChamado3);
		marcacaoChamadoDAO.save(marcacaoChamado4);
		
		
	}*/
	
	public static void buscarTodasAsMarcacoes() {
		
		List<MarcacaoChamado> marcacoes = marcacaoChamadoDAO.findAll();
		
		if(marcacoes.isEmpty())
			throw new BusinessException("Erro ao buscar por todas as Marcacoes!");
		else
			marcacoes.stream().forEach(System.out::println);
		
	}
	
	public static void buscarPorDescricaoEspecifica() {
		
		List<MarcacaoChamado> marcacaoByDescricoes = marcacaoChamadoDAO.findByDescricao("#");
		
		if(marcacaoByDescricoes.isEmpty())
			throw new BusinessException("Erro ao buscar a marcação informada!");
		else
			marcacaoByDescricoes.stream().forEach(System.out::println);
		
	}
	
	
}
