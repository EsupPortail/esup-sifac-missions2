package org.esupportail.sifacmissions.web.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("userParameters")
@Scope("session")
public class UserParameters {

    private String uid;
    private String matricule;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
