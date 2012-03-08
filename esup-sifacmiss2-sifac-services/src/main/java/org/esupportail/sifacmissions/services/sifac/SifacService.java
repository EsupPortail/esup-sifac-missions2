/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.services.sifac;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.esupportail.sifacmissions.domain.beans.Mission;

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
	 * @throws ServiceException 
	 * @throws RemoteException
	 * @throws ParseException
	 */
	public List<Mission> getFraisMissions(String matricule, String nom,
			String prenom, Integer year) throws ServiceException, RemoteException, ParseException;
	
}
