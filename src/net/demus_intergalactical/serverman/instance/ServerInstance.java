package net.demus_intergalactical.serverman.instance;

import net.demus_intergalactical.serverman.Globals;
import net.demus_intergalactical.serverman.OutputHandler;
import net.demus_intergalactical.serverman.PlayerHandler;
import net.demus_intergalactical.serverman.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class ServerInstance {

	private String serverInstanceID;

	private String name;
	private String serverFile;
	private String serverVersion;
	private List<String> javaArgs;

	private ServerInstanceProcess p;
	private ScriptEngine js;

	private OutputHandler out;
	private PlayerHandler playerHandler;

	public ServerInstance() {
		javaArgs = new LinkedList<>();
	}

	public ServerInstance(String serverInstanceID, OutputHandler out,
	                      PlayerHandler playerHandler) {
		javaArgs = new LinkedList<>();
		setServerInstanceID(serverInstanceID);
		setOut(out);
		setPlayerHandler(playerHandler);
	}

	public ServerInstance load() {

		loadConfig();

		loadMatchScript();

		return this;
	}

	public ServerInstance save() {

		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("server_file", serverFile);
		obj.put("server_version", serverVersion);
		obj.put("java_args", javaArgs);
		Globals.getInstanceSettings().add(serverInstanceID, obj);
		Globals.getInstanceSettings().saveConfig();

		return this;
	}


	public void loadConfig() {
		String instanceHome = Globals.getServerManConfig()
			.get("instances_home") + File.separator
				+ serverInstanceID;

		File dir = new File(instanceHome);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				System.err.println("Could not create"
					+ "instance folder");
			}
		}

		JSONObject obj = (JSONObject)
			Globals.getInstanceSettings().get(serverInstanceID);

		if (obj == null) {
			Globals.getInstanceSettings()
				.addDefault(serverInstanceID);
			obj = (JSONObject)
			Globals.getInstanceSettings().get(serverInstanceID);
		}

		Object[] tmpArgs;

		name = (String) obj.get("name");
		serverFile = (String) obj.get("server_file");
		serverVersion = (String) obj.get("server_version");
		tmpArgs = ((JSONArray) obj.get("java_args")).toArray();
		for (Object arg : tmpArgs) {
			javaArgs.add((String) arg);
		}
	}



	public void loadMatchScript() {
		PlayerWrapper pw = new PlayerWrapper(this, playerHandler);

		String matchScriptPath = Globals.getServerManConfig()
			.get("instances_home") + File.separator
			+ serverInstanceID + File.separator + "match.js";

		File matchScriptFile = new File(matchScriptPath);
		if (!matchScriptFile.exists()) {
			System.err.println("match.js not found .. attempt to download");
			File tmp = new File(matchScriptPath);
			String url = "http://serverman.demus-intergalactical.net/v/" + serverVersion + "/match.js";
			Utils.download(url, tmp);
		}

		ScriptEngineManager sm = new ScriptEngineManager();
		this.js = sm.getEngineByName("JavaScript");

		try {
			js.put("log", out);
			js.put("players", pw);
			js.eval(new FileReader(matchScriptFile));
		} catch (ScriptException | FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			((Invocable) js).invokeFunction("init");
		} catch (ScriptException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServerFile() {
		return serverFile;
	}

	public void setServerFile(String serverFile) {
		this.serverFile = serverFile;
	}


	public String getServerInstanceID() {
		return serverInstanceID;
	}

	public void setServerInstanceID(String serverInstanceID) {
		this.serverInstanceID = serverInstanceID;
	}

	public List<String> getJavaArgs() {
		return javaArgs;
	}

	public void setJavaArgs(List<String> javaArgs) {
		this.javaArgs = javaArgs;
	}


	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

	public void setPlayerHandler(PlayerHandler playerHandler) {
		this.playerHandler = playerHandler;
	}

	public OutputHandler getOut() {
		return out;
	}

	public void setOut(OutputHandler out) {
		this.out = out;
	}


	public void run() {
		p = new ServerInstanceProcess(this);
		p.start();
	}

	public synchronized boolean isRunning() {
		return p.isRunning();
	}

	public synchronized void send(String command) {
		p.send(command);
	}

	public synchronized void stop() {
		p.stop();
	}

	public synchronized ServerInstanceProcess getProcess() {
		return p;
	}

	public ScriptEngine getMatcherJS() {
		return js;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}
}