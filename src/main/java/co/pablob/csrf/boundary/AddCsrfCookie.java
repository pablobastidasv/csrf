package co.pablob.csrf.boundary;

import java.util.UUID;

import javax.servlet.ServletContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.ext.Provider;

@Provider
public class AddCsrfCookie implements ContainerResponseFilter {
    private static final String XSRF_COOKIE = "XSRF-TOKEN";
    private static final String SET_COOKIE_HEADER = "Set-Cookie";

    @Context
    ServletContext servletContext;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        NewCookie csrfCookie = new NewCookie(XSRF_COOKIE,
                UUID.randomUUID().toString(),
                servletContext.getContextPath(),
                null,
                1,
                null,
                -1,
                null,
                true,
                true);

        if (nonCsrfPresent(responseContext)) {
            responseContext.getHeaders().add(SET_COOKIE_HEADER, csrfCookie.toString());
        }

    }

    private boolean nonCsrfPresent(ContainerResponseContext responseContext) {
        return !isCsrfPresent(responseContext);
    }

    private boolean isCsrfPresent(ContainerResponseContext responseContext) {
        return responseContext.getCookies().keySet().stream()
                .filter(k -> k.equalsIgnoreCase(XSRF_COOKIE))
                .map(k -> responseContext.getCookies().get(k))
                .anyMatch(c -> c.getName().equalsIgnoreCase(XSRF_COOKIE));
    }
}
