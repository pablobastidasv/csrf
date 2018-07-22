package co.pablob.csrf.boundary;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import co.pablob.csrf.control.AuthenticatedRequestValidator;
import co.pablob.csrf.control.MethodAllowedValidator;

import static co.pablob.csrf.entity.Constants.CSRF_COOKIE_NAME;
import static co.pablob.csrf.entity.Constants.CSRF_HEADER_NAME;

@Provider
@PreMatching
@Priority(CsrfValidatorFilter.PRIORITY)
public class CsrfValidatorFilter implements ContainerRequestFilter {

    static final int PRIORITY = Priorities.AUTHENTICATION + 1;

    private MethodAllowedValidator methodAllowedValidator;
    private AuthenticatedRequestValidator authenticatedRequestValidator;

    @Inject
    public void setMethodAllowedValidator(MethodAllowedValidator methodAllowedValidator) {
        this.methodAllowedValidator = methodAllowedValidator;
    }

    @Inject
    public void setAuthenticatedRequestValidator(AuthenticatedRequestValidator authenticatedRequestValidator) {
        this.authenticatedRequestValidator = authenticatedRequestValidator;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        if (shouldNotValidateMethod(requestContext.getMethod())) {
            return; // Continue
        }

        if (nonAuthenticatedRequest(requestContext)) {
            return; // Continue
        }

        final Cookie cookie = requestContext.getCookies().get(CSRF_COOKIE_NAME);

        if (isCookieValid(cookie)) {
            String headerValue = requestContext.getHeaderString(CSRF_HEADER_NAME);
            if (nonValidValue(headerValue)) {
                headerValue = getCsrfParameter(requestContext)
                        .orElse(null);
            }

            if (isValidValue(headerValue)
                    && cookie.getValue().equals(headerValue)) {
                return; // Continue
            }
        }

        fail(requestContext);
    }

    private boolean nonValidValue(String headerValue) {
        return !isValidValue(headerValue);
    }

    private boolean isValidValue(String headerValue) {
        return Objects.nonNull(headerValue) && !headerValue.isEmpty();
    }

    private boolean isCookieValid(Cookie cookie) {
        return Objects.nonNull(cookie)
                && isValidValue(cookie.getValue());
    }

    private boolean shouldNotValidateMethod(String method) {
        return !methodAllowedValidator.isMethodAllowed(method);
    }

    private Optional<String> getCsrfParameter(ContainerRequestContext requestContext) {
        return Optional.empty();
    }

    private void fail(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(403).build());
    }

    private boolean nonAuthenticatedRequest(ContainerRequestContext requestContext) {
        return !authenticatedRequestValidator.isRequestAuthenticated(requestContext);
    }
}
