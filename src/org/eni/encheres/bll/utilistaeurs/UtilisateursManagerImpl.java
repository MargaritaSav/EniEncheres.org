package org.eni.encheres.bll.utilistaeurs;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bll.utilistaeurs.CodesResultatBLL;
import org.eni.encheres.bo.Utilisateur;
import org.eni.encheres.dal.CodesResultatDAL;
import org.eni.encheres.dal.DAOFactory;
import org.eni.encheres.dal.EncheresDAO;
import org.eni.encheres.bo.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.eni.encheres.*;


public class UtilisateursManagerImpl implements UtilisateurManager {
	private EncheresDAO dao = DAOFactory.getInstace();
	List<Utilisateur> utilisateurs = new ArrayList<>();


	@Override
	/**
	 * Il faut vérifier si le mot de passe enregistré dans la base de sonnée 
	 * corresponds au mdp rentré par utilisateur
	 * Retourner erreur si non, et si tout va bien on retourne Utilisateur.
	 */
	public Utilisateur login(String login, String password) throws BusinessException {
	
		Utilisateur user = dao.selectUtilisateurByLogin(login, login);

		if (user != null && user.getMotDePasse().equals(password))
		{
			return user;
		}
		
		throw new BusinessException("Login ou mot de passe invalide");
		
	}

	
	/**
	 * Verifier si les données entrés par utilisateurs sont corrects (par ex. code postal inclus 5 chiffres) et que login n'existe pas dans la base des données
	 * throw exception si erreur de vérification, persister Utilisateur dans la base des données et return Utilisateur si tout s'est bien passé
	 * @throws BusinessException 
	 */
	public Utilisateur addUtilisateur(Utilisateur utilisateur) throws BusinessException {
		Utilisateur user = dao.selectUtilisateurByLogin(utilisateur.getPseudo(), utilisateur.getEmail());
		BusinessException businessException;

		if (user == null)
		{
			if (checkCodePostal(utilisateur.getCodePostal())) {
				try {
					dao.insertUtilisateur(utilisateur);
				} catch (BusinessException e) {
					throw new BusinessException("Problème avec l'ajout de l'utilisateur");
				}
			}
		}
		else {
			utilisateur = null;
			throw new BusinessException("Login ou email existe deja");
		}
		
		return utilisateur;
	}

	public void updateUtilisateur(Utilisateur utilisateur) throws BusinessException {
		try {
			 dao.updateUtilisateur(utilisateur);
		}catch(BusinessException e) {
			throw new BusinessException("Problème avec la mise à jour de l'utilisateur");
		}
		

	}
	
	public Boolean checkCodePostal (String cp)
	{
		if (cp.length() == 5)
		{
			return true;
		}
		return false;
	}

	public void deleteUtilisateur(int numUtilisateur) throws BusinessException {

		if (numUtilisateur > 0)
		{
			dao.deleteUtilisateur(numUtilisateur);

		}
		else {
			throw new BusinessException("Probleme suppression utilisateur");
		}
}

}
