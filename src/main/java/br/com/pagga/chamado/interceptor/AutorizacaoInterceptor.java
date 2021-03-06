package br.com.pagga.chamado.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.pagga.chamado.security.Open;
import br.com.pagga.chamado.security.UsuarioLogado;

@Intercepts
@RequestScoped
public class AutorizacaoInterceptor {

	private UsuarioLogado usuarioLogado;
	private ControllerMethod method;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public AutorizacaoInterceptor() {
	}
	
	@Inject
	public AutorizacaoInterceptor(UsuarioLogado usuarioLogado, ControllerMethod method, HttpServletRequest request, HttpServletResponse response) {
		this.usuarioLogado = usuarioLogado;
		this.method = method;
		this.request = request;
		this.response = response;
	}
	
	@Accepts
	public boolean accept(){
		return !method.containsAnnotation(Open.class);
	}
	
	@AroundCall
	public void intercept(SimpleInterceptorStack stack) throws IOException{
		
		if(!usuarioLogado.isLogado()){
			
			if ( "application/json".equals(request.getContentType()) ) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.setContentType("application/json");
				PrintWriter pw = response.getWriter();
				pw.println("{message: 'Acesso negado'}");
				pw.flush();
			}
		}
		else {
			stack.next();
		}
	}
	
}
