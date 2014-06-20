package atlauncher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mooklabs.Settings;
import openl.LastLogin;
import openl.Modpack;
import openl.Utils;

public class MCLauncher {

	public static Process launch(Modpack instance) throws IOException {

		StringBuilder cpb = new StringBuilder("");

		File jarMods = instance.getJarModsDirectory();
		if (jarMods.exists() && (instance.hasJarMods() || jarMods.listFiles().length != 0)) {
			if (instance.hasJarMods()) {
				ArrayList<String> jarmods = new ArrayList<String>(Arrays.asList(instance.getJarOrder().split(",")));
				for (String mod : jarmods) {
					File thisFile = new File(jarMods, mod);
					if (thisFile.exists()) {
						cpb.append(File.pathSeparator);
						cpb.append(thisFile);
					}
				}
				for (File file : jarMods.listFiles()) {
					if (jarmods.contains(file.getName())) {
						continue;
					}
					cpb.append(File.pathSeparator);
					cpb.append(file);
				}
			} else {
				for (File file : jarMods.listFiles()) {
					cpb.append(File.pathSeparator);
					cpb.append(file);
				}
			}
		}

		/*CRIT for (String jarFile : instance.getLibrariesNeeded().split(",")) {
			cpb.append(File.pathSeparator);
			cpb.append(new File(instance.getBinDirectory(), jarFile));
		}*/

		cpb.append(File.pathSeparator);
		cpb.append(instance.getMinecraftJar());

		List<String> arguments = new ArrayList<String>();

		String path = Settings.getJavaPath() + File.separator + "bin" + File.separator + "java";
		if (Utils.isWindows()) {
			path += "w";
		}
		arguments.add(path);

		if (Utils.isWindows()) {
			arguments.add("-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump");
		}

		arguments.add("-XX:-OmitStackTraceInFastThrow");
		arguments.add("-Xms256M");

		if (Settings.getMemory() < instance.getMemory()) {
			if ((Utils.getMaximumRam() / 2) < instance.getMemory()) {
				arguments.add("-Xmx" + Settings.getMemory() + "M");
			} else {
				arguments.add("-Xmx" + instance.getMemory() + "M");
			}
		} else {
			arguments.add("-Xmx" + Settings.getMemory() + "M");
		}
		if (Settings.getPermGen() < instance.getPermGen() && (Utils.getMaximumRam() / 8) < instance.getPermGen()) {
			if (Utils.isJava8()) {
				arguments.add("-XX:MetaspaceSize=" + instance.getPermGen() + "M");
			} else {
				arguments.add("-XX:PermSize=" + instance.getPermGen() + "M");
			}
		} else {
			if (Utils.isJava8()) {
				arguments.add("-XX:MetaspaceSize=" + Settings.getPermGen() + "M");
			} else {
				arguments.add("-XX:PermSize=" + Settings.getPermGen() + "M");
			}
		}

		arguments.add("-Duser.language=en");
		arguments.add("-Duser.country=US");
		arguments.add("-Dfml.ignorePatchDiscrepancies=true");
		arguments.add("-Dfml.ignoreInvalidMinecraftCertificates=true");

		arguments.add("-Dfml.log.level=" + Settings.getForgeLoggingLevel());

		if (Utils.isMac()) {
			arguments.add("-Dapple.laf.useScreenMenuBar=true");
			arguments.add("-Xdock:icon=" + new File(instance.getAssetsDir(), "icons/minecraft.icns").getAbsolutePath());
			arguments.add("-Xdock:name=\"" + instance.getName() + "\"");
		}

		if (!Settings.getJavaParameters().isEmpty()) {
			for (String arg : Settings.getJavaParameters().split(" ")) {
				if (!arg.isEmpty()) {
					if (instance.hasExtraArguments()) {
						if (instance.getExtraArguments().contains(arg)) {
							System.err.println("Duplicate argument " + arg + " found and not added!");
						} else {
							arguments.add(arg);
						}
					} else {
						arguments.add(arg);
					}
				}
			}
		}

		//CRIT arguments.add("-Djava.library.path=" + instance.getNativesDirectory().getAbsolutePath());
		arguments.add("-cp");
		arguments.add(System.getProperty("java.class.path") + cpb.toString());
		arguments.add(instance.getMainClass());
		String props = "";//CRIT new Gson().toJson((LastLogin.getUser() == null ? new HashMap<String, Collection<String>>() : LastLogin.getProperties()));
		if (instance.hasMinecraftArguments()) {
			String[] minecraftArguments = instance.getMinecraftArguments().split(" ");
			for (String argument : minecraftArguments) {
				argument = argument.replace("${auth_player_name}", LastLogin.username);
				argument = argument.replace("${profile_name}", instance.getName());
				argument = argument.replace("${user_properties}", props);
				argument = argument.replace("${version_name}", instance.getMcVersion());
				argument = argument.replace("${game_directory}", instance.getRootDirectory().getAbsolutePath());
				argument = argument.replace("${game_assets}", instance.getAssetsDir().getAbsolutePath());
				argument = argument.replace("${assets_root}", Settings.getResourcesDir().getAbsolutePath());
				//CRIT		argument = argument.replace("${assets_index_name}", instance.getAssets());
				argument = argument.replace("${auth_uuid}", LastLogin.UUID);
				argument = argument.replace("${auth_access_token}", LastLogin.ACCESS_TOKEN);
				argument = argument.replace("${auth_session}", LastLogin.getSession());
				//				argument = argument.replace("${user_type}", (LastLogin.getSelectedProfile().isLegacy() ? UserType.LEGACY.getName() : UserType.MOJANG.getName()));
				arguments.add(argument);
			}
		} else {
			arguments.add("--username=" + LastLogin.username);
			arguments.add("--session=" + LastLogin.getSession());

			// This is for 1.7
			arguments.add("--accessToken=" + LastLogin.ACCESS_TOKEN);
			arguments.add("--uuid=" + LastLogin.UUID);
			// End of stuff for 1.7

			arguments.add("--version=" + instance.getMcVersion());
			arguments.add("--gameDir=" + instance.getRootDirectory().getAbsolutePath());
			arguments.add("--assetsDir=" + Settings.getResourcesDir().getAbsolutePath());
		}
		if (Settings.startMinecraftMaximised()) {
			arguments.add("--width=" + Utils.getMaximumWindowWidth());
			arguments.add("--height=" + Utils.getMaximumWindowHeight());
		} else {
			arguments.add("--width=" + Settings.getWindowWidth());
			arguments.add("--height=" + Settings.getWindowHeight());
		}
		if (instance.hasExtraArguments()) {
			String args = instance.getExtraArguments();
			if (args.contains(" ")) {
				for (String arg : args.split(" ")) {
					arguments.add(arg);
				}
			} else {
				arguments.add(args);
			}
		}

		String argsString = arguments.toString();
		/*argsString = argsString.replace(LastLogin.username, "REDACTED");
		argsString = argsString.replace(LastLogin.UUID, "REDACTED");
		argsString = argsString.replace(LastLogin.ACCESS_TOKEN, "REDACTED");
		argsString = argsString.replace(LastLogin.getSession(), "REDACTED");
		argsString = argsString.replace(props, "REDACTED");*/

		System.err.println("Launching Minecraft with the following arguments " + "(user related stuff has been removed): " + argsString);
		ProcessBuilder processBuilder = new ProcessBuilder(arguments);
		processBuilder.directory(instance.getRootDirectory());
		processBuilder.redirectErrorStream(true);
		return processBuilder.start();
	}
}