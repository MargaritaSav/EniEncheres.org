package org.eni.encheres.dal;

import org.eni.encheres.bo.Utilisateur;

public interface EncheresDAO {
	
	public Utilisateur selectUtilisateurByLogin(String login);
	
	public Utilisateur insertUtilisateur(Utilisateur utilisateur);

}
