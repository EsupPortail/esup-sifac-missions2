package org.esupportail.sifacmissions.models;

import java.math.BigDecimal;

/**
 * Cette classe représente un frais lié à une mission Sifac.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class MissionDetails {

    private String categorie;
    private String description;
    private BigDecimal montant;
    private Boolean paid;

    /**
     * @return Catégorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @param categorie Catégorie
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Montant
     */
    public BigDecimal getMontant() {
        return montant;
    }

    /**
     * @param montant Montant
     */
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    /**
     * @return paid Etat du paiement
     */
    public Boolean getPaid() {
        return paid;
    }

    /**
     * @param paid Etat du paiement
     */
    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

}
