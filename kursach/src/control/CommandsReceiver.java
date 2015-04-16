package control;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandsReceiver {
	
	private Socket socket;
	DataInputStream inStream;
	
	public CommandsReceiver(int port) {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			socket = serverSocket.accept();
			inStream = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startReceive() {
		Thread receiveThread = new Thread(new Runnable()
        {
            public void run()
            {
                receiveCommands();
            }
        });
        receiveThread.start();
	}
	
	private void receiveCommands() {
		try {
			while (true) {
				byte command = inStream.readByte();
				handleCommand(command);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handleCommand(byte command) {
		switch (command) {
			case CommandsSender.CLICK : doClick();
			break;
			case CommandsSender.MOVE : doMove();
		}
	}
	
	private void doClick() {
		try {
			byte buttonID = inStream.readByte();
			Robot robot = new Robot();
			if (buttonID == 1) {
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			}
			if (buttonID == 3) {
				robot.mousePress(InputEvent.BUTTON3_MASK);
				robot.mouseRelease(InputEvent.BUTTON3_MASK);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	private void doMove() {
		try {
			int cursorX = inStream.readInt();
			int cursorY = inStream.readInt();
			Robot robot = new Robot();
			robot.mouseMove(cursorX, cursorY);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
