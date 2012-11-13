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
 * web service MATRICULE de Sifac.
 *
 * @author Yves Deschamps (Universite Lille1)
 * @author Florent Cailhol (Anyware Services)
 */
public class SifacMatriculeService implements MatriculeService, InitializingBean {

    private static final String CACHE_NAME = SifacMatriculeService.class.getName();
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private SifacMatricule matriculeService;
    private CacheManager cacheManager;
    private Cache cache;

    /**
     * @param matriculeService Service Sifac de récupération des matricules
     */
    public void setMatriculeService(SifacMatricule matriculeService) {
        this.matriculeService = matriculeService;
    }

    /**
     * @param cacheManager Gestionnaire de cache
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(matriculeService, "matriculeService is required");
        Assert.notNull(cacheManager, "cacheManager is required");

        if (!cacheManager.cacheExists(CACHE_NAME)) {
            cacheManager.addCache(CACHE_NAME);
        }

        cache = cacheManager.getCache(CACHE_NAME);
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