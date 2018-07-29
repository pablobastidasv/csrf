package co.pablob.csrf.control;

import javax.enterprise.inject.Specializes;
import javax.ws.rs.core.UriInfo;

/**
 * Validator to determine if path provided is allowed or not.
 * <p>
 * When a Path is allowed, this means the CSRF protection does not apply to this path.
 * </p>
 *
 * <p>
 * Default behaviour of the validator is not allow any path and perform the CSRF validation
 * on all request.
 * </p>
 *
 * <p>
 * To override default behaviour create a new class extending {@link PathAllowedValidator}
 * and override the behaviour of method {@link PathAllowedValidator#isPathAllowed(String)}.
 * </p>
 *
 * <p>
 * That method received a variable which value is the path of the request, this path is
 * provided by method {@link UriInfo#getPath()}.
 * </p>
 *
 * Last but not least, the new class must be annotated with @{@link Specializes}.
 *
 * <pre>
 * @Specializes
 * public class CustomPathValidator extends PathAllowedValidator {
 *
 *    public boolean isPathAllowed(String path){
 *        return "/login".equals(path);
 *    }
 * </pre>
 *
 * Above example tells the system not perform CSRF validation when path "/login" is requested.
 */
public class PathAllowedValidator {

    /**
     * Validate if given path is allowed to continue without
     * perform CSRF validation.
     *
     * @param path Value of accessed path URL
     * @return True if validation does not be performed in given path.
     */
    public boolean isPathAllowed(String path) {
        return Boolean.FALSE;
    }
}
