package org.esupportail.sifacmissions.web.beans;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Ce composant permet de stocker en session les informations de l'utilisateur.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserParameters  implements Serializable {

    private static final long serialVersionUID = 8785072876451821566L;

    private String uid;
    private String matricule;

    /**
     * @return Identifiant
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid Identifiant
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return Matricule
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * @param matricule Matricule
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * @return <code>true</code> si le composant est initialisé
     */
    public boolean isInitialized() {
        return uid != null;
    }

}
