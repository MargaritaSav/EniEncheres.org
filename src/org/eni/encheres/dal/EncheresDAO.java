package org.eni.encheres.dal;

import java.util.ArrayList;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.ArticleVendu;
import org.eni.encheres.bo.Utilisateur;

public interface EncheresDAO {
	
	public Utilisateur selectUtilisateurByLogin(String login, String email) throws BusinessException ;
	
	public void insertUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public void updateUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public void deleteUtilisateur(int noUtilisateur) throws BusinessException;
	
	public ArrayList<ArticleVendu> selectAllArticles() throws BusinessException;
	
	public ArticleVendu selectArticleById(int noArticle) throws BusinessException;
	
	public ArticleVendu insertArticle(ArticleVendu article) throws BusinessException;
	
	public ArticleVendu updateArticle(ArticleVendu article) throws BusinessException;
	
	public void deleteArticle(int noArticle) throws BusinessException;

}
