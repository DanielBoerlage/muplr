package muplr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.AudioDevice;

public class Player implements Runnable {

	private final static double FRAMES_PER_SECOND = 38.46153846;

	private final static int PLAYING = 0;
	private final static int PAUSED = 1;
	private final static int FINISHED = 2;

	private int state;
	private Object lock = new Object();
	/** current playback position in truncated seconds */
	private int position;
	private Bitstream bitstream;
	private Decoder decoder;
	private AudioDevice audio;
	private PlayerListener listener;
	
	public Player(File file, double secondsOffset, PlayerListener listener) {
		position = 0;
		this.listener = listener;
		try {
			bitstream = new Bitstream(new FileInputStream(file));
			audio = FactoryRegistry.systemRegistry().createAudioDevice();
			audio.open(decoder = new Decoder());
			int framesOffset = (int)(secondsOffset * FRAMES_PER_SECOND);
			while(framesOffset-- > 0 && bitstream.readFrame() != null)
				bitstream.closeFrame();
		} catch(FileNotFoundException e) {
			Main.error("File not found: " + file);
		} catch(JavaLayerException e) {
			Main.error("JavaLayerException");
		}
	}

	public void run() {
		while(state != FINISHED) {
			if(playFrame())
				break;
			if(audio.getPosition() / 1000 > position) {
				position = audio.getPosition() / 1000;
				listener.timeUpdate(position);
			}
			synchronized(lock) {
				if(state == PAUSED) {
					try {
						lock.wait();
					} catch(InterruptedException e) {
						break;
					}
				}
			}
		}
		close();
	}

	private void close() {
		audio.flush();
		audio.close();
		try {
			bitstream.close();
		} catch(JavaLayerException e) {
			Main.error("JavaLayerException");
		}
	}

	public void stop() {
		synchronized(lock) {
			state = FINISHED;
			lock.notify();
		}
	}

	public void resume() {
		synchronized(lock) {
			state = PLAYING;
			lock.notify();
		}
	}

	public void pause() {
		synchronized(lock) {
			state = PAUSED;
		}
	}

	/**
	 * @return true if the last frame was played
	 */
	public boolean playFrame() {
		try {
			Header header = bitstream.readFrame();
			if(header == null)
				return true;
			SampleBuffer output = (SampleBuffer)decoder.decodeFrame(header, bitstream);
			audio.write(output.getBuffer(), 0, output.getBufferLength());
			bitstream.closeFrame();
		} catch(RuntimeException e) {
			Main.error("RuntimeException" + e);
		} catch(JavaLayerException e) {
			Main.error("JavaLayerException" + e);
		}
		return false;
	}
}