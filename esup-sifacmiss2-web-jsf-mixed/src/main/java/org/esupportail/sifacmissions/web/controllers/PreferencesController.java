/**
 * ESUP-Portail Directory Application - Copyright (c) 2011 ESUP-Portail consortium.
 */
package org.esupportail.sifacmissions.web.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2011
 * 
 */
public class PreferencesController extends AbstractContextAwareController {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = -5420709995066427730L;

	/**
	 * An items list for for missions pagination.
	 */
	private List<SelectItem> missionsPerPageItems;
	
	private boolean displayAboutLink = true;
	private boolean displayHelpLink = true;
	private boolean displayMobileLink = true;
	private boolean displayServletLink = true;

	/**
	 * Constructor.
	 */
	public PreferencesController() {
		super();
	}
	
	@Override
	public void reset() {
		super.reset();
		
		missionsPerPageItems = null;
	}

	/**
	 * @return the missionsPerPageItems
	 */
	public List<SelectItem> getMissionsPerPageItems() {
		missionsPerPageItems = new ArrayList<SelectItem>();
		missionsPerPageItems.add(new SelectItem(5, "5"));
		missionsPerPageItems.add(new SelectItem(10, "10"));
		missionsPerPageItems.add(new SelectItem(25, "25"));
		missionsPerPageItems.add(new SelectItem(50, "50"));
		missionsPerPageItems.add(new SelectItem(100, "100"));
		
		return missionsPerPageItems;
	}

	/**
	 * @return true if the 'about' page link should be displayed
	 */
    public boolean isDisplayAboutLink()
    {
        return displayAboutLink;
    }

    /**
     * @param flag the 'displayAboutLink' flag
     */
    public void setDisplayAboutLink(boolean flag)
    {
        this.displayAboutLink = flag;
    }

    /**
     * @return true if the 'about' page link should be displayed
     */
    public boolean isDisplayHelpLink()
    {
        return displayHelpLink;
    }

    /**
     * @param flag the 'displayHelpLink' flag
     */
    public void setDisplayHelpLink(boolean flag)
    {
        this.displayHelpLink = flag;
    }
    
    /**
     * @return true if the 'mobile' link should be displayed
     */
    public boolean isDisplayMobileLink()
    {
        return displayMobileLink;
    }
    
    /**
     * @param flag the 'displayMobileLink' flag
     */
    public void setDisplayMobileLink(boolean flag)
    {
        this.displayMobileLink = flag;
    }
    
    /**
     * @return true if the 'servlet' link should be displayed
     */
    public boolean isDisplayServletLink()
    {
        return displayServletLink;
    }
    
    /**
     * @param flag the 'displayServletLink' flag
     */
    public void setDisplayServletLink(boolean flag)
    {
        this.displayServletLink = flag;
    }
}
