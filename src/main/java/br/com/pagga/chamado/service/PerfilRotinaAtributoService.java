package br.com.pagga.chamado.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.pagga.chamado.dao.AtributoDAO;
import br.com.pagga.chamado.dao.PerfilDAO;
import br.com.pagga.chamado.dao.PerfilRotinaAtributoDAO;
import br.com.pagga.chamado.model.Atributo;
import br.com.pagga.chamado.model.Perfil;
import br.com.pagga.chamado.model.PerfilRotinaAtributo;

public class PerfilRotinaAtributoService {
	
	private PerfilRotinaAtributoDAO perfilRotinaAtributoDAO;
	
	@Inject
	private PerfilDAO perfilDAO;
	
	@Inject
	private AtributoDAO atributoDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(PerfilRotinaAtributoService.class);
	
	public PerfilRotinaAtributoService() {
	}
	
	@Inject
	public PerfilRotinaAtributoService(PerfilRotinaAtributoDAO perfilRotinaAtributoDAO) {
		this.perfilRotinaAtributoDAO = perfilRotinaAtributoDAO;
	}
	
	public List<PerfilRotinaAtributo> buscaPerfilRotinaAtributo(){
		List<PerfilRotinaAtributo> list = perfilRotinaAtributoDAO.findAll();
		return list;
	}
	
	public void cadastrarPerfilRotinaAtributo(PerfilRotinaAtributo perfilRotinaAtributo) {
		try {
			
			Perfil perfil = perfilRotinaAtributo.getPerfil();
			Atributo atributo = atributoDAO.findById(perfilRotinaAtributo.getAtributo().getId());
			
			perfilRotinaAtributo = PerfilRotinaAtributo.createPerfilRotinaAtributo(perfil, atributo.getRotina(), atributo);
			
			perfilRotinaAtributoDAO.save(perfilRotinaAtributo);
			logger.info("Perfil ID= {} Rotina ID= {} e Atributo  ID= {} vinculados", 
					perfilRotinaAtributo.getPerfil().getId(), 
					perfilRotinaAtributo.getRotina().getId(),
					perfilRotinaAtributo.getAtributo().getId());
		} catch (Exception e) {
			logger.error("Erro ao vincular o perfil a rotina!", e.getMessage());
		}
	}
	
	public void removerPerfilRotinaAtributo(PerfilRotinaAtributo perfilRotinaAtributo) {
		try {
			logger.info("Perfil-Rotina-Atributo ID = {} removido", 
					perfilRotinaAtributo.getId());
			perfilRotinaAtributoDAO.removeById(perfilRotinaAtributo.getId());
		} catch (Exception e) {
			logger.error("Erro ao excluir o Perfil-Rotina-Atributo!", e.getMessage());
		}
		
	}
	
	public void removerTodasAsRotinasDoPerfil( Perfil perfil ) {
		try {
			
			Perfil perfilRemove = perfilDAO.findById(perfil.getId());
			
			perfilRotinaAtributoDAO.removeByPerfil(perfilRemove);
			logger.info("Vinculos deletados");
		} catch (Exception e) {
			logger.error("Erro ao desvincular o perfil e rotina!", e.getMessage());
		}
		
	}
	
}
