package br.com.pagga.chamado.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.com.pagga.chamado.model.Perfil;
import br.com.pagga.chamado.model.Usuario;

public class PerfilDAO extends AbstractDAO<Perfil> {

	public PerfilDAO() {
	}
	
	@Inject
	public PerfilDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	public List<Perfil> findByDescricao(String descricao) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Perfil> criteria = builder.createQuery(getClazz());

		Root<Perfil> root = criteria.from(Perfil.class);

		criteria.select(root).where(builder.like(builder.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%"));

		TypedQuery<Perfil> query = entityManager.createQuery(criteria);

		List<Perfil> perfils = null;
		
		try {
			perfils = query.getResultList();
		}catch (NoResultException e) {
			 return null;
		}
			
		return perfils;
	}
	
	public Perfil findByDesc(String descricao) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Perfil> criteria = builder.createQuery(getClazz());

		Root<Perfil> root = criteria.from(Perfil.class);

		criteria.select(root).where(builder.like(builder.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%"));

		TypedQuery<Perfil> query = entityManager.createQuery(criteria);

		Perfil perfil = null;
		
		try {
			perfil = query.getSingleResult();
		}
		catch ( NoResultException ex ) {}
		
		return perfil;
	}
	
	public List<Perfil> listPerfil(int pageNumber,  int pageSize, String descricao){
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Perfil> criteria = builder.createQuery(getClazz());

		Root<Perfil> root = criteria.from(getClazz());
		
		criteria.select(root);
		
		List<Predicate> predicateList = new ArrayList<>();
		
		if(StringUtils.isNotEmpty(descricao)) {
			predicateList.add(builder.like(builder.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%"));
		}
		
		criteria.where(predicateList.toArray(new Predicate[predicateList.size()]));
		
		criteria.orderBy(builder.asc(root.get("id")));
		
		TypedQuery<Perfil> query = entityManager.createQuery(criteria);
		
		if(pageNumber > 0) {
			query.setFirstResult(pageNumber);
		}
		
		if(pageSize > 0) {
			query.setMaxResults(pageSize);
		}
		
		return  query.getResultList();
	}
	
	public Long countPerfil(String descricao){
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);

		Root<Perfil> root = countQuery.from(Perfil.class);
		
		List<Predicate> predicateList = new ArrayList<>();
		
		if(StringUtils.isNotEmpty(descricao)) {
			predicateList.add(builder.like(builder.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%"));
		}
		
		Predicate[] predArray = new Predicate[predicateList.size()];
		predicateList.toArray(predArray);

		countQuery.select(builder.count(root));

		countQuery.where(predArray);
		
		TypedQuery<Long> query = entityManager.createQuery(countQuery);

		return  query.getSingleResult();
	}
	
	
}
