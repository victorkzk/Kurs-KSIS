package control;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class CommandsSender {
	static byte LEFT_CLICK = 0;
	static byte RIGHT_CLICK = 1;
	static byte CURSOR_MOVE = 2;
	private Socket socket;
	private DataOutputStream outStream;
	
	public CommandsSender(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			outStream = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendClick() {
		
	}
	
	public void sendCursorPosition() {
		
	}
}