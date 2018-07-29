package co.pablob.csrf.control;

import java.util.List;
import java.util.Objects;

import javax.enterprise.inject.Specializes;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import static co.pablob.csrf.entity.Constants.AUTHORIZATION_HEADER_NAME;

/**
 * Validator to determine if the incoming request is authenticated.
 *
 * <p>
 *     By default the library determine an authenticated request when it has a header which key is
 *     <code>Authorization</code>.
 * </p>
 *
 * <p>
 *     To override default behaviour create a new class extending {@link AuthenticatedRequestValidator}
 *     and override the behaviour of method
 *     {@link AuthenticatedRequestValidator#isRequestAuthenticated(ContainerRequestContext)}.
 * </p>
 *
 * <p>
 *     Method {@link AuthenticatedRequestValidator#isRequestAuthenticated(ContainerRequestContext)} gives
 *     access to {@link ContainerRequestContext} received when {@link ContainerRequestFilter} run in the
 *     filter chain.
 * </p>
 *
 * Last but not least, the new class must be annotated with @{@link Specializes}.
 *
 */
public class AuthenticatedRequestValidator {

    /**
     * Validation method to determine if the request comes with authentication.
     *
     * @param requestContext Value comes from {@link ContainerRequestFilter}
     * @return True is the request is authenticated.
     */
    public boolean isRequestAuthenticated(ContainerRequestContext requestContext){

        final List<String> authorizationHeader = requestContext.getHeaders()
                .get(AUTHORIZATION_HEADER_NAME);

        return Objects.nonNull(authorizationHeader)
                && !authorizationHeader.isEmpty();
    }
}
