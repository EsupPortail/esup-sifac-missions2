package org.esupportail.sifacmissions.web.controllers;

import org.esupportail.sifacmissions.models.User;

import org.springframework.util.Assert;

/**
 * An abstract class inherited by all the beans for them to get:
 * - the context of the application (sessionController).
 * - the domain service (domainService).
 * - the application service (applicationService).
 * - the i18n service (i18nService).
 */
public abstract class AbstractContextAwareController extends AbstractDomainAwareBean {

    /**
     * The serialization id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The SessionController.
     */
    private SessionController sessionController;

    /**
     * Constructor.
     */
    protected AbstractContextAwareController() {
        super();
    }

    @Override
    public void afterPropertiesSetInternal() {
        Assert.notNull(sessionController, "property sessionController can not be null");
    }

    /**
     * @param sessionController the sessionController to set
     */
    public void setSessionController(final SessionController sessionController) {
        this.sessionController = sessionController;
    }

    /**
     * @return the sessionController
     */
    public SessionController getSessionController() {
        return sessionController;
    }

    @Override
    protected User getCurrentUser() {
        try {
            return sessionController.getCurrentUser();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
