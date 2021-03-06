package br.com.pagga.chamado.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.pagga.chamado.dao.HistoricoChamadoDAO;
import br.com.pagga.chamado.model.Chamado;
import br.com.pagga.chamado.model.HistoricoChamado;
import br.com.pagga.chamado.model.MarcacaoChamado;
import br.com.pagga.chamado.model.StatusChamado;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.security.UsuarioLogado;
import br.com.pagga.chamado.service.ChamadoService;
import br.com.pagga.chamado.service.exception.BusinessException;

@Controller
@Path("/chamado")
public class ChamadoController extends PaggaController {
	
	@Inject
	private HistoricoChamadoDAO historicoChamadoDAO;
	
	private ChamadoService chamadoService;
	
	@Inject
	private UsuarioLogado usuarioLogado;

	private static final Logger logger = LoggerFactory.getLogger(ChamadoController.class);
	
	public ChamadoController() {}
	
	@Inject
	public ChamadoController(HttpServletRequest request, HttpServletResponse response, Result result,
			EntityManager entityManager, ChamadoService chamadoService) {
		super(request, response, result, entityManager);
		this.chamadoService = chamadoService;
	}
	
	@Get
	@Path("")
	public void listaChamado() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			List<Chamado> findAll = chamadoService.listaChamado();
			List<JSONObject> jsonObjList = new ArrayList<>();
			
			if(findAll == null || findAll.isEmpty()) {
				createJSON().message("Não foram encontrados chamados").notFound();
			}else{
				
				for (Chamado chamado : findAll) {
					
					List<JSONObject> jsonObjListHistorico = new ArrayList<>();
					
					if(chamado.getHistoricoChamadoList() != null && chamado.getHistoricoChamadoList().size() > 0) {
						
						for (HistoricoChamado historicoChamado : chamado.getHistoricoChamadoList()) {
							JSONObject historico = createJSON()
									.put("id", historicoChamado.getId())
									.put("comentario", historicoChamado.getComentario())
									.put("anexo",historicoChamado.getAnexo())
									.put("usuario", historicoChamado.getUsuario().getId())
									.put("dataComentario", sdf.format(historicoChamado.getDataComentario()))
									.build();
							
							jsonObjListHistorico.add(historico);
						}
					}
					
					
					JSONObject jsonObject = createJSON()
					.put("id", chamado.getId())
					.put("usuario", chamado.getUsuario().getNome())
					.put("titulo", chamado.getTitulo())
					.put("status", chamado.getStatus().getDescricao())
					.put("tipo", chamado.getTipo().getDescricao())
					.put("data", sdf.format(chamado.getDataAbertura()))
					.put("historico", jsonObjListHistorico)
					.build();
					
					jsonObjList.add(jsonObject);
				}
				createJSON().put("chamados", jsonObjList)
				.ok();
			}
			
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao listar chamados").serverError(); 
		}
	}
	
	@Post
	@Path("/id")
	public void buscaPorId(long id) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		SimpleDateFormat sdfNoHours = new SimpleDateFormat("dd/MM/yyyy");

		try {
			Chamado chamado = chamadoService.buscaPorID(id);

			if (chamado == null) {
				createJSON().message("Chamado não encontrado.").notFound();
			} else {
				List<JSONObject> jsonObjListHistorico = new ArrayList<>();
				if (chamado.getHistoricoChamadoList() != null && chamado.getHistoricoChamadoList().size() > 0) {

					for (HistoricoChamado historicoChamado : chamado.getHistoricoChamadoList()) {

						JSONObject historico = createJSON().put("id", historicoChamado.getId())
								.put("comentario", historicoChamado.getComentario())
								.put("anexo", historicoChamado.getAnexo())
								.put("usuario", historicoChamado.getUsuario().getId())
								.put("dataComentario", sdf.format(historicoChamado.getDataComentario())).build();

						jsonObjListHistorico.add(historico);
					}
				}
				
				List<JSONObject> jsonObjListMarcacao = new ArrayList<>();
				if (chamado.getMarcacaoChamadoList() != null && chamado.getMarcacaoChamadoList().size() > 0) {

					for (MarcacaoChamado marcacao : chamado.getMarcacaoChamadoList()) {

						JSONObject marcacaoChamado = createJSON().put("id", marcacao.getId())
								.put("descricao", marcacao.getDescricao()).build();

						jsonObjListMarcacao.add(marcacaoChamado);
					}
				}
				
				JSONObject jsonObjectUsuario = createJSON().put("id", chamado.getUsuario().getId())
						.put("nome", chamado.getUsuario().getNome())
						.build();
				
				JSONObject jsonObjectUsuarioFechamento = null;
				
				if(chamado.getUsuarioFechamento() != null) {
					jsonObjectUsuarioFechamento = createJSON().put("id", chamado.getUsuarioFechamento().getId())
						.put("nome", chamado.getUsuarioFechamento().getNome())
						.build();
				}
				
				JSONObject jsonObjectChamado = createJSON().put("id", chamado.getId())
						.put("dataAbertura", sdf.format(chamado.getDataAbertura()))
						.put("dataFechamento", chamado.getDataFechamento() == null ? " " : sdf.format(chamado.getDataFechamento()))
						.put("dataPrevisao", chamado.getDataPrevisao() == null ? " " : sdfNoHours.format(chamado.getDataPrevisao()))
						.put("descricao", chamado.getDescricao())
						.put("titulo", chamado.getTitulo())
						.put("status", chamado.getStatus().getDescricao())
						.put("tipo", chamado.getTipo().getDescricao())
						.put("usuario", jsonObjectUsuario)
						.put("usuarioFechamento", chamado.getUsuarioFechamento() == null ? " " : jsonObjectUsuarioFechamento)
						.put("historico", jsonObjListHistorico)
						.put("marcacaoChamado", jsonObjListMarcacao)
						.build();
				createJSON().put("chamado", jsonObjectChamado).ok();
			}

		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao listar chamados").serverError();
		}
	}
	
	@Post
	@Path("")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void aberturaChamado( Chamado chamado) {
		
		try {
			chamadoService.aberturaChamado(chamado);
			
			createJSON().put("msg", "Ticket com o código " + chamado.getId() + " criado com sucesso! ")
						.put("idChamado", chamado.getId())
						.ok();
			
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			createJSON().failure(e.getMessage()).serverError();
		} catch (Exception e) {
			logger.error(e.getMessage());
			createJSON().failure("Erro ao abrir chamado!").serverError();
		}
		
	}
	
	@Post
	@Path("/historico")
	public void acrescentarComentario(long idChamado, UploadedFile file, String comentario, long idUsuario, String dataPrevisao) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {

			Chamado chamado = chamadoService.buscaPorID(idChamado);
			
			Usuario usuario = chamadoService.buscaUsarioID(idUsuario);
			
			if(!dataPrevisao.equals("NaN/NaN/NaN")) {
				chamado.setDataPrevisao(sdf.parse(dataPrevisao));
			}
			
			HistoricoChamado historicoChamado = HistoricoChamado.create(chamado, comentario, usuario);
			
			if(file == null) {
				
				historicoChamado.setAnexo(null);
				
			}else {
				
				historicoChamado.setNomeAnexo(file.getFileName().replaceAll(" ", ""));
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				IOUtils.copy(file.getFile(), baos);
				
				historicoChamado.setAnexo(baos.toByteArray());
				
				IOUtils.closeQuietly(baos);
				
			}
			
			historicoChamado.setDataComentario(new Date());
			
			chamadoService.acrescentarComentarioChamado(historicoChamado);
			
			createJSON().put("msg", "Comentário enviado com sucesso! ")
						.put("idChamado", chamado.getId())
						.ok();
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			createJSON().failure(e.getMessage()).serverError();
		} catch (Exception e) {
			logger.error(e.getMessage());
			createJSON().failure("Erro ao enviar comentário ao chamado!").serverError();
		}
		
	}
	
	@Post
	@Path("/upload")
	public void uploudFile(long idChamado, UploadedFile file) {
		try {
			
			Chamado chamado = chamadoService.buscaPorID(idChamado);
			
			HistoricoChamado historicoChamado = HistoricoChamado.create(chamado, "Anexo "+ file.getFileName() + " adicionado ao chamado " + chamado.getId(), chamado.getUsuario());
			
			historicoChamado.setNomeAnexo(file.getFileName().replaceAll(" ", ""));
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			IOUtils.copy(file.getFile(), baos);
			
			historicoChamado.setAnexo(baos.toByteArray());
			
			IOUtils.closeQuietly(baos);
			
			chamadoService.salvaHistoricoChamado(historicoChamado);
			
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			createJSON().failure("Erro ao anexar arquivo!").serverError();
		}
		
	}
	
	@Get
	@Path("/download/{idHistoricoChamado}")
	public void downloadFile(long idHistoricoChamado) {
		
		HistoricoChamado historicoChamadoDownload = chamadoService.buscaHistoricoID(idHistoricoChamado);
		
		byte[] anexo = historicoChamadoDownload.getAnexo();
		
		String fileName = historicoChamadoDownload.getNomeAnexo();
		
		response.setHeader("Content-disposition", "attachment; filename="+fileName+""); 
		response.setContentLength(anexo.length);
		
		try {
			 
			OutputStream output = response.getOutputStream();
	    	
			output.write(anexo , 0, anexo.length);
			
		} catch (IOException e) {
		
			e.printStackTrace();
			
		}
		
	}
	
	@Post
	@Path("/finalizar")
	public void finalizarChamado(long idChamado, String comentario, long idUsuario) {
		
		try {

			Chamado chamado = chamadoService.buscaPorID(idChamado);
			
			Usuario usuario = chamadoService.buscaUsarioID(idUsuario);
			
			chamado.setUsuarioFechamento(usuario);
			
			chamado.setDataFechamento(new Date());
			
			HistoricoChamado historicoChamado = HistoricoChamado.create(chamado, comentario, usuario);

			historicoChamado.setDataComentario(new Date());
			
			chamadoService.finalizandoChamado(historicoChamado);
			
			createJSON().put("msg", "Chamado Finalizado! ")
						.put("idChamado", chamado.getId())
						.ok();
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			createJSON().failure(e.getMessage()).serverError();
		} catch (Exception e) {
			logger.error(e.getMessage());
			createJSON().failure("Erro ao finalizar chamado!").serverError();
		}
		
	}
	
	@Post
	@Path("/status")
	public void buscaStatus(String status) {
		
		StatusChamado statusChamado = StatusChamado.valueOf(status);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			
			List<Chamado> chamadoStatus = chamadoService.buscaPorStatus(statusChamado);
			List<JSONObject> jsonObjList = new ArrayList<>();
			
			for (Chamado chamado : chamadoStatus) {
				
				JSONObject jsonObjectUsuario = createJSON().put("id", chamado.getUsuario().getId())
						.put("nome", chamado.getUsuario().getNome())
						.build();
				
				JSONObject jsonObjectUsuarioFechamento = null;
				
				if(chamado.getUsuarioFechamento() != null) {
					jsonObjectUsuarioFechamento = createJSON().put("id", chamado.getUsuarioFechamento().getId())
						.put("nome", chamado.getUsuarioFechamento().getNome())
						.build();
				}
				
				JSONObject jsonObject = createJSON()
				.put("id", chamado.getId())
				.put("titulo", chamado.getTitulo())
				.put("usuario", jsonObjectUsuario)
				.put("usuarioFechamento", jsonObjectUsuarioFechamento)
				.put("status", chamado.getStatus().getDescricao())
				.put("tipo", chamado.getTipo().getDescricao())
				.put("data", sdf.format(chamado.getDataAbertura()))
				.put("dataFechamento", chamado.getDataFechamento() == null ? " " : sdf.format(chamado.getDataFechamento()))
				.put("dataPrevisao", chamado.getDataPrevisao() == null ? " " : sdf.format(chamado.getDataPrevisao()))
				.build();
				
				jsonObjList.add(jsonObject);
			}
			createJSON().put("chamados", jsonObjList)
			.ok();
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao buscar chamados").serverError();
		}
		
	}
	
	@Get
	@Path("/historico")
	public void listaHistoricoChamado() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			
			List<HistoricoChamado> historicoChamados = chamadoService.listaHistorico();
			
			List<JSONObject> jsonObjList = new ArrayList<>();
			
			if(historicoChamados == null || historicoChamados.isEmpty()) {
				createJSON().message("Não foram encontrados chamados").notFound();
			} else {
				
				for (HistoricoChamado historicoChamado : historicoChamados) {
					
					JSONObject jsonObject = createJSON()
					.put("id", historicoChamado.getId())
					.put("chamadoid", historicoChamado.getChamado().getId())
					.put("datacomentario", sdf.format(historicoChamado.getDataComentario()))
					.put("titulo", historicoChamado.getChamado().getTitulo())
					.put("usuario", historicoChamado.getUsuario().getNome())
					.put("status", historicoChamado.getChamado().getStatus().getDescricao())
					.build();
					
					jsonObjList.add(jsonObject);
				}
				createJSON().put("historicos", jsonObjList)
				.ok();
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			createJSON().failure("Erro ao listar historico de chamados").serverError(); 
		}
		
	}
	
	@Get
	@Path("/paginacao/historico")
	public void paginacaoHistorico(int start, int length) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {

			List<HistoricoChamado> paginacaoHistorico = historicoChamadoDAO.listHistorico(start, length, usuarioLogado);
			
			Long countHistorico = historicoChamadoDAO.countHistorico(usuarioLogado);
			
			List<JSONObject> jsonObjList = new ArrayList<>();
			
			for (HistoricoChamado historicoChamado : paginacaoHistorico) {
				
				JSONObject jsonObject = createJSON()
				.put("id", historicoChamado.getId())
				.put("chamadoid", historicoChamado.getChamado().getId())
				.put("datacomentario", sdf.format(historicoChamado.getDataComentario()))
				.put("titulo", historicoChamado.getChamado().getTitulo())
				.put("usuario", historicoChamado.getUsuario().getNome())
				.put("status", historicoChamado.getChamado().getStatus().getDescricao())
				.build();
				
				jsonObjList.add(jsonObject);
			}
	
			createJSON().put("recordsFiltered", countHistorico).put("recordsTotal", countHistorico).put("data", jsonObjList).ok();
		
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			createJSON().failure("Erro ao listar chamados").serverError(); 
		}
	}
	
	@Get
	@Path("/paginacao")
	public void paginacaoChamado(int start, int length, String status, String filtro) {
		
		StatusChamado statusChamado = null; 
		List<Chamado> paginacaoChamado = null;
		Long countChamado = null;
		
		statusChamado = StatusChamado.valueOf(status);
		
		paginacaoChamado = chamadoService.listaPorFiltro(start, length, statusChamado, filtro, usuarioLogado);
		countChamado = chamadoService.countChamado(filtro, statusChamado, usuarioLogado);
			
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			 
			List<JSONObject> jsonObjList = new ArrayList<>();
			
			for (Chamado chamado : paginacaoChamado) {
				
				JSONObject jsonObjectUsuario = createJSON().put("id", chamado.getUsuario().getId())
						.put("nome", chamado.getUsuario().getNome())
						.build();
				
				JSONObject jsonObjectUsuarioFechamento = null;
				
				if(chamado.getUsuarioFechamento() != null) {
					jsonObjectUsuarioFechamento = createJSON().put("id", chamado.getUsuarioFechamento().getId())
						.put("nome", chamado.getUsuarioFechamento().getNome())
						.build();
				}
				
				JSONObject jsonObject = createJSON()
				.put("id", chamado.getId())
				.put("titulo", chamado.getTitulo())
				.put("usuario", jsonObjectUsuario)
				.put("usuarioFechamento", jsonObjectUsuarioFechamento)
				.put("status", chamado.getStatus().getDescricao())
				.put("tipo", chamado.getTipo().getDescricao())
				.put("data", sdf.format(chamado.getDataAbertura()))
				.put("dataFechamento", chamado.getDataFechamento() == null ? " " : sdf.format(chamado.getDataFechamento()))
				.put("dataPrevisao", chamado.getDataPrevisao() == null ? " " : sdf.format(chamado.getDataPrevisao()))
				.build();
				
				jsonObjList.add(jsonObject);
			}
			
			createJSON().put("recordsFiltered", countChamado).put("recordsTotal", countChamado).put("data", jsonObjList).ok();
				
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			createJSON().failure("Erro ao listar chamados").serverError(); 
		}
	}
	
}
