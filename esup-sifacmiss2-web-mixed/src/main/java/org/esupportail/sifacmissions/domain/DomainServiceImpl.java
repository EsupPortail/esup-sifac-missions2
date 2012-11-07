/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.domain;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.esupportail.sifacmissions.models.Mission;
import org.esupportail.sifacmissions.models.MissionDetails;
import org.esupportail.sifacmissions.models.User;
import org.esupportail.sifacmissions.services.matricule.MatriculeService;
import org.esupportail.sifacmissions.services.mission.MissionException;
import org.esupportail.sifacmissions.services.mission.MissionService;
import org.esupportail.sifacmissions.services.user.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author Yves Deschamps (Universite de Lille 1)
 * @author Florent Cailhol (Anyware Services)
 */
public class DomainServiceImpl implements DomainService, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MissionService sifacService;
    private UserService userService;
    private MatriculeService matriculeService;
    private CacheManager cacheManager;
    private String cacheName;
    private Cache cache;

    /**
     * @param userService Service de récupération des utilisateurs
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param sifacService Services SIFAC
     */
    public void setSifacService(MissionService sifacService) {
        this.sifacService = sifacService;
    }

    /**
     * @param matriculeService Service de récupération des matricules
     */
    public void setMatriculeService(MatriculeService matriculeService) {
        this.matriculeService = matriculeService;
    }

    /**
     * @param cacheManager Gestionnaire de cache
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * @param cacheName Nom interne du cache à utiliser
     */
    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(userService, "property userService can not be null");
        Assert.notNull(sifacService, "property sifacService can not be null");
        Assert.notNull(matriculeService, "property matriculeService can not be null");
        Assert.notNull(cacheManager, "property cacheManager can not be null");
        Assert.notNull(cacheName, "property cacheName can not be null");

        if (!cacheManager.cacheExists(cacheName)) {
            cacheManager.addCache(cacheName);
        }

        cache = cacheManager.getCache(cacheName);
    }

    @Override
    public User getUser(final String id) {
        User user = null;
        if (cache.get(id) != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("User " + id + " found in cache");
            }

            user = (User) cache.get(id).getObjectValue();
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("User " + id + " not found in cache...");
            }

            user = userService.getUser(id);

            if (user != null) {
                cache.put(new Element(id, user));
            } else {
                logger.warn("User " + id + " not found");
            }
        }

        return user;
    }

    @Override
    public Integer getFirstYear() {
        return sifacService.getFirstYear();
    }

    @Override
    public String getNom(String id) {
        return getUser(id).getNom().toUpperCase();
    }

    @Override
    public String getPrenom(String id) {
        return getUser(id).getPrenom().toUpperCase();
    }

    @Override
    public List<Mission> getFraisMissions(String matricule, String nom, String prenom, Integer year) throws MissionException {
        return sifacService.getFraisMissions(matricule, nom, prenom, year);
    }

    @Override
    public List<MissionDetails> getMissionDetails(String matricule, String numeroMission) throws MissionException {
        return sifacService.getMissionDetails(matricule, numeroMission);
    }

    @Override
    public MatriculeService getMatriculeService() {
        return matriculeService;
    }

}
