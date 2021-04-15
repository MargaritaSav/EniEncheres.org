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
urlPatterns = { "/profil/*", "/nouvellevente", "/admin" })
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
		//setting the right encoding of parameters
		httpRequest.setCharacterEncoding("UTF-8");
		boolean wasFiltered = false;
		Utilisateur user = (Utilisateur) session.getAttribute("user");
		System.out.println(user!=null);
		System.out.println(httpRequest.getParameter("pseudo"));
		if(session.getAttribute("session") != null && session.getAttribute("session").equals("on") && user != null) {
			//only active users can sell stuff
			if(httpRequest.getServletPath().toLowerCase().contains("nouvellevente")) {
				if(user.isActive()){
					wasFiltered = true;
					chain.doFilter(request, response);
				} 
			} else if(httpRequest.getServletPath().toLowerCase().contains("admin")){
				//only admin can access the admin page
				if(user.isAdministrateur()){
					wasFiltered = true;
					chain.doFilter(request, response);
				}
			} else {
				//all registered users can access their or others' profile
				wasFiltered = true;
				chain.doFilter(request, response);
			}
		} else {
			//unregistered users can only access profile page with a pseudo parameter ??
			if(httpRequest.getServletPath().toLowerCase().contains("profil") 
					&& !httpRequest.getServletPath().toLowerCase().contains("edit") 
					&& httpRequest.getParameter("pseudo")!= null){
				wasFiltered = true;
				chain.doFilter(request, response);
			}
		}
		if(!wasFiltered) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/accueil");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
