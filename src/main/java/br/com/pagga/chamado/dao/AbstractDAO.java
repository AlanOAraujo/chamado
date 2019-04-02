package br.com.pagga.chamado.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.pagga.chamado.model.Model;

public abstract class AbstractDAO<T extends Model<Long>> {

	private Class<T> clazz = null;
	
	protected EntityManager entityManager;

	public AbstractDAO() {
	}
	
	@SuppressWarnings("unchecked")
	public AbstractDAO(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
		this.clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void save( T t ) {
		
		if ( t.getId() == null || t.getId() == 0 )
			entityManager.persist(t);
		else
			entityManager.merge(t);
	}
	
	public T findById( long i ) {
		
		/*T t = entityManager.find(clazz, id);
		
		return t;*/
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<T> criteria = builder.createQuery(clazz);

		Root<T> root = criteria.from(clazz);

		criteria.select(root).where(builder.equal(root.get("id"),i));

		TypedQuery<T> query = entityManager.createQuery(criteria);

		T t = null;

		try {
			t = query.getSingleResult();
		}
		catch (NoResultException ex) {}
				
		return t;
		
	}
	
	public void remove(T t) {
		
		entityManager.remove(t);
	}
	
	public void removeById( Long id ) {
		
		T t = this.findById(id);
		
		if ( t != null )
			this.remove(t);
	}
	
	public List<T> findAll(){
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<T> criteria = builder.createQuery(clazz);
		
		Root<T> root = criteria.from(clazz);
		
		criteria.select(root);
		
		TypedQuery<T> query = entityManager.createQuery(criteria);
		
		List<T> list = null;
		
		try {
			list = query.getResultList();
		}
		catch (NoResultException ex) {}
		
		return list;
	}
	
	protected Class<T> getClazz(){
		return clazz;
	}
}
