package org.esupportail.sifacmissions.web.controllers;

import org.springframework.util.Assert;

/**
 * Classe héritée par les contrôleurs afin de récupérer :
 * - le service application (applicationService)
 * - le contexte de l'application (sessionController)
 * - le service d'internationalisation (i18nService)
 *
 * @author Florent Cailhol (Anyware Services)
 */
@SuppressWarnings("serial")
public abstract class AbstractContextAwareController extends AbstractDomainAwareBean {

    private SessionController sessionController;

    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(final SessionController sessionController) {
        this.sessionController = sessionController;
    }

    @Override
    protected void afterPropertiesSetInternal() {
        Assert.notNull(sessionController, "sessionController is required");
    }

    @Override
    public String getCurrentUser() {
        return sessionController.getCurrentUser();
    }

}
