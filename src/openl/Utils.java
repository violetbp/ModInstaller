package openl;

import java.io.File;
import java.io.FileInputStream;

import org.json.JSONObject;

public class Utils {

	public static String APP_NAME = "NightfallLauncher";

	public static OS getPlatform() {
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("win")) return OS.WINDOWS;
		if (osName.contains("mac")) return OS.MACOS;
		if (osName.contains("linux")) return OS.LINUX;
		if (osName.contains("unix")) return OS.LINUX;
		return OS.UNKNOWN;
	}

	public static File getWorkingDirectory() {
		String userHome = System.getProperty("user.home", ".");
		File workingDirectory;
		switch (getPlatform()) {
		case WINDOWS:
		case MACOS:
			workingDirectory = new File(userHome, APP_NAME + "/");
			break;
		case SOLARIS:
			String applicationData = System.getenv("APPDATA");
			String folder = applicationData != null ? applicationData : userHome;
			workingDirectory = new File(folder, APP_NAME + "/");
			break;
		case LINUX:
			workingDirectory = new File(userHome, APP_NAME + "/");
			break;
		default:
			workingDirectory = new File(userHome, APP_NAME + "/");
		}
		workingDirectory.mkdirs();
		return workingDirectory;
	}

	private static enum OS {
		WINDOWS, MACOS, SOLARIS, LINUX, UNKNOWN;
	}

	public String getNativeString(OS platform) {
		switch (platform) {
		case LINUX:
			return "linux";
		case WINDOWS:
			return "windows";
		case MACOS:
			return "osx";
		default:
			return "linux";
		}

	}
	public static boolean isWindows() {
		return getPlatform() == OS.WINDOWS;
	}public static boolean isMac() {
		return getPlatform() == OS.MACOS;
	}

	public static File getInstancesFolder() {
		return new File(getWorkingDirectory(), "instances/");
	}

	public static File getMinecraftFolder() {
		return new File(getWorkingDirectory(), "minecrafts/");
	}

	public static File getDownloadsFolder() {
		return new File(getWorkingDirectory(), "downloads/");
	}

	public static JSONObject getPackData(String server, String packid) {
		try {
			File f = new File(getInstancesFolder(), server + "_" + packid + "/pack.json");
			FileInputStream fis = new FileInputStream(f);
			return JSONUtils.getJSONObjectFromInputStream(fis);
		} catch (Exception e) {
		}
		return null;
	}

	public static File getPackDataFile(String server, String packid) {
		try {
			return new File(getInstancesFolder(), server + "_" + packid + "/pack.json");
		} catch (Exception e) {
		}
		return null;
	}

	public static Modpack getModpackFromLocalInfo(String server, String packid) {
		JSONObject data = getPackData(server, packid);
		if (data != null) {
			Modpack pack = new Modpack(packid, null, null, null, false, data.getInt("versionid"), data.getString("version"), null, server, null);
			return pack;
		}
		return null;
	}

	/**
	 * Gets the maximum window width.
	 * 
	 * @return the maximum window width
	 */
	public static int getMaximumWindowWidth() {
		return 500;
		//TODO this

	}

	/**
	 * Gets the maximum window height.
	 * 
	 * @return the maximum window height
	 */
	public static int getMaximumWindowHeight() {
		//TODO this
		return 500;
	}

	public static int getMaximumRam() {
		return 10000;
	}

	/**TODO bit iffy may want to revise before people start using it
	 * @return
	 */
	public static boolean isJava8(){
		return System.getProperty("java.version").substring(0, 3).equalsIgnoreCase("1.8");
	}

}
