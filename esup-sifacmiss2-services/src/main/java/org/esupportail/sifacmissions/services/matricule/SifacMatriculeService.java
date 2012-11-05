package org.esupportail.sifacmissions.services.matricule;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.esupportail.sifacmissions.ws.SifacMatricule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Service permettant de récupérer le matricule de l'utilisateur depuis le
 * web service de SIFAC.
 *
 * @author Yves Deschamps (Universite Lille1 - France)
 * @author Florent Cailhol (Anyware Services)
 */
public class SifacMatriculeService implements MatriculeService, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private SifacMatricule matriculeService;
    private CacheManager cacheManager;
    private String cacheName;
    private Cache cache;

    /**
     * @param matriculeService Service SIFAC de récupération des matricules
     */
    public void setMatriculeService(SifacMatricule matriculeService) {
        this.matriculeService = matriculeService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(matriculeService, "property matriculeService can not be null");
        Assert.notNull(cacheManager, "property cacheManager can not be null");
        Assert.notNull(cacheName, "property cacheName can not be null");

        if (!cacheManager.cacheExists(cacheName)) {
            cacheManager.addCache(cacheName);
        }

        cache = cacheManager.getCache(cacheName);
    }

    @Override
    public String getMatricule(String id) {
        if (cache.get(id) == null) {
            try {
                String matricule = matriculeService.getMatricule(id);

                if (StringUtils.hasText(matricule)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Récupération du matricule '" + matricule + "' pour '" + id + "'");
                    }

                    cache.put(new Element(id, matricule));
                    return matricule;
                } else {
                    logger.warn("Aucun matricule n'a pu être récupéré pour '" + id + "'");
                }
            } catch (Exception e) {
                logger.error("Problème d'accès au web service SAP-GETMATRICULE", e);
            }
        } else {
            String matricule = (String) cache.get(id).getObjectValue();

            if (logger.isDebugEnabled()) {
                logger.debug("Récupération du matricule '" + matricule + "' pour '" + id + "' depuis le cache");
            }

            return matricule;
        }

        return null;
    }

}