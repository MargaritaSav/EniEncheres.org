package org.eni.encheres.bll.utilistaeurs;

import java.io.IOException;
import java.lang.invoke.ClassSpecializer.Factory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eni.encheres.bo.Utilisateur;
import org.eni.encheres.dal.DAOFactory;
import org.eni.encheres.dal.EncheresDAO;

/**
 * Servlet implementation class testBLLUtil
 */
@WebServlet("/test")
public class testBLLUtil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public testBLLUtil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Utilisateur u = new Utilisateur(1, "toto", "antoine", "le bot", "toto56@gmail.cil", "0645568923", "2 rue des lilas", "25120", "Arras", "toto56", 0);
		EncheresDAO dao = DAOFactory.getInstace();
		dao.insertUtilisateur(u);
		UtilisateurManager mng = new UtilisateursManagerImpl();
		mng.login("toto", "fail");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
