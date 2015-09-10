package net.demus_intergalactical.serverman.instance;

import net.demus_intergalactical.serverman.Globals;
import org.json.simple.JSONObject;

import java.io.File;

public class ServerInstance {

	private String serverInstanceID;

	private String name;
	private String serverJar;
	private String minRam;
	private String maxRam;

	public ServerInstance(String serverInstanceID) {
		this.serverInstanceID = serverInstanceID;
	}

	public ServerInstance loadInstance() {
		String instanceHome = Globals.getServerManConfig()
			.get("instances_home") + File.separator
				+ serverInstanceID;

		File dir = new File(instanceHome);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		JSONObject obj = (JSONObject)
			Globals.getInstanceSettings().get(serverInstanceID);

		name = (String) obj.get("name");
		serverJar = (String) obj.get("server_jar");
		minRam = (String) obj.get("min_ram");
		maxRam = (String) obj.get("max_ram");

		System.out.println(name);
		System.out.println(serverJar);
		System.out.println(minRam);
		System.out.println(maxRam);

		return this;
	}

}
