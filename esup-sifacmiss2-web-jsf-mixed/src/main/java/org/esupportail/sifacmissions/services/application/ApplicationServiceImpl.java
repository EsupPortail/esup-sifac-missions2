package org.esupportail.sifacmissions.services.application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.esupportail.commons.services.application.SimpleApplicationServiceImpl;

import org.springframework.util.Assert;

/**
 * @author Florent Cailhol
 */
@SuppressWarnings("serial")
public class ApplicationServiceImpl extends SimpleApplicationServiceImpl {
	
	private String versionString;
	
	/**
	 * @param versionString The version to set
	 */
	public void setVersionString(String versionString)
	{
		this.versionString = versionString;
	}
	
	@Override
	public void afterPropertiesSet() {
		Assert.notNull(versionString, "Missing version");
		
		Pattern p = Pattern.compile("^([0-9]+)\\.([0-9]+)\\.([0-9]+)(?:\\-.+)$");
		Matcher m = p.matcher(versionString);
		
		Assert.isTrue(m.matches(), "Invalid version");
		
		setVersionMajorNumber(Integer.valueOf(m.group(1)));
		setVersionMinorNumber(Integer.valueOf(m.group(2)));
		setVersionUpdate(Integer.valueOf(m.group(3)));
		
		super.afterPropertiesSet();
	}

}
