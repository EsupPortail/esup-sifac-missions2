package org.esupportail.sifacmissions.services.user;

import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;

import org.esupportail.sifacmissions.models.User;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.util.Assert;

public class LdapUserService implements UserService, InitializingBean {

    private LdapTemplate ldapTemplate;
    private String dnSubPath;
    private String objectClass;
    private String uidAttribute;

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

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(ldapTemplate, "property ldapTemplate can not be null");
        Assert.notNull(dnSubPath, "property dnSubPath can not be null");
        Assert.notNull(objectClass, "property objectClass can not be null");
        Assert.notNull(uidAttribute, "property uidAttribute can not be null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public User getUser(String uid) {
        DistinguishedName dn = new DistinguishedName(dnSubPath);

        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", objectClass));
        filter.and(new EqualsFilter(uidAttribute, uid));

        List<User> users = ldapTemplate.search(dn, filter.encode(), SearchControls.SUBTREE_SCOPE, new UserAttributesMapper());

        if (users.size() != 1) {
            return null;
        }

        return users.get(0);
    }

    private class UserAttributesMapper implements AttributesMapper {

        @Override
        public Object mapFromAttributes(Attributes attributes) throws NamingException {
            User user = new User();

            user.setLogin(getAttribute(attributes, uidAttribute));
            user.setNom(getAttribute(attributes, "sn"));
            user.setPrenom(getAttribute(attributes, "givenName"));

            NamingEnumeration<? extends Attribute> attrs = attributes.getAll();
            while (attrs.hasMore()) {
                Attribute attr = attrs.next();
                Object value = attr.get();
                if (value != null) {
                    user.getAttributes().put(attr.getID(), value.toString());
                }
            }

            return user;
        }

        private String getAttribute(Attributes attributes, String attrID) throws NamingException {
            Attribute attr = attributes.get(attrID);
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
