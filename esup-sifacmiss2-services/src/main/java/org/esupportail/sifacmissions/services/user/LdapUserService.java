package org.esupportail.sifacmissions.services.user;

import java.util.List;

import javax.naming.directory.SearchControls;

import org.esupportail.sifacmissions.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.util.Assert;

public class LdapUserService implements UserService, InitializingBean {

    private static final String LDAP_GIVEN_NAME = "givenName";
    private static final String LDAP_SN = "sn";
    private static final String LDAP_UID = "uid";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private LdapTemplate ldapTemplate;
    private AttributesMapper attributesMapper;
    private String dnSubPath;
    private String objectClass;
    private String uidAttribute = LDAP_UID;
    private String firstnameAttribute = LDAP_GIVEN_NAME;
    private String lastnameAttribute = LDAP_SN;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public void setDnSubPath(String dnSubPath) {
        this.dnSubPath = dnSubPath;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public void setUidAttribute(String uidAttribute) {
        this.uidAttribute = uidAttribute;
    }

    public void setFirstnameAttribute(String firstnameAttribute) {
        this.firstnameAttribute = firstnameAttribute;
    }

    public void setLastnameAttribute(String lastnameAttribute) {
        this.lastnameAttribute = lastnameAttribute;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(ldapTemplate, "ldapTemplate is required");
        Assert.notNull(dnSubPath, "dnSubPath is required");
        Assert.notNull(objectClass, "objectClass is required");
        Assert.hasText(uidAttribute, "uidAttribute is required");
        Assert.hasText(firstnameAttribute, "firstnameAttribute is required");
        Assert.hasText(lastnameAttribute, "lastnameAttribute is required");

        attributesMapper = new LdapUserAttributesMapper(uidAttribute, firstnameAttribute, lastnameAttribute);
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getUser(String uid) {
        DistinguishedName dn = new DistinguishedName(dnSubPath);

        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", objectClass));
        filter.and(new EqualsFilter(uidAttribute, uid));

        List<User> users = ldapTemplate.search(dn, filter.encode(), SearchControls.SUBTREE_SCOPE, attributesMapper);

        if (users.size() != 1) {
            if (logger.isWarnEnabled()) {
                logger.warn("Unable to find user with uid {}", uid);
            }

            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Found user with uid {}", uid);
        }

        return users.get(0);
    }

}
