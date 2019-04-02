package br.com.pagga.chamado.dao;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.pagga.chamado.model.Chamado;
import br.com.pagga.chamado.model.HistoricoChamado;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.util.JPAUtil;

public class HistoricoDAOTest {

		private static EntityManager entityManager;
		private static HistoricoChamadoDAO historicoChamadoDAO;
		private static HistoricoChamado historicoChamado;
		
		public static void main(String[] args) {
			
			//conectar();
			
			entityManager.getTransaction().begin();
			
			try {
				estanciarHistoricoChamado();
				
				salvarHistorico();
				
				buscarPorTodosOsHistoricos();
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				entityManager.getTransaction().rollback();
			} finally {
				entityManager.close();
			}
			
		}
		
		public static void conectar() {
			
			entityManager = JPAUtil.createEntityManager();
			historicoChamadoDAO = new HistoricoChamadoDAO(entityManager);
			
		}
		
		public static void estanciarHistoricoChamado() {
			
			//ChamadoDAO chamadoDAO = new ChamadoDAO(entityManager);
			UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);
			
			//Chamado chamado = chamadoDAO.findById(1);
			Usuario usuario = usuarioDAO.findById(1);
			
			String comentario = "Segue Anexo as evidências Necessarias.";
			
			//historicoChamado = HistoricoChamado.create(chamado, comentario, usuario);
			
			anexarArquivosAoHistorico();
			
		}
		
		public static void anexarArquivosAoHistorico() {
			
			FileInputStream fis = null;
			
			try {
				File file = new File("C:\\Java\\workspace\\sistemachamados\\chamado\\src\\test\\java\\br\\com\\pagga\\chamado\\dao\\MarcacaoDAOTest.java");
				historicoChamado.setAnexo(new byte[(int) file.length()]);
				
				fis = new FileInputStream(file);
				fis.read(historicoChamado.getAnexo());
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(fis != null) {
					try {
						fis.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	public static void salvarHistorico() {
		
		historicoChamadoDAO.save(historicoChamado);
		
	}
	
	public static void buscarPorTodosOsHistoricos() {
		List<HistoricoChamado> historicos = historicoChamadoDAO.findAll();
		
		if(historicos.isEmpty())
			throw new BusinessException("Não à historicos a serem encontrados!");
		else
			historicos.stream().forEach(System.out::println);
	}
	
}
