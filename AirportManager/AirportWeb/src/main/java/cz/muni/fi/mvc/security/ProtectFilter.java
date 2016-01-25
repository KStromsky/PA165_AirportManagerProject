package cz.muni.fi.mvc.security;

import cz.muni.fi.airportapi.dto.StewardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Protects administrative part of application.
 *
 * @author Jakub Stromský
 */
@WebFilter(urlPatterns = {"/", "/steward/list", "/steward", "/steward/detail", "/steward/detail/*", 
    "/steward/edit/*", "/steward/update/*", "/flight/*", "/destination/*", "/airplane/*"})
public class ProtectFilter implements Filter {

    final static Logger log = LoggerFactory.getLogger(ProtectFilter.class);


    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;

        Object auth = request.getSession().getAttribute("authenticated");
        if (auth != null && auth instanceof StewardDTO) {
            StewardDTO steward = (StewardDTO) auth;
            if (!steward.isAdmin() 
                    && request.getRequestURL().toString().matches(".*new.*|.*create.*|.*update.*|.*edit.*")
                    && !request.getRequestURL().toString().matches(".*/steward/.*/" + steward.getId().toString())) {
                log.warn("requires admin to access");
            } else {
                chain.doFilter(request, response);
                return;
            }
        } else {
            log.warn("authentication fail");
        }
        response401(response, request);
    }

    private void response401(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect(request.getContextPath()+"/login?access=False");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
