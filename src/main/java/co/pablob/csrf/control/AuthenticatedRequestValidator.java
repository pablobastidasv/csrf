package co.pablob.csrf.control;

import java.util.List;
import java.util.Objects;

import javax.ws.rs.container.ContainerRequestContext;

import static co.pablob.csrf.entity.Constants.AUTHORIZATION_HEADER_NAME;

public class AuthenticatedRequestValidator {

    public boolean isRequestAuthenticated(ContainerRequestContext requestContext){

        final List<String> authorizationHeader = requestContext.getHeaders()
                .get(AUTHORIZATION_HEADER_NAME);

        return Objects.nonNull(authorizationHeader)
                && !authorizationHeader.isEmpty();
    }
}
