package org.esupportail.sifacmissions.services.matricule;

import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Service permettant de récupérer le matricule de l'utilisateur depuis un
 * annuaire LDAP.
 *
 * @author Yves Deschamps (Université Lille1 - France)
 * @author Florent Cailhol (Anyware Services)
 */
public class LdapMatriculeService implements MatriculeService, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private LdapUserService ldapUserService;
    private String matriculeAttribute;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(ldapUserService, "property ldapUserService can not be null");
        Assert.notNull(matriculeAttribute, "property matriculeAttribute can not be null");
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