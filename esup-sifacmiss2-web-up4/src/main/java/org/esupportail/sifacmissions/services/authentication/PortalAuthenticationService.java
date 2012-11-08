package org.esupportail.sifacmissions.services.authentication;

import java.util.Map;

import javax.portlet.PortletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class PortalAuthenticationService implements AuthenticationService, InitializingBean {

    /**
     * Par défaut, l'attribut permettant de récupérer l'identifiant est uid.
     */
    public static final String DEFAULT_UID_ATTRIBUTE = "uid";

    private String uidAttribute = DEFAULT_UID_ATTRIBUTE;

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
