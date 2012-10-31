package org.esupportail.sifacmissions.web.controllers;

import org.esupportail.commons.beans.AbstractJsfMessagesAwareBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.web.controllers.Resettable;
import org.esupportail.sifacmissions.domain.DomainService;
import org.esupportail.sifacmissions.models.User;

/**
 * An abstract class inherited by all the beans for them to get:
 * - the domain service (domainService).
 * - the application service (applicationService).
 * - the i18n service (i18nService).
 */
public abstract class AbstractDomainAwareBean extends AbstractJsfMessagesAwareBean implements Resettable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * A logger.
     */
    @SuppressWarnings("unused")
    private final Logger logger = new LoggerImpl(this.getClass());

    /**
     * see {@link DomainService}.
     */
    private DomainService domainService;

    /**
     * Constructor.
     */
    protected AbstractDomainAwareBean() {
        super();
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Assert.notNull(domainService, "property domainService of class " + getClass().getName() + " can not be null");
        afterPropertiesSetInternal();
        reset();
    }

    /**
     * This method is run once the object has been initialized, just before
     * reset().
     */
    protected void afterPropertiesSetInternal() {
        // override this method
    }

    @Override
    public void reset() {
        // nothing to reset
    }

    /**
     * @return the current user.
     */
    protected User getCurrentUser() {
        // this method should be overriden
        return null;
    }

    /**
     * @param domainService
     */
    public void setDomainService(final DomainService domainService) {
        this.domainService = domainService;
    }

    /**
     * @return the domainService
     */
    public DomainService getDomainService() {
        return domainService;
    }

}
