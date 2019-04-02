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
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.pagga.chamado.dao.RotinaDAO;
import br.com.pagga.chamado.model.Rotina;
import br.com.pagga.chamado.service.exception.BusinessException;

@Controller
@Path("/rotina")
public class RotinaController extends PaggaController {
	
	@Inject
	private RotinaDAO rotinaDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(RotinaController.class);
	
	/*@Inject
	private Environment environment;*/
	
	public RotinaController() {
	}

	@Inject
	public RotinaController(HttpServletRequest request, HttpServletResponse response, Result result,
			EntityManager entityManager) {
		super(request, response, result, entityManager);
	}

	@Get
	@Path("")
	public void listaRotinas(String nome) {
		try {
		
			List<Rotina> findAll = rotinaDAO.findAll();
			
			List<JSONObject> jsonObjList = new ArrayList<>();
			
			if(findAll == null || findAll.isEmpty()) {
				
				createJSON().message("Rotina não encontrado").notFound();
				
			} else {
			
				for ( Rotina rotina: findAll ) {
					
					JSONObject jsonObject = createJSON().put("id", rotina.getId())
						.put("nome", rotina.getNome())
						.build();
					
					jsonObjList.add(jsonObject);
				}
				
				createJSON().put("rotinas", jsonObjList)
					.ok();
			}
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao listar rotinas").serverError(); 
		}
	}
	
	@Put
	@Post
	@Path("")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void salva( Rotina rotina ) {
		try {
			rotinaDAO.save(rotina);
			createJSON().put("msg", "Rotina salva com sucesso").ok();
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao cadastrar rotina").serverError(); 
		}
		
	}
	
	@Get
	@Path("{id}")
	public void procura( int id ) {
		
		try {
			
			
			Rotina rotina = rotinaDAO.findById(id);
			
			if ( rotina == null ) {
				
				createJSON().message("Rotina não encontrado").notFound();
			}
			else {

				List<JSONObject> jsonObjList = new ArrayList<>();
	
				JSONObject jsonObject = createJSON().put("id", rotina.getId())
						.put("nome", rotina.getNome())
						.build();
	
				jsonObjList.add(jsonObject);
	
				createJSON().put("usuario", jsonObjList).ok();
			}


		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao buscar rotina").serverError(); 
		}
		
	}
}
