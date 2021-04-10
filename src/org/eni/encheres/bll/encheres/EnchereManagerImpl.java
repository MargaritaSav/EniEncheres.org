package org.eni.encheres.bll.encheres;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eni.encheres.BusinessException;
import org.eni.encheres.bo.*;
import org.eni.encheres.dal.DAOFactory;
import org.eni.encheres.dal.EncheresDAO;

public class EnchereManagerImpl implements EnchereManager{
	
	private EncheresDAO dao = DAOFactory.getInstance(); 

	@Override
	public ArticleVendu addArticle(Utilisateur vendeur, String nom, String description, Categorie categorie,
			String date_debut, String date_fin, int miseAPrix, String rue, String code_postal, String ville)
			throws BusinessException {
		ArticleVendu article = new ArticleVendu();
		String errors = checkEntry(nom, description, categorie, date_debut, date_fin, miseAPrix, rue, code_postal, ville);;
		if(vendeur == null) {
			throw new BusinessException("Utilisateur est null");
		}
		if(errors != null) {
			throw new BusinessException(errors);
		}
		article.setNomArticle(nom);
		article.setDescription(description);
		article.setCategorieArticle(categorie);
		article.setDateDebutEncheres(convertToLocalDateTime(date_debut));
		article.setDateFinEncheres(convertToLocalDateTime(date_fin));
		article.setMiseAPrix(miseAPrix);
		article.setVendeur(vendeur);
		
		Retrait retrait = new Retrait();
		retrait.setRue(rue);
		retrait.setCode_postal(code_postal);
		retrait.setVille(ville);
		article.setLieuRetrait(retrait);
		
		dao.insertArticle(article);
		
		return article;
	}
	
	public LocalDateTime convertToLocalDateTime(String datetime) {
		return LocalDateTime.parse(datetime, DateTimeFormatter.ISO_DATE_TIME);
	}

	@Override
	public ArticleVendu updateArticle(ArticleVendu article) throws BusinessException {
		dao.updateArticle(article);
		return article;
	}

	@Override
	public void deleteArticle(int noArticle) throws BusinessException {
		dao.deleteArticle(noArticle);	
	}

	@Override
	public Enchere addEnchere(Utilisateur utilisateur, int no_article, int montant_enchere) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ArticleVendu> getArticles() throws BusinessException {
		ArrayList<ArticleVendu> articles = dao.selectAllArticles();
		return articles;
	}
	
	private String checkEntry(String nom, String description, Categorie categorie,
			String date_debut, String date_fin, int miseAPrix, String rue, String code_postal, String ville) {
		
		StringBuffer sb = new StringBuffer();
		
		if(nom.isEmpty() || description.isEmpty() || categorie == null 
				|| date_debut.isEmpty() || date_fin.isEmpty() || rue.isEmpty() 
				|| code_postal.isEmpty() || ville.isEmpty()) {
			
			return new String("Veuillez remplir tous les champs");
			
		}
		
		if(miseAPrix <= 0) {
			sb.append("Le prix de depart doit etre positif");
			sb.append(System.lineSeparator());
		}
		

		if(convertToLocalDateTime(date_debut).compareTo(LocalDateTime.now()) < 0
				|| convertToLocalDateTime(date_fin).compareTo(LocalDateTime.now()) < 0) {
			sb.append("Vous ne pouvez pas renseigner les dates passées");
			sb.append(System.lineSeparator());
		}
		
		if(convertToLocalDateTime(date_debut).compareTo(convertToLocalDateTime(date_fin)) > 0) {
			sb.append("La date de début d'enchere ne peut pas etre supérieur a la date de fin");
			sb.append(System.lineSeparator());
		}
		
		
		if(!checkCodePostal(code_postal)) {
			sb.append("Le code postal est invalide ou contient autrechose que des chiffres");
			sb.append(System.lineSeparator());
		}
		
		if(sb.length() == 0) {
			return null;
		} else {
			return sb.toString();
		}	
		
	}
	
	public Boolean checkCodePostal (String cp)
	{
		if (cp.length() < 5 || cp.length() > 6 || !onlyDigits(cp)){
			return false;
		}
		return true;
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

}
