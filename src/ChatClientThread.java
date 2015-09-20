import java.net.*;
import java.io.*;

public class ChatClientThread extends Thread {
	private Socket socket = null;
	private newEngine client = null;
	private DataInputStream streamIn = null;
	private volatile boolean stopMe = false;

	public ChatClientThread(newEngine _client, Socket _socket) {
		client = _client;
		socket = _socket;
		open();
		start();
	}

	public void open() {
		try {
			streamIn = new DataInputStream(socket.getInputStream());
		} catch (IOException ioe) {
			System.out.println("Error getting input stream: " + ioe);
			client.close();
		}
	}

	public void close() {
		try {
			if (streamIn != null)
				streamIn.close();
			interrupt();
		} catch (IOException ioe) {
			System.out.println("Error closing input stream: " + ioe);
		}
	}

	public void stopMe() {
		stopMe = true;
	}

	public void run() {
		while (!stopMe) {
			try {
				client.handle(streamIn.readUTF());
			} catch (IOException ioe) {
				System.out.println("Listening error: " + ioe.getMessage());
				client.close();
				interrupt();
				break;
			}
		}
	}
}