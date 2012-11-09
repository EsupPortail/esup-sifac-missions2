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

import org.esupportail.sifacmissions.models.Mission;
import org.esupportail.sifacmissions.services.matricule.MatriculeService;
import org.esupportail.sifacmissions.services.mission.MissionException;
import org.esupportail.sifacmissions.services.mission.MissionService;

import org.apache.myfaces.trinidad.component.UIXTable;
import org.apache.myfaces.trinidad.component.core.data.CoreTable;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * A visual bean for the welcome page.
 */
@SuppressWarnings("serial")
public class MissionController extends AbstractContextAwareController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String matricule;
    private Integer year;
    private List<SelectItem> yearItems;
    private List<Mission> missions;
    private Integer missionsPerPage = 5;
    private List<SelectItem> missionsPerPageItems;
    private CoreTable missionsTable;
    private MatriculeService matriculeService;
    private MissionService missionService;

    @Override
    protected void afterPropertiesSetInternal() {
        Assert.notNull(matriculeService, "matriculeService is required");
        Assert.notNull(missionService, "missionService is required");
    }

    public void setMatriculeService(MatriculeService matriculeService) {
        this.matriculeService = matriculeService;
    }

    public void setMissionService(MissionService missionService) {
        this.missionService = missionService;
    }

    public CoreTable getMissionsTable() {
        return missionsTable;
    }

    public void setMissionsTable(CoreTable table) {
        this.missionsTable = table;
    }

    @Override
    public void reset() {
        missionsTable = null;
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

        int lastYear = Math.max(missionService.getFirstYear(), year - 1);
        for (int i = year; i >= lastYear; i--) {
            yearItems.add(new SelectItem(i));
        }
    }

    /**
     * Set the SIFAC web service parameters
     */
    private void initSifac() {
        String currentUser = getCurrentUser();

        if (logger.isDebugEnabled()) {
            logger.debug("Current User: {}", currentUser);
        }

        if (matricule == null) {
            matricule = matriculeService.getMatricule(currentUser);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Web Service Parameters: maticule={}", matricule);
        }
    }

    /**
     * @return missions
     */
    public List<Mission> getMissions() {
        if (missions == null) {
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

    protected void requestMissionDetails(Mission mission) {
        if (mission.getDetails() != null) {
            return;
        }

        try {
            mission.setDetails(missionService.getMissionDetails(matricule, mission.getNumero()));
        } catch (MissionException e) {
            logger.error("Failed to get mission details", e);
            addWarnMessage(null, "WELCOME.ERROR.SERVICE");
        }
    }

    public void changeYear() {
        try {
            if (matricule == null) {
                addWarnMessage(null, "WELCOME.ERROR.GETMATRICULE");
            } else {
                missions = missionService.getFraisMissions(matricule, year);
                Collections.sort(missions, Collections.reverseOrder(Mission.ORDER_ORDRE));
                dataChanged();
            }
        } catch (Exception e) {
            logger.error("Failed to change year", e);
            addWarnMessage(null, "WELCOME.ERROR.SERVICE");
        }
    }

    /**
     * @return the missionsPerPageItems
     */
    public List<SelectItem> getMissionsPerPageItems() {
        return missionsPerPageItems;
    }

}
