package org.esupportail.sifacmissions.web.controllers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import org.esupportail.sifacmissions.models.Mission;
import org.esupportail.sifacmissions.models.MissionDetails;
import org.esupportail.sifacmissions.services.authentication.AuthenticationService;
import org.esupportail.sifacmissions.services.matricule.MatriculeService;
import org.esupportail.sifacmissions.services.mission.MissionException;
import org.esupportail.sifacmissions.services.mission.MissionService;
import org.esupportail.sifacmissions.web.beans.MissionHolder;
import org.esupportail.sifacmissions.web.beans.UserParameters;
import org.esupportail.sifacmissions.web.beans.YearSelectionForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller
@Scope("request")
@RequestMapping("VIEW")
public class MissionController implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "authenticationService")
    private AuthenticationService authenticationService;

    @Resource(name = "matriculeService")
    private MatriculeService matriculeService;

    @Resource(name = "missionService")
    private MissionService missionService;

    @Resource
    private MissionHolder missionHolder;

    @Resource
    private UserParameters userParameters;

    @ModelAttribute("yearSelectionForm")
    public YearSelectionForm getYearSelectionForm() {
        YearSelectionForm form = new YearSelectionForm();

        if (missionHolder != null) {
            form.setYear(missionHolder.getCurrentYear());
        }

        return form;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void setMatriculeService(MatriculeService matriculeService) {
        this.matriculeService = matriculeService;
    }

    public void setMissionService(MissionService missionService) {
        this.missionService = missionService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(authenticationService, "property authenticationService cannot be null");
        Assert.notNull(matriculeService, "property matriculeService cannot be null");
        Assert.notNull(missionService, "property missionService cannot be null");
    }

    @ActionMapping(params = "action=changeYear")
    public void changeYear(ActionRequest request, YearSelectionForm form) {
        initialize(request);

        int year = form.getYear();
        if (missionService.getFirstYear() <= year && year <= Calendar.getInstance().get(Calendar.YEAR)) {
            missionHolder.setCurrentYear(year);

            if (logger.isDebugEnabled()) {
                logger.debug("Changed current year to {}", year);
            }
        }
    }

    @RequestMapping
    public ModelAndView viewMissions(RenderRequest request) {
        initialize(request);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("year", missionHolder.getCurrentYear());

        try {
            List<Mission> missions = missionService.getFraisMissions(userParameters.getMatricule(), missionHolder.getCurrentYear());
            model.put("missions", missions);
        } catch (MissionException e) {
            logger.error("Unable to get missions", e);
        }

        Map<Integer, String> years = new LinkedHashMap<Integer, String>();

        int i = Calendar.getInstance().get(Calendar.YEAR);
        int n = missionService.getFirstYear();
        for (; i >= n; i--) {
            years.put(i, Integer.toString(i));
        }

        model.put("years", years);

        return new ModelAndView(isMobile(request) ? "list-jQM" : "list", model);
    }

    @ResourceMapping("details")
    public ModelAndView getMissionDetails(ResourceRequest request, ResourceResponse response, @RequestParam("id") String id) {
        initialize(request);

        Map<String, Object> model = new HashMap<String, Object>();
        try {
            List<MissionDetails> details = missionService.getMissionDetails(userParameters.getMatricule(), id);
            model.put("mission", id);
            model.put("details", details);
        } catch (MissionException e) {
            logger.error("Unable to get mission details", e);
            response.setProperty(ResourceResponse.HTTP_STATUS_CODE, Integer.toString(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
        }

        return new ModelAndView("details", model);
    }

    @RequestMapping(params = "action=mission")
    public ModelAndView viewMission(RenderRequest request, @RequestParam("id") String id) {
        initialize(request);

        Map<String, Object> model = new HashMap<String, Object>();
        try {
            List<Mission> missions = missionService.getFraisMissions(userParameters.getMatricule(), missionHolder.getCurrentYear());
            for (Mission mission : missions) {
                if (mission.getNumero().equals(id)) {
                    List<MissionDetails> details = missionService.getMissionDetails(userParameters.getMatricule(), id);
                    model.put("mission", mission);
                    model.put("details", details);
                    break;
                }
            }
        } catch (MissionException e) {
            logger.error("Unable to get mission", e);
        }

        return new ModelAndView("mission-jQM", model);
    }

    private void initialize(PortletRequest request) {
        if (!userParameters.isInitialized()) {
            String uid = authenticationService.getUid(request);
            String matricule = matriculeService.getMatricule(uid);

            userParameters.setUid(uid);
            userParameters.setMatricule(matricule);

            missionHolder.setCurrentYear(Calendar.getInstance().get(Calendar.YEAR));

            if (logger.isDebugEnabled()) {
                logger.debug("Set user parameters for user {}", uid);
            }
        }
    }

    private boolean isMobile(PortletRequest request) {
        return true;
    }
}
