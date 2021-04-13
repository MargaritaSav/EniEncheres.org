package org.eni.encheres.bo;

import java.util.ArrayList;

public class Utilisateur {

	private int noUtilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String codePostal;
	private String ville;
	private byte[] motDePasse;
	private byte[] salt;
	private int credit;
	private boolean administrateur = false;
	private ArrayList<Enchere> encheres;
	private ArrayList<ArticleVendu> articlesAchetes;
	private ArrayList<ArticleVendu> articlesVendus;
	
	public Utilisateur() {}
	
	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, int credit ) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.credit = credit;
	}
	
	public void transfererPoints(int montant) {
		this.credit += montant;
	}
	
	public int getNoUtilisateur() {
		return noUtilisateur;
	}
	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public byte[] getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(byte[] motDePasse) {
		this.motDePasse = motDePasse;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public boolean isAdministrateur() {
		return administrateur;
	}
	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}
	public ArrayList<Enchere> getEncheres() {
		return encheres;
	}
	public void setEncheres(ArrayList<Enchere> encheres) {
		this.encheres = encheres;
	}
	public ArrayList<ArticleVendu> getArticlesAchetes() {
		return articlesAchetes;
	}
	public void setArticlesAchetes(ArrayList<ArticleVendu> articlesAchetes) {
		this.articlesAchetes = articlesAchetes;
	}
	public ArrayList<ArticleVendu> getArticlesVendus() {
		return articlesVendus;
	}
	public void setArticlesVendus(ArrayList<ArticleVendu> articlesVendus) {
		this.articlesVendus = articlesVendus;
	}
	
	
	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "Utilisateur [noUtilisateur=" + noUtilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", codePostal="
				+ codePostal + ", ville=" + ville + ", motDePasse=" + motDePasse + ", credit=" + credit
				+ ", administrateur=" + administrateur + "]";
	}
	
	
}
