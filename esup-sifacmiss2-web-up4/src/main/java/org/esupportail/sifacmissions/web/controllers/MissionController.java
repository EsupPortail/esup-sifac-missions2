package org.esupportail.sifacmissions.web.controllers;

import javax.annotation.Resource;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;

import org.esupportail.sifacmissions.models.User;
import org.esupportail.sifacmissions.services.matricule.MatriculeService;
import org.esupportail.sifacmissions.services.mission.MissionService;
import org.esupportail.sifacmissions.services.user.UserService;
import org.esupportail.sifacmissions.web.beans.MissionHolder;
import org.esupportail.sifacmissions.web.beans.UserParameters;
import org.esupportail.sifacmissions.web.services.authentication.AuthenticationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller
@Scope("request")
@RequestMapping("VIEW")
public class MissionController implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private AuthenticationService authenticationService;
    private MatriculeService matriculeService;
    private MissionService missionService;
    private UserService userService;

    @Resource
    private MissionHolder missionHolder;

    @Resource
    private UserParameters userParameters;

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void setMatriculeService(MatriculeService matriculeService) {
        this.matriculeService = matriculeService;
    }

    public void setMissionService(MissionService missionService) {
        this.missionService = missionService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(authenticationService, "property authenticationService cannot be null");
        Assert.notNull(matriculeService, "property matriculeService cannot be null");
        Assert.notNull(missionService, "property missionService cannot be null");
        Assert.notNull(userService, "property userService cannot be null");
    }

    @RequestMapping
    public ModelAndView viewMissions(RenderRequest request) throws Exception {
        initialize(request);
        return null;
    }

    @ResourceMapping("details")
    public ModelAndView getMissionDetails(ResourceRequest request, @RequestParam("id") String id) throws Exception {
        initialize(request);
        return null;
    }

    private void initialize(PortletRequest request) {
        if (!userParameters.isInitialized()) {
            String uid = authenticationService.getUid(request);
            String matricule = matriculeService.getMatricule(uid);
            User user = userService.getUser(uid);

            userParameters.setUid(uid);
            userParameters.setNom(user.getNom());
            userParameters.setPrenom(user.getPrenom());
            userParameters.setMatricule(matricule);

            if (logger.isDebugEnabled()) {
                logger.debug("Set user parameters for user " + uid);
            }
        }
    }

}
