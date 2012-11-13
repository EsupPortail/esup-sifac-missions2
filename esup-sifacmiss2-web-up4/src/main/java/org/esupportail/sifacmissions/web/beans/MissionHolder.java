package org.esupportail.sifacmissions.web.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Ce composant permet de stocker en session les informations relatives aux
 * missions.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Service("missionHolder")
@Scope("session")
public class MissionHolder {

    private int currentYear;

    /**
     * @return Année affichée
     */
    public int getCurrentYear() {
        return currentYear;
    }

    /**
     * @param currentYear Année affichée
     */
    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }
}
