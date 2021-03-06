package br.com.pagga.chamado.interceptor;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.http.MutableResponse;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.validator.Validator;

@Intercepts
public class EntityManagerTransactionInterceptor implements EntityManagerInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityManagerTransactionInterceptor.class);
	private EntityManager entityManager;
	private Validator validator;
	private MutableResponse response;

	/**
	 * @deprecated CDI eyes only
	 */
	public EntityManagerTransactionInterceptor() {
	}

	@Inject
	public EntityManagerTransactionInterceptor(EntityManager entityManager, Validator validator, MutableResponse response) {
		this.entityManager = entityManager;
		this.validator = validator;
		this.response = response;
	}

	@AroundCall
	@Override
	public void intercept(SimpleInterceptorStack stack) {
		addRedirectListener();

		EntityTransaction transaction = entityManager.getTransaction();
		
		transaction.begin();
		
		LOGGER.debug("tx was started");

		try {
			stack.next();
			commit(transaction);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
				LOGGER.debug("tx was rolled back");
			} else {
				LOGGER.debug("tx isn't active");
			}
		}
	}

	private void commit(EntityTransaction transaction) {
		if (!validator.hasErrors() && transaction.isActive()) {
			transaction.commit();
			LOGGER.debug("tx was commited");
		} else {
			LOGGER.debug("tx wasn't commited, hasError {}, isActive {}", validator.hasErrors(), transaction.isActive());
		}
	}

	/**
	 * We force the commit before the redirect, this way we can abort the redirect if a database error occurs.
	 */
	private void addRedirectListener() {
		response.addRedirectListener(new MutableResponse.RedirectListener() {
			@Override
			public void beforeRedirect() {
				commit(entityManager.getTransaction());
			}
		});
	}
	
}