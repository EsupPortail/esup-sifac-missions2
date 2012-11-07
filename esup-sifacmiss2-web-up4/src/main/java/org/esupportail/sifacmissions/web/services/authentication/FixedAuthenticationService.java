package org.esupportail.sifacmissions.web.services.authentication;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class FixedAuthenticationService implements AuthenticationService, InitializingBean {

    private String uid;

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
