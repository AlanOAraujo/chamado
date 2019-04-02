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
import br.com.pagga.chamado.dao.AtributoDAO;
import br.com.pagga.chamado.dao.PerfilDAO;
import br.com.pagga.chamado.model.Atributo;
import br.com.pagga.chamado.model.Perfil;
import br.com.pagga.chamado.model.PerfilRotinaAtributo;
import br.com.pagga.chamado.service.PerfilRotinaAtributoService;
import br.com.pagga.chamado.service.exception.BusinessException;

@Controller
@Path("/perfil-rotina-atributo")
public class PerfilRotinaAtributoController extends PaggaController{

	private PerfilRotinaAtributoService  perfilRotinaAtributoService;
	
	@Inject
	private PerfilDAO perfilDAO;
	
	@Inject
	private AtributoDAO atributoDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(PerfilRotinaAtributoController.class);
	
	public PerfilRotinaAtributoController() {}
	
	@Inject
	public PerfilRotinaAtributoController(HttpServletRequest request, HttpServletResponse response, Result result,
			EntityManager entityManager, PerfilRotinaAtributoService  perfilRotinaAtributoService) {
		super(request, response, result, entityManager);
		this.perfilRotinaAtributoService = perfilRotinaAtributoService;
	}
	
	
	@Get
	@Path("")
	public void listaPerfilRotinaAtributo(String perfil, String rotina, String atributo){
		
		List<PerfilRotinaAtributo> findAll = perfilRotinaAtributoService.buscaPerfilRotinaAtributo();
		List<JSONObject> jsonObjList = new ArrayList<>();
		
		if(findAll == null || findAll.isEmpty()) {
			
			createJSON().message("Lista não encontrada").notFound();
			
		}else {
		
			for (PerfilRotinaAtributo perfilRotinaAtributo : findAll) {
				
				JSONObject jsonObject = createJSON().put("id", perfilRotinaAtributo.getId())
						.put("perfil", perfilRotinaAtributo.getPerfil().getDescricao())
						.put("rotina", perfilRotinaAtributo.getRotina().getNome())
						.put("atributo", perfilRotinaAtributo.getAtributo().getNomeAtributo())
						.build();
					
					jsonObjList.add(jsonObject);
			}
			
			createJSON().put("perfil_rotina_atributo", jsonObjList).ok();
		}
 	}
	
	@Post
	@Path("")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void cadastrarPerfilRotinaAtributo(PerfilRotinaAtributo perfilRotinaAtributo) {
		
		try {
			
			Perfil perfil = perfilDAO.findById(perfilRotinaAtributo.getPerfil().getId());
			Atributo atributo = atributoDAO.findById(perfilRotinaAtributo.getAtributo().getId());
			
			perfilRotinaAtributo = PerfilRotinaAtributo.createPerfilRotinaAtributo(perfil, atributo.getRotina(), atributo);
			
			perfilRotinaAtributoService.cadastrarPerfilRotinaAtributo(perfilRotinaAtributo);
			
			createJSON().put("msg", "PerfilRotinaAtributo cadastro com sucesso!").ok();
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			createJSON().failure(e.getMessage()).serverError();
		} catch (Exception e) {
			logger.error(e.getMessage());
			createJSON().message("Erro ao cadastrar PerfilRotinaAtributo! ").ok();
		}
	}
	
	@Delete
	@Path("")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void removerPerfilRotinaAtributo(PerfilRotinaAtributo perfilRotinaAtributo) {
		
		try {
			perfilRotinaAtributoService.removerPerfilRotinaAtributo(perfilRotinaAtributo);
			
			createJSON().put("msg", "PerfilRotinaAtributo removido com sucesso").ok();
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			createJSON().failure(e.getMessage()).serverError();
		} catch (Exception e) {
			logger.error(e.getMessage());
			createJSON().message("Erro ao remover PerfilRotinaAtributo! ").ok();
		}
	}
	
	@Delete
	@Path("/perfil")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void removerPorPerfil(Perfil perfil) {
		
		try {
			perfilRotinaAtributoService.removerTodasAsRotinasDoPerfil(perfil);;
			
			createJSON().put("msg", "PerfilRotinaAtributo removido com sucesso").ok();
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			createJSON().failure(e.getMessage()).serverError();
		} catch (Exception e) {
			logger.error(e.getMessage());
			createJSON().message("Erro ao remover PerfilRotinaAtributo! ").ok();
		}
	}
}
