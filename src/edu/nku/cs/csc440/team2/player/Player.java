package edu.nku.cs.csc440.team2.player;

/**
 * Represents and abstract Player object that has the ability to render itself
 * on the canvas (by getting it's layout from the internal layout object) and
 * control is own playback. The playback commands will be issued to the Player
 * objects from Player container objects such as ParPlayer and SeqPlayer.
 * 
 * @author John Murray
 * @version 1.0 4/24/11
 */
public abstract class Player {

	public static final double PLAYBACK_INTERVAL = 0.1f;

	/**
	 * Used in the subscriber pattern to receive notifications/ commands from in
	 * case of a buffer issued by either an audio or media player object.
	 */
	protected Arbiter subject;

	/**
	 * Determines the start time for the object in a double type. However, the
	 * precision of the time should not exceed 1 tenth. The time is represented
	 * in seconds.
	 */
	protected double start;

	/**
	 * Determines how long the media object should remain on the canvas
	 */
	protected double duration;

	/**
	 * Determines how much of the media object has been played. Can be used
	 * along with duration to determine when the media object should be removed
	 * from the canvas.
	 */
	protected double timePlayed = 0;

	/**
	 * Flag to determine (quickly) if playback has started without having to
	 * calculate time and what not.
	 */
	public boolean isPlaying = false;

	/**
	 * Plays the media object. Simple enough. Each media object implements a
	 * slightly different algorithm for playing.
	 */
	public abstract void play();

	/**
	 * Pauses the media object. Playing from this point will cause the media
	 * object to continue at the point from which it was paused.
	 */
	public abstract void pause();

	/**
	 * Prepare the Player (buffer, start stream, etc.)
	 */
	public abstract void prepare();

	/**
	 * Seeks the video forward a set period of time; Fast-Forward.
	 */
	public abstract void seekForward();

	/**
	 * Seeks the video backward a set period of time; Rewind.
	 */
	public abstract void seekBackward();

	/**
	 * 
	 * @return double
	 * 
	 *         Return the total time the video should be played
	 */
	public double getDuration() {
		return this.duration;
	}

	/**
	 * 
	 * @return double
	 * 
	 *         Return the total time that has been played. Should always be less
	 *         than the duration of the object when playing.
	 */
	public double getTimePlayed() {
		return this.timePlayed;
	}

	/**
	 * Add an Arbiter object as the class's subject in the subscriber pattern.
	 */
	protected void bindArbiter(Arbiter a) {
		this.subject = a;
	}

	/**
	 * Get the Arbiter (subject)
	 */
	public Arbiter getSubject() {
		return this.subject;
	}

	/**
	 * Reset the player object
	 */
	public void reset() {
		this.timePlayed = 0;
		this.isPlaying = false;
	}

}
