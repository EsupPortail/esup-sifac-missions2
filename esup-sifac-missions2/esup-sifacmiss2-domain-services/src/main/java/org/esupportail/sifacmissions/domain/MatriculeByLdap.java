/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.domain;

import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserService;
import org.esupportail.commons.utils.Assert;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Yves deschamps - Universite Lille1 - France
 */
public class MatriculeByLdap implements MatriculeService, InitializingBean {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = -6041573166349914967L;

	/**
	 * {@link LdapUserService}.
	 */
	private LdapUserService ldapUserService;
	
	/**
	 * The matricule attribute.
	 */
	private String matriculeAttribute;

	/**
	 * Bean constructor.
	 */
	public MatriculeByLdap() {
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(ldapUserService, "property ldapUserService of class " + getClass().getName() + " can not be null");
		Assert.notNull(matriculeAttribute, "property matriculeAttribute of class " + getClass().getName() + " can not be null");
	}

	/**
	 * @param ldapUserService the ldapUserService to set
	 */
	public void setLdapUserService(LdapUserService ldapUserService) {
		this.ldapUserService = ldapUserService;
	}

	/**
	 * @param matriculeAttribute the matriculeAttribute to set
	 */
	public void setMatriculeAttribute(String matriculeAttribute) {
		this.matriculeAttribute = matriculeAttribute;
	}

	@Override
	public String getMatricule(String id) {
		LdapUser ldapUser = ldapUserService.getLdapUser(id);
		return ldapUser.getAttribute(matriculeAttribute);
	}

}