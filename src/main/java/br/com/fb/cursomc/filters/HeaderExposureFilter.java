package br.com.fb.cursomc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class HeaderExposureFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// Esse casting é safe pq toda resposta desse backend sera do tipo HttpServletResponse
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.addHeader("access-control-expose-headers", "location");// Para o Cors
		chain.doFilter(request, response);
	}
	
}
