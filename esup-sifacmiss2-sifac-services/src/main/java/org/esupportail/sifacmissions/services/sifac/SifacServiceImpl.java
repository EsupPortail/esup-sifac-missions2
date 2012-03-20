/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.services.sifac;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.sifacmissions.domain.beans.Mission;
import org.esupportail.sifacmissions.domain.beans.MissionDetails;
import org.esupportail.sifacmissions.ws.SifacPortailService;
import org.springframework.beans.factory.InitializingBean;

/**
 * The implementation of SifacService.
 * 
 */
public class SifacServiceImpl implements SifacService, InitializingBean {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -8200845058340254019L;

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * The Sifac mandant.
	 */
	private String mandant;

	/**
	 * The first year of SIFAC Application.
	 */
	private Integer firstYear;
	
	/**
	 * The Web Service Stub
	 */
	private SifacPortailService portailService;

	/**
	 * the cacheManager.
	 */
	private CacheManager cacheManager;
	private String cacheName;
	private Cache cache;

	/**
	 * Bean constructor.
	 */
	public SifacServiceImpl() {
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(mandant, "property mandant of class " + getClass().getName() + " can not be null");
		Assert.notNull(firstYear, "property firstYear of class " + getClass().getName() + " can not be null");
		Assert.notNull(portailService, "property portailService of class " + this.getClass().getName() + " can not be null");
		Assert.notNull(cacheManager, "property cacheManager of class " + getClass().getName() + " can not be null");
		Assert.notNull(cacheName, "property cacheName of class " + getClass().getName() + " can not be null");
		
		if (!cacheManager.cacheExists(cacheName)) {
			cacheManager.addCache(cacheName);
		}
		
		cache = cacheManager.getCache(cacheName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mission> getFraisMissions(String matricule, String nom,
			String prenom, Integer year) throws SifacException {

		String paramNom = "";
		String paramPrenom = "";
		
		if (nom != null) {
			paramNom = nom;
		}
		
		if (prenom != null) {
			paramPrenom = "";
		}
		
		List<Mission> fms = null;
		String cacheKey = matricule+"|"+paramNom+"|"+paramPrenom+"|"+year+"|"+mandant;
		
		if (cache.get(cacheKey) == null) {
			try {
				fms = portailService.getFraisMissions(matricule, paramNom, paramPrenom, year.toString());
			}
			catch (Exception e) {
				throw new SifacException(e);
			}
			
			// put in cache
			if (logger.isDebugEnabled()) {
				logger.debug("Frais de missions " + cacheKey + " not found in cache");
			}
			
			cache.put(new Element(cacheKey, fms));
			return fms;
		} else {
			fms = (List<Mission>) cache.get(cacheKey).getObjectValue();
			
			if (logger.isDebugEnabled()) {
				logger.debug("Frais de missions " + cacheKey + " found in cache");
			}
		}
		
		return fms;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MissionDetails> getMissionDetails(String matricule, String numeroMission) throws SifacException {
		
		List<MissionDetails> details = null;
		String cacheKey = matricule+"|"+numeroMission+"|"+mandant;
		
		if (cache.get(cacheKey) == null) {
			try {
				details = portailService.getMissionDetails(matricule, numeroMission);
			}
			catch (Exception e) {
				throw new SifacException(e);
			}
			
			// put in cache
			if (logger.isDebugEnabled()) {
				logger.debug("Mission details " + cacheKey + " not found in cache");
			}
			
			cache.put(new Element(cacheKey, details));
			return details;
		} else {
			details = (List<MissionDetails>) cache.get(cacheKey).getObjectValue();
			
			if (logger.isDebugEnabled()) {
				logger.debug("Mission details " + cacheKey + " found in cache");
			}
		}
		
		return details;
	}

	/**
	 * @see org.esupportail.sifacmissions.services.sifac.SifacService#getFirstYear()
	 */
	public Integer getFirstYear() {
		return firstYear;
	}

	/**
	 * @param firstYear the firstYear to set.
	 */
	public void setFirstYear(Integer firstYear) {
		this.firstYear = firstYear;
	}
	
	/**
	 * @param service the service to set
	 */
	public void setPortailService(SifacPortailService service) {
		this.portailService = service;
	}

	/**
	 * @param cacheManager the cacheManager to set
	 */
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * @param cacheName the cacheName to set
	 */
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	/**
	 * @param mandant the mandant to set.
	 */
	public void setMandant(String mandant) {
		this.mandant = mandant;
	}
	
}
