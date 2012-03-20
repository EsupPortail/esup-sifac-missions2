package org.esupportail.sifacmissions.services.sifac;

import java.io.Serializable;
import java.util.List;

import org.esupportail.sifacmissions.domain.beans.Mission;
import org.esupportail.sifacmissions.domain.beans.MissionDetails;

/**
 * The sifac service interface.
 */
public interface SifacService extends Serializable {

	/**
	 * @return the first year of SIFAC application.
	 */
	Integer getFirstYear();

	/**
	 * @param matricule
	 * @param nom
	 * @param prenom
	 * @param year
	 * @return a list of frais de missions.
	 * @throws SifacException
	 */
	public List<Mission> getFraisMissions(String matricule, String nom,
			String prenom, Integer year) throws SifacException;

	/**
	 * @param matricule
	 * @param numeroMission
	 * @return a list of mission details.
	 * @throws SifacException
	 */
	public List<MissionDetails> getMissionDetails(String matricule,
			String numeroMission) throws SifacException;

}
