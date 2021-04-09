package org.eni.encheres.servlets;

import java.io.IOException;

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
 * Servlet implementation class ServletModificationProfil
 */
@WebServlet("/profil/edit")
public class ServletModificationProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletModificationProfil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/profilUtilisateur/editUserForm.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setNoUtilisateur(((Utilisateur) request.getSession().getAttribute("user")).getNoUtilisateur());
		utilisateur.setMotDePasse(((Utilisateur) request.getSession().getAttribute("user")).getMotDePasse());
		String pseudo = request.getParameter("pseudo");
		String mdpNow = request.getParameter("password_now");
		String mdpNew = request.getParameter("password_new");
		String mdp2 = request.getParameter("confirmation");
		String prenom = request.getParameter("prenom");
		String nom = request.getParameter("nom");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("cp");
		String ville = request.getParameter("ville");
		
		try {
			UtilisateurManager um = UtilisateurManagerSingl.getInstance();
			if(pseudo.isEmpty() || prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || tel.isEmpty() || rue.isEmpty() || codePostal.isEmpty() || ville.isEmpty()) {
				throw new ServletException("Veuillez remplir tous les champs");
			}
			if(!mdpNow.isEmpty()) {
				if(mdpNew.isEmpty() || mdp2.isEmpty()) {
					throw new ServletException("Veuillez renseigner le nouveau mot de passe");
				}
				if(!um.checkMotDePasse(pseudo, mdpNow)) {
					throw new ServletException("Le mot de passe actuel est incorrect");
				}
				if(!mdpNew.equals(mdp2)) {
					throw new ServletException("Les nouveux mots de passe saisis ne correspondent pas !");
				}
			}
			
			
			utilisateur.setNom(nom);
			utilisateur.setPrenom(prenom);
			utilisateur.setPseudo(pseudo);
			utilisateur.setEmail(email);
			
			utilisateur.setCodePostal(codePostal);
			utilisateur.setRue(rue);
			utilisateur.setTelephone(tel);
			utilisateur.setVille(ville);
			HttpSession session = request.getSession();
			session.setAttribute("user", utilisateur);
			um.updateUtilisateur(utilisateur, mdpNew);
			response.sendRedirect(request.getContextPath() + "/profil");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		}
	}

}
