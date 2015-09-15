package net.demus_intergalactical.serverman;

// [15:46:21] [Server thread/INFO]: Dosenfrucht joined the game
// [16:13:08] [Server thread/INFO]: Dosenfrucht left the game

public interface OutputHandler {

	void receive(String type, String time, String thread, String[] args);

}
