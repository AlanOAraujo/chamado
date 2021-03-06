package br.com.pagga.chamado.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pagga.chamado.controller.PerfilRotinaAtributoController;
import br.com.pagga.chamado.dao.PerfilDAO;
import br.com.pagga.chamado.dao.RotinaDAO;
import br.com.pagga.chamado.model.Perfil;
import br.com.pagga.chamado.model.PerfilRotinaAtributo;
import br.com.pagga.chamado.model.Rotina;
import br.com.pagga.chamado.security.UsuarioLogado;
import br.com.pagga.chamado.service.exception.BusinessException;
import br.com.pagga.chamado.service.role.ControlePermissoes;

public class PerfilService {
	
	private PerfilDAO perfilDAO;
	
	@Inject
	PerfilRotinaAtributoController perfilRotinaAtributoController;
	
	@Inject
	private RotinaDAO rotinaDAO;
	
	@Inject
	private UsuarioLogado usuarioLogado;
	
	private PerfilRotinaAtributoService perfilRotinaAtributoService;
	
	private static final Logger logger = LoggerFactory.getLogger(PerfilService.class);

	public PerfilService() {
	}
	
	@Inject
	public PerfilService(PerfilDAO perfilDAO, PerfilRotinaAtributoService perfilRotinaAtributoService) {
		this.perfilDAO = perfilDAO;
		this.perfilRotinaAtributoService = perfilRotinaAtributoService;
	}
	
	public List<Perfil> buscarPerfil() {

		List<Perfil> list = perfilDAO.findAll();
		
		return list;
	
	}
	
	public List<Rotina> buscarRotina() {

		List<Rotina> listRotina = rotinaDAO.findAll();
		
		return listRotina;
	
	}
	
	public List<Perfil> buscaPorDescricao(String descricao) {
		return perfilDAO.findByDescricao(descricao);
	}
	
	public void cadastrarPerfil(Perfil perfil) {
		
		String permissaoCadPrf = ControlePermissoes.permissaoCadPrf(usuarioLogado);
		String permissaoAddRtn = ControlePermissoes.permissaoAddRtn(usuarioLogado);
		
		if(permissaoCadPrf == " " && !permissaoCadPrf.equals("CAD_PRF")) {
			throw new BusinessException("Você não possui permissão para alterar/cadastrar perfil");
		}else {
			if ( StringUtils.isBlank(perfil.getDescricao())) {
				throw new BusinessException("Descrição do Perfil é obrigatória");
			}
			
			Perfil findByDesc = perfilDAO.findByDesc(perfil.getDescricao());
			
			if(findByDesc != null) {
				
				if (findByDesc.getDescricao().toLowerCase().equals(perfil.getDescricao().toLowerCase())) {
					throw new BusinessException(String.format("Perfil %s já cadastrado", perfil.getDescricao()));
				}
			}
			
			if(perfil != null && perfil.getId() != null) {
				logger.info("Perfil {} atualizado com sucesso!", perfil.getDescricao());
			}else {
				logger.info("Perfil {} cadastrado com sucesso!", perfil.getDescricao());
			}
			
			perfilDAO.save(perfil);
			
			if(perfil.getPerfilRotinaAtributo().size() > 0 || !perfil.getPerfilRotinaAtributo().isEmpty()) {
				if(permissaoAddRtn == " " && !permissaoAddRtn.equals("ADD_RTN")) {
					throw new BusinessException("Você não possui permissão para Adicionar/Remover Rotina do Perfil");
				}else {
					perfilRotinaAtributoService.removerTodasAsRotinasDoPerfil(perfil);
					
					for(PerfilRotinaAtributo perfilRotinaAtributo : perfil.getPerfilRotinaAtributo()) {
						perfilRotinaAtributo.setPerfil(perfil);
						perfilRotinaAtributoService.cadastrarPerfilRotinaAtributo(perfilRotinaAtributo);
					}
				}	
			}
		}	
		
	}

	public void removePerfil(Perfil perfil) {
		
		
		String permissaoExcPrf = ControlePermissoes.permissaoExcPrf(usuarioLogado);
		
		if(permissaoExcPrf == " " && !permissaoExcPrf.equals("EXC_PRF")) {
			throw new BusinessException("Você não possui permissão para excluir perfil");
		}else {
		
			Perfil perfilRemove = perfilDAO.findById(perfil.getId());
			
			if ( perfilRemove == null )
				throw new IllegalArgumentException("perfil is null");
			
			if(perfilRemove.getUsuarioPerfilList().size() > 0) 
				throw new IllegalArgumentException("A Usuarios utilizando este perfil.");		
	
			if(perfilRemove.getPerfilRotinaAtributo().size() > 0) {
				perfilRotinaAtributoService.removerTodasAsRotinasDoPerfil(perfilRemove);
				perfilDAO.remove(perfilRemove);
			}
				
			perfilDAO.remove(perfilRemove);
		}	
	}
	
	public Perfil buscaPorId(long id) {
		return perfilDAO.findById(id);
	}
	
	public List<Perfil> paginacaoPerfil(int pageNumber,  int pageSize, String descricao) {
		
		String permissaoCadPrf = ControlePermissoes.permissaoCadPrf(usuarioLogado);
		
		if(permissaoCadPrf == " " && !permissaoCadPrf.equals("CAD_PRF")) {
			return new ArrayList<>();
		}else {
		
			List<Perfil> listPerfil = perfilDAO.listPerfil(pageNumber, pageSize, descricao);
			
			if(listPerfil.isEmpty()) {
				throw new BusinessException(String.format("Descrição %s não encontrado", descricao));
			}
			
			return listPerfil;
		}
		
	}
	
}
