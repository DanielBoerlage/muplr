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

	public void add(File file) {
		if(file != null && file.exists() && file.canRead()/* && file.getName().endsWith(".mp3")*/)
			songs.add(file);
	}

	public void add(Playlist playlist) {
		songs.addAll(playlist.songs);
	}

	public String toString() {
		String out = "Listing playlist..\n";
		for(int i = 0; i < songs.size(); i++)
			out += "\t[" + i + "] " + songs.get(i) + "\n";
		return out;
	}
}