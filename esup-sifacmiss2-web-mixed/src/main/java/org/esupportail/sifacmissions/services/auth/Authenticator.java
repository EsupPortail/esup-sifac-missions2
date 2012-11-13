/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.sifacmissions.services.auth;

/**
 * Cette classe permet d'authentifier l'utilisateur courant.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public interface Authenticator {

    /**
     * Authentifie et récupère l'utilisateur courant.
     *
     * @return Utilisateur courant
     * @throws Exception
     */
    String getUser() throws Exception;

}