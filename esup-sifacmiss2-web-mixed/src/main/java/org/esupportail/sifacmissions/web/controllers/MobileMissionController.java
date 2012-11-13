package org.esupportail.sifacmissions.web.controllers;

import org.esupportail.sifacmissions.models.Mission;

/**
 * Contrôleur permettant l'affichage des frais de missions en vue mobile.
 *
 * @author Florent Cailhol (Anyware Services)
 */
@SuppressWarnings("serial")
public class MobileMissionController extends MissionController {

    private Mission currentMission;

    @Override
    public void reset() {
        super.reset();
        currentMission = null;
    }

    /**
     * @return Mission courante
     */
    public Mission getCurrentMission() {
        return currentMission;
    }

    /**
     * @param mission Mission courante
     */
    public void setCurrentMission(Mission mission) {
        requestMissionDetails(mission);
        currentMission = mission;
    }

}
