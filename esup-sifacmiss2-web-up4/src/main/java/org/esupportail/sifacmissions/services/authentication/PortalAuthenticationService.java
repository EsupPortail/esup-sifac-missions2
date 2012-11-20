package org.esupportail.sifacmissions.services.authentication;

import java.util.Map;

import javax.portlet.PortletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Ce service permet de récupérer l'identifiant de l'utilisateur courant à
 * partir du portail.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class PortalAuthenticationService implements AuthenticationService, InitializingBean {

    private static final String DEFAULT_UID_ATTRIBUTE = "uid";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String uidAttribute = DEFAULT_UID_ATTRIBUTE;

    /**
     * @param uidAttribute Attribut permettant de récupérer l'identifiant de
     *            l'utilisateur. <code>uid</code> par défaut.
     */
    public void setUidAttribute(String uidAttribute) {
        this.uidAttribute = uidAttribute;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(uidAttribute, "uidAttribute is required");
    }

    @Override
    public String getUid(Object request) {
        if (!(request instanceof PortletRequest)) {
            return null;
        }

        PortletRequest portletRequest = (PortletRequest) request;

        @SuppressWarnings("unchecked")
        Map<String, String> userInfo = (Map<String, String>) portletRequest.getAttribute(PortletRequest.USER_INFO);

        String uid = userInfo.get(uidAttribute);

        if (logger.isDebugEnabled()) {
            logger.debug("Got uid '{}'", uid);
        }

        return uid;
    }

}
