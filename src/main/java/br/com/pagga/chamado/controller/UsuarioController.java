package br.com.pagga.chamado.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.pagga.chamado.dao.UsuarioDAO;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.model.UsuarioPerfil;
import br.com.pagga.chamado.service.EmailService;
import br.com.pagga.chamado.service.UsuarioService;
import br.com.pagga.chamado.service.exception.BusinessException;

@Controller
@Path("/usuario")
public class UsuarioController extends PaggaController {

	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private UsuarioService usuarioService;
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	public UsuarioController() {
	}
	
	@Inject
	public UsuarioController(HttpServletRequest request, HttpServletResponse response, Result result,
			EntityManager entityManager) {
		super(request, response, result, entityManager);
	}
	
	@Get
	@Path("")
	public void listaUsuarios() {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			List<Usuario> findAll = usuarioDAO.findAll();
			
			if(findAll == null || findAll.isEmpty()) {
				
				createJSON().message("Usuário não encontrado").notFound();
				
			} else {
				
				List<JSONObject> jsonObjList = new ArrayList<>();
			
				for ( Usuario usuario : findAll ) {
					JSONObject jsonObject = createJSON().put("id", usuario.getId())
						.put("nome", usuario.getNome())
						.put("cpf", usuario.getCpf())
						.put("email", usuario.getEmail())
						.put("situacao", usuario.isSituacao())
						.put("dataCadastro", sdf.format(usuario.getDataCadastro()))
						.build();
					
					jsonObjList.add(jsonObject);
				}	
				
				createJSON().put("usuarios", jsonObjList)
					.ok();
			}
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao listar usuarios").serverError(); 
		}
		
	}
	
	@Get
	@Path("/paginacao")
	public void paginacaoUsuarios(int start, int length, String name) {
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			List<Usuario> paginacaoUsuario = usuarioService.paginacaoUsuario(start, length, name);
			
			Long countUsuario = usuarioService.countUsuario(name);
			 
			List<JSONObject> jsonObjList = new ArrayList<>();
		
			for ( Usuario usuario : paginacaoUsuario ) {
				JSONObject jsonObject = createJSON().put("id", usuario.getId())
					.put("nome", usuario.getNome())
					.put("cpf", usuario.getCpf())
					.put("email", usuario.getEmail())
					.put("situacao", usuario.isSituacao())
					.put("dataCadastro", sdf.format(usuario.getDataCadastro()))
					.build();
				
				jsonObjList.add(jsonObject);
			}	
			
			createJSON().put("recordsFiltered", countUsuario).put("recordsTotal", countUsuario).put("data", jsonObjList).ok();
				
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			//ex.printStackTrace();
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			createJSON().failure("Erro ao listar usuarios").serverError(); 
		}
		
	}
	
	@Post
	@Path("/id")
	public void buscaUsuarioPorId(long id) {
	
		try {
		
			Usuario usuario = usuarioDAO.findById(id);
			
			if(usuario == null) {
				
				createJSON().message("Usuário não encontrado").notFound();
				
			} else {
				
				List<JSONObject> perfis = new ArrayList<>(); 
				
				if(usuario.getUsuarioPerfilList() != null && usuario.getUsuarioPerfilList().size() > 0) {
					for(UsuarioPerfil usuarioPerfil : usuario.getUsuarioPerfilList()) {
						
						JSONObject perfil = createJSON().put("id", usuarioPerfil.getPerfil().getId())
								.put("descricao", usuarioPerfil.getPerfil().getDescricao())
								.build();
						
						perfis.add(perfil);
					}
				}
	
				JSONObject jsonObject = createJSON().put("id", usuario.getId()).put("nome", usuario.getNome())
						.put("cpf", usuario.getCpf()).put("email", usuario.getEmail()).put("senha", usuario.getSenha())
						.put("dataCadastro", usuario.getDataCadastro())
						.put("situacao", usuario.isSituacao())
						.put("usuarioPerfilList", perfis)
						.build();
	
				createJSON().put("usuario", jsonObject).ok();
				
			}

		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao buscar usuario").serverError(); 
		}
	}
	
	@Post
	@Path("/nome")
	public void buscaPorDescricao(String nome) {
		
		List<Usuario> findUsuario = usuarioDAO.findByNome(nome);
		
		List<JSONObject> jsonObjList = new ArrayList<>();
		
		if(findUsuario == null ||  findUsuario.isEmpty()) {
			createJSON().failure("Usuario não encontrado").serverError();
		}else{
				
			for (Usuario usuario : findUsuario) {
				JSONObject jsonObject = createJSON()
						.put("id", usuario.getId())
						.put("nome", usuario.getNome())
						.put("cpf", usuario.getCpf())
						.put("email", usuario.getEmail())
						.put("situacao", usuario.isSituacao())
						.build();
				
				jsonObjList.add(jsonObject);
				
			}
			
			createJSON().put("usuario", jsonObjList)
			.ok();
			
		}
		
	}
	
	@Post
	@Path("")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void cadastraUsuario(Usuario usuario) {
		try {
			
			usuarioService.cadastraUsuario(usuario);
			
			EmailService.emailConfirmacaoCadastro(
					usuario.getEmail(), usuario.getNome(), usuario.getSenha(), usuario.getCpf());
			
			createJSON().put("id", usuario.getId()).put("msg", "Usuario cadastrado com sucesso").ok();
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao cadastrar usuario").serverError(); 
		}
	}
	
	@Post
	@Path("/altera")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void alteraUsuario(Usuario usuario) {
		try {
			
			usuarioService.alterarUsuario(usuario);

			createJSON().put("msg", "Usuario alterado com sucesso").ok();
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao alterar usuario").serverError(); 
		}
	}
	
	@Post
	@Path("/alterasenha")
	public void alteraSenha(Long idUsuario, String senha) throws IOException {
		
		try {
			
			usuarioService.alterarSenha(idUsuario, senha);
			
			createJSON().put("mensagem", "Senha alterada com sucesso!").ok();
		
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();	
		} catch (Exception e) {
			e.printStackTrace();
			createJSON().failure("Erro ao redefinir a senha").serverError();
		}
		
	}
	
	@Post
	@Path("/ativa")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void ativaUsuario(Usuario usuario) {
		try {
			
			usuarioService.ativaUsuario(usuario);

			createJSON().put("msg", "Usuario ativado com sucesso").ok();
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao ativar usuario").serverError(); 
		}
	}
	
	@Post
	@Path("/inativa")
	@Consumes( value = "application/json", options=WithoutRoot.class )
	public void inativaUsuario(Usuario usuario) {
		try {
			usuarioService.inativaUsuario(usuario);

			createJSON().put("msg", "Usuario inativado com sucesso").ok();
		} catch (BusinessException ex) {
			logger.error(ex.getMessage());
			createJSON().failure(ex.getMessage()).serverError();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			createJSON().failure("Erro ao inativar usuarios").serverError();
		}
	}
	
}
