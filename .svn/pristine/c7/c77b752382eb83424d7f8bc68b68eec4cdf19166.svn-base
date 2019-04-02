package br.com.pagga.chamado.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.pagga.chamado.model.Atributo;

public class AtributoDAO extends AbstractDAO<Atributo> {

	public AtributoDAO() {
	}
	
	@Inject
	public AtributoDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	public Atributo findByNome(String nomeAtributo) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Atributo> criteria = builder.createQuery(getClazz());

		Root<Atributo> root = criteria.from(getClazz());

		criteria.select(root).where(builder.equal(root.get("nomeAtributo"), nomeAtributo));

		TypedQuery<Atributo> query = entityManager.createQuery(criteria);
		
		return	query.getSingleResult();

	}
	
	public Atributo findByCod( String codAtributo ) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Atributo> criteria = builder.createQuery(getClazz());

		Root<Atributo> root = criteria.from(getClazz());

		criteria.select(root).where(builder.equal(root.get("codAtributo"), codAtributo));

		TypedQuery<Atributo> query = entityManager.createQuery(criteria);
		
		return	query.getSingleResult();
	}
	
}
