package br.com.pagga.chamado.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.pagga.chamado.dao.AtributoDAO;
import br.com.pagga.chamado.model.Atributo;

@Controller
@Path("/atributo")
public class AtributoController extends PaggaController {
	
	@Inject
	private AtributoDAO atributoDAO;

	public AtributoController() {
	}
	
	@Inject
	public AtributoController(HttpServletRequest request, HttpServletResponse response, Result result,
			EntityManager entityManager) {
	
		super(request, response, result, entityManager);
	}
	
	@Get
	@Path("")
	public void listaAtributo() {
		
		List<Atributo> findAll = atributoDAO.findAll();
		
		List<JSONObject> jsonObjList = new ArrayList<>();
		
		if(findAll == null || findAll.isEmpty()) {
			createJSON().message("Lista de Atributo não encontrada").notFound();
		}else {
			for ( Atributo atributo : findAll ) {
				
				JSONObject jsonObject = createJSON().put("id", atributo.getId())
					.put("nomeAtributo", atributo.getNomeAtributo())
					.put("codAtributo", atributo.getCodAtributo())
					.put("rotina", atributo.getRotina().getId())
					.build();
				
				jsonObjList.add(jsonObject);
			}
			
			createJSON().put("atributos", jsonObjList).ok();
		}
	}
}
