package org.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bll.utilistaeurs.UtilisateurManager;
import org.eni.encheres.bll.utilistaeurs.UtilisateurManagerSingl;
import org.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletAdmin
 */
@WebServlet("/admin")
public class ServletAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static UtilisateurManager um = UtilisateurManagerSingl.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(((Utilisateur) request.getSession().getAttribute("user")).isAdministrateur()) {
//			ArrayList<Utilisateur> users = um.selectAllUtilisateurs();
//			request.setAttribute("users", users);
			ArrayList<Utilisateur> users = new ArrayList<>();
			try {
				users.add(um.login("toto", "toto"));
				users.add(um.login("lulu", "lulu"));
				request.setAttribute("users", users);

			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/admin.jsp");
			rd.forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/accueil");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.valueOf(request.getParameter("noUser"));
		switch(request.getParameter("action")) {
		case "desactiver":
//			try {
//				um.desactiverUtilisateur(id);
				request.setAttribute("success", "Utilisateur désactivé");

//			} catch (BusinessException e) {
//				request.setAttribute("error", e.getMessage());
//			}
			break;
		case "supprimer":
//			try {
//			um.desactiverUtilisateur(id);
			request.setAttribute("success", "Utilisateur supprimé");

	//		} catch (BusinessException e) {
	//			request.setAttribute("error", e.getMessage());
	//		}
			break;
		}
		doGet(request, response);
	}

}
