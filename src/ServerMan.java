import net.demus_intergalactical.serverman.Globals;
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
			(type, time, th, args1) -> {
				System.out.print(th);
				System.out.print(": ");
				for (String arg : args1) {
					System.out.print(arg);
					System.out.print(' ');
				}
				System.out.println();
			}
		);
		i.loadInstance();
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
