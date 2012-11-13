package org.esupportail.sifacmissions.services.authentication;

import java.util.Map;

import javax.portlet.PortletRequest;

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

        return userInfo.get(uidAttribute);
    }

}
