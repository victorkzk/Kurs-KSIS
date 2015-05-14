package control;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class CommandsSender {
	public final static byte CLICK = 0;
	public final static byte MOVE = 1;
	public final static byte LEFT_CLICK = 0;
	public final static byte RIGHT_CLICK = 1;
	
	private Socket socket;
	private DataOutputStream outStream;
	private Canvas canvas;

	public CommandsSender(String ip, int port, Canvas canvas) {
		try {
			socket = new Socket(ip, port);
			outStream = new DataOutputStream(socket.getOutputStream());
			this.canvas = canvas;
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
			final double PROPERTY = 1366.0/canvas.getWidth();
            System.out.println(canvas.getWidth() + " " + canvas.getHeight());
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