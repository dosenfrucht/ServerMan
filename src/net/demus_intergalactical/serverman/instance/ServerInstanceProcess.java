package net.demus_intergalactical.serverman.instance;

import net.demus_intergalactical.serverman.OutputHandler;

public class ServerInstanceProcess {

	private volatile ServerInstance instance;
	private volatile OutputHandler outputHandler;

	private volatile Thread rT;

	private volatile ServerInstanceRunner runner;

	public ServerInstanceProcess(ServerInstance inst, OutputHandler out) {
		this.instance = inst;
		this.outputHandler = out;
		runner = new ServerInstanceRunner(inst, out);
	}

	public void start() {
		rT = new Thread(runner);
		rT.start();
	}

	public void send(String command) {
		runner.send(command);
	}

	public synchronized boolean isRunning() {
		return rT.isAlive();
	}

	public synchronized void stop() {
		runner.stop();
	}
}
