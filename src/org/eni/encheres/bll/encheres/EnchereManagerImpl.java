package org.eni.encheres.bll.encheres;

import java.util.ArrayList;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.ArticleVendu;
import org.eni.encheres.bo.Categorie;
import org.eni.encheres.bo.Enchere;
import org.eni.encheres.bo.Utilisateur;
import org.eni.encheres.dal.DAOFactory;
import org.eni.encheres.dal.EncheresDAO;

public class EnchereManagerImpl implements EnchereManager{
	
	private EncheresDAO dao = DAOFactory.getInstance(); 

	@Override
	public ArticleVendu addArticle(Utilisateur vendeur, String nom, String description, Categorie categorie,
			String date_debut, String date_fin, int miseAPrix, String rue, String code_postal, String ville)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArticleVendu updateArticle(ArticleVendu article) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteArticle(int noArticle) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Enchere addEnchere(Utilisateur utilisateur, int no_article, int montant_enchere) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ArticleVendu> getArticles() throws BusinessException {
		ArrayList<ArticleVendu> articles = dao.selectAllArticles();
		return articles;
	}

}
