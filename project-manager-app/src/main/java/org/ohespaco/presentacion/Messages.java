package org.ohespaco.presentacion;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static String BUNDLE_NAME = "org.ohespaco.presentacion.messages"; //$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	private static Locale getLocale(String appIdioma) {
		Locale locale = new Locale(appIdioma);

		return locale;
	}

	public static void setIdioma(String idioma) {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, getLocale(idioma));
	}
}
