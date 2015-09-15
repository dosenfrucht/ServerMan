package net.demus_intergalactical.serverman.instance;

import net.demus_intergalactical.serverman.Globals;

import net.demus_intergalactical.serverman.OutputHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ServerInstance {

	private String serverInstanceID;

	private String name;
	private String serverFile;
	private List<String> javaArgs;

	private ServerInstanceProcess p;
	private ScriptEngine js;

	private OutputHandler out;

	public ServerInstance(String serverInstanceID, OutputHandler out) {
		this.serverInstanceID = serverInstanceID;
		this.out = out;
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

		if (obj == null) {
			Globals.getInstanceSettings()
				.addDefault(serverInstanceID);
			obj = (JSONObject)
			Globals.getInstanceSettings().get(serverInstanceID);
		}

		Object[] tmpArgs;

		name = (String) obj.get("name");
		serverFile = (String) obj.get("server_file");
		tmpArgs = ((JSONArray) obj.get("java_args")).toArray();
		javaArgs = new LinkedList<>();
		for (Object arg : tmpArgs) {
			javaArgs.add((String) arg);
		}

		// loadMatchScript
		String matchScriptPath = Globals.getServerManConfig()
			.get("instances_home") + File.separator
			+ serverInstanceID + File.separator + "match.js";

		File matchScriptFile = new File(matchScriptPath);
		if (!matchScriptFile.exists()) {
			// TODO download suitable version
			System.err.println("match.js not found");
			try {
				matchScriptFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ScriptEngineManager sm = new ScriptEngineManager();
		this.js = sm.getEngineByName("JavaScript");

		try {
			js.put("writer", out);
			js.eval(new FileReader(matchScriptFile));
		} catch (ScriptException | FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			((Invocable) js).invokeFunction("init");
		} catch (ScriptException | NoSuchMethodException e) {
			e.printStackTrace();
		}

		return this;
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
}
