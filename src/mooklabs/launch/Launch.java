package mooklabs.launch;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import mooklabs.auth.Account;
import mooklabs.auth.MojangAuth;
import openl.Downloader;
import openl.LastLogin;
import openl.Utils;


/**
 * 
 * @author mooklabs, fishyFishIndustries
 */
public class Launch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GuiWindow.init();
		LastLogin.tryLoading();


		GuiWindow.window.setVisible(true);
	}

	public static void login() {
		Map<String, String> data = null;
		try {
			data = MojangAuth.authenticate(GuiWindow.username.getText(), new String(GuiWindow.password.getText()));

			LastLogin.ACCESS_TOKEN = data.get("accessToken");
			LastLogin.CLIENT_TOKEN = data.get("clientToken");
			LastLogin.UUID = data.get("UUID");
			Account.username = data.get("username");
			System.out.println("Your UUID is "+LastLogin.UUID+" in case you wanted to know!");
		} catch (Exception e) {
		}

		if (data != null) {
			setLoggedIn();
			LastLogin.save();
		}
	}

	public static void setLoggedIn(){
		GuiWindow.password.setVisible(false);
		GuiWindow.username.setVisible(false);
		GuiWindow.loginButton.setVisible(false);
		GuiWindow.window.setTitle("NightfallLauncher--" + Account.username);
		GuiWindow.sidebarTop.remove(GuiWindow.loginButton);
		GuiWindow.sidebarTop.remove(GuiWindow.username);
		GuiWindow.sidebarTop.remove(GuiWindow.password);
		GuiWindow.sidebarTop.remove(GuiWindow.sidebarTop);

		try {
			JLabel picLabel = new JLabel(new ImageIcon(ImageIO.read(new URL("https://minotar.net/avatar/" + Account.username + "/150.png"))));
			//GuiWindow.sidebarTop.add(picLabel);
			GuiWindow.c.weightx = 0;
			GuiWindow.c.weighty = 0;
			GuiWindow.c.fill = GuiWindow.c.NONE;
			GuiWindow.window.add(picLabel, GuiWindow.constrain(0, 0, 1, GuiWindow.c.RELATIVE, GuiWindow.c.NORTHWEST));

		} catch (IOException e) {e.printStackTrace();}
		//TODO logout!
		downloadMc();

	}

	// {{minecraft jar stuff

	/** location of them Mojang server that MC itself & the json's are pulled from*/
	public final static String mc_dl = "https://s3.amazonaws.com/Minecraft.Download/";
	/** location of them Mojang server that MC's resources are pulled from*/
	public final static String mc_res = "http://resources.download.minecraft.net/";
	/** location of them Mojang server that hosts the Minecraft Maven host*/
	public final static String mc_libs = "https://libraries.minecraft.net/";
	public static final String VERSION_MANIFEST_URL = "https://s3.amazonaws.com/Minecraft.Download/versions/%s/%s.json";
	public static final String BASE_LIB_URL = "https://s3.amazonaws.com/Minecraft.Download/libraries/";

	/**This is an xml file storing all of the modpack data*/
	static final String urlstring = "http://textuploader.com/03aa/raw";// dont think ill ever have to change this(for git i did)

	public static void downloadMc() {
		if (true) {
			File jarPath = new File(Utils.getMinecraftFolder(), "minecraft.jar");
			File manifestPath = new File(Utils.getMinecraftFolder(), "1.7.2.json");
			// Obtain the release manifest, save it, and parse it
			try {
				// If the JSON does not exist, update it
				if (!jarPath.exists()) {
					Downloader.download(new URL(VERSION_MANIFEST_URL.replace("%s", "1.7.2")), manifestPath);
					System.out.println("json updated");
				}// If the JAR does not exist, install it
				if (!jarPath.exists()) {
					Downloader.download(new URL("http://s3.amazonaws.com/Minecraft.Download/versions/%s/%s.jar".replace("%s", "1.7.2")), jarPath);
					System.out.println("minecraft.jar updated");
				}
			} catch (Exception e) {
				System.err.println("something went wrong while downloading mc jar or json");
				e.printStackTrace();
			}

			File contentDir = Utils.getInstancesFolder();
			File librariesDir = new File(Utils.getInstancesFolder(), "1.7.2.json");



			// Assets!?
			// JSONObject json = JSONUtils..getJSONObjectFromInputStream(new FileInputStream(manifestPath));//get all needed libs

			// for (String library : json.getJSONArray("libraries").getString("name")) {

			/*
					for (Library library : manifest.getLibraries()) {
						if (true){//library.matches(Utils.getPlatform())) {//right os
							URL url = new URL(BASE_LIB_URL); //library.getUrl(Utils.getPlatform());
							File file = new File(librariesDir, library.getPath(Utils.getPlatform()));

							if (!file.exists()) {
								Downloader.download(url, file);
							}

							//checkInterrupted();
						}
					}*/

		}
	}
	// }}


}
