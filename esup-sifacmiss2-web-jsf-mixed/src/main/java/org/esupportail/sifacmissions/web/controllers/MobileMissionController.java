package org.esupportail.sifacmissions.web.controllers;

import org.esupportail.sifacmissions.domain.beans.Mission;

@SuppressWarnings("serial")
public class MobileMissionController extends MissionController {

	private Mission currentMission;

	/**
	 * @return the current mission
	 */
	public Mission getCurrentMission() {
		return currentMission;
	}

	/**
	 * @param mission the current mission
	 */
	public void setCurrentMission(Mission mission) {
		// Reset the mission table
		setMissionsTable(null);

		// Get mission details and update the current one
		requestMissionDetails(mission);
		currentMission = mission;
	}

}
