package org.eni.encheres.servlets;

import java.io.IOException;
import java.util.regex.*;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eni.encheres.bll.utilistaeurs.UtilisateurManager;
import org.eni.encheres.bll.utilistaeurs.UtilisateurManagerSingl;
import org.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletInscription
 */
@WebServlet("/inscription")
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/inscription.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		System.out.println(request.getParameter("pseudo"));
		System.out.println(request.getParameter("password"));
		String pseudo = request.getParameter("pseudo");
		String mdp = request.getParameter("password");
		String mdp2 = request.getParameter("confirmation");
		String prenom = request.getParameter("prenom");
		String nom = request.getParameter("nom");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("cp");
		String ville = request.getParameter("ville");
		
		try {
		
			if(!mdp.equals(mdp2)) {
				throw new ServletException("Les mots de passe saisis ne correspondent pas !");
			}
			
			UtilisateurManager um = UtilisateurManagerSingl.getInstance();
			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setNom(nom);
			utilisateur.setPrenom(prenom);
			utilisateur.setPseudo(pseudo);
			utilisateur.setEmail(email);
			utilisateur.setCodePostal(codePostal);
			utilisateur.setRue(rue);
			utilisateur.setTelephone(tel);
			utilisateur.setVille(ville);
			um.addUtilisateur(utilisateur, mdp);
			HttpSession session = request.getSession();
			session.setAttribute("session", "on");
			session.setAttribute("user", utilisateur);
			response.sendRedirect(request.getContextPath() + "/accueil");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		}
	}
	
	

}
