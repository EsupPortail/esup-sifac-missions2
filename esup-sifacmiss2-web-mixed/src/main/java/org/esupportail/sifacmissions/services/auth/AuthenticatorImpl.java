package org.esupportail.sifacmissions.services.auth;

import org.esupportail.commons.services.authentication.AuthenticationService;
import org.esupportail.commons.services.authentication.info.AuthInfo;
import org.esupportail.commons.utils.ContextUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Cette classe permet d'authentifier l'utilisateur courant.
 *
 * @author Yves Deschamps (Universite de Lille 1)
 * @author Florent Cailhol (Anyware Services)
 */
public class AuthenticatorImpl implements Authenticator, InitializingBean {

    private static final String AUTH_INFO_ATTRIBUTE = AuthenticatorImpl.class.getName() + ".authInfo";
    private static final String USER_ATTRIBUTE = AuthenticatorImpl.class.getName() + ".user";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private AuthenticationService authenticationService;

    /**
     * @param authenticationService Service d'authentification
     */
    public void setAuthenticationService(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

        if (logger.isDebugEnabled()) {
            logger.debug("authenticationService: " + authenticationService.getClass().getName());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(authenticationService, "authenticationService is required");
    }

    @Override
    public String getUser() throws Exception {
        try {
            AuthInfo authInfo = (AuthInfo) ContextUtils.getSessionAttribute(AUTH_INFO_ATTRIBUTE);
            if (authInfo != null) {
                String user = (String) ContextUtils.getSessionAttribute(USER_ATTRIBUTE);
                if (logger.isDebugEnabled()) {
                    logger.debug("found auth info in session: {}", user);
                }

                return user;
            }

            if (logger.isDebugEnabled()) {
                logger.debug("No authInfo found in session");
            }

            authInfo = authenticationService.getAuthInfo();
            if (authInfo == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("authInfo is null");
                }
            } else {
                storeToSession(authInfo, authInfo.getId());
                return authInfo.getId();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }

        return null;
    }

    private void storeToSession(final AuthInfo authInfo, final String user) {
        if (logger.isDebugEnabled()) {
            logger.debug("Storing to session: {}", authInfo);
        }

        ContextUtils.setSessionAttribute(AUTH_INFO_ATTRIBUTE, authInfo);
        ContextUtils.setSessionAttribute(USER_ATTRIBUTE, user);
    }

}