package org.esupportail.sifacmissions.services.view;

import javax.portlet.PortletRequest;

public class ThemeNameViewResolver implements ViewResolver {

    private static final String THEME_NAME_PROPERTY = "themeName";
    private static final String MOBILE_THEMES_KEY = "mobileThemes";
    private static final String[] MOBILE_THEMES_DEFAULT = new String[] { "UniversalityMobile" };

    @Override
    public boolean isMobile(PortletRequest request) {
        String[] mobileThemes = request.getPreferences().getValues(MOBILE_THEMES_KEY, MOBILE_THEMES_DEFAULT);
        String themeName = request.getProperty(THEME_NAME_PROPERTY);
        if (themeName == null) {
            return false;
        }

        for (String theme : mobileThemes) {
            if (themeName.equals(theme)) {
                return true;
            }
        }

        return false;
    }

}
