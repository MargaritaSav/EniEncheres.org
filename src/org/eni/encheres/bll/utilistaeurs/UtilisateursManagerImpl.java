package org.eni.encheres.bll.utilistaeurs;

import org.apache.catalina.authenticator.BasicAuthenticator.BasicCredentials;
import org.eni.encheres.BusinessException;
import org.eni.encheres.bll.utilistaeurs.CodesResultatBLL;
import org.eni.encheres.bo.Utilisateur;
import org.eni.encheres.dal.CodesResultatDAL;
import org.eni.encheres.dal.DAOFactory;
import org.eni.encheres.dal.EncheresDAO;
import org.eni.encheres.bo.*;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;


public class UtilisateursManagerImpl implements UtilisateurManager {
	private EncheresDAO dao = DAOFactory.getInstance();
	List<Utilisateur> utilisateurs = new ArrayList<>();


	@Override
	/**
	 * Vérifier si le mot de passe enregistré dans la base des donnée s
	 * corresponds au mdp rentré par utilisateur
	 * @param String login, String password
	 * @return Utilisateur
	 * @throws BusinessException
	 */
	public Utilisateur login(String login, String password) throws BusinessException {
	
		Utilisateur user = dao.selectUtilisateurByLogin(login, login);
		
		if (user != null)
		{
			if(checkHashedPassword(password, user.getMotDePasse(), user.getSalt()))
			return user;
		}
		
		throw new BusinessException("Login ou mot de passe invalide");
		
	}

	
	/**
	 * Verifie si les données entrées par utilisateurs sont correctes et que login n'existe pas dans la base des données
	 * @param Utilisateur utilisateur
	 * @return Utilisateur avec le noUtilisateur attribué par la base des données
	 * @throws BusinessException 
	 */
	public Utilisateur addUtilisateur(Utilisateur utilisateur, String plainPassword) throws BusinessException {
		Utilisateur user = dao.selectUtilisateurByLogin(utilisateur.getPseudo(), utilisateur.getEmail());
		String errors = checkUserEntry(utilisateur, plainPassword);
		BusinessException businessException;
		System.out.println(errors);
		HashMap<String, byte[]> pwd = hashPassword(plainPassword);
		utilisateur.setMotDePasse(pwd.get("password"));
		utilisateur.setSalt(pwd.get("salt"));
		
		if (user == null)
		{
			if (errors == null) {
				dao.insertUtilisateur(utilisateur);	
			} else {
				throw new BusinessException(errors);
			}
		}
		else {
			
			throw new BusinessException("Login ou email existe deja" + System.lineSeparator() + errors);
		}
		
		return utilisateur;
	}
	
	/**
	 * Mets à jour les données utilisateurs
	 * @param Utilisateur utilisateur
	 * @throws BusinessException
	 */
	public void updateUtilisateur(Utilisateur utilisateur, String plainPassword) throws BusinessException {
		String errors = checkUserEntry(utilisateur, plainPassword);
			if(errors == null) {
				if(plainPassword != null) {
					HashMap<String, byte[]> pwd = hashPassword(plainPassword);
					utilisateur.setMotDePasse(pwd.get("password"));
					utilisateur.setSalt(pwd.get("salt"));
				}
				dao.updateUtilisateur(utilisateur);
			} else {
				throw new BusinessException(errors);
			}
			 
		
	}
	
	public Boolean checkCodePostal (String cp)
	{
		if (cp.length() < 5 || cp.length() > 6 || !onlyDigits(cp))
		{
			return false;
		}
		return true;
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


	@Override
	public boolean checkMotDePasse(String login, String password) throws BusinessException {
		Utilisateur u = dao.selectUtilisateurByLogin(login, login);
		
		if(checkHashedPassword(password, u.getMotDePasse(), u.getSalt())){
			return true;
		}
		return false;
		
	}
	
	private boolean onlyDigits(String str)
    {
        // Regex to check string
        // contains only digits
        String regex = "[0-9]+";
  
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
  
        // If the string is empty
        // return false
        if (str == null) {
            return false;
        }
  
        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(str);
  
        // Return if the string
        // matched the ReGex
        return m.matches();
    }
	
	private String checkUserEntry(Utilisateur user, String plainPassword) {
		StringBuffer sb = new StringBuffer();
		if(user.getPseudo().isEmpty() || plainPassword.isEmpty() || user.getPrenom().isEmpty() 
				|| user.getNom().isEmpty() || user.getEmail().isEmpty() || user.getTelephone().isEmpty() || user.getRue().isEmpty() 
				|| user.getCodePostal().isEmpty() || user.getVille().isEmpty()) {
			
			return new String("Veuillez remplir tous les champs");
			
		}
		
		if(!checkCodePostal(user.getCodePostal())) {
			sb.append("Le code postal est invalid ou contient autrechose que des chiffres");
			sb.append(System.lineSeparator());
		}
		
		if(!onlyDigits(user.getTelephone()) || user.getTelephone().length() != 10 ) {
			sb.append("Le nombre de telephone est trop longue, trop court ou contient autrechose que des chiffres");
		}
		
		if(sb.length() == 0) {
			return null;
		} else {
			return sb.toString();
		}	
		
	}
	
	//à discuter
	
	private HashMap<String, byte[]> hashPassword(String mdp) throws BusinessException {
		byte[] hash;
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		KeySpec spec = new PBEKeySpec(mdp.toCharArray(), salt, 65536, 128);
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new BusinessException("Problème avec sauvegarde du mot de passe");
		}
		HashMap<String, byte[]> map = new HashMap<>();
	
		map.put("password", hash);
		map.put("salt", salt);
		return map;
		
	}
	
	private boolean checkHashedPassword(String plainPwd, byte[] hashedPwd, byte[] salt) throws BusinessException {
		byte[] hash;
		KeySpec spec = new PBEKeySpec(plainPwd.toCharArray(), salt, 65536, 128);
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new BusinessException("Problème avec la recuperation du mot de passe");
		}
		
		System.out.println(hash);
		System.out.println(hashedPwd);
		
		return Arrays.equals(hash, hashedPwd);
		
	}


}
