/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.sifacmissions.web.controllers;

import java.io.IOException;
import java.util.Locale;

import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.myfaces.trinidad.util.ExternalContextUtils;
import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.services.i18n.I18nUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.utils.strings.StringUtils;
import org.esupportail.sifacmissions.domain.beans.User;
import org.esupportail.sifacmissions.services.auth.Authenticator;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 * 
 */
public class SessionController extends AbstractDomainAwareBean {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * For Logging.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * The current action from menu.
	 */
	private String action;

	/**
	 * The authenticator.
	 */
	private Authenticator authenticator;

	/**
	 * The detected mode (desktop or mobile).
	 */
	private boolean modeDetected;

	/**
	 * True if we are in portlet mode.
	 */
	private boolean portletMode;

	/**
	 * The CAS logout URL.
	 */
	private String casLogoutUrl;

	/**
	 * The current User.
	 */
	private User currentUser;
	
	/**
	 * Constructor.
	 */
	public SessionController() {
		super();
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(authenticator, "property authenticator of class " + this.getClass().getName() + " can not be null");
		Assert.notNull(casLogoutUrl, "property casLogoutUrl of class " + this.getClass().getName() + " can not be null");
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public User getCurrentUser() {
		if (isPortletMode()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			String uid = fc.getExternalContext().getRemoteUser();
			
			if (currentUser != null && currentUser.getLogin().equals(uid)) {
				return currentUser;
			}
			
			try {
				currentUser = getDomainService().getUser(uid);
			} catch (UserNotFoundException e) {
				currentUser = new User();
				currentUser.setLogin(uid);
				currentUser.setDisplayName(I18nUtils.createI18nService() .getString(e.getMessage()));
				currentUser.setAdmin(false);
			}
			
			return currentUser;
		}
		
		User authUser;
		
		try {
			authUser = authenticator.getUser();
			if (authUser != null) {
				if (currentUser != null && currentUser.getLogin().equals(authUser.getLogin())) {
					return currentUser;
				}
				
				String uid = authUser.getLogin();
				
				try {
					currentUser = getDomainService().getUser(uid);
				} catch (UserNotFoundException e) {
					currentUser = new User();
					currentUser.setLogin(uid);
					currentUser.setDisplayName(I18nUtils.createI18nService().getString(e.getMessage()));
					currentUser.setAdmin(false);
				}
				
				return currentUser;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		
		return null;
	}

	@Override
	public void reset() {
		super.reset();
		action = null;
		currentUser = null;
	}

	/**
	 * @return true if portlet mode.
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
	 * @return true if login button is enable.
	 * @throws Exception
	 */
	public boolean isLoginEnable() throws Exception {
		if (isPortletMode()) {
			return false;
		}
		
		return (getCurrentUser() == null);
	}

	/**
	 * @return true if login button is enable.
	 * @throws Exception
	 */
	public boolean isLogoutEnable() throws Exception {
		if (isPortletMode()) {
			return false;
		}
		
		return (getCurrentUser() != null);
	}

	/**
	 * @return nothing and make logout.
	 */
	public String logoutAction() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		String preReturnUrl = request.getRequestURL().toString().replaceFirst("/stylesheets/[^/]*$", "");
		int index = preReturnUrl.lastIndexOf("/");
		String returnUrl = preReturnUrl.substring(0, index + 1).concat("welcome.xhtml");
		String forwardUrl;
		forwardUrl = String.format(casLogoutUrl,StringUtils.utf8UrlEncode(returnUrl));
		request.getSession().invalidate();
		request.getSession(true);
		
		try {
			externalContext.redirect(forwardUrl);
		} catch (IOException e) {
			logger.error(e);
		}
		
		facesContext.responseComplete();
		action = "welcome";
		
		return null;
	}

	/**
	 * @param casLogoutUrl the casLogoutUrl to set
	 */
	public void setCasLogoutUrl(String casLogoutUrl) {
		this.casLogoutUrl = casLogoutUrl;
	}

	/**
	 * @param authenticator the authenticator to set
	 */
	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (context != null) {
			context.getViewRoot().setLocale(locale);
		} else {
			logger.warn("Cannot set the locale because the context is null");
		}
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
	 * @return an Url (with the good host, port and context...).
	 */
	public String getServletUrl() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getExternalContext().getRequestContextPath() + "/stylesheets/home.xhtml";
	}

	/**
	 * @return language.
	 */
	public String getDisplayLanguage() {
		Locale locale = getLocale();
		StringBuffer buf = new StringBuffer(locale.getDisplayLanguage(locale));
		
		return buf.toString();
	}

	/**
	 * @param event
	 * @return null;
	 */
	public String setLocaleAction(ActionEvent event) {
		UIParameter component = (UIParameter) event.getComponent().findComponent("language");
		String languageString = component.getValue().toString();
		FacesContext context = FacesContext.getCurrentInstance();
		
		if (context != null) {
			context.getViewRoot().setLocale(new Locale(languageString));
		}
		
		return null;
	}
	
	/**
	 * @param language the language to set
	 */
	public void setDefaultLanguage(String language) {
		setSessionLocale(new Locale(language));
	}
}
