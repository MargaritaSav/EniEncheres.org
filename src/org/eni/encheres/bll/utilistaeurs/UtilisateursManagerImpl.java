package org.eni.encheres.bll.utilistaeurs;

import org.eni.encheres.bo.Utilisateur;

public class UtilisateursManagerImpl implements UtilisateurManager {

	@Override
	/**
	 * Il faut vérifier si le mot de passe enregistré dans la base de sonnée 
	 * corresponds au mdp rentré par utilisateur
	 * Retourner erreur si non, et si tout va bien on retourne Utilisateur.
	 */
	public Utilisateur login(String login, String password) {
		return null;
		// TODO Implement method
		
	}

	@Override
	/**
	 * Verifier si les données entrés par utilisateurs sont corrects (par ex. code postal inclus 5 chiffres) et que login n'esiste pas dans la base des données
	 * throw exception si erreur de vérification, persister Utilisateur dans la base des données et return Utilisateur si tout s'est bien passé
	 */
	public Utilisateur addUtilisateur(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		return null;
	}

}
