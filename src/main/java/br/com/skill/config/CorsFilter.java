package br.com.skill.config;

import java.util.Arrays;
import java.util.List;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {
    
	private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
	        "http://localhost:3000",
	        "http://localhost:4200",
	        "http://localhost:5173",
	        "http://localhost:8000",
	        "http://localhost:5174"
	    );

	    @Override
	    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
	        
	        // 1. Pega a origem da requisição
	        String origin = requestContext.getHeaderString("Origin");

	        // 2. Se a origem existe e está na nossa lista de permitidas
	        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
	            // Define Access-Control-Allow-Origin para *A ORIGEM SOLICITANTE*
	            responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", origin);
	            
	            responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
	            responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", 
	                    "origin, content-type, accept, authorization");
	            responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", 
	                    "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	        }
	    }
}
