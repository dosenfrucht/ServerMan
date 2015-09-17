package net.demus_intergalactical.serverman;

// [15:46:21] [Server thread/INFO]: Dosenfrucht joined the game
// [16:13:08] [Server thread/INFO]: Dosenfrucht left the game

import java.util.Date;

public interface OutputHandler {

	void receive(
		String type,
		Object time,
		String thread,
		String infoLvl,
		String[] args
	);

}
