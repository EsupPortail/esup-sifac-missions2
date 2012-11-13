package org.esupportail.sifacmissions.services.authentication;

/**
 * Ce service permet de récupérer l'identifiant l'utilisateur courant.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public interface AuthenticationService {

    /**
     * Récupère l'identifiant de l'utilisateur courant.
     *
     * @param request Requête
     * @return uid
     */
    String getUid(Object request);

}
