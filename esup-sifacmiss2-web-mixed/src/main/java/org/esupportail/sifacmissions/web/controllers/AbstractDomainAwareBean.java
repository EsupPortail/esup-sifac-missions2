package org.esupportail.sifacmissions.web.controllers;

import org.esupportail.commons.beans.AbstractJsfMessagesAwareBean;
import org.esupportail.commons.web.controllers.Resettable;

/**
 * Classe héritée par les contrôleurs afin de récupérer :
 * - le contexte de l'application (sessionController)
 * - le service d'internationalisation (i18nService)
 *
 * @author Florent Cailhol (Anyware Services)
 */
@SuppressWarnings("serial")
public abstract class AbstractDomainAwareBean extends AbstractJsfMessagesAwareBean implements Resettable {

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        afterPropertiesSetInternal();
        reset();
    }

    @Override
    public void reset() {
        // nothing to reset
    }

    protected void afterPropertiesSetInternal() {
        // override this method
    }

    /**
     * @return Utilisateur courant.
     */
    public String getCurrentUser() {
        // override this method
        return null;
    }

}
