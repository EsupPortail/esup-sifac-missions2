package org.esupportail.sifacmissions.services.authentication;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Récupère l'identifiant courant à partir d'un paramètre de configuration. Ce
 * service ne doit pas être utilisé en production.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class FixedAuthenticationService implements AuthenticationService, InitializingBean {

    private String uid;

    /**
     * @param uid Identifiant à retourner
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(uid, "uid is required");
    }

    @Override
    public String getUid(Object request) {
        return uid;
    }

}
