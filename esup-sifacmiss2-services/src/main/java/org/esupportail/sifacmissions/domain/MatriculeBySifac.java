package org.esupportail.sifacmissions.domain;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.sifacmissions.ws.SifacMatriculeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

/**
 * Service permettant de récupérer le matricule de l'utilisateur depuis le
 * web service de SIFAC.
 * 
 * @author Yves Deschamps (Universite Lille1 - France)
 */
public class MatriculeBySifac implements MatriculeService, InitializingBean {

	private final Logger logger = new LoggerImpl(getClass());

	private SifacMatriculeService matriculeService;

	/**
	 * @param matriculeService Service SIFAC de récupération des matricules
	 */
	public void setMatriculeService(SifacMatriculeService matriculeService) {
		this.matriculeService = matriculeService;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(matriculeService, "property matriculeService of class " + getClass().getName() + " can not be null");
	}

	@Override
	public String getMatricule(String id) {
		try {
			String matricule = matriculeService.getMatricule(id);

			if (StringUtils.hasText(matricule)) {
				if (logger.isDebugEnabled()) {
					logger.debug("Récupération du matricule '" + matricule + "' pour '" + id + "'");
				}

				return matricule;
			}
			else {
				logger.warn("Aucun matricule n'a pu être récupéré pour '" + id + "'");
			}
		}
		catch (Exception e) {
			logger.error("Problème d'accès au web service SAP-GETMATRICULE", e);
		}

		return null;
	}

}