/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.domain;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.sifacmissions.ws.SifacMatriculeService;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Yves deschamps - Universite Lille1 - France
 * 
 */
public class MatriculeBySifac implements MatriculeService, InitializingBean {

	/**
	 * The serialization id
	 */
	private static final long serialVersionUID = 8799556709982230546L;

	/**
	 * A logger.
	 */
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

			if (matricule != null) {
				return matricule;
			} else {
				logger.error("Aucun matricule n a pu etre recupere pour " + id + " par le WS SAP-GETMATRICULE");
			}
		} catch (ServiceException e) {
			logger.error("Probleme d'acces au WS SAP-GETMATRICULE", e);
		} catch (RemoteException e) {
			logger.error("Probleme d'acces a SAP pour l'appel au WS SAP-GETMATRICULE", e);
		}

		return null;
	}
}