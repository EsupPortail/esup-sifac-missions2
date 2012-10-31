package org.esupportail.sifacmissions.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Cette classe représente les missions SIFAC.
 *
 * @author Yves deschamps (Universite Lille1 - France)
 * @author Florent Cailhol (Anyware Services)
 */
public class Mission implements Serializable {

    private static final long serialVersionUID = 7531554542364823201L;

    /**
     * Comparateur pour effectuer un tri par offre de missions.
     */
    public static Comparator<Mission> ORDER_ORDRE = new Comparator<Mission>() {

        @Override
        public int compare(Mission m1, Mission m2) {
            return m1.getOrdre().compareTo(m2.getOrdre());
        }
    };

    private String numero;
    private String motif;
    private BigDecimal montant;
    private BigDecimal remboursement;
    private Date date;
    private Long ordre;
    private Periode periode;
    private List<MissionDetails> details;

    @Override
    public int hashCode() {
        return numero.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Mission)) {
            return false;
        }

        return numero.equals(((Mission) obj).getNumero());
    }

    /**
     * @return Numéro de mission
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero Numéro de mission
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return Motif
     */
    public String getMotif() {
        return motif;
    }

    /**
     * @param motif Motif
     */
    public void setMotif(String motif) {
        this.motif = motif;
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
     * @return Montant remboursé
     */
    public BigDecimal getRemboursement() {
        return remboursement;
    }

    /**
     * @param remboursement Montant remboursé
     */
    public void setRemboursement(BigDecimal remboursement) {
        this.remboursement = remboursement;
    }

    /**
     * @return Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date Date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return Ordre
     */
    public Long getOrdre() {
        return ordre;
    }

    /**
     * @param ordre the ordre to set.
     */
    public void setOrdre(Long ordre) {
        this.ordre = ordre;
    }

    /**
     * @return Période.
     */
    public Periode getPeriode() {
        return periode;
    }

    /**
     * @param periode Période
     */
    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    /**
     * @return Liste des détails
     */
    public List<MissionDetails> getDetails() {
        return details;
    }

    /**
     * @param details Liste des détails
     */
    public void setDetails(List<MissionDetails> details) {
        this.details = details;
    }

    /**
     * @return status Etat du remboursement
     */
    public Integer getStatus() {
        if (remboursement.signum() == 0) {
            return MissionStatus.NON_REMBOURSABLE;
        }

        if (date != null) {
            return MissionStatus.VALIDEE;
        }

        return MissionStatus.EN_ATTENTE;
    }

}
