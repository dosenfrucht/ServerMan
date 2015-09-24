import net.demus_intergalactical.serverman.Globals;
import net.demus_intergalactical.serverman.PlayerHandler;
import net.demus_intergalactical.serverman.instance.ServerInstance;
import net.demus_intergalactical.serverman.instance.ServerInstanceProcess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerMan {


	public static void main(String[] args) throws IOException, InterruptedException {
		Globals.init();
		try {
			Globals.getServerManConfig().load();
			Globals.getInstanceSettings().load();
		} catch (IOException e) {
			e.printStackTrace();
		}



		ServerInstance i = new ServerInstance("vanilla_1.8",
			(type, time, th, infoLvl, arg) -> {
				System.out.print(time.getClass().toString());
				System.out.print(' ');
				System.out.print(th);
				System.out.print('/');
				System.out.print(infoLvl);
				System.out.print(": ");
				System.out.print(arg.toString());
				System.out.println();
			}, null
		);
		i.setPlayerHandler(new PlayerHandler() {
			@Override
			public void onPlayerJoined(String name) {
				System.out.println("Player " + name
					+ " joined");
				System.out.println(i.getProcess().getPlayers()
					.size());
			}

			@Override
			public void onPlayerLeft(String name) {
				System.out.println("Player " + name + " left");
				System.out.println(i.getProcess().getPlayers()
					.size());
			}
		});

		i.load();
		i.save();
		i.load();
		i.run();


		BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in)
		);

		ServerInstanceProcess p = i.getProcess();

		boolean running = p.isRunning();
		while (running) {
			if (in.ready()) {
				p.send(in.readLine());
			}
			Thread.sleep(1);
			running = p.isRunning();
		}
	}

}
