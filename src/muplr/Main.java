package muplr;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.decoder.JavaLayerException;

public class Main {

	public final static Path WORKING_DIRECTORY = Paths.get(System.getProperty("user.dir"));
	public final static String OS = System.getProperty("os.name");
	public final static InputStream EMPTY_STREAM = new ByteArrayInputStream(new byte[0]);

	public static void main(String[] args) throws Exception {
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

		System.out.println("xx:xx");
		File audioFile = new File("C:\\Users\\Daniel\\Music\\Jonathan Coultan\\Songs from Ze Frank's The Show\\Fun Winter.mp3");
		Player player = new Player(audioFile, 0, new PlayerListener(){
			@Override
			public void timeUpdate(int seconds) {
				System.out.printf("%2d:%02d%n", seconds / 60, seconds % 60);
			}

			@Override
			public void playbackFinished(boolean userInvoked) {

			}
		});
		Thread playerThread = new Thread(player);
		playerThread.setDaemon(true);
		playerThread.start();
		Thread.sleep(80000);

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