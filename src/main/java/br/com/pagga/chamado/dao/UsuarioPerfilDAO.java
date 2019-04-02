package br.com.pagga.chamado.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.pagga.chamado.model.UsuarioPerfil;

public class UsuarioPerfilDAO extends AbstractDAO<UsuarioPerfil> {

	public UsuarioPerfilDAO() {
	}
	
	@Inject
	public UsuarioPerfilDAO(EntityManager entityManager) {
		super(entityManager);
	}
}
