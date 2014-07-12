package muplr;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Properties;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.decoder.JavaLayerException;

import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.ID3v2;

public class Main {

	public final static Path WORKING_DIRECTORY = Paths.get(System.getProperty("user.dir"));
	public final static String OS = System.getProperty("os.name");
	//public final static InputStream EMPTY_STREAM = new ByteArrayInputStream(new byte[0]);
	public static Properties properties;

	public static void main(String[] args) throws Exception {
		properties = new Properties();
		Playlist playlist = Input.parseArgs(args);
		playlist.play(0);

		//Output.clear();
		//Output.printHeader("muplr", "Daniel Boerlage 2014", 80);
		/*InputStream keyboard = System.in;
		Thread t = new Thread(){
			public void run() {
				try {
					while(true) {
						Scanner sc = new Scanner(System.in);
						if(sc.hasNextLine())
							System.out.println(sc.nextLine());
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.setDaemon(true);
		t.start();
		try {
			System.setIn(EMPTY_STREAM);
			System.out.println("first");
			Thread.sleep(5000);
			System.setIn(keyboard);
			System.out.println("second");
			Thread.sleep(5000);
			System.out.println("third");
		} catch(Exception e) {
			e.printStackTrace();
		}*/


		//jlap player = new jlap();
		//player.play("C:\\Users\\Daniel\\Music\\RUSH\\1975 - Fly By Night @320\\05 - Fly By Night.mp3");

		/*System.out.println("xx:xx");
		File audioFile = new File("C:\\Users\\Daniel\\Music\\Jonathan Coultan\\Songs from Ze Frank's The Show\\Fun Winter.mp3");
		Player player = new Player(audioFile, 60, new PlayerListener(){
			@Override
			public void timeUpdate(int seconds) {
				System.out.printf("%2d:%02d%n", seconds / 60, seconds % 60);
			}

			@Override
			public void playbackFinished(boolean userInvoked) {
				System.out.println("playbackFinished " + userInvoked);
			}
		});
		Thread playerThread = new Thread(player);
		playerThread.setDaemon(true);
		playerThread.start();
		Thread.sleep(10000);
		player.stop();
		Thread.sleep(1000);*/

		/*Mp3File mp3file = new Mp3File("C:\\Users\\Daniel\\Music\\Jonathan Coultan\\Songs from Ze Frank's The Show\\Fun Winter.mp3");
		System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
		System.out.println("Bitrate: " + mp3file.getLengthInSeconds() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
		System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
		System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
		System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
		System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
		if (mp3file.hasId3v2Tag()) {
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();
			System.out.println("Track: " + id3v2Tag.getTrack());
			System.out.println("Artist: " + id3v2Tag.getArtist());
			System.out.println("Title: " + id3v2Tag.getTitle());
			System.out.println("Album: " + id3v2Tag.getAlbum());
			System.out.println("Year: " + id3v2Tag.getYear());
			System.out.println("Genre: " + id3v2Tag.getGenre() + " (" + id3v2Tag.getGenreDescription() + ")");
			System.out.println("Comment: " + id3v2Tag.getComment());
			System.out.println("Composer: " + id3v2Tag.getComposer());
			System.out.println("Publisher: " + id3v2Tag.getPublisher());
			System.out.println("Original artist: " + id3v2Tag.getOriginalArtist());
			System.out.println("Album artist: " + id3v2Tag.getAlbumArtist());
			System.out.println("Copyright: " + id3v2Tag.getCopyright());
			System.out.println("URL: " + id3v2Tag.getUrl());
			System.out.println("Encoder: " + id3v2Tag.getEncoder());
			byte[] albumImageData = id3v2Tag.getAlbumImage();
			if (albumImageData != null) {
				System.out.println("Have album image data, length: " + albumImageData.length + " bytes");
				System.out.println("Album image mime type: " + id3v2Tag.getAlbumImageMimeType());
			}
		}*/

		/*File file = new File("C:\\Users\\Daniel\\Music\\RUSH\\1975 - Fly By Night @320\\05 - Fly By Night.mp3");
		FileInputStream stream = new FileInputStream(file);
		AdvancedPlayer player = new AdvancedPlayer(stream);
		player.play(0,100);
		player.close();
		player = new AdvancedPlayer(new FileInputStream(file));
		player.play(100,200);
		player.close();*/

	}

	public static void exitSuccess() {
		System.exit(0);
	}

	public static void fatalError(String errMsg) {
		error(errMsg);
		System.exit(-1);
	}

	public static void error(String errMsg) {
		Output.printErr(errMsg);
	}
}