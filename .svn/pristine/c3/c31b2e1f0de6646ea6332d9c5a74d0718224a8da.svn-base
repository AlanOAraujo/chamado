package br.com.pagga.chamado.model;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.dao.ChamadoDAO;
import br.com.pagga.chamado.dao.HistoricoChamadoDAO;
import br.com.pagga.chamado.dao.MarcacaoChamadoDAO;
import br.com.pagga.chamado.dao.UsuarioDAO;
import br.com.pagga.chamado.service.ChamadoService;
import br.com.pagga.chamado.util.JPAUtil;

public class ChamadoTest {

	public static void main(String[] args) {
		
		EntityManager entityManager = JPAUtil.createEntityManager();
		
		//ChamadoDAO chamadoDAO = new ChamadoDAO(entityManager);
		
		HistoricoChamadoDAO historicoChamadoDAO = new HistoricoChamadoDAO(entityManager);

		MarcacaoChamadoDAO marcacaoChamadoDAO = new MarcacaoChamadoDAO(entityManager);
		
		//ChamadoService chamadoService = new ChamadoService(chamadoDAO,marcacaoChamadoDAO,historicoChamadoDAO);
		
		
		/*******************Criar Chamado******************************/
		
		
		UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);
		
		Usuario usuario = usuarioDAO.findById(3);
		
		Chamado chamado = Chamado.create("Enviando Anexo", "Socorro", TipoChamado.SOLICITACAO, usuario);
		
		//chamado.getMarcacaoChamadoList().equals(o)
		
		entityManager.getTransaction().begin();
		
		try {
			
			//chamadoService.aberturaChamado(chamado);
			entityManager.getTransaction().commit();
			
		}catch ( Exception ex ) {

			ex.printStackTrace();

			entityManager.getTransaction().rollback();
		}
		
		
		/**************************************************************/
	}
}

