package org.esupportail.sifacmissions.web.beans;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Ce composant permet de stocker en session les informations relatives aux
 * missions.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MissionHolder implements Serializable {

    private static final long serialVersionUID = -5567711696709477297L;

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
