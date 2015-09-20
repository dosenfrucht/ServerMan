package net.demus_intergalactical.serverman.instance;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ServerInstanceProcess {

	private volatile Thread rT;

	private volatile ServerInstanceRunner runner;

	private HashSet<String> players;

	public ServerInstanceProcess(ServerInstance inst) {
		runner = new ServerInstanceRunner(inst);
		players = new HashSet<>();
	}

	public void start() {
		rT = new Thread(runner);
		rT.start();
	}

	public void send(String command) {
		runner.send(command);
	}

	public synchronized void addPlayer(String name) {
		players.add(name);
	}

	public synchronized void removePlayer(String name) {
		players.remove(name);
	}

	public synchronized Set<String> getPlayers() {
		return players;
	}

	public synchronized boolean isRunning() {
		return rT.isAlive();
	}

	public synchronized void stop() {
		runner.stop();
	}
}
