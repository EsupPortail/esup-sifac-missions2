/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.sifacmissions.services.auth;

import org.esupportail.sifacmissions.domain.beans.User;

/**
 * Cette classe permet d'authentifier l'utilisateur courant.
 * 
 * @author Yves Deschamps (Universite de Lille 1)
 */
public interface Authenticator {

	/**
	 * Authentifie et récupère l'utilisateur courant.
	 * 
	 * @return Utilisateur courant
	 * @throws Exception 
	 */
	User getUser() throws Exception;

}