package org.eni.encheres.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ArticleVendu {
	
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDateTime dateDebutEncheres;
	private LocalDateTime dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private String etatVente;
	private boolean retraitEffectue = false;
	private Retrait lieuRetrait;
	private Categorie categorieArticle;
	private Utilisateur vendeur;
	private Utilisateur acheteur = null;
	private ArrayList<Enchere> encheres = new ArrayList<>();
	private Image image = null;
	
	public ArticleVendu() {}
	
	public ArticleVendu(String nomArticle, String description, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, int miseAPrix, Retrait lieuRetrait,
			Categorie categorieArticle, Utilisateur vendeur) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.lieuRetrait = lieuRetrait;
		this.categorieArticle = categorieArticle;
		this.vendeur = vendeur;
		setEtatVente();
	}

	public boolean isRetraitEffectue() {
		return retraitEffectue;
	}

	public void setRetraitEffectue(boolean retraitEffectue) {
		this.retraitEffectue = retraitEffectue;
	}

	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}

	public Utilisateur getAcheteur() {
		return acheteur;
	}

	public void setAcheteur(Utilisateur acheteur) {
		this.acheteur = acheteur;
	}

	public ArrayList<Enchere> getEncheres() {
		return encheres;
	}

	public void setEncheres(ArrayList<Enchere> encheres) {
		this.encheres = encheres;
	}

	public Utilisateur getVendeur() {
		return vendeur;
	}

	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}

	public int getNoArticle() {
		return noArticle;
	}
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}
	public String getNomArticle() {
		return nomArticle;
	}
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getDateDebutEncheres() {
		return dateDebutEncheres;
	}
	public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}
	public LocalDateTime getDateFinEncheres() {
		return dateFinEncheres;
	}
	public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}
	public int getMiseAPrix() {
		return miseAPrix;
	}
	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}
	public int getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}
	public String getEtatVente() {
		this.setEtatVente();
		return this.etatVente;
	}
	public void setEtatVente() {
		if(LocalDateTime.now().compareTo(this.dateFinEncheres) > 0) {
			this.etatVente = "Terminé";
		} else if(LocalDateTime.now().compareTo(this.dateDebutEncheres) < 0) {
			this.etatVente = "Non débutée";
		} else {
			this.etatVente = "En cours";
		}
	}
	
	public Retrait getLieuRetrait() {
		return lieuRetrait;
	}
	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;
	}
	public Categorie getCategorieArticle() {
		return categorieArticle;
	}
	public void setCategorieArticle(Categorie categorieArticle) {
		this.categorieArticle = categorieArticle;
	}
	@Override
	public String toString() {
		return "ArticleVendu [noArticle=" + noArticle + ", vendeur = " + vendeur.getNoUtilisateur() + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", miseAPrix="
				+ miseAPrix + ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", lieuRetrait=" + lieuRetrait
				+ ", categorieArticle=" + categorieArticle + "]";
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	
	
	
}
