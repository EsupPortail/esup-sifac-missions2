/**
 * ESUP-Portail esup-sifac-missions - Copyright (c) 2009 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-sifacmissions
 */
package org.esupportail.sifacmissions.web.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.component.core.data.CoreTable;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.sifacmissions.domain.beans.Mission;
import org.esupportail.sifacmissions.domain.beans.User;
import org.esupportail.sifacmissions.services.sifac.SifacException;
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
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/**
	 * The current user.
	 */
	private User currentUser;
	
	/**
	 * The current mission.
	 */
	private Mission currentMission;

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
	 * Current year.
	 */
	private Integer year;

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
	private Integer missionsPerPage = 5;
	
	/**
	 * An items list for for missions pagination.
	 */
	private List<SelectItem> missionsPerPageItems;
	
	private CoreTable missionsTable;

	public CoreTable getMissionsTable() {
		return missionsTable;
	}

	public void setMissionsTable(CoreTable table) {
		this.missionsTable = table;
	}

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
		
		// Get missions table pagination
		missionsPerPageItems = new ArrayList<SelectItem>();
		missionsPerPageItems.add(new SelectItem(5, "5"));
		missionsPerPageItems.add(new SelectItem(10, "10"));
		missionsPerPageItems.add(new SelectItem(25, "25"));
		missionsPerPageItems.add(new SelectItem(50, "50"));
		missionsPerPageItems.add(new SelectItem(100, "100"));
		
	    // Get the current year
        year = Integer.valueOf(new SimpleDateFormat("yyyy").format(new Date())).intValue();
        
        // Get displayed years
        yearItems = new ArrayList<SelectItem>();
        
        int lastYear = Math.max(getDomainService().getFirstYear(), year - 1);
        for (int i = year; i >= lastYear; i--) {
            yearItems.add(new SelectItem(i));
        }
	}

	/**
	 * Set the SIFAC web service parameters
	 */
	private void initSifac() {
		logger.info("Current User: " + currentUser.getLogin());
		
		if (matricule == null) {
			matricule = getDomainService().getMatriculeService().getMatricule(currentUser.getLogin());
			
			if (matricule == null) {
				if (getDomainService().isHomonyme(currentUser)) {
				    addWarnMessage(null, "WELCOME.ERROR.HOMONYME");
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
		if (missions == null) {
			currentUser = getCurrentUser();
			initSifac();
			changeYear();
		}
		
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
	public Integer getYear() {
		return year;
	}

	/**
	 * @param year the year to set.
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * @return the years items.
	 */
	public List<SelectItem> getYearItems() {
		return yearItems;
	}
	
	public void yearChanged(ValueChangeEvent event) {
		setYear((Integer) event.getNewValue());
		changeYear();
	}
	
	public void detailsToggled(RowDisclosureEvent event) {
		RowKeySet rows = event.getAddedSet();
		
		if (rows.size() > 0) {
			UIXTable table = (UIXTable) event.getSource();
			Iterator<Object> it = rows.iterator();
			
			while (it.hasNext()) {
				requestMissionDetails((Mission) table.getRowData(it.next()));
			}
		}
	}
	
	private void dataChanged() {
		if (missionsTable != null) {
			missionsTable.setDisclosedRowKeys(new RowKeySetImpl());
		}
	}
	
	private void requestMissionDetails(Mission mission) {
		if (mission.getDetails() != null) {
			return;
		}
		
		try {
			mission.setDetails(getDomainService().getMissionDetails(matricule, mission.getNumero()));
		}
		catch (SifacException e) {
			logger.error(e);
		    addWarnMessage(null, "WELCOME.ERROR.SERVICE");
		}
	}

	public void changeYear() {
		try {
			if (matricule == null) {
				addWarnMessage(null, "WELCOME.ERROR.GETMATRICULE");
			} else {
				missions = getDomainService().getFraisMissions(matricule, nom, prenom, year);
				Collections.sort(missions, Collections.reverseOrder(Mission.ORDER_ORDRE));
				dataChanged();
			}
		} catch (Exception e) {
			logger.error(e);
		    addWarnMessage(null, "WELCOME.ERROR.SERVICE");
		}
	}

	/**
	 * @return matricule.
	 */
	public String getMatricule() {
		return matricule;
	}
	
	public Mission getCurrentMission() {
		return currentMission;
	}
	
	public void setCurrentMission(Mission mission) {
		requestMissionDetails(mission);
		this.currentMission = mission;
	}
	
    /**
	 * @return the missionsPerPageItems
	 */
	public List<SelectItem> getMissionsPerPageItems() {
		return missionsPerPageItems;
	}
}
