package org.eni.encheres.bo;

import java.time.LocalDateTime;

public class Enchere {

	private LocalDateTime dateEnchere;
	private int montant_enchere;
	private Utilisateur utilisateur;
	
	public Enchere() {}
	
	public Enchere(LocalDateTime dateEnchere, int montant_enchere) {
		super();
		this.dateEnchere = dateEnchere;
		this.montant_enchere = montant_enchere;
	}
	
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}
	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}
	public int getMontant_enchere() {
		return montant_enchere;
	}
	public void setMontant_enchere(int montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	@Override
	public String toString() {
		return "Enchere [dateEnchere=" + dateEnchere + ", montant_enchere=" + montant_enchere + ", utilisateur="
				+ utilisateur + "]";
	}

	
	
	
	
}
