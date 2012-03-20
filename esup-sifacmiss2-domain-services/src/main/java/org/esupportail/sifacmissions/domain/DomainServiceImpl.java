/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.domain;

import java.util.ArrayList;
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
 * The basic implementation of DomainService.
 * 
 */
public class DomainServiceImpl implements DomainService, InitializingBean {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -8200845058340254019L;

	/**
	 * {@link SifacService}.
	 */
	private SifacService sifacService;

	/**
	 * {@link LdapUserService}.
	 */
	private LdapUserService ldapUserService;

	/**
	 * The matricule service.
	 */
	private MatriculeService matriculeService;

	/**
	 * The administrators.
	 */
	private List<String> administrators;

	/**
	 * the cacheManager.
	 */
	private CacheManager cacheManager;
	
	/**
	 * the cache name.
	 */
	private String cacheName;

	/**
	 * the cache.
	 */
	private Cache cache;

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * Bean constructor.
	 */
	public DomainServiceImpl() {
		administrators = new ArrayList<String>();
	}
	
	/**
     * @param ldapUserService The ldapUserService to set
     */
    public void setLdapUserService(LdapUserService ldapUserService) {
        this.ldapUserService = ldapUserService;
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.ldapUserService, "property ldapUserService of class " + this.getClass().getName() + " can not be null");
		Assert.notNull(this.sifacService, "property sifacService of class " + this.getClass().getName() + " can not be null");
		Assert.notNull(this.matriculeService, "property matriculeService of class " + this.getClass().getName() + " can not be null");
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
			user.setLanguage("fr");
			
			if (administrators.contains(id)) {
				user.setAdmin(true);
			}
			
			if (logger.isDebugEnabled()) {
			    logger.debug("User " + id + " not found in cache... and put in...");
			}
			
			cache.put(new Element(id, user));
			
			return user;
		}
		
		return user;
	}
	
	/**
	 * @see org.esupportail.sifacmissions.domain.DomainService#getFirstYear()
	 */
	public Integer getFirstYear() {
		return getSifacService().getFirstYear();
	}

	/**
	 * @return the sifacService.
	 */
	public SifacService getSifacService() {
		return sifacService;
	}

	/**
	 * @param sifacService
	 *            the sifacService to set
	 */
	public void setSifacService(SifacService sifacService) {
		this.sifacService = sifacService;
	}

	/**
	 * @see org.esupportail.sifacmissions.domain.DomainService#getNom(java.lang.String)
	 */
	public String getNom(String id) {
		LdapUser ldapUser = this.ldapUserService.getLdapUser(id);
		return ldapUser.getAttribute("sn").toUpperCase();
	}

	/**
	 * @see org.esupportail.sifacmissions.domain.DomainService#getPrenom(java.lang.String)
	 */
	public String getPrenom(String id) {
		LdapUser ldapUser = this.ldapUserService.getLdapUser(id);
		return ldapUser.getAttribute("givenName").toUpperCase();
	}

	/**
	 * @see org.esupportail.sifacmissions.domain.DomainService#getFraisMissions(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public List<Mission> getFraisMissions(String matricule, String nom,
			String prenom, Integer year) throws SifacException {
		return getSifacService().getFraisMissions(matricule, nom, prenom, year);
	}
	
	/**
	 * @see org.esupportail.sifacmissions.domain.DomainService#getMissionDetails(java.lang.String,
	 *      java.lang.String)
	 */
	public List<MissionDetails> getMissionDetails(String matricule, 
			String numeroMission) throws SifacException {
		return getSifacService().getMissionDetails(matricule, numeroMission);
	}

	/**
	 * @see org.esupportail.sifacmissions.domain.DomainService#isHomonyme(org.esupportail.sifacmissions.domain.beans.User)
	 */
	public Boolean isHomonyme(User user) {
		String filter = "(displayname=" + user.getDisplayName() + ")";
		List<LdapUser> users = ldapUserService.getLdapUsersFromFilter(filter);
		if (users.size() > 1)
			return true;
		return false;
	}

	/**
	 * @see org.esupportail.sifacmissions.domain.DomainService#getMatriculeService()
	 */
	public MatriculeService getMatriculeService() {
		return matriculeService;
	}

	/**
	 * @param matriculeService The mtriculeService to set.
	 */
	public void setMatriculeService(MatriculeService matriculeService) {
		this.matriculeService = matriculeService;
	}

	/**
	 * @param administrators The administrators to set.
	 */
	public void setAdministrators(List<String> administrators) {
		this.administrators = administrators;
	}

	/**
	 * @param cacheManager
	 *            the cacheManager to set
	 */
	public void setCacheManager(final CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * @param cacheName
	 *            the cacheName to set
	 */
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

}
