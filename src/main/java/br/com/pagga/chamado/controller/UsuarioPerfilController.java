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
import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.pagga.chamado.dao.UsuarioPerfilDAO;
import br.com.pagga.chamado.model.UsuarioPerfil;
import br.com.pagga.chamado.service.UsuarioPerfilService;
import br.com.pagga.chamado.service.exception.BusinessException;

@Controller
@Path("/usuarioPerfil")
public class UsuarioPerfilController extends PaggaController {

	@Inject
	UsuarioPerfilService usuarioPerfilService;
	
	@Inject
	UsuarioPerfilDAO usuarioPerfilDAO;
	
	@Inject
	Environment environment;
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioPerfilController.class);
	
	public UsuarioPerfilController() {
	}
	
	@Inject
	public UsuarioPerfilController(HttpServletRequest request, HttpServletResponse response, Result result,
			EntityManager entityManager) {
		super(request, response, result, entityManager);
	}
	
	@Get
	@Path("")
	public void listaUsuarioPerfil() {
		try {
			List<UsuarioPerfil> findAll = usuarioPerfilDAO.findAll();
			
			if(findAll == null || findAll.isEmpty()) {
				
				createJSON().message("Perfis de usuários não encontrados").notFound();
				
			} else {

				List<JSONObject> jsonObjList = new ArrayList<>();
	
				for (UsuarioPerfil usuarioPerfil : findAll) {
	
					JSONObject jsonObject = createJSON().put("id", usuarioPerfil.getId())
							.put("perfil", usuarioPerfil.getPerfil().getDescricao())
							.put("usuario", usuarioPerfil.getUsuario().getNome()).build();
	
					jsonObjList.add(jsonObject);
				}
	
				createJSON().put("usuarioPerfilList", jsonObjList).ok();
				
			}
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao listar perfis de usuarios").serverError(); 
		}
	}
	
	@Post
	@Path("")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void cadastraUsuarioPerfil(UsuarioPerfil usuarioPerfil) {
		try {
			usuarioPerfilService.cadastroUsuarioPerfil(usuarioPerfil);
			
			createJSON().put("msg", "Usuario cadastrado com sucesso").ok();
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao cadastrar perfil de usuario").serverError(); 
		}
	}
	
	@Put
	@Path("")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void removePerfilUsuario(UsuarioPerfil usuarioPerfil) {
		try {
			usuarioPerfilService.removerPerfilUsuario(usuarioPerfil);

			createJSON().put("msg", "Usuario cadastrado com sucesso").ok();
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao remover perfil de usuario").serverError(); 
		}
	}
}
