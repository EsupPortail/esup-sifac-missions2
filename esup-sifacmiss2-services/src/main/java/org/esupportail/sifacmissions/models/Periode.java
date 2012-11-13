package org.esupportail.sifacmissions.models;

import java.util.Date;

/**
 * Cette classe permet de représenter un interval de temps.
 *
 * @author Yves deschamps (Universite Lille1)
 * @author Florent Cailhol (Anyware Services)
 */
public class Periode {

    private Date debut;
    private Date fin;

    /**
     * @return Date de debut
     */
    public Date getDebut() {
        return debut;
    }

    /**
     * @param debut Date de debut
     */
    public void setDebut(Date debut) {
        this.debut = debut;
    }

    /**
     * @return Date de fin
     */
    public Date getFin() {
        return fin;
    }

    /**
     * @param fin Date de fin
     */
    public void setFin(Date fin) {
        this.fin = fin;
    }

}
