package org.eclipse.swt.examples.ole.win32;

/*
 * (c) Copyright IBM Corp. 2000, 2002.
 * This file is made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */

import java.io.*;import java.net.*;import java.text.*;import java.util.*;import org.eclipse.core.runtime.*;import org.eclipse.swt.*;import org.eclipse.swt.graphics.*;import org.eclipse.ui.plugin.*;

/**
 * The main plugin class to be used in the desktop.
 */
public class OlePlugin extends AbstractUIPlugin {
	//The shared instance.
	private static OlePlugin plugin;
	private static ResourceBundle resourceBundle;

	static final int
		biBack = 0,
		biForward = 1,
		biHome = 2,
		biStop = 3,
		biRefresh = 4,
		biSearch = 5;
	static final String[] imageLocations = {
		"icons/backward_nav.gif",
		"icons/forward_nav.gif",
		"icons/home_nav.gif",
		"icons/stop_nav.gif",
		"icons/refresh_nav.gif",
		"icons/search_nav.gif" };
	static Image images[];
	static Font  browserFont;
   
	/**
	 * Constructs an OLE plugin.
	 */
	public OlePlugin(IPluginDescriptor descriptor) {
		super(descriptor);
		plugin = this;
		resourceBundle = descriptor.getResourceBundle();
	}
	
	/**
	 * Clean up
	 */
	public void shutdown() throws CoreException {
		super.shutdown();
		freeResources();
	}

	/**
	 * Returns the shared instance.
	 */
	public static OlePlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns a string from the resource bundle.
	 * We don't want to crash because of a missing String.
	 * Returns the key if not found.
	 */
	public static String getResourceString(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";
		}			
	}

	/**
	 * Returns a string from the resource bundle and binds it
	 * with the given arguments. If the key is not found,
	 * return the key.
	 */
	public static String getResourceString(String key, Object[] args) {
		try {
			return MessageFormat.format(getResourceString(key), args);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";
		}
	}

	/**
	 * Log an error to the ILog for this plugin
	 * 
	 * @param message the localized error message text
	 * @param exception the associated exception, or null
	 */
	public static void logError(String message, Throwable exception) {
		plugin.getLog().log(new Status(IStatus.ERROR, plugin.getDescriptor().getUniqueIdentifier(),
			0, message, exception));
	}

	/**
	 * Loads the resources.
	 */
	public static void initResources() {
		if (images == null) {
			images = new Image[imageLocations.length];
				
			for (int i = 0; i < imageLocations.length; ++i) {
				images[i] = getImageFromPlugin(plugin.getDescriptor(), imageLocations[i]);
				if (images[i] == null) {
					freeResources();
					logError(getResourceString("error.CouldNotLoadResources"), null);
					throw new IllegalStateException();
				}
			}
		}
		if (browserFont == null) {
			try {
				browserFont = new Font (null, "MS Sans Serif", 8, SWT.NULL);
			} catch (Throwable ex) {
			}
		}
		if (images == null || browserFont == null) {
			freeResources();
			logError(getResourceString("error.CouldNotLoadResources"), null);
			throw new IllegalStateException();
		}
	}

	/**
	 * Frees the resources
	 */
	public static void freeResources() {
		if (images != null) {
			for (int i = 0; i < images.length; ++i) {
				final Image image = images[i];
				if (image != null) image.dispose();
			}
			images = null;
		}
		if (browserFont != null) browserFont.dispose ();
		browserFont = null;
	}
	
	/**
	 * Gets an image from a path relative to the plugin install directory.
	 *
	 * @param pd the plugin descriptor for the plugin with the image
	 * @param iconPath the path relative to the install directory
	 * @return the image, or null if not found
	 */
	private static Image getImageFromPlugin(IPluginDescriptor pd, String iconPath) {
		InputStream is = null;
		try {
			URL installUrl = pd.getInstallURL();
			URL url = new URL(installUrl, iconPath);
			is = url.openConnection().getInputStream();
			ImageData source = new ImageData(is);
			ImageData mask = source.getTransparencyMask();
			Image image = new Image(null, source, mask);
			return image;
		} catch (Throwable ex) {
			return null;
		} finally {
			try {
				if (is != null) is.close();
			} catch (IOException e) {
			}
		}
	}
}
