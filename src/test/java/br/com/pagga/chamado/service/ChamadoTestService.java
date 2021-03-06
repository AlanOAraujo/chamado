package br.com.pagga.chamado.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.dao.ChamadoDAO;
import br.com.pagga.chamado.dao.HistoricoChamadoDAO;
import br.com.pagga.chamado.dao.MarcacaoChamadoDAO;
import br.com.pagga.chamado.dao.UsuarioDAO;
import br.com.pagga.chamado.model.Chamado;
import br.com.pagga.chamado.model.HistoricoChamado;
import br.com.pagga.chamado.model.MarcacaoChamado;
import br.com.pagga.chamado.model.StatusChamado;
import br.com.pagga.chamado.model.TipoChamado;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class ChamadoTestService {
	
	private static ChamadoService chamadoService;
	private static ChamadoDAO chamadoDAO;
	private static MarcacaoChamadoDAO marcacaoChamadoDAO;
	private static HistoricoChamadoDAO historicoChamadoDAO;
	private static EntityManager entityManager; 
	private static Chamado chamado;
	
	public static void main(String[] args) {
		
		
		entityManager = JPAUtil.createEntityManager();
		iniciar();
		entityManager.getTransaction().begin();
		
		try {
			listaChamado();
//			listaChamadoPorUsuario();
//			listaChamadoPorStatus();
			abrirChamado();
//			comentarioChamado();
//			mudarStatus();
//			carregarAnexo();
//			listaChamadoPorStatus();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			
			entityManager.getTransaction().rollback();
			e.getMessage();
				
		}finally {
			entityManager.close();
		}
		
	}
	
	public static void abrirChamado() {
		
		chamadoDAO = new ChamadoDAO(entityManager);
		MarcacaoChamadoDAO marcacaoChamadoDAO = new MarcacaoChamadoDAO(entityManager);
		HistoricoChamadoDAO historicoChamadoDAO = new HistoricoChamadoDAO(entityManager);
		
		UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);
		
		Usuario usuario = usuarioDAO.findById(1);
		
		chamado = Chamado.create("09 de Janeiro", "Estamos efetuando os anexos para efetuar a evidências", TipoChamado.SOLICITACAO, 
				usuario);
		
		List<MarcacaoChamado> marcacoes = new ArrayList<MarcacaoChamado>();
		
		MarcacaoChamado marcacaoChamado1 = MarcacaoChamado.create("52", chamado);
		marcacoes.add(marcacaoChamado1);
		
		MarcacaoChamado marcacaoChamado2 = MarcacaoChamado.create("#50ponto", chamado); 
		marcacoes.add(marcacaoChamado2);
		
		MarcacaoChamado marcacaoChamado3 = MarcacaoChamado.create("#Acabou", chamado);
		marcacoes.add(marcacaoChamado3);
		
		MarcacaoChamado marcacaoChamado4 = MarcacaoChamado.create("Beneficios_Alelo", chamado);
		marcacoes.add(marcacaoChamado4);
		
		chamado.setMarcacaoChamadoList(marcacoes);
		
		chamadoService = new ChamadoService(chamadoDAO, marcacaoChamadoDAO, historicoChamadoDAO);
		
		chamadoService.aberturaChamado(chamado);
	}
	
	public static void carregarAnexo() {

		File file = new File("C:\\Java\\workspace\\sistemachamados\\chamado\\src\\test\\java\\br\\com\\pagga\\chamado\\dao\\MarcacaoDAOTest.java");
		
		InputStream inputStream;
		
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new BusinessException("Erro ao carregar arquivo" + e.getMessage());
		}
		
		HistoricoChamado historicoChamado = HistoricoChamado.create(chamado, "Anexando evidências", chamado.getUsuario());
		
		historicoChamado.setChamado(chamado);
		
		historicoChamado.setAnexo(new byte[(int) file.length()]);
		
		chamadoService.salvaHistoricoChamado(historicoChamado);
		
	}
	
	private static void listaChamado() {
		List<Chamado> chamados = chamadoService.listaChamado();
		for(Chamado chamado : chamados) {
			System.out.println("Chamado.id: " +chamado.getId());
		}
	}
	
	private static void mudarStatus() {
		
		Chamado chamadoStatus = chamadoDAO.findById(5);
		
		chamadoStatus.setStatus(StatusChamado.FECHADO);
		
	}
	
	private static void comentarioChamado() {
		
		Chamado chamadoComentario = chamadoDAO.findById(1);
		
		UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);
		
		Usuario usuario = usuarioDAO.findById(2);
		
		HistoricoChamado historicoChamado = HistoricoChamado.create(chamadoComentario, 
				"Chamado finalizado", 
				usuario);
		
		chamadoService.acrescentarComentarioChamado(historicoChamado);
		
	}
	
	private static void listaChamadoPorUsuario() {
		UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);
		Usuario usuario = usuarioDAO.findById(1l);
		List<Chamado> chamados = chamadoService.listaChamadoPorUsuario(usuario);
		for(Chamado chamado : chamados) {
			System.out.println("Chamado.id: " + chamado.getId());
		}
	}
	
	private static void listaChamadoPorStatus() {
		List<Chamado> chamados = chamadoService.buscaPorStatus(StatusChamado.FECHADO);
		for(Chamado chamado : chamados) {
			System.out.println("Chamado: " + chamado.getId() + " Status:" + chamado.getStatus() );
		}
	}
	
	private static void iniciar() {
		entityManager = JPAUtil.createEntityManager();
		chamadoDAO = new ChamadoDAO(entityManager);
		marcacaoChamadoDAO = new MarcacaoChamadoDAO(entityManager);
		historicoChamadoDAO = new HistoricoChamadoDAO(entityManager);
		chamadoService = new ChamadoService(chamadoDAO, marcacaoChamadoDAO, historicoChamadoDAO);
	}
}
