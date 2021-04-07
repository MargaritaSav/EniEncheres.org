package org.eni.encheres.dal;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.Utilisateur;

public interface EncheresDAO {
	
	public Utilisateur selectUtilisateurByLogin(String login);
	
	public void insertUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public void updateUtilisateur(Utilisateur utilisateur) throws BusinessException;
	
	public void deleteUtilisateur(int noUtilisateur);

}
