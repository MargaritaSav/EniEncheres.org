package org.eni.encheres.servlets.filters;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eni.encheres.bo.Utilisateur;


/**
 * Servlet Filter implementation class ConnexionFilter
 */
@WebFilter(dispatcherTypes = {
		DispatcherType.REQUEST, 
		DispatcherType.FORWARD, 
		DispatcherType.INCLUDE, 
		DispatcherType.ERROR
}, 
urlPatterns = { "/profil/*", "/nouvellevente" })
public class ConnexionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ConnexionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		httpRequest.setCharacterEncoding("UTF-8");

		if(httpRequest.getServletPath().toLowerCase().contains("nouvellevente")) {
			if(session.getAttribute("session") != null && session.getAttribute("session").equals("on") && ((Utilisateur) session.getAttribute("user")).isActive())
			{
				chain.doFilter(request, response);
			} else {
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/accueil");
			}
		} else {
			if(session.getAttribute("session") != null && session.getAttribute("session").equals("on") || httpRequest.getParameter("pseudo")!= null)
			{
				chain.doFilter(request, response);
			} else {
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/connexion");
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
