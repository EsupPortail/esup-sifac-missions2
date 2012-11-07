package org.esupportail.sifacmissions.web.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("userParameters")
@Scope("session")
public class UserParameters {

    private String uid;
    private String nom;
    private String prenom;
    private String matricule;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public boolean isInitialized() {
        return uid != null;
    }

}
