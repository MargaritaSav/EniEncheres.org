package org.eni.encheres.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bll.encheres.EnchereManager;
import org.eni.encheres.bll.encheres.EnchereManagerSingl;
import org.eni.encheres.bll.utilistaeurs.UtilisateurManager;
import org.eni.encheres.bll.utilistaeurs.UtilisateurManagerSingl;
import org.eni.encheres.bo.ArticleVendu;
import org.eni.encheres.bo.Categorie;
import org.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletDetailVente
 */
@WebServlet("/vente")
public class ServletDetailVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private EnchereManager em = EnchereManagerSingl.getInstance();
	private UtilisateurManager um = UtilisateurManagerSingl.getInstance();


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("noArticle") != null) {
			int id = Integer.valueOf(request.getParameter("noArticle"));
			ArticleVendu article;
			try {
				article = em.getArticleById(id);
				if(article == null) {
					response.sendRedirect(request.getContextPath() + "/accueil");
					return;
				}
				request.setAttribute("article", article);

			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(request.getParameter("action")) {
			case "modifier":
				try {
					ArrayList<Categorie> cat = em.getCategories();
					request.setAttribute("categories", cat);
					RequestDispatcher rd2 = request.getRequestDispatcher("/WEB-INF/nouvelleVente.jsp");
					rd2.forward(request, response);
				} catch (BusinessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				break;
			case "supprimer":
				try {
					em.deleteArticle(id);
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response.sendRedirect(request.getContextPath() + "/accueil");
				break;
			default:
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/detailEncheres.jsp");
				rd.forward(request, response);
				break;
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/accueil");
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int prix = Integer.valueOf(request.getParameter("encherir"));
		HttpSession session = request.getSession();
		try {
			ArticleVendu article = em.getArticleById(Integer.valueOf(request.getParameter("noArticle")));
			em.faireEnchere((Utilisateur)(session.getAttribute("user")), article, prix);
		} catch (BusinessException e) {
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		}
		request.setAttribute("success", "Votre enchère est accepté");
		doGet(request, response);
	}

}
