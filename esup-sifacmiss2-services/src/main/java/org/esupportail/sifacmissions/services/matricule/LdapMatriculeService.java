package org.esupportail.sifacmissions.services.matricule;

import org.esupportail.sifacmissions.models.User;
import org.esupportail.sifacmissions.services.user.UserService;

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

    private UserService userService;
    private String matriculeAttribute;

    /**
     * @param userService Service de récupération des utilisateurs
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param matriculeAttribute Attribut LDAP permettant de lire le matricule
     */
    public void setMatriculeAttribute(String matriculeAttribute) {
        this.matriculeAttribute = matriculeAttribute;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(userService, "userService is required");
        Assert.notNull(matriculeAttribute, "matriculeAttribute is required");
    }

    @Override
    public String getMatricule(String id) {
        User user = userService.getUser(id);
        String matricule = user.getAttributes().get(matriculeAttribute);

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