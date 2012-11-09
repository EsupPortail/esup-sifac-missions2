package org.esupportail.sifacmissions.web.controllers;

import org.springframework.util.Assert;

/**
 * An abstract class inherited by all the controller beans to get:
 * - the application service (applicationService)
 * - the context of the application (sessionController)
 * - the i18n service (i18nService)
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
