/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.domain.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Yves Deschamps - Universite Lille1 - France.
 * 
 */
public class Periode implements Serializable {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 6548384879369400668L;

	private Date debut;
	private Date fin;

	/**
	 * Bean constructor.
	 */
	public Periode() {
		super();
	}

	/**
	 * @return debut.
	 */
	public Date getDebut() {
		return debut;
	}

	/**
	 * @param debut
	 *            the debut to set.
	 */
	public void setDebut(Date debut) {
		this.debut = debut;
	}

	/**
	 * @return fin.
	 */
	public Date getFin() {
		return fin;
	}

	/**
	 * @param fin
	 *            the fin to set.
	 */
	public void setFin(Date fin) {
		this.fin = fin;
	}

}
