/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.sifacmissions.domain.beans;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;

/**
 * The class that represent users.
 */
@SuppressWarnings("unchecked")
public class User implements Serializable {

	/**
	 * For serialize.
	 */
	private static final long serialVersionUID = 7427732897404494181L;

	/**
	 * For Sorting.
	 */
	@SuppressWarnings("rawtypes")
	public static Comparator<User> ORDER_DISPLAYNAME = new Comparator() {
		@Override
		public int compare(Object o1, Object o2) {
			return ((User) o1).getDisplayName().compareTo(((User) o2).getDisplayName());
		}
	};

	private String login;
	private String displayName;
	private String language;
	private boolean admin;

	/**
	 * Bean constructor.
	 */
	public User() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		return login.equals(((User) obj).getLogin());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return "User#" + hashCode() + "[login=[" + login + "], displayName=["
				+ displayName + "], admin=[" + admin + "], language=["
				+ language + "]]";
	}

	/**
	 * @return the login of the user.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the displayName.
	 */
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * @param displayName The displayName to set.
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the admin flag.
	 */
	public boolean isAdmin() {
	    return admin;
	}
	
	/**
	 * @param admin The flag to set.
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language The language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the user display language.
	 */
	public String getDisplayLanguage() {
		Locale locale = new Locale(language);
		return locale.getDisplayLanguage(locale);
	}

}