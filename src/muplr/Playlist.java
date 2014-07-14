package muplr;

import java.util.ArrayList;
import java.io.File;

public class Playlist {

	private ArrayList<File> songs;

	public Playlist() {
		songs = new ArrayList<File>();
	}

	public Playlist(File song) {
		this();
		songs.add(song);
	}

	public void play(final int track) {
		Player player = new Player(songs.get(track), 0, new PlayerListener(){
			@Override
			public void timeUpdate(int seconds) {
				System.out.printf("%2d:%02d%n", seconds / 60, seconds % 60);
			}

			@Override
			public void playbackFinished(boolean userInvoked) {
				if(userInvoked)
					return;
				if(track == songs.size() - 1) {
					if(Boolean.parseBoolean(Main.properties.getProperty("repeat", "false")))
						play(0);
				} else
					play(track + 1);
			}
		});
		Thread playerThread = new Thread(player);
		playerThread.setDaemon(false);  // !!!!
		playerThread.start();
	}

	public void add(File file) {
		if(file != null && file.exists() && file.canRead())
			songs.add(file);
	}

	public void add(Playlist playlist) {
		songs.addAll(playlist.songs);
	}

	public File get(int i) {
		return songs.get(i);
	}

	public int size() {
		return songs.size();
	}

	public void list() {
		for(int i = 0; i < songs.size(); i++) {
			System.out.println("[" + i + "]: " + songs.get(i));
		}
	}
}