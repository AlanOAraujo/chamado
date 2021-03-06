package br.com.pagga.chamado.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import br.com.pagga.chamado.dao.filter.ChamadoFilter;
import br.com.pagga.chamado.model.Atributo;
import br.com.pagga.chamado.model.Chamado;
import br.com.pagga.chamado.model.StatusChamado;
import br.com.pagga.chamado.model.TipoChamado;
import br.com.pagga.chamado.model.Usuario;
import br.com.pagga.chamado.security.UsuarioLogado;
import br.com.pagga.chamado.service.LoginService;
import br.com.pagga.chamado.util.StringUtil;

public class ChamadoDAO extends AbstractDAO<Chamado> {
	
	@Inject
	private UsuarioDAO usuarioDAO;

	public ChamadoDAO() {
	}
	
	@Inject
	public ChamadoDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	public List<Chamado> findByUsuario(Usuario usuario) {


		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Chamado> criteria = builder.createQuery(getClazz());

		Root<Chamado> root = criteria.from(getClazz());

		criteria.select(root).where(builder.equal(root.get("usuario"), usuario));

		TypedQuery<Chamado> query = entityManager.createQuery(criteria);

		List<Chamado> chamados = null; 
				
		try {
			chamados = query.getResultList();
		}
		catch (NoResultException ex) {}
				
		return chamados;
	}
	
	public List<Chamado> findByStatus(StatusChamado status) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Chamado> criteria = builder.createQuery(getClazz());

		Root<Chamado> root = criteria.from(getClazz());

		criteria.select(root).where(builder.equal(root.get("status"), status));

		TypedQuery<Chamado> query = entityManager.createQuery(criteria);

		List<Chamado> chamados = null; 
				
		try {
			chamados = query.getResultList();
		}
		catch (NoResultException ex) {
			
		}
				
		return chamados;
		
	}
	
	public List<Chamado> findByTipo(TipoChamado tipo) {
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Chamado> criteria = builder.createQuery(getClazz());

		Root<Chamado> root = criteria.from(getClazz());

		criteria.select(root).where(builder.equal(root.get("tipo"), tipo));

		TypedQuery<Chamado> query = entityManager.createQuery(criteria);

		List<Chamado> chamados = null; 
				
		try {
			chamados = query.getResultList();
		}
		catch (NoResultException ex) {
			
		}
				
		return chamados;
		
	}
	
	
	public List<Chamado> findByFilter(int pageNumber,  int pageSize, StatusChamado statusChamado, ChamadoFilter filter, UsuarioLogado usuarioLogado) throws ParseException {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Chamado> criteria = builder.createQuery(getClazz());

		Root<Chamado> root = criteria.from(getClazz());
		
		aplicaFilter(builder, root, criteria, statusChamado, filter, usuarioLogado);
		
		if(statusChamado == StatusChamado.ABERTO) {
			criteria.orderBy(builder.desc(root.get("id")));
		}
		
		if(statusChamado == StatusChamado.EM_ATENDIMENTO) {
			criteria.orderBy(builder.asc(root.get("id")));
		}
		
		if(statusChamado == StatusChamado.FECHADO) {
			criteria.orderBy(builder.desc(root.get("dataFechamento")));
		}

		TypedQuery<Chamado> query = entityManager.createQuery(criteria);

		if(pageNumber > 0) {
			query.setFirstResult(pageNumber);
		}
		
		if(pageSize > 0) {
			query.setMaxResults(pageSize);
		}
		
		List<Chamado> chamados = new ArrayList<>(); 
				
		try {
			
			chamados = query.getResultList();
		}
		catch (NoResultException ex) {}
				
		return chamados;
	}

	public Long countChamadoFiltro(StatusChamado statusChamado, ChamadoFilter filter, UsuarioLogado usuarioLogado) throws ParseException{
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);

		Root<Chamado> root = countQuery.from(Chamado.class);
		
		countQuery.select(builder.count(root));
		
		aplicaFilter(builder, root, countQuery, statusChamado, filter, usuarioLogado);

		TypedQuery<Long> query = entityManager.createQuery(countQuery);

		return  query.getSingleResult();
	}
	
	private static void aplicaFilter(CriteriaBuilder builder, Root<Chamado> root, CriteriaQuery<?> criteria, 
			StatusChamado statusChamado, ChamadoFilter filter, UsuarioLogado usuarioLogado) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		List<Predicate> predicateList = new ArrayList<>();
		
		List<Atributo> listPermissoes = LoginService.autentificarPerfil(usuarioLogado);
		
		String permissaoAllTickets = " ";
		
		for (Atributo atributo : listPermissoes) {
			if(atributo.getCodAtributo().equals("ALL_TCK")) {
				permissaoAllTickets = atributo.getCodAtributo();
			}
		}
		
		if(permissaoAllTickets == " " && !permissaoAllTickets.equals("ALL_TCK")) {
			Path<Usuario> user = root.get("usuario");
			predicateList.add(builder.equal(user, usuarioLogado.getUsuario()));
		}
		
		builder.and(builder.equal(root.get("status"), statusChamado));
		
		if ( filter.getId() != null ) {
			
			predicateList.add(builder.equal(root.get("id"), filter.getId()));
		}
		
		if ( !StringUtil.isNullOrEmpty(filter.getTitulo()) ) {
			
			predicateList.add(builder.like(builder.lower(root.get("titulo")),"%" + filter.getTitulo().toLowerCase()  + "%"));
		}
		
		if ( !StringUtil.isNullOrEmpty(filter.getDescricao()) ) {
			
			predicateList.add(builder.like( builder.lower(root.get("descricao")),"%" + filter.getDescricao().toLowerCase() + "%"));
		}
		
		if ( !StringUtil.isNullOrEmpty(filter.get_dataAbertura()) ) {
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(filter.get_dataAbertura()));
			c.add(Calendar.HOUR, 23);
			c.add(Calendar.MINUTE, 59);
			c.add(Calendar.SECOND, 59);
			c.add(Calendar.MILLISECOND, 999);
			
			Date d = c.getTime();
			
			predicateList.add(builder.between(root.get("dataAbertura"), sdf.parse(filter.get_dataAbertura()), d));
		}
		
		if ( !StringUtil.isNullOrEmpty(filter.get_dataFechamento()) ) {
			
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(filter.get_dataFechamento()));
			c.add(Calendar.HOUR, 23);
			c.add(Calendar.MINUTE, 59);
			c.add(Calendar.SECOND, 59);
			c.add(Calendar.MILLISECOND, 999);
			
			Date d = c.getTime();
			
			predicateList.add( builder.between(root.get("dataFechamento"), sdf.parse(filter.get_dataFechamento()), d));
		}
		
		if (!StringUtil.isNullOrEmpty(filter.getDataPrevisao())) {
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(filter.getDataPrevisao()));
			c.add(Calendar.HOUR, 23);
			c.add(Calendar.MINUTE, 59);
			c.add(Calendar.SECOND, 59);
			c.add(Calendar.MILLISECOND, 999);
			
			Date d = c.getTime();
			
			predicateList.add(builder.between(root.get("dataPrevisao"), sdf.parse(filter.getDataPrevisao()), d));
			
		}
		
		if ( filter.getTipo() != null ) {
			
			predicateList.add(builder.equal(root.get("tipo"), filter.getTipo()));
		}
		
		if ( !StringUtil.isNullOrEmpty(filter.getNomeUsuario()) ) {
			
			predicateList.add(builder.like( builder.upper( root.get("usuario").get("nome")),"%"+filter.getNomeUsuario().toUpperCase()+"%" ));
		}
		
		if ( !StringUtil.isNullOrEmpty(filter.getNomeUsuarioFechamento()) ) {
			
			predicateList.add(builder.like(builder.upper(root.get("usuarioFechamento").get("nome")),"%"+filter.getNomeUsuarioFechamento().toUpperCase()+"%"));			
		}
		
		Predicate predicateAnd = builder.and(builder.equal(root.get("status"), statusChamado));
		
		Predicate predicateOr = predicateList.isEmpty() ? builder.equal(root.get("status"), statusChamado) : builder.or(predicateList.toArray(new Predicate[] {}));
		
		criteria.where(builder.and(predicateAnd, predicateOr));
		
	}
	
	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

}
