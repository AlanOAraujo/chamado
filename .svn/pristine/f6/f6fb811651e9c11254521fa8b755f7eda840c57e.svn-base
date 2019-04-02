package br.com.pagga.chamado.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

import br.com.pagga.chamado.model.Perfil;
import br.com.pagga.chamado.model.PerfilRotinaAtributo;

public class PerfilRotinaAtributoDAO extends AbstractDAO<PerfilRotinaAtributo> {
	
	public PerfilRotinaAtributoDAO() {
	}
	
	@Inject
	public PerfilRotinaAtributoDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public void removeByPerfil(Perfil perfil) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaDelete<PerfilRotinaAtributo> delete = builder.createCriteriaDelete(PerfilRotinaAtributo.class);

        Root<PerfilRotinaAtributo> root = delete.from(PerfilRotinaAtributo.class);

        delete.where(builder.equal(root.get("perfil"), perfil));
        
		try {
			 entityManager.createQuery(delete).executeUpdate();
		}
		catch (NoResultException ex) {
            ex.printStackTrace();
		}
		
	}
	
}
