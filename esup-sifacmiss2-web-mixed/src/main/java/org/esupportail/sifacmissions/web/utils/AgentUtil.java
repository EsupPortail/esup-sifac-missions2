/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.sifacmissions.web.utils;

import java.util.Iterator;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author Yves Deschamps (Universite de Lille 1)
 */
public class AgentUtil implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String phoneFamily;
    private Map<String, String> skins;
    private boolean mobile;

    /**
     * @param skins Thèmes
     */
    public void setSkins(Map<String, String> skins) {
        this.skins = skins;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(skins, "skins is required");
    }

    /**
     * Détecte le thème à utiliser en fonction de l'en-tête
     * <code>User-Agent</code> envoyé lors de la requête à la page.
     *
     * @return Thème à utiliser
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
     * @return <code>true</code> si l'utilisateur utilise un mobile.
     */
    public boolean isMobile() {
        return mobile;
    }

}
