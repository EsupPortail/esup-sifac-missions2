/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.web.controllers;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.xml.rpc.ServiceException;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.ContextUtils;
import org.esupportail.sifacmissions.domain.beans.Mission;
import org.esupportail.sifacmissions.domain.beans.User;
import org.esupportail.sifacmissions.web.utils.StringUtils;

/**
 * A visual bean for the welcome page.
 */
public class WelcomeController extends AbstractContextAwareController {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -239570715531002003L;

	/**
	 * The jsf navigation.
	 */
	private static final String NAVIGATION_WELCOME = "navigationWelcome";

	/**
	 * The current user.
	 */
	private User currentUser;
	
	/**
	 * The current mission.
	 */
	private Mission currentMission;

	/**
	 * Current year.
	 */
	private String year;

	/**
	 * Matricule.
	 */
	private String matricule;

	/**
	 * Nom.
	 */
	private String nom;

	/**
	 * Prenom.
	 */
	private String prenom;

	/**
	 * A list of JSF year for the years.
	 */
	private List<SelectItem> yearItems;

	/**
	 * The missions.
	 */
	private List<Mission> missions;
	
	/**
	 * The mission pagination count.
	 */
	private Integer missionsPerPage = 25;

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * Bean constructor.
	 */
	public WelcomeController() {
		super();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "#" + hashCode();
	}

	/**
	 * @see org.esupportail.sifacmissions.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		currentUser = null;
		matricule = null;
		
		// Initialize current user
		initUser();
		
		// Initialize SIFAC parameters
		initSifac();
		
		// Initialize application data
		initData();
	}

	/**
	 * @return true if the current user is allowed to view the page.
	 */
	public boolean isPageAuthorized() {
		initUser();
		
		if (ContextUtils.isPortlet()) {
			return false;
		}
		
		return true;
	}

	/**
	 * JSF callback.
	 * 
	 * @return a String.
	 */
	public String initData() {
		setYearItems();
		return changeYear();
	}

	/**
	 * set the web service sifac parameters.
	 * 
	 */
	private void initSifac() {
		logger.info("Current User: " + currentUser.getLogin());
		
		if (matricule == null) {
			matricule = getDomainService().getMatriculeService().getMatricule(currentUser.getLogin());
			
			if (matricule == null) {
				if (getDomainService().isHomonyme(currentUser)) {
				    addWarnMessage(null, "WELCOME.ERROR.MATRICULE");
				} else {
					if (nom == null) {
						nom = StringUtils.removeAccent(getDomainService().getNom(currentUser.getLogin()));
					}
					
					if (prenom == null) {
						prenom = StringUtils.removeAccent(getDomainService().getPrenom(currentUser.getLogin()));
					}
				}
			}
		}
		
		logger.info("Web Service Parameters : Maticule " + matricule + " - Nom " + nom + " Prenom " + prenom);
	}

	/**
	 * @return missions
	 */
	public List<Mission> getMissions() {
		return missions;
	}

	/**
	 * @return the missionsPerPage.
	 */
	public Integer getMissionsPerPage() {
		return missionsPerPage;
	}

	/**
	 * @param missionsPerPage the missionsPerPage to set.
	 */
	public void setMissionsPerPage(Integer missionsPerPage) {
		this.missionsPerPage = missionsPerPage;
	}

	/**
	 * @return the year.
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set.
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the years items.
	 */
	public List<SelectItem> getYearItems() {
		if (yearItems == null) {
			setYearItems();
		}

		return yearItems;
	}

	/**
	 * Set the years items.
	 */
	private void setYearItems() {
		if (yearItems == null) {
			yearItems = new ArrayList<SelectItem>();
			Date now = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy");
			year = dateFormat.format(now);
			int currentYear = Integer.valueOf(year).intValue();
			year = Integer.toString(currentYear);
			int firstYear = Integer.valueOf(getDomainService().getFirstYear()).intValue();
			for (int i = currentYear; i >= firstYear && yearItems.size() < 2; i--) {
				yearItems.add(new SelectItem(Integer.toString(i), Integer.toString(i)));
			}
		}
	}

	/**
	 * @return the navigation.
	 */
	public String changeYear() {
		try {
			if (matricule == null) {
				addWarnMessage(null, "WELCOME.ERROR.GETMATRICULE");
			} else {
				missions = getDomainService().getFraisMissions(matricule, nom, prenom, year);
				Collections.sort(missions, Collections.reverseOrder(Mission.ORDER_ORDRE));
			}
		} catch (ParseException e) {
		    addWarnMessage(null, "WELCOME.ERROR.DATES");
		} catch (RemoteException e) {
			addErrorMessage(null, "WELCOME.ERROR.SERVICE");
		} catch (ServiceException e) {
		    addErrorMessage(null, "WELCOME.ERROR.SERVICE");
		}
		
		return NAVIGATION_WELCOME;
	}

	/**
	 * @return matricule.
	 */
	public String getMatricule() {
		return matricule;
	}

	/**
	 * initialize user (object).
	 */
	private void initUser() {
		if (currentUser == null) {
			if (getCurrentUser() != null) {
				String myUid = getCurrentUser().getLogin();
				currentUser = getDomainService().getUser(myUid);
			}
		} else if (!currentUser.equals(getCurrentUser())) {
			reset();
		}
	}
	
	public Mission getCurrentMission() {
		return currentMission;
	}
	
	public void setCurrentMission(Mission mission) {
		this.currentMission = mission;
	}

}
