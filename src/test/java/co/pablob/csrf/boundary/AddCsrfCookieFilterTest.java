package co.pablob.csrf.boundary;


import org.junit.Test;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddCsrfCookieFilterTest {

    @Test
    public void csrfShouldBeAdded()  {
        // Given: A Filter and a response context
        AddCsrfCookieFilter addCsrfCookie = new AddCsrfCookieFilter();

        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        ContainerResponseContext responseContextMock = mock(ContainerResponseContext.class);
        when(responseContextMock.getHeaders()).thenReturn(headers);

        ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getContextPath()).thenReturn("/");
        addCsrfCookie.servletContext = servletContext;

        // When: when filter is excecuted
        addCsrfCookie.filter(null, responseContextMock);

        // Then

        // XSRF-TOKEN must be added as a Cookie Header
        final List<Object> xsrfCookieHeader = headers.get("Set-Cookie");
        assertNotNull(xsrfCookieHeader);

        String xsrfCookieHeaderValue = "";
        int xsrfCookieQty = 0;
        for (Object o : xsrfCookieHeader) {
            String cookie = o.toString();
            if(cookie.contains("XSRF-TOKEN")){
                xsrfCookieQty++;
                xsrfCookieHeaderValue = cookie;
            }
        }
        assertEquals(1, xsrfCookieQty);

        final NewCookie xsrfCookie = NewCookie.valueOf(xsrfCookieHeaderValue);

        // HttpOnly must be true
        assertTrue(xsrfCookie.isHttpOnly());

        // Path must be /
        assertEquals("/", xsrfCookie.getPath());
    }
}