package org.eni.encheres.bll.utilistaeurs;

import java.util.ArrayList;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.Utilisateur;

public interface UtilisateurManager {
	
	
	public Utilisateur login(String login, String password) throws BusinessException;
	
	public Utilisateur addUtilisateur(Utilisateur utilisateur, String plainPwd) throws BusinessException;
	
	public Utilisateur getUtilisateurByPseudo(String pseudo) throws BusinessException;
	
	public void updateUtilisateur(Utilisateur utilisateur, String plainPwd) throws BusinessException;
	
	public void deleteUtilisateur(int numUtilisateur) throws BusinessException;
	
	public boolean checkMotDePasse(String login, String password) throws BusinessException;
	
	ArrayList<Utilisateur> selectAllUtilisateurs() throws BusinessException;

	void desactiverUtilisateur(String pseudo) throws BusinessException;
	
	
}
