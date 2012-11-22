package org.esupportail.sifacmissions.web.jsf;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.sifacmissions.web.utils.AgentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobilePhaseListener implements PhaseListener {

    private static final long serialVersionUID = -3940484207676347482L;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        String view = facesContext.getViewRoot().getViewId();

        AgentUtil agentUtil = ApplicationContextHolder.getContext().getBean(AgentUtil.class);
        if (agentUtil.isMobile()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Mobile mode detected");
            }

            if (!view.startsWith("/stylesheets/mobile")) {
                view = "/stylesheets/mobile/missions.xhtml";
            } else {
                view = null;
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Desktop mode detected");
            }

            if (!view.startsWith("/stylesheets/desktop")) {
                view = "/stylesheets/desktop/missions.xhtml";
            } else {
                view = null;
            }
        }

        if (view != null) {
            try {
                String viewURL = facesContext.getApplication().getViewHandler().getActionURL(facesContext, view);
                event.getFacesContext().getExternalContext().redirect(viewURL);
            } catch (IOException e) {
                logger.error("Failed to redirect to mobile views", e);
            }
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
    }

}
