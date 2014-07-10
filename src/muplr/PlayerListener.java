package muplr;

public interface PlayerListener {

	/**
	 * Called everytime the player's position advances by one second
	 * @param seconds  the player's current position in seconds
	 */
	public void timeUpdate(int seconds);

	/**
	 * Called when the player ends playback, in any circumstance
	 * @param userInvoked  the player's <code>stop</code> method was called
	 */
	public void playbackFinished(boolean userInvoked);
}