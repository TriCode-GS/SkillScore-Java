package br.com.skill.config;

import java.util.Arrays;
import java.util.List;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
        "http://localhost:3000",
        "http://localhost:4200",
        "http://localhost:5173",
        "http://localhost:5174",
        "http://localhost:5175",
        "http://127.0.0.1:5173",
        "http://127.0.0.1:5174",
        "http://127.0.0.1:5175",
        "http://localhost:8000",
        "https://skillscore.vercel.app",
        "https://skillscore-java.onrender.com"
    );

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String origin = requestContext.getHeaderString("Origin");
        
        // Tratar requisições OPTIONS (preflight)
        if ("OPTIONS".equals(requestContext.getMethod())) {
            if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
                Response.ResponseBuilder response = Response.ok();
                response.header("Access-Control-Allow-Origin", origin);
                response.header("Access-Control-Allow-Credentials", "true");
                response.header("Access-Control-Allow-Headers",
                        "Origin, Content-Type, Accept, Authorization, X-Requested-With");
                response.header("Access-Control-Allow-Methods",
                        "GET, POST, PUT, DELETE, OPTIONS, HEAD");
                response.header("Access-Control-Max-Age", "3600");
                requestContext.abortWith(response.build());
            }
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        String origin = requestContext.getHeaderString("Origin");
        
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
            responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
            responseContext.getHeaders().add("Access-Control-Allow-Headers",
                    "Origin, Content-Type, Accept, Authorization, X-Requested-With");
            responseContext.getHeaders().add("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            responseContext.getHeaders().add("Access-Control-Max-Age", "3600");
        }
    }
}