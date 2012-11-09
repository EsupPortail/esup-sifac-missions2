package org.esupportail.sifacmissions.web.controllers;

import org.esupportail.commons.beans.AbstractJsfMessagesAwareBean;
import org.esupportail.commons.web.controllers.Resettable;

/**
 * An abstract class inherited by all the controller beans to get:
 * - the context of the application (sessionController)
 * - the i18n service (i18nService)
 */
@SuppressWarnings("serial")
public abstract class AbstractDomainAwareBean extends AbstractJsfMessagesAwareBean implements Resettable {

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
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
    public String getCurrentUser() {
        // override this method
        return null;
    }

}
