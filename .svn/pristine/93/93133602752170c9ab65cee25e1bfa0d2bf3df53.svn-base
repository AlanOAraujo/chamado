package br.com.pagga.chamado.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.pagga.chamado.model.MarcacaoChamado;

public class MarcacaoChamadoDAO extends AbstractDAO<MarcacaoChamado> {

	public MarcacaoChamadoDAO() {
	}
	
	@Inject
	public MarcacaoChamadoDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	public List<MarcacaoChamado> findByDescricao(String descricao){
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<MarcacaoChamado> criteria = builder.createQuery(getClazz());
		
		Root<MarcacaoChamado> root = criteria.from(getClazz());
		
		Path<String> pathDescricao = root.<String>get("descricao");
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(!descricao.isEmpty()) {
			Predicate predicateDescricao = builder.like(builder.lower(pathDescricao), "%"+ descricao.toLowerCase() +"%");
			predicates.add(predicateDescricao);
		}
		
		criteria.select(root).where((Predicate[]) predicates.toArray(new Predicate[0]));
		
		TypedQuery<MarcacaoChamado> query = entityManager.createQuery(criteria);
		
		List<MarcacaoChamado> list = null;
		
		try {
			list = query.getResultList();
		}
		catch (NoResultException ex) {}
		
		return list;
		
	}


}
