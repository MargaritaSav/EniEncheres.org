package org.eni.encheres.dal;

import java.util.ArrayList;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.*;

public interface EncheresDAO {
	
	public Utilisateur selectUtilisateurByLogin(String login, String email) throws BusinessException ;
	
	void insertUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public void updateUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public void deleteUtilisateur(int noUtilisateur) throws BusinessException;
	
	public ArrayList<ArticleVendu> selectAllArticles() throws BusinessException;
	
	public ArticleVendu selectArticleById(int noArticle) throws BusinessException;
	
	public ArrayList<ArticleVendu> selectArticlesAchetesByUser(Utilisateur utilisateur) throws BusinessException;
	
	
	public ArticleVendu insertArticle(ArticleVendu article) throws BusinessException;
	
	public ArticleVendu updateArticle(ArticleVendu article) throws BusinessException;
	
	public void deleteArticle(int noArticle) throws BusinessException;
	
	public Retrait insertRetrait (Retrait retrait, int noArticle) throws BusinessException;
	
	public Retrait updateRetrait (Retrait retrait, int noArticle) throws BusinessException;
	
	public ArrayList<Categorie> selectAllCategories() throws BusinessException;
	
	public Enchere insertEnchere(Enchere enchere, int noArticle, int noUtilisateur) throws BusinessException;
	
	public Enchere updateEnchere(Enchere enchere) throws BusinessException;
	
	public ArrayList<Enchere> selectEncheresByNoArticle (int noArticle) throws BusinessException;
	
	public ArrayList<Enchere> selectEncheresByUser (Utilisateur utilisateur) throws BusinessException;
	
	public void deleteEncheres (int noArticle) throws BusinessException;

	public ArrayList<ArticleVendu> selectEncheresFinis() throws BusinessException;

	ArrayList<ArticleVendu> selectArticlesVendusByUser(Utilisateur utilisateur) throws BusinessException;

}
