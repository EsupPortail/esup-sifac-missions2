package org.esupportail.sifacmissions.domain.beans;

import java.math.BigDecimal;

public class MissionDetails {

	private String categorie;
	private String description;
	private BigDecimal montant;
	private Boolean paid;
	
	/**
	 * @return categorie
	 */
	public String getCategorie() {
		return categorie;
	}

	/**
	 * @param categorie the categorie to set.
	 */
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return Montant.
	 */
	public BigDecimal getMontant() {
		return montant;
	}

	/**
	 * @param montant the montant to set.
	 */
	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}
	
	/**
	 * @return paid
	 */
	public Boolean getPaid() {
		return paid;
	}
	
	/**
	 * @param paid
	 */
	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

}
