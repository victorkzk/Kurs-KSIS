package control;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.scene.input.MouseEvent;

public class CommandsSender {
	public final static byte CLICK = 0;
	public final static byte MOVE = 1;
	public final static byte LEFT_CLICK = 0;
	public final static byte RIGHT_CLICK = 1;
	
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
	
	public void sendClick(MouseEvent mouseEvent) {
		try {
			outStream.writeByte(CLICK);
			outStream.writeByte(mouseEvent.getButton().ordinal());
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendCursorMove(MouseEvent mouseEvent) {
		try {
			final double PROPERTY = 768.0/400;
			outStream.writeByte(MOVE);
			int displayX = (int)(mouseEvent.getSceneX() * PROPERTY);
			int displayY = (int)(mouseEvent.getSceneY() * PROPERTY);
			outStream.writeInt(displayX);
			outStream.writeInt(displayY);
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}