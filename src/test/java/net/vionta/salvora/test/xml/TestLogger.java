package net.vionta.salvora.test.xml;

import org.slf4j.Logger;
import org.slf4j.Marker;

public class TestLogger implements Logger {
	
	public static boolean ERROR_USED= Boolean.FALSE;
	public static String LAST_LEVEL_USED = "INFO";
	private static String LAST_STRING_USED = "not-started";
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTraceEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void trace(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(String format, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isTraceEnabled(Marker marker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void trace(Marker marker, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(Marker marker, String format, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(Marker marker, String format, Object... argArray) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDebugEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void debug(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(String format, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDebugEnabled(Marker marker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void debug(Marker marker, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(Marker marker, String format, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(Marker marker, String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInfoEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void info(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(String format, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInfoEnabled(Marker marker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void info(Marker marker, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(Marker marker, String format, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(Marker marker, String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void info(Marker marker, String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isWarnEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void warn(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(String format, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isWarnEnabled(Marker marker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void warn(Marker marker, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(Marker marker, String format, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(Marker marker, String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isErrorEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void error(String msg) {
		ERROR_USED = Boolean.TRUE;
		LAST_STRING_USED = msg;
		LAST_LEVEL_USED = "ERROR";
	}

	@Override
	public void error(String format, Object arg) {
		ERROR_USED = Boolean.TRUE;
		LAST_STRING_USED = arg.toString();
		LAST_LEVEL_USED = "ERROR";
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		ERROR_USED = Boolean.TRUE;
		LAST_STRING_USED = arg1.toString();
		LAST_LEVEL_USED = "ERROR";
	}

	@Override
	public void error(String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isErrorEnabled(Marker marker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void error(Marker marker, String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(Marker marker, String format, Object arg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(Marker marker, String format, Object... arguments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(Marker marker, String msg, Throwable t) {
		// TODO Auto-generated method stub

	}

}
