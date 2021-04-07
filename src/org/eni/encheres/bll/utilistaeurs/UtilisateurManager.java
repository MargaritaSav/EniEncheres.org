package org.eni.encheres.bll.utilistaeurs;

import org.eni.encheres.bo.Utilisateur;

public interface UtilisateurManager {
	
	
	public Utilisateur login(String login, String password);
	
	public Utilisateur addUtilisateur(Utilisateur utilisateur);
	
	
}
