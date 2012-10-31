package org.esupportail.sifacmissions.web.controllers;

import org.esupportail.sifacmissions.domain.beans.Mission;

@SuppressWarnings("serial")
public class MobileMissionController extends MissionController {

	private Mission currentMission;
	
	@Override
	public void reset() {
		super.reset();
		currentMission = null;
	}

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
		requestMissionDetails(mission);
		currentMission = mission;
	}

}
