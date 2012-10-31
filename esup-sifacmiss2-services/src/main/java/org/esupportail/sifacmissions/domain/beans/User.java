package org.esupportail.sifacmissions.domain.beans;

import java.io.Serializable;

import org.springframework.core.style.ToStringCreator;

/**
 * Cette classe représente un utilisateur.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 3045693858467707618L;

    private String login;
    private String displayName;

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
        return tsc.append("login", login).append("displayName", displayName).toString();
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
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * @param displayName Nom
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}