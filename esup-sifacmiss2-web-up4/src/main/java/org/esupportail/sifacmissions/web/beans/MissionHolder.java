package org.esupportail.sifacmissions.web.beans;

import org.esupportail.sifacmissions.models.Mission;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("missionHolder")
@Scope("session")
public class MissionHolder {

    private Mission currentMission;
    private int currentYear;

    public Mission getCurrentMission() {
        return currentMission;
    }

    public void setCurrentMission(Mission currentMission) {
        this.currentMission = currentMission;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

}
