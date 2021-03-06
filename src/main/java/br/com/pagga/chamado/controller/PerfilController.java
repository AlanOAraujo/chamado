package br.com.pagga.chamado.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.pagga.chamado.dao.PerfilDAO;
import br.com.pagga.chamado.model.Atributo;
import br.com.pagga.chamado.model.Perfil;
import br.com.pagga.chamado.model.PerfilRotinaAtributo;
import br.com.pagga.chamado.model.Rotina;
import br.com.pagga.chamado.service.PerfilService;
import br.com.pagga.chamado.service.exception.BusinessException;

@Controller
@Path("/perfil")
public class PerfilController extends PaggaController {
	
	@Inject
	private PerfilService perfilService;
	
	@Inject
	private PerfilDAO perfilDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(PerfilController.class);
	
	public PerfilController() {
	}
	
	@Inject
	public PerfilController(HttpServletRequest request, HttpServletResponse response, Result result,
			EntityManager entityManager) {
		super(request, response, result, entityManager);
	}
	
	@Get
	@Path("")
	public void listaPerfil() {
		
		List<Perfil> findAll = this.perfilService.buscarPerfil();
		
		List<JSONObject> jsonObjList = new ArrayList<>();
		
		if(findAll == null || findAll.isEmpty()) {
			createJSON().message("Lista de perfil não encontrada").notFound();
		}else{
		
			for (Perfil perfil : findAll) {
				
				JSONObject jsonObject = createJSON()
						.put("id", perfil.getId())
						.put("descricao", perfil.getDescricao())
						.build();
				
				jsonObjList.add(jsonObject);
			}
			
			createJSON().put("perfis", jsonObjList)
			.ok();
		}
	}
	
	@Get
	@Path("/rotina")
	public void listaRotina() {
		
		List<Rotina> findAll = this.perfilService.buscarRotina();
		
		List<JSONObject> jsonObjList = new ArrayList<>();
		
		if(findAll == null || findAll.isEmpty()) {
			createJSON().message("Lista de perfil não encontrada").notFound();
		}else{
		
			for (Rotina rotina : findAll) {
				
				List<JSONObject> jsonObjListAtributo = new ArrayList<>();
				
				if(rotina.getAtributoList() != null && rotina.getAtributoList().size() > 0) {
					
					for (Atributo atributo : rotina.getAtributoList()) {

						JSONObject atributoRotina = createJSON()
								.put("id", atributo.getId())
								.put("codigo", atributo.getCodAtributo())
								.put("descricao", atributo.getNomeAtributo())
								.build();
						
						jsonObjListAtributo.add(atributoRotina);
					}
				}
				
				JSONObject jsonObject = createJSON()
						.put("id", rotina.getId())
						.put("descricao", rotina.getNome())
						.put("atributoList", jsonObjListAtributo)
						.build();
				
				jsonObjList.add(jsonObject);
			}
			
			createJSON().put("rotinas", jsonObjList).ok();
		}
	}
	
	@Post
	@Path("/descricao")
	public void buscaPorDescricao(String descricao) {
		
		List<Perfil> perfils = this.perfilService.buscaPorDescricao(descricao);
		
		List<JSONObject> jsonObjList = new ArrayList<>();
		
		if(perfils == null ||  perfils.isEmpty()) {
			createJSON().failure("Perfil não encontrado").serverError();
		}else{
				
			for (Perfil perfil : perfils) {
				JSONObject jsonObject = createJSON()
						.put("id", perfil.getId())
						.put("descricao", perfil.getDescricao())
						.build();
				
				jsonObjList.add(jsonObject);
				
			}
			
			createJSON().put("perfil", jsonObjList)
			.ok();
			
		}
		
	}
	
	@Post
	@Path("")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void salvarPerfil(Perfil perfil) {
		
		try {
			String msg = perfil.getId() == null ? "Perfil criado com sucesso" : "Perfil atualizado com sucesso" ;
			
			perfilService.cadastrarPerfil(perfil);
			
			if(perfil != null && perfil.getId() != null) {
			
				JSONObject jsonObject = createJSON()
						.put("msg", msg)
						.put("id", perfil.getId())
						.put("descricao", perfil.getDescricao())
						.build();
			
				createJSON().put("perfil", jsonObject)
				.ok();
			
			} else {
				createJSON().message("Erro ao salvar perfil").serverError();
			}
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			createJSON().failure(e.getMessage()).serverError();
		} catch (Exception e) {
			logger.error(e.getMessage());
			createJSON().message("houve erro ao salvar ").serverError();
		}
		
	}
	
	@Delete
	@Path("")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void removerPerfil(Perfil perfil) {
		
		try {
			perfilService.removePerfil(perfil);
			
			createJSON().put("msg", "Perfil removido com sucesso").ok();
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			createJSON().failure(e.getMessage()).serverError();
		} catch (IllegalArgumentException ia){
			logger.error(ia.getMessage());
			createJSON().failure(ia.getMessage()).serverError();
		} catch (Exception e) {
			logger.error(e.getMessage());
			createJSON().message("Erro ao remover perfil! ").serverError();
		}
		
	}
	
	@Post
	@Path("/id")
	public void buscaPorId(long id) {
		
		try {
			
			Perfil perfil = this.perfilService.buscaPorId(id);
			
			if(perfil == null) {
				createJSON().failure("Perfil não encontrado").serverError();
			} else{
				
				List<JSONObject> rotinas = new ArrayList<>(); 
				
				if(perfil.getPerfilRotinaAtributo() != null && perfil.getPerfilRotinaAtributo().size() > 0) {
					for(PerfilRotinaAtributo perfilRotinaAtributo : perfil.getPerfilRotinaAtributo()) {
						
						JSONObject rotina = createJSON()
								.put("idRotina", perfilRotinaAtributo.getRotina().getId())
								.put("nomeRotina", perfilRotinaAtributo.getRotina().getNome())
								.put("idAtributo", perfilRotinaAtributo.getAtributo().getId())
								.put("codAtributo", perfilRotinaAtributo.getAtributo().getCodAtributo())
								.build();
						
						rotinas.add(rotina);
					}
				}
				
				JSONObject jsonObject = createJSON()
						.put("id", perfil.getId())
						.put("descricao", perfil.getDescricao())
						.put("perfilRotinaAtributo", rotinas)
						.build();	
				
				createJSON().put("perfil", jsonObject)
				.ok();			
			}
		
		} catch (Exception e) {
			logger.error(e.getMessage());
			createJSON().message("Erro ao editar perfil! ").serverError();
		}
	}
	
	
	@Get
	@Path("/paginacao")
	public void paginacaoPerfil(int start, int length, String name) {
		try {
			
			List<Perfil> paginacaoPerfil = perfilService.paginacaoPerfil(start, length, name);
			
			Long countPerfil = perfilDAO.countPerfil(name);
			 
			List<JSONObject> jsonObjList = new ArrayList<>();
		
			for ( Perfil perfil : paginacaoPerfil ) {
				JSONObject jsonObject = createJSON()
						.put("id", perfil.getId())
						.put("descricao", perfil.getDescricao())
						.build();
				
				jsonObjList.add(jsonObject);
			}
			
			createJSON().put("recordsFiltered", countPerfil).put("recordsTotal", countPerfil).put("data", jsonObjList).ok();
				
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			createJSON().failure("Erro ao listar perfis").serverError(); 
		}
	}
	
}
