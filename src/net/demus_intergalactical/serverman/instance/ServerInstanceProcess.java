package net.demus_intergalactical.serverman.instance;

public class ServerInstanceProcess {

	private volatile Thread rT;

	private volatile ServerInstanceRunner runner;

	public ServerInstanceProcess(ServerInstance inst) {
		runner = new ServerInstanceRunner(inst);
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
