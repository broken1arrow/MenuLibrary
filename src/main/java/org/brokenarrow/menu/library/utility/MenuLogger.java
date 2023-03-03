package org.brokenarrow.menu.library.utility;


import java.util.logging.Level;

public class MenuLogger {

	private java.util.logging.Logger LOG;

	public MenuLogger(final Class<?> logg) {
		this.LOG = java.util.logging.Logger.getLogger(logg.getName());
	}

	public void sendLOG(Level level, String message) {
		LOG.log(level, message);
	}

	public java.util.logging.Logger getLOG() {
		return LOG;
	}
}
