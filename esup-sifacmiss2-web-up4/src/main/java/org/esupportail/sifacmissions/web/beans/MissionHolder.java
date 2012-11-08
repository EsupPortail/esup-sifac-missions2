package org.esupportail.sifacmissions.web.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("missionHolder")
@Scope("session")
public class MissionHolder {

    private int currentYear;

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }
}
