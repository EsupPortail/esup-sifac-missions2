package org.esupportail.sifacmissions.domain;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Service permettant de récupérer le matricule de l'utilisateur depuis un
 * paramètre de configuration.
 * 
 * @author Florent Cailhol
 */
public class MatriculeByMock implements MatriculeService, InitializingBean {

	private String matricule;

	/**
	 * @param matricule Matricule
	 */
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(matricule);
	}

	@Override
	public String getMatricule(String uid) {
		return matricule;
	}

}
