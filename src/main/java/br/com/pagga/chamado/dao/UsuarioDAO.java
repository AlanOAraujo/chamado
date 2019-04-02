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

import br.com.pagga.chamado.dao.filter.UsuarioFilter;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.util.StringUtil;

public class UsuarioDAO extends AbstractDAO<Usuario> {

	public UsuarioDAO() {
	}
	
	@Inject
	public UsuarioDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	public Usuario findByCPF( String cpf ) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Usuario> criteria = builder.createQuery(getClazz());

		Root<Usuario> root = criteria.from(getClazz());

		criteria.select(root).where(builder.equal(root.get("cpf"), cpf));

		TypedQuery<Usuario> query = entityManager.createQuery(criteria);

		Usuario usuario = null; 
				
		try {
			usuario = query.getSingleResult();
		}
		catch (NoResultException ex) {}
				
		return usuario;
	}
	
	public List<Usuario> findByNome( String nome ) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Usuario> criteria = builder.createQuery(getClazz());

		Root<Usuario> root = criteria.from(getClazz());

		criteria.select(root).where(builder.like(builder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));

		TypedQuery<Usuario> query = entityManager.createQuery(criteria);

		List<Usuario> usuarios = null; 
				
		try {
			usuarios = query.getResultList();
		}
		catch (NoResultException ex) {
			return null;
		}
				
		return usuarios;
	}
	
	public Usuario findByEmail(String email) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Usuario> criteria = builder.createQuery(getClazz());

		Root<Usuario> root = criteria.from(getClazz());

		criteria.select(root).where(builder.equal(root.get("email"), email));

		TypedQuery<Usuario> query = entityManager.createQuery(criteria);

		Usuario usuario = null;
		
		try {
			usuario = query.getSingleResult();
		}
		catch ( NoResultException ex ) {}
		
		return usuario;
	}
	
	public List<Usuario> listUsuario(int pageNumber,  int pageSize, UsuarioFilter filter){
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Usuario> criteria = builder.createQuery(getClazz());

		Root<Usuario> root = criteria.from(getClazz());
		
		criteria.select(root);
		
		List<Predicate> predicateList = new ArrayList<>();
		
		if(filter.getId() != null) {
			predicateList.add(builder.equal(root.get("id"), filter.getId()));
		}
		
		if(!StringUtil.isNullOrEmpty(filter.getNome())) {
			predicateList.add(builder.like(builder.lower(root.get("nome")), "%" + filter.getNome().toLowerCase() + "%"));
		}
		
		if(!StringUtil.isNullOrEmpty(filter.getCpf())) {
			predicateList.add(builder.equal(root.get("cpf"), filter.getCpf()));
		}
		
		if(!StringUtil.isNullOrEmpty(filter.getEmail())) {
			predicateList.add(builder.like(builder.lower(root.get("email")), "%" + filter.getEmail() + "%"));
		}
		
		criteria.where(predicateList.toArray(new Predicate[predicateList.size()]));
		
		criteria.orderBy(builder.asc(root.get("id")));
		
		TypedQuery<Usuario> query = entityManager.createQuery(criteria);
		
		if(pageNumber > 0) {
			query.setFirstResult(pageNumber);
		}
		
		if(pageSize > 0) {
			query.setMaxResults(pageSize);
		}
		
		return  query.getResultList();
	}
	
	public Long countUsuario(UsuarioFilter filter){
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);

		Root<Usuario> root = countQuery.from(Usuario.class);
		
		List<Predicate> predicateList = new ArrayList<>();
		
		if(filter.getId() != null) {
			predicateList.add(builder.equal(root.get("id"), filter.getId()));
		}
		
		if(!StringUtil.isNullOrEmpty(filter.getNome())) {
			predicateList.add(builder.like(builder.lower(root.get("nome")), "%" + filter.getNome().toLowerCase() + "%"));
		}
		
		if(!StringUtil.isNullOrEmpty(filter.getCpf())) {
			predicateList.add(builder.equal(root.get("cpf"), filter.getCpf()));
		}
		
		if(!StringUtil.isNullOrEmpty(filter.getEmail())) {
			predicateList.add(builder.equal(root.get("email"), filter.getEmail()));
		}
		
		Predicate[] predArray = new Predicate[predicateList.size()];
		predicateList.toArray(predArray);

		countQuery.select(builder.count(root));

		countQuery.where(predArray);
		
		TypedQuery<Long> query = entityManager.createQuery(countQuery);

		return  query.getSingleResult();
	}
	
	public Usuario autentificaLogin(Usuario usuario) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Usuario> criteria = builder.createQuery(getClazz());

		Root<Usuario> root = criteria.from(getClazz());
		
		List<Predicate> predicateList = new ArrayList<>();
		
		if(!StringUtil.isNullOrEmpty(usuario.getCpf())) {
			predicateList.add(builder.equal(root.get("cpf"), usuario.getCpf()));
		}
		
		if(!StringUtil.isNullOrEmpty(usuario.getSenha())) {
			predicateList.add(builder.equal(root.get("senha"), usuario.getSenha()));
		}
		
		criteria.where(predicateList.toArray(new Predicate[predicateList.size()]));
		
		TypedQuery<Usuario> query = entityManager.createQuery(criteria);
		
		Usuario loginUser = null;
		
		try {
			loginUser = query.getSingleResult();
		} catch (NoResultException e) {}
		return loginUser;
		
	}
	
}
