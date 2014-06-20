package mooklabs;

import java.io.File;

import openl.Utils;


public class Settings {

	public static String getWindowWidth() {
		return null;
	}

	public static String getWindowHeight() {
		return null;
	}

	public static String getForgeLoggingLevel() {
		return null;
	}

	public static String getJavaPath() {
		return null;
	}

	public static int getMemory() {
		return 0;
	}

	public static File getResourcesDir() {
		return new File(Utils.getMinecraftFolder(), "/assets");
	}

	public static boolean startMinecraftMaximised() {
		// TODO Auto-generated method stub
		return false;
	}

	public static int getPermGen() {
		return 128;
	}

	public static String getJavaParameters() {
		return "";
	}


}
