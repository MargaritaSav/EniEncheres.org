package org.eni.encheres.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eni.encheres.bll.utilistaeurs.UtilisateurManager;
import org.eni.encheres.bll.utilistaeurs.UtilisateurManagerSingl;
import org.eni.encheres.bll.utilistaeurs.UtilisateursManagerImpl;
import org.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet("/connexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/connexion.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String mdp = request.getParameter("password");
		String resterConnecte = request.getParameter("resterConnecte");
		try {
			if(login.isEmpty() || mdp.isEmpty()) {
				throw new ServletException("Les champs ne peuvent pas etre vide");
			}
			
			UtilisateurManager um = UtilisateurManagerSingl.getInstance();
			Utilisateur utilisateur = um.login(login, mdp);
			if(utilisateur!= null) {
				HttpSession session = request.getSession();
				session.setAttribute("session", "on");
				session.setAttribute("user", utilisateur);
				//limiter la duree d'inactivite a 5min
				session.setMaxInactiveInterval(5 * 60);
				if(resterConnecte != null) {
					Cookie cookieLogin = new Cookie("eni_login", login);
				    response.addCookie( cookieLogin );
				}
				response.sendRedirect(request.getContextPath() + "/accueil");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		}
	}

}
