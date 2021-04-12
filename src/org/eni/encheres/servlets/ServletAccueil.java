package org.eni.encheres.servlets;

import java.io.IOException;
import java.util.List;

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
import org.eni.encheres.bo.ArticleVendu;
import org.eni.encheres.dal.DAOFactory;
import org.eni.encheres.dal.EncheresDAO;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet("/accueil")
public class ServletAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//EnchereManager em = EnchereManagerSingl.getInstance();
		EncheresDAO dao = DAOFactory.getInstance();
		
		try {
			List<ArticleVendu> articles = dao.selectAllArticles();
			request.setAttribute("articles", articles);
			System.out.println(articles);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		if(request.getParameter("deconnexion") != null) {
			HttpSession session = request.getSession();
			session.setAttribute("session", "off");
			session.removeAttribute("user");
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil/accueil.jsp");
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
