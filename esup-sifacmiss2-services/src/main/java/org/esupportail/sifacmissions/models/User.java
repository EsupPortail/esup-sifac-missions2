package org.esupportail.sifacmissions.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.style.ToStringCreator;

/**
 * Cette classe représente un utilisateur.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 3045693858467707618L;

    private String login;
    private String nom;
    private String prenom;
    private Map<String, String> attributes;

    public User() {
        attributes = new HashMap<String, String>();
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof User)) {
            return false;
        }

        return login.equals(((User) obj).getLogin());
    }

    @Override
    public String toString() {
        ToStringCreator tsc = new ToStringCreator(this);
        return tsc.append("login", login).append("nom", nom).append("prenom", prenom).toString();
    }

    /**
     * @return Login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login Login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return Nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom Nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return Prénom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom Prénom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return Attributs
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes Attributs
     */
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

}