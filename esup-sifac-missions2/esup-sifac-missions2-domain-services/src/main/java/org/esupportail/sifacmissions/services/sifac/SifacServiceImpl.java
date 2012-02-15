/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.services.sifac;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.sifacmissions.domain.beans.Mission;
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

	/**
	 * @throws ServiceException
	 * @throws ServiceException
	 * @throws RemoteException
	 * @throws ParseException
	 * @see org.esupportail.sifacmissions.services.sifac.SifacService#getFraisMissions(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Mission> getFraisMissions(String matricule, String nom,
			String prenom, Integer year) throws ServiceException,
			RemoteException, ParseException {

		String mandat = mandant;
		String paramNom = "";
		String paramPrenom = "";
		
		if (nom != null) {
			paramNom = nom;
		}
		
		if (prenom != null) {
			paramPrenom = "";
		}
		
		List<Mission> fms = null;
		String cacheKey = matricule+"|"+paramNom+"|"+paramPrenom+"|"+year+"|"+mandat;
		
		if (cache.get(cacheKey) == null) {
			fms = portailService.getFraisMissions(matricule, paramNom, paramPrenom, year.toString());
			
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
