package co.pablob.csrf.boundary;

import java.util.UUID;

import javax.servlet.ServletContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.ext.Provider;

import static co.pablob.csrf.entity.Constants.SET_COOKIE_HEADER_NAME;
import static co.pablob.csrf.entity.Constants.CSRF_COOKIE_NAME;

@Provider
public class AddCsrfCookieFilter implements ContainerResponseFilter {

    @Context
    ServletContext servletContext;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {

        NewCookie csrfCookie = new NewCookie(CSRF_COOKIE_NAME,
                UUID.randomUUID().toString()
                , servletContext.getContextPath(),
                null,
                null,
                NewCookie.DEFAULT_MAX_AGE,
                false, true
        );

        responseContext.getHeaders().add(SET_COOKIE_HEADER_NAME, csrfCookie.toString());

    }

}
