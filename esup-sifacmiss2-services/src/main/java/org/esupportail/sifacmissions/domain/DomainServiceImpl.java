/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.domain;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.sifacmissions.domain.beans.Mission;
import org.esupportail.sifacmissions.domain.beans.MissionDetails;
import org.esupportail.sifacmissions.domain.beans.User;
import org.esupportail.sifacmissions.services.sifac.SifacException;
import org.esupportail.sifacmissions.services.sifac.SifacService;
import org.springframework.beans.factory.InitializingBean;

/**
 * @see org.esupportail.sifacmissions.domain.DomainService
 * 
 * @author Yves Deschamps (Universite de Lille 1)
 * @author Florent Cailhol (Anyware Services)
 */
public class DomainServiceImpl implements DomainService, InitializingBean {

	private final Logger logger = new LoggerImpl(getClass());

	private SifacService sifacService;
	private LdapUserService ldapUserService;
	private MatriculeService matriculeService;
	private CacheManager cacheManager;
	private String cacheName;
	private Cache cache;

	/**
	 * @param ldapUserService Service de récupération des utilisateurs via LDAP
	 */
	public void setLdapUserService(LdapUserService ldapUserService) {
		this.ldapUserService = ldapUserService;
	}

	/**
	 * @param sifacService Services SIFAC
	 */
	public void setSifacService(SifacService sifacService) {
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
		Assert.notNull(ldapUserService, "property ldapUserService of class " + getClass().getName() + " can not be null");
		Assert.notNull(sifacService, "property sifacService of class " + getClass().getName() + " can not be null");
		Assert.notNull(matriculeService, "property matriculeService of class " + getClass().getName() + " can not be null");
		Assert.notNull(cacheManager, "property cacheManager of class " + getClass().getName() + " can not be null");
		Assert.notNull(cacheName, "property cacheName of class " + getClass().getName() + " can not be null");

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
		}
		else {
			LdapUser ldapUser = this.ldapUserService.getLdapUser(id);

			user = new User();
			user.setLogin(ldapUser.getId());
			user.setDisplayName(ldapUser.getAttribute("displayName"));

			if (logger.isDebugEnabled()) {
				logger.debug("User " + id + " not found in cache... and put in...");
			}

			cache.put(new Element(id, user));

			return user;
		}

		return user;
	}

	@Override
	public Integer getFirstYear() {
		return sifacService.getFirstYear();
	}

	@Override
	public String getNom(String id) {
		LdapUser ldapUser = ldapUserService.getLdapUser(id);
		return ldapUser.getAttribute("sn").toUpperCase();
	}

	@Override
	public String getPrenom(String id) {
		LdapUser ldapUser = ldapUserService.getLdapUser(id);
		return ldapUser.getAttribute("givenName").toUpperCase();
	}

	@Override
	public List<Mission> getFraisMissions(String matricule, String nom, String prenom, Integer year) throws SifacException {
		return sifacService.getFraisMissions(matricule, nom, prenom, year);
	}

	@Override
	public List<MissionDetails> getMissionDetails(String matricule, String numeroMission) throws SifacException {
		return sifacService.getMissionDetails(matricule, numeroMission);
	}

	@Override
	public Boolean isHomonyme(User user) {
		String filter = "(displayname=" + user.getDisplayName() + ")";
		List<LdapUser> users = ldapUserService.getLdapUsersFromFilter(filter);
		
		return users.size() > 1;
	}

	@Override
	public MatriculeService getMatriculeService() {
		return matriculeService;
	}

}
