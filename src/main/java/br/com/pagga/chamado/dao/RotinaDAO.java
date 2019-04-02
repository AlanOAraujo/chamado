package br.com.pagga.chamado.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.pagga.chamado.model.Rotina;

public class RotinaDAO extends AbstractDAO<Rotina> {

	public RotinaDAO() {
	}
	
	@Inject
	public RotinaDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	public Rotina findByName(String nome) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Rotina> criteria = builder.createQuery(getClazz());

		Root<Rotina> root = criteria.from(getClazz());

		criteria.select(root).where(builder.equal(root.get("nome"), nome));

		TypedQuery<Rotina> query = entityManager.createQuery(criteria);
		
		return query.getSingleResult();
		
	}
}

