package br.com.pagga.chamado.db;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EntityManagerFactoryProducer {

	private static final Logger log = LoggerFactory.getLogger(EntityManagerFactoryProducer.class);

	private EntityManagerFactory entityManagerFactory;

	@PostConstruct
	public void init(){

		try {
			log.info("Conectando-se ao banco de dados...");

			this.entityManagerFactory = Persistence.createEntityManagerFactory("default"); 

			log.info("Conexão com o banco de dados efetuado com sucesso...");
		} 
		catch (Exception ex) {

			ex.printStackTrace();

			log.error("Não foi possível conectar-se ao banco de dados...", ex);

			throw new RuntimeException("Exception building EntityManagerFactory: " + ex.getMessage(), ex);
		}
	}

	@Produces @ApplicationScoped
	public EntityManagerFactory getEntityManagerFactory() {

		log.info("Produzindo EntityManagerFactory {}...", entityManagerFactory.hashCode());

		return entityManagerFactory;
	}

	public void close( @Disposes EntityManagerFactory entityManagerFactory ){

		log.info("Fechando EntityManagerFactory {}...", entityManagerFactory.hashCode());

		entityManagerFactory.close();
	}
}