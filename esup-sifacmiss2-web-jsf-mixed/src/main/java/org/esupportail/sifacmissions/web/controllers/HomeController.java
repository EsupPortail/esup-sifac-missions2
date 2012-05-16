package org.esupportail.sifacmissions.web.controllers;

import org.esupportail.commons.beans.AbstractApplicationAwareBean;

@SuppressWarnings("serial")
public class HomeController extends AbstractApplicationAwareBean {

	private boolean displayAboutLink = true;
	private boolean displayHelpLink = true;
	private boolean displayMobileLink = true;
	private boolean displayServletLink = true;

	/**
	 * @return true if the 'about' page link should be displayed
	 */
	public boolean isDisplayAboutLink() {
		return displayAboutLink;
	}

	/**
	 * @param flag the 'displayAboutLink' flag
	 */
	public void setDisplayAboutLink(boolean flag) {
		this.displayAboutLink = flag;
	}

	/**
	 * @return true if the 'about' page link should be displayed
	 */
	public boolean isDisplayHelpLink() {
		return displayHelpLink;
	}

	/**
	 * @param flag the 'displayHelpLink' flag
	 */
	public void setDisplayHelpLink(boolean flag) {
		this.displayHelpLink = flag;
	}

	/**
	 * @return true if the 'mobile' link should be displayed
	 */
	public boolean isDisplayMobileLink() {
		return displayMobileLink;
	}

	/**
	 * @param flag the 'displayMobileLink' flag
	 */
	public void setDisplayMobileLink(boolean flag) {
		this.displayMobileLink = flag;
	}

	/**
	 * @return true if the 'servlet' link should be displayed
	 */
	public boolean isDisplayServletLink() {
		return displayServletLink;
	}

	/**
	 * @param flag the 'displayServletLink' flag
	 */
	public void setDisplayServletLink(boolean flag) {
		this.displayServletLink = flag;
	}

	/**
	 * @return the version of the application
	 */
	public String getVersion() {
		return getApplicationService().getVersion().toString();
	}
}
