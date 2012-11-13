package org.esupportail.sifacmissions.services.view;

import javax.portlet.PortletRequest;

/**
 * Ce service permet de déterminer le type de vue utilisé.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public interface ViewResolver {

    /**
     * Détermine si l'utilisateur utilisateur consulte la portlet par le biai
     * d'un terminal mobile.
     *
     * @param request Requête
     * @return <code>true</code> si vue mobile
     */
    boolean isMobile(PortletRequest request);

}
