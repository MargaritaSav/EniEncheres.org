package org.eni.encheres.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bll.encheres.EnchereManager;
import org.eni.encheres.bll.encheres.EnchereManagerSingl;
import org.eni.encheres.bo.ArticleVendu;
import org.eni.encheres.bo.Categorie;
import org.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class ServletNouvelleVente
 */
@WebServlet("/nouvellevente")

@MultipartConfig(
		  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		  maxFileSize = 1024 * 1024 * 10,      // 10 MB
		  maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "/uploaded-files/";

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
		request.setCharacterEncoding("UTF-8");
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
			ArticleVendu article = new ArticleVendu();
			
			Part filePart = request.getPart("image");
			String imagePath = null;
			if(!filePart.getSubmittedFileName().isEmpty()) {
				InputStream fileInputStream = filePart.getInputStream();
				
				String path = request.getRealPath(SAVE_DIR)  + filePart.getSubmittedFileName();
			
				Files.copy(fileInputStream, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
				imagePath = SAVE_DIR  + filePart.getSubmittedFileName();
			}
	
			
			
			String action = request.getParameter("action");
			if(action.equals("modifier")) {
				int noArticle = Integer.valueOf(request.getParameter("noArticle"));
				article = em.updateArticle(noArticle, utilisateur, nomArticle, description, categorie, dateDebut, dateFin, prix, rue, codePostal, ville, imagePath);

			} else if(action.equals("creer")) {
				article = em.addArticle(utilisateur, nomArticle, description, categorie, dateDebut, dateFin, prix, rue, codePostal, ville, imagePath);
			}
			
			
		
			request.setAttribute("success", "Rajout d'article réussi");
			response.sendRedirect(request.getContextPath() + "/vente?action=detail&noArticle="+article.getNoArticle());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			doGet(request, response);
		}
	}

}
