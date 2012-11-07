package org.esupportail.sifacmissions.services.user;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.esupportail.sifacmissions.models.User;

import org.springframework.ldap.core.AttributesMapper;

public class LdapUserAttributesMapper implements AttributesMapper {

    private final String uidAttribute;
    private final String firstnameAttribute;
    private final String lastnameAttribute;

    public LdapUserAttributesMapper(String uidAttribute, String firstnameAttribute, String lastnameAttribute) {
        this.uidAttribute = uidAttribute;
        this.firstnameAttribute = firstnameAttribute;
        this.lastnameAttribute = lastnameAttribute;
    }

    @Override
    public Object mapFromAttributes(Attributes attributes) throws NamingException {
        User user = new User();

        user.setLogin(getAttribute(attributes, uidAttribute));
        user.setNom(getAttribute(attributes, lastnameAttribute));
        user.setPrenom(getAttribute(attributes, firstnameAttribute));

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

    private static String getAttribute(Attributes attributes, String attrID) throws NamingException {
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
