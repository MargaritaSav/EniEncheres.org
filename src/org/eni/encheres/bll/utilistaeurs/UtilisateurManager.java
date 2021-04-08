package org.eni.encheres.bll.utilistaeurs;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.Utilisateur;

public interface UtilisateurManager {
	
	
	public Utilisateur login(String login, String password) throws BusinessException;
	
	public Utilisateur addUtilisateur(Utilisateur utilisateur);
	
	public void updateUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public void deleteUtilisateur(int numUtilisateur) throws BusinessException;
	
	
}
