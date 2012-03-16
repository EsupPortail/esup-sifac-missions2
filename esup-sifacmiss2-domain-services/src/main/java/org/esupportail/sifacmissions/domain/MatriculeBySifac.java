/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.domain;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.sifacmissions.ws.SifacMatriculeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

/**
 * @author Yves deschamps - Universite Lille1 - France
 * 
 */
public class MatriculeBySifac implements MatriculeService, InitializingBean {

	private static final long serialVersionUID = 8799556709982230546L;

	private final Logger logger = new LoggerImpl(getClass());

	private SifacMatriculeService matriculeService;

	/**
	 * Bean constructor.
	 */
	public MatriculeBySifac() {
		super();
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(matriculeService, "property matriculeService of class " + getClass().getName() + " can not be null");
	}

	public void setMatriculeService(SifacMatriculeService matriculeService) {
		this.matriculeService = matriculeService;
	}

	public String getMatricule(String id) {
		try {
			String matricule = matriculeService.getMatricule(id);

			if (StringUtils.hasText(matricule)) {
				logger.info("Récupération du matricule '" + matricule + "' pour '" + id + "'");
				return matricule;
			} else {
				logger.error("Aucun matricule n'a pu être récupéré pour '" + id + "'");
			}
		} catch (Exception e) {
			logger.error("Problème d'accès au web service SAP-GETMATRICULE", e);
		}

		return null;
	}
}