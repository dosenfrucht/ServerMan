package net.demus_intergalactical.serverman;

// [15:46:21] [Server thread/INFO]: Dosenfrucht joined the game
// [16:13:08] [Server thread/INFO]: Dosenfrucht left the game

public interface OutputHandler {

	void onOutputPlayerJoined(String time, String player);
	void onOutputPlayerLeft(String time, String player);
	void onOutputInfo(String time, String text);
	void onOutputWarn(String time, String text);
	void onOutputErr(String time, String text);
	void onOutput(String time, String text);

}
