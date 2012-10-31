package org.esupportail.sifacmissions.domain;

import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

/**
 * Service permettant de récupérer le matricule de l'utilisateur depuis un
 * annuaire LDAP.
 *
 * @author Yves Deschamps (Université Lille1 - France)
 */
public class MatriculeByLdap implements MatriculeService, InitializingBean {

    private final Logger logger = new LoggerImpl(getClass());

    private LdapUserService ldapUserService;
    private String matriculeAttribute;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(ldapUserService, "property ldapUserService of class " + getClass().getName() + " can not be null");
        Assert.notNull(matriculeAttribute, "property matriculeAttribute of class " + getClass().getName() + " can not be null");
    }

    /**
     * @param ldapUserService Service de récupération des utilisateurs via LDAP
     */
    public void setLdapUserService(LdapUserService ldapUserService) {
        this.ldapUserService = ldapUserService;
    }

    /**
     * @param matriculeAttribute Attribut LDAP permettant de lire le matricule
     */
    public void setMatriculeAttribute(String matriculeAttribute) {
        this.matriculeAttribute = matriculeAttribute;
    }

    @Override
    public String getMatricule(String id) {
        LdapUser ldapUser = ldapUserService.getLdapUser(id);
        String matricule = ldapUser.getAttribute(matriculeAttribute);

        if (StringUtils.hasText(matricule)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Récupération du matricule '" + matricule + "' pour '" + id + "'");
            }

            return matricule;
        }

        logger.warn("Aucun matricule n'a pu être récupéré pour '" + id + "'");
        return null;
    }

}