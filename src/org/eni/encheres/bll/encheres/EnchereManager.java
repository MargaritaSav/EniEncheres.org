package org.eni.encheres.bll.encheres;

import java.util.ArrayList;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.*;

public interface EnchereManager{
	
	public ArticleVendu addArticle(Utilisateur vendeur, String nom, String description, Categorie categorie, String date_debut, String date_fin, int miseAPrix, String rue, String code_postal, String ville) throws BusinessException;
	public ArticleVendu updateArticle(ArticleVendu article) throws BusinessException;
	public void deleteArticle(int noArticle) throws BusinessException;
	public ArrayList<ArticleVendu> getArticles() throws BusinessException;
	public ArticleVendu getArticleById(int id) throws BusinessException;
	public void checkFinEnchere() throws BusinessException;
	public Enchere faireEnchere (Utilisateur utilisateur, ArticleVendu article, int montant_enchere) throws BusinessException;
	public ArrayList<Categorie> getCategories() throws BusinessException;
}
