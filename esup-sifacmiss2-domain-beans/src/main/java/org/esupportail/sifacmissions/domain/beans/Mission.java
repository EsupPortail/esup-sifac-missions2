/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.domain.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author Yves deschamps - Universite Lille1 - France
 */
@SuppressWarnings("unchecked")
public class Mission implements Serializable {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 7458384879369400668L;
	
	/**
     * For Sorting.
     */
    @SuppressWarnings("rawtypes")
    public static Comparator<Mission> ORDER_ORDRE = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            return ((Mission) o1).getOrdre().compareTo(((Mission) o2).getOrdre());
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

	/**
	 * Bean constructor.
	 */
	public Mission() {
		super();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
	
	@Override
    public int hashCode() {
        return super.hashCode();
    }

	/**
	 * @return Numero.
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set.
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return Motif.
	 */
	public String getMotif() {
		return motif;
	}

	/**
	 * @param motif the motif to set.
	 */
	public void setMotif(String motif) {
		this.motif = motif;
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
	 * @return remboursement.
	 */
	public BigDecimal getRemboursement() {
		return remboursement;
	}

	/**
	 * @param remboursement the remboursement to set.
	 */
	public void setRemboursement(BigDecimal remboursement) {
		this.remboursement = remboursement;
	}

	/**
	 * @return Date.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set.
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return Ordre.
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
	 * @return période.
	 */
	public Periode getPeriode() {
		return periode;
	}

	/**
	 * @param periode the periode to set.
	 */
	public void setPeriode(Periode periode) {
		this.periode = periode;
	}
	
	/**
	 * @return Details
	 */
	public List<MissionDetails> getDetails() {
		return details;
	}
	
	/**
	 * @param details the details to set.
	 */
	public void setDetails(List<MissionDetails> details) {
		this.details = details;
	}
	
	/**
	 * @return status
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
