package br.com.pagga.chamado.observers;

import javax.enterprise.event.Observes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.events.VRaptorInitialized;

public class AplicacaoObserver {

	private static final Logger log = LoggerFactory.getLogger(AplicacaoObserver.class);
	
	public void iniciaVRaptor( @Observes VRaptorInitialized vRaptorInitialized ) {
		
		log.info("Aplicacao Chamado inicializada...");
	}
}
