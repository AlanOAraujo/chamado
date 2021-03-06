package br.com.pagga.chamado.db;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class EntityManagerProducer {

	private static final Logger log = LoggerFactory.getLogger(EntityManagerProducer.class);

	@Inject
	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	@PostConstruct
	public void init(){
		this.entityManager = entityManagerFactory.createEntityManager();
	}

	@Produces @RequestScoped
	public EntityManager getEntityManager(){

		log.debug("Criada sessão(EntityManager) {}...", this.entityManager.hashCode());

		return this.entityManager;
	}

	public void close(@Disposes EntityManager entityManager){

		entityManager.close();

		log.debug("Sessão(EntityManager) {} fechada com sucesso...", entityManager.hashCode());
	}
}
