package br.com.pagga.chamado.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.model.Chamado;
import br.com.pagga.chamado.model.TipoChamado;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class ChamadoDAOTest {

	private static ChamadoDAO chamadoDAO;
	private static UsuarioDAO usuarioDAO;
	private static EntityManager entityManager;
	private static Chamado chamado;
	
	private static long idChamado;
	
	public static void main(String[] args) {
		
		conectar();
		
		entityManager.getTransaction().begin();
		
		try {
			buscaPorTipo();
			
			buscarTodosOsChamados();
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new BusinessException("Erro executando testes | " + e.getMessage());
		}finally {
			entityManager.close();
		}
	}
	
	public static void conectar() {
		
		entityManager = JPAUtil.createEntityManager();
		usuarioDAO = new UsuarioDAO(entityManager);
		chamadoDAO = new ChamadoDAO(entityManager);
		
	}
	
	public static void criarChamado() {
		
		UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);
		Usuario usuario = usuarioDAO.findById(2);
		
		chamado = Chamado.create("Problemas Monitor", "Por favor poderiam verificar", 
				TipoChamado.SOLICITACAO, usuario);
		
	}
	
	public static void salvandoChamado() {
		
		chamadoDAO.save(chamado);
		
		if(chamado.getId() != null || chamado.getId() > 0) {
			System.out.println("Chamado aberto com sucesso!" + chamado.getId());
			idChamado = chamado.getId();
		}else {
			throw new BusinessException("Algo ocorreu ao abrir este chamado!");
		}
	}

	public static void buscarChamadoPorID() {
		Chamado chamadoFindID = chamadoDAO.findById(idChamado);
		
		if(chamadoFindID == null)
			throw new BusinessException("Id não encontrado");
		else
			System.out.println(chamadoFindID);
		
	}
	
	public static void buscarTodosOsChamados() {
		
		List<Chamado> chamados = chamadoDAO.findAll();
		
		if(chamados.isEmpty())
			throw new BusinessException("Não foram encontrados nenhum chamado!");
		else
			chamados.stream().forEach(System.out::println);
	}
	
	public static void buscaPorTipo() {
		
		List<Chamado> chamados = chamadoDAO.findByTipo(chamado.getTipo());
		
		if(chamados.isEmpty())
			throw new BusinessException("Não foram encontrados nenhum chamado!");
		else
			chamados.stream().forEach(System.out::println);
		
	}
}
