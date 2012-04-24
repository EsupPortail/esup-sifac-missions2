/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.sifacmissions.web.utils;

import java.util.Iterator;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 */
public class AgentUtil implements InitializingBean {

	private final Logger logger = new LoggerImpl(getClass());

	private String phoneFamily;
	private Map<String, String> skins;
	private boolean mobile;
	
	/**
	 * @param skins the skins to set
	 */
	public void setSkins(Map<String, String> skins) {
		this.skins = skins;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(skins, "property skins of class " + this.getClass().getName() + " can not be null");
	}

	/**
	 * @return the skin from user-agent detect.
	 */
	public String getPhoneFamily() {
		if (phoneFamily == null) {
			String agent = null;
			FacesContext fc = FacesContext.getCurrentInstance();
			agent = fc.getExternalContext().getRequestHeaderMap().get("User-Agent");
			
			if (logger.isDebugEnabled()) {
				logger.debug("User-Agent: " + agent);
			}
			
			for (Iterator<String> i = skins.keySet().iterator(); i.hasNext();) {
				String key = i.next();
				if (agent != null && agent.indexOf(key) > -1) {
					phoneFamily = skins.get(key);
					mobile = true;
					
					return phoneFamily;
				}
			}
			
			phoneFamily = "minimalFamily";
		}
		
		return phoneFamily;
	}

	/**
	 * @return true if mobile
	 */
	public boolean isMobile() {
		return mobile;
	}

}
