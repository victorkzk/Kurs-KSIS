package control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandsReceiver {
	
	private Socket socket;
	
	public CommandsReceiver(int port) {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
