package org.eni.encheres.bll.encheres;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eni.encheres.bll.utilistaeurs.UtilisateurManager;
import org.eni.encheres.bll.utilistaeurs.UtilisateurManagerSingl;
import org.eni.encheres.bo.ArticleVendu;
import org.eni.encheres.bo.Categorie;
import org.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletTestEncheres
 */
@WebServlet("/ServletTestEncheres")
public class ServletTestEncheres extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UtilisateurManager um = UtilisateurManagerSingl.getInstance();
			Utilisateur lolo = um.login("lolo", "lolo");
			Utilisateur toto = um.login("toto", "toto");
			lolo.setCredit(500);
			toto.setCredit(500);
			um.updateUtilisateur(toto, "toto");
			um.updateUtilisateur(lolo, "lolo");
			EnchereManager em = EnchereManagerSingl.getInstance();
			Categorie maison = new Categorie(1, "maison");
			ArticleVendu article = em.addArticle(toto, "article bll", "description", maison, "2021-04-10T08:30", "2021-08-01T08:30", 100, "rue de caves", "35700", "rennes");
			article.setNomArticle("mon article modifie toto");
			em.updateArticle(article);
			em.faireEnchere(toto, article, 200);
			em.faireEnchere(lolo, article, 300);
			
			System.out.println("====Articles de TOTO =====");
			ArrayList<ArticleVendu> articlesLolo = toto.getArticlesVendus();
			for(ArticleVendu b : articlesLolo) {
				System.out.println(b.toString());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
