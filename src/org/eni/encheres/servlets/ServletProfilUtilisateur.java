package org.eni.encheres.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eni.encheres.bll.utilistaeurs.UtilisateurManager;
import org.eni.encheres.bll.utilistaeurs.UtilisateurManagerSingl;
import org.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletProfilUtilisateur
 */
@WebServlet("/profil")
public class ServletProfilUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletProfilUtilisateur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("pseudo") != null) {
			try {
				UtilisateurManager um = UtilisateurManagerSingl.getInstance();
				Utilisateur user = um.getUtilisateurByPseudo(request.getParameter("pseudo"));
				request.setAttribute("userProfil", user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profilUtilisateur/userProfile.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

}
