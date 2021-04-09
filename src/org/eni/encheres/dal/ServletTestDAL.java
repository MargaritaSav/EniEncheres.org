package org.eni.encheres.dal;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.*;

/**
 * Servlet implementation class ServletTestDAL
 */
@WebServlet("/ServletTestDAL")
public class ServletTestDAL extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EncheresDAO dao = DAOFactory.getInstance();
		Categorie maison = new Categorie(1, "maison");
		ArticleVendu article = new ArticleVendu();
		Utilisateur vendeur = new Utilisateur();
		Retrait retrait = new Retrait();
		vendeur.setNoUtilisateur(1);
		retrait.setCode_postal("35700");
		retrait.setRue("rue de cave");
		retrait.setVille("Rennes");
		article.setNomArticle("mon article");
		article.setDescription("blablabla");
		article.setMiseAPrix(50);
		article.setDateDebutEncheres(LocalDateTime.now());
		article.setDateFinEncheres(LocalDateTime.now());
		article.setCategorieArticle(maison);
		article.setVendeur(vendeur);
		article.setLieuRetrait(retrait);
		try {
			dao.insertArticle(article);
			article.setNomArticle("nom modifie");
			dao.updateArticle(article);
			ArrayList<ArticleVendu> articles = dao.selectAllArticles();
			for( ArticleVendu a : articles) {
				System.out.println(a.toString());
			}
			dao.deleteArticle(4);
			
			Enchere enchere1 = new Enchere();
			enchere1.setDateEnchere(LocalDateTime.now());
			enchere1.setMontant_enchere(100);
			Enchere enchere2 = new Enchere();
			enchere2.setDateEnchere(LocalDateTime.now());
			enchere2.setMontant_enchere(200);
//			dao.insertEnchere(enchere1, 4, 1);
//			dao.insertEnchere(enchere2, 4, 2);
			ArrayList<Enchere> encheres = dao.selectEncheresByNoArticle(4);
			for( Enchere e : encheres) {
				System.out.println(e.toString());
			}
		} catch (BusinessException e) {
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
