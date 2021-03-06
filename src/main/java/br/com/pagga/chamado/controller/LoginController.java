package br.com.pagga.chamado.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.pagga.chamado.model.Atributo;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.security.Open;
import br.com.pagga.chamado.security.UsuarioLogado;
import br.com.pagga.chamado.service.LoginService;
import br.com.pagga.chamado.service.exception.BusinessException;

@Controller
@Path("/login")
public class LoginController extends PaggaController {

	private LoginService loginService;
	
	private UsuarioLogado usuarioLogado;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	public LoginController() {
	}
	
	@Inject
	public LoginController(LoginService loginService, HttpServletResponse response, UsuarioLogado usuarioLogado) {
		super();
		this.loginService = loginService;
		this.usuarioLogado = usuarioLogado;
		this.response = response;
	}
	
	@Post 
	@Get
	@Open
	@Path("")
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void autentificaLogin(Usuario usuario) {

		try {

			Usuario usuarioAutentifica = loginService.autentificaLogin(usuario);

			usuarioLogado.fazLogin(usuarioAutentifica);
			
			List<JSONObject> jsonObjListAtributos = new ArrayList<>();
			
			if(usuarioLogado.getUsuario().getUsuarioPerfilList() != null ||usuarioLogado.getUsuario().getUsuarioPerfilList().size() > 0) {
				
				List<Atributo> atributos = LoginService.autentificarPerfil(usuarioLogado);
				
				for (Atributo atributo : atributos) {
					JSONObject usuarioRoles = createJSON()
							.put("id", atributo.getId())
							.put("codAtributo", atributo.getCodAtributo())
							.build();
					
					jsonObjListAtributos.add(usuarioRoles);
					
				}
				
			}

			JSONObject jsonObjectUsuario = createJSON()
					.put("cpf", usuarioAutentifica.getCpf())
					.put("senha", usuarioAutentifica.getSenha())
					.put("roles", jsonObjListAtributos)
					.build();

			createJSON().put("usuario", jsonObjectUsuario).ok();

		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			createJSON().failure("Erro ao efetuar login").serverError();
		}
	}
	
	@Get
	@Open
	@Path("/verifica")
	public void  verificaLogin() {
		try {
			
			if(usuarioLogado.getUsuario() != null) {
				
				JSONObject jsonObjectUsuario = createJSON()
						.put("id", usuarioLogado.getUsuario().getId())
						.put("email", usuarioLogado.getUsuario().getEmail())
						.put("nome", usuarioLogado.getUsuario().getNome())
						.put("situacao", usuarioLogado.getUsuario().isSituacao())
						.build();
				
				createJSON().put("usuario", jsonObjectUsuario).ok();
			}else {
				createJSON().failure("Usuário não esta logado").serverError();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			createJSON().failure("Erro no login").serverError();
		}
		
	}
	
	@Get
	@Open
	@Path("/desloga")
	public void  desloga() {
		try {
			usuarioLogado.desloga();
			
			createJSON().put("usuario", "Deslogado").ok();
		} catch (Exception e) {
			e.printStackTrace();
			createJSON().failure("Erro ao deslogar").serverError();
		}
		
	}
	
	@Post
	@Open
	@Path("/senha")
	public void esqueceSenha(String emailUsuario, String cpfUsuario) throws IOException {
		
		try {
			
			Usuario usuarioSolicitante = loginService.senhaEsquecida(emailUsuario, cpfUsuario);
			
			createJSON().put("mensagem", "Email enviado para "+usuarioSolicitante.getEmail()+" com nova senha").ok();
		
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();	
		} catch (Exception e) {
			e.printStackTrace();
			createJSON().failure("Erro ao redefinir a senha").serverError();
		}
		
	}
	
	@Get
	@Path("/role")
	public void usuarioRole() {
		try {
			
			List<JSONObject> jsonObjListAtributos = new ArrayList<>();
				
			List<Atributo> atributos = LoginService.autentificarPerfil(usuarioLogado);
			
			for (Atributo atributo : atributos) {
				JSONObject usuarioRoles = createJSON()
						.put("id", atributo.getId())
						.put("codAtributo", atributo.getCodAtributo())
						.build();
				
				jsonObjListAtributos.add(usuarioRoles);
			}
			
			JSONObject jsonObjectRole = createJSON()
					.put("roles", jsonObjListAtributos)
					.build();

			createJSON().put("role", jsonObjectRole).ok();
		} catch (Exception e) {
			e.printStackTrace();
			createJSON().failure("Erro no login").serverError();
		}
	}
}
