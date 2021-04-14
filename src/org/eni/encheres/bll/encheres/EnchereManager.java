package org.eni.encheres.bll.encheres;

import java.util.ArrayList;
import java.util.HashMap;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.*;

public interface EnchereManager{
	
	public ArticleVendu addArticle(Utilisateur vendeur, String nom, String description, Categorie categorie, String date_debut, String date_fin, int miseAPrix, String rue, String code_postal, String ville) throws BusinessException;
	public ArticleVendu updateArticle(int noArticle, Utilisateur vendeur, String nom, String description, Categorie categorie, String date_debut, String date_fin, int miseAPrix, String rue, String code_postal, String ville) throws BusinessException;
	public void deleteArticle(int noArticle) throws BusinessException;
	public ArrayList<ArticleVendu> getArticles() throws BusinessException;
	public ArticleVendu getArticleById(int id) throws BusinessException;
	public Enchere faireEnchere (Utilisateur utilisateur, ArticleVendu article, int montant_enchere) throws BusinessException;
	public ArrayList<Categorie> getCategories() throws BusinessException;
	public HashMap<String, ArrayList<ArticleVendu>> getArticlesParUtilisateur(Utilisateur utilisateur);
	public void checkFinEnchere() throws BusinessException;

}
