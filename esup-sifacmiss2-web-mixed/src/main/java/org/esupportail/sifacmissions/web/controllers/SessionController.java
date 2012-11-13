/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.sifacmissions.web.controllers;

import java.io.IOException;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.esupportail.commons.utils.strings.StringUtils;
import org.esupportail.commons.web.controllers.ExceptionController;
import org.esupportail.sifacmissions.services.auth.Authenticator;

import org.apache.myfaces.trinidad.util.ExternalContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author Yves Deschamps (Universite de Lille 1)
 * @author Florent Cailhol (Anyware Services)
 */
@SuppressWarnings("serial")
public class SessionController extends AbstractDomainAwareBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String action;
    private ExceptionController exceptionController;
    private Authenticator authenticator;
    private boolean modeDetected;
    private boolean portletMode;
    private String casLogoutUrl;

    /**
     * @param exceptionController Gestionnaire d'exceptions
     */
    public void setExceptionController(ExceptionController exceptionController) {
        this.exceptionController = exceptionController;
    }

    /**
     * @param casLogoutUrl the casLogoutUrl to set
     */
    public void setCasLogoutUrl(String casLogoutUrl) {
        this.casLogoutUrl = casLogoutUrl;
    }

    /**
     * @param authenticator Service d'authentification
     */
    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    protected void afterPropertiesSetInternal() {
        Assert.notNull(exceptionController, "exceptionController is required");
        Assert.notNull(authenticator, "authenticator is required");
        Assert.notNull(casLogoutUrl, "casLogoutUrl is required");
    }

    @Override
    public String getCurrentUser() {
        if (isPortletMode()) {
            FacesContext fc = FacesContext.getCurrentInstance();
            return fc.getExternalContext().getRemoteUser();
        }

        try {
            return authenticator.getUser();
        } catch (Exception e) {
            logger.error("Failed to get current user", e);
        }

        return null;
    }

    @Override
    public void reset() {
        super.reset();
        action = "missions";
    }

    /**
     * @return Action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action Action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return <code>true</code> si mode portlet.
     */
    public boolean isPortletMode() {
        if (!modeDetected) {
            modeDetected = true;

            if (logger.isDebugEnabled()) {
                logger.debug("Mode detected in Application");
            }

            FacesContext fc = FacesContext.getCurrentInstance();
            portletMode = ExternalContextUtils.isPortlet(fc.getExternalContext());

            if (logger.isDebugEnabled()) {
                if (portletMode) {
                    logger.debug("Portlet mode detected");
                } else {
                    logger.debug("Servlet mode detected");
                }
            }
        }
        return portletMode;
    }

    /**
     * Déconnexion.
     *
     * @return null
     */
    public String logoutAction() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        String preReturnUrl = request.getRequestURL().toString().replaceFirst("/stylesheets/[^/]*$", "");
        int index = preReturnUrl.lastIndexOf("/");

        String returnUrl = preReturnUrl.substring(0, index + 1).concat("missions.xhtml");
        String forwardUrl = String.format(casLogoutUrl, StringUtils.utf8UrlEncode(returnUrl));

        request.getSession().invalidate();
        request.getSession(true);

        exceptionController.restart();
        externalContext.redirect(forwardUrl);
        facesContext.responseComplete();

        return null;
    }

    @Override
    public Locale getLocale() {
        Locale locale = null;
        FacesContext context = FacesContext.getCurrentInstance();

        if (context != null) {
            locale = context.getViewRoot().getLocale();
        } else {
            locale = new Locale("fr");
        }

        return locale;
    }

    /**
     * @param language Langue
     */
    public void setDefaultLanguage(String language) {
        setSessionLocale(new Locale(language));
    }
}
