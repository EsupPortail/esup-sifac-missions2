package org.esupportail.sifacmissions.web.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Ce composant permet de stocker en session les informations de l'utilisateur.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Service("userParameters")
@Scope("session")
public class UserParameters {

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
