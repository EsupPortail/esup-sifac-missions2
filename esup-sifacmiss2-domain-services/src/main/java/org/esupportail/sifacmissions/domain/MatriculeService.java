/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.domain;

import java.io.Serializable;

/**
 * @author Yves deschamps - Universite Lille1 - France
 */
public interface MatriculeService extends Serializable {
	
	/**
	 * @param id
	 * @return matricule for user id.
	 */
	String getMatricule(String id);

}
