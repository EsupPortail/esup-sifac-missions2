package org.esupportail.sifacmissions.services.matricule;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.util.Assert;

public class LdapMatriculeService implements MatriculeService, InitializingBean {

    private static final String CACHE_NAME = LdapMatriculeService.class.getName();
    private static final String LDAP_OBJECT_CLASS = "Person";
    private static final String LDAP_UID = "uid";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private LdapTemplate ldapTemplate;
    private String dnSubPath;
    private String objectClass = LDAP_OBJECT_CLASS;
    private String uidAttribute = LDAP_UID;
    private String matriculeAttribute;
    private CacheManager cacheManager;
    private Cache cache;

    private AttributesMapper attributesMapper;
    private DistinguishedName dn;

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

    /**
     * @param matriculeAttribute Attribut LDAP permettant de lire le matricule
     */
    public void setMatriculeAttribute(String matriculeAttribute) {
        this.matriculeAttribute = matriculeAttribute;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cacheManager, "cacheManager is required");
        Assert.notNull(ldapTemplate, "ldapTemplate is required");
        Assert.hasText(dnSubPath, "dnSubPath is required");
        Assert.hasText(objectClass, "objectClass is required");
        Assert.hasText(uidAttribute, "uidAttribute is required");
        Assert.hasText(matriculeAttribute, "matriculeAttribute is required");

        attributesMapper = new LdapMatriculeAttributesMapper();
        dn = new DistinguishedName(dnSubPath);

        if (!cacheManager.cacheExists(CACHE_NAME)) {
            cacheManager.addCache(CACHE_NAME);
        }

        cache = cacheManager.getCache(CACHE_NAME);
    }

    @Override
    public String getMatricule(String uid) {
        String matricule;

        if (cache.get(uid) == null) {
            AndFilter filter = new AndFilter();
            filter.and(new EqualsFilter("objectClass", objectClass));
            filter.and(new EqualsFilter(uidAttribute, uid));

            @SuppressWarnings("unchecked")
            List<String> matricules = ldapTemplate.search(dn, filter.encode(), SearchControls.SUBTREE_SCOPE, attributesMapper);

            if (matricules.size() != 1) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Unable to find matricule with uid {}", uid);
                }

                return null;
            }

            matricule = matricules.get(0);

            if (logger.isDebugEnabled()) {
                logger.debug("Found matricule {} with uid {}", matricule, uid);
            }

            cache.put(new Element(uid, matricule));
        } else {
            matricule = (String) cache.get(uid).getObjectValue();
        }

        return matricule;
    }

    private class LdapMatriculeAttributesMapper implements AttributesMapper {

        @Override
        public Object mapFromAttributes(Attributes attributes) throws NamingException {
            Attribute attr = attributes.get(matriculeAttribute);
            if (attr != null) {
                Object value = attr.get();
                if (value != null) {
                    return value.toString();
                }
            }

            return "";
        }

    }

}
