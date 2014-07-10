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
import javazoom.jl.decoder.BitstreamException;

public class MuplrPlayer implements Runnable {

	private File file;
	private Bitstream bitstream;
	private Decoder decoder;
	private AudioDevice audio;
	
	public MuplrPlayer(File file) {
		setFile(file);
	}

	public void run() {
		play(80);
		seek(80);
		play(80);
		seek(160);
		play(80);
	}

	public void setFile(File file) {
		this.file = file;
		try {
			bitstream = new Bitstream(new FileInputStream(file));
			audio = FactoryRegistry.systemRegistry().createAudioDevice();
			audio.open(decoder = new Decoder());
		} catch(FileNotFoundException e) {
			Main.error("Error: file not found: " + file);
		} catch(JavaLayerException e) {
			Main.error("JavaLayerException");
		}
	}

	public void close() {
		audio.flush();
		audio.close();
		try {
			bitstream.close();
		} catch(JavaLayerException e) {
			Main.error("JavaLayerException");
		}
	}

	public void play(int frames) {
		for(int i = 0; i < frames; i++)
			playFrame();
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
			Main.error("Error decoding audio " + e);
		} catch(JavaLayerException e) {
			Main.error("JavaLayerException");
		}
		return false;
	}

	public void seek(int frame) {
		try {
			close();
			setFile(file);
			while(frame-- > 0) {
				Header h = bitstream.readFrame();
				if(h == null) {
					System.out.println("break");
					break;
				}
				bitstream.closeFrame();
			}
		} catch(JavaLayerException e) {
			Main.error("JavaLayerException");
		}
	}
}