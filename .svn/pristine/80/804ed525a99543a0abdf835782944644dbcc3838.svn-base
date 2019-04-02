package br.com.pagga.chamado.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	public static EntityManager createEntityManager() {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
		
		return factory.createEntityManager();
	}

}
