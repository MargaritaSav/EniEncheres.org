package org.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bll.encheres.EnchereManager;
import org.eni.encheres.bll.encheres.EnchereManagerSingl;

import org.eni.encheres.bo.Categorie;
import org.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletNouvelleVente
 */
@WebServlet("/nouvellevente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnchereManager em = EnchereManagerSingl.getInstance();
		
		try {
			List<Categorie> categories = em.getCategories();
			request.setAttribute("categories", categories);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nomArticle = request.getParameter("titre");
		String description = request.getParameter("description");
		int cat = Integer.valueOf(request.getParameter("categorie"));
		int prix = Integer.valueOf(request.getParameter("prix"));
		String dateDebut = request.getParameter("debut");
		String dateFin = request.getParameter("fin");
		String rue = request.getParameter("rueRetrait");
		String codePostal = request.getParameter("code_postal");
		String ville = request.getParameter("villeRetrait");
		System.out.println(cat);
		System.out.println(dateDebut);
		System.out.println(dateFin);
		System.out.println(cat);
		
		try {
		
			EnchereManager em = EnchereManagerSingl.getInstance();
			if(cat == 0) {
				throw new BusinessException("Selectionnez une categorie");
			}
			Categorie categorie = new Categorie();
			categorie.setNoCategorie(cat);
			Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("user");
			em.addArticle(utilisateur, nomArticle, description, categorie, dateDebut, dateFin, prix, rue, codePostal, ville);

			response.sendRedirect(request.getContextPath() + "/accueil");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		}
	}

}
