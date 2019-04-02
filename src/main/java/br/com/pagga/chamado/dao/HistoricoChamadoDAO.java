package br.com.pagga.chamado.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.pagga.chamado.model.Atributo;
import br.com.pagga.chamado.model.HistoricoChamado;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.security.UsuarioLogado;
import br.com.pagga.chamado.service.LoginService;

public class HistoricoChamadoDAO extends AbstractDAO<HistoricoChamado> {

	public HistoricoChamadoDAO() {
	}
	
	@Inject
	public HistoricoChamadoDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public List<HistoricoChamado> listHistorico(int pageNumber,  int pageSize, UsuarioLogado usuarioLogado){
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<HistoricoChamado> criteria = builder.createQuery(getClazz());
		
		Root<HistoricoChamado> root = criteria.from(getClazz());
		
		/******************************************************/
		
		List<Atributo> listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissaoAllTickets = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("ALL_TCK")) {
				permissaoAllTickets = atributo.getCodAtributo();
			}
		}
		
		if(permissaoAllTickets == " " && !permissaoAllTickets.equals("ALL_TCK")) {
		
			Path<Usuario> pathUser = root.get("chamado").<Usuario>get("usuario");
			
			Predicate predicateUser = builder.equal(pathUser, usuarioLogado.getUsuario());
			
			criteria.where(predicateUser);
			
		}
		
		/******************************************************/
		
		criteria.orderBy(builder.desc(root.get("id")));
		
		TypedQuery<HistoricoChamado> query = entityManager.createQuery(criteria);
		
		if(pageNumber > 0) {
			query.setFirstResult(pageNumber);
		}
		
		if(pageSize > 0) {
			query.setMaxResults(pageSize);
		}
		
		return  query.getResultList();
	}
	
	public Long countHistorico(UsuarioLogado usuarioLogado){
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);

		Root<HistoricoChamado> root = countQuery.from(HistoricoChamado.class);
		
		countQuery.select(builder.count(root));
		
		/******************************************************/
		
		List<Atributo> listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissaoAllTickets = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("ALL_TCK")) {
				permissaoAllTickets = atributo.getCodAtributo();
			}
		}
		
		if(permissaoAllTickets == " " && !permissaoAllTickets.equals("ALL_TCK")) {
		
			Path<Usuario> pathUser = root.get("chamado").<Usuario>get("usuario");
			
			Predicate predicateUser = builder.equal(pathUser, usuarioLogado.getUsuario());
			
			countQuery.where(predicateUser);
			
		}
		
		/******************************************************/
		
		TypedQuery<Long> query = entityManager.createQuery(countQuery);

		return  query.getSingleResult();
	}
	
	
}
