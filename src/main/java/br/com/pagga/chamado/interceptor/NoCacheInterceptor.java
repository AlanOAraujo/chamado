package br.com.pagga.chamado.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.Intercepts;

@Intercepts( after = EntityManagerTransactionInterceptor.class )
public class NoCacheInterceptor {

    private final HttpServletResponse response;

    /**
     * @deprecated CDI eyes only
     */
    protected NoCacheInterceptor(){
        this(null);
    }

    @Inject
    public NoCacheInterceptor(HttpServletResponse response) {
        this.response = response;
    }

    @BeforeCall
    public void intercept() {
        response.setHeader("Expires", "Wed, 31 Dec 1969 21:00:00 GMT");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        
        response.addHeader("X-XSS-Protection", "1; mode=block");
        response.addHeader("X-Frame-Options", "SAMEORIGIN");
        response.addHeader("X-Download-Options", "noopen");
		response.addHeader("X-Content-Type-Options", "nosniff");
    }
}