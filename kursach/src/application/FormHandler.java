package application;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import control.CommandsReceiver;
import control.CommandsSender;
import control.ImagesReceiver;
import control.ImagesSender;

public class FormHandler implements Initializable{
	
	@FXML
	private Canvas canvas;
	@FXML
	private CheckBox isController;
	
	final String IP = "127.0.0.1";
	final int IMAGES_PORT = 11111;
	final int COMMANDS_PORT = 11112;
	
	ImagesReceiver imagesReceiver;
	ImagesSender imagesSender;
	CommandsReceiver commandsReceiver;
	CommandsSender commandsSender;
	
	public void initialize (URL url, ResourceBundle sourceBundle) {
		
	}
	
	@FXML
	private void connectEvent() {
		if (isController.isSelected()) {
			imagesReceiver = new ImagesReceiver(canvas.getGraphicsContext2D(), IMAGES_PORT);
			commandsSender = new CommandsSender(IP, COMMANDS_PORT);
		}
		else {
			imagesSender = new ImagesSender(IP, IMAGES_PORT);
			commandsReceiver = new CommandsReceiver(COMMANDS_PORT);
		}
	}
	
	@FXML
	private void startControl() {
		if (isController.isSelected()) {
			imagesReceiver.startReceive();
			canvas.setDisable(false);
		}
		else {
			imagesSender.startSending();
			commandsReceiver.startReceive();
		}
	}
	
	@FXML
	private void testEvent() {
		imagesSender = new ImagesSender(IP, IMAGES_PORT);
		imagesSender.sendScreenCapture();
	/*	try {
			Robot robot = new Robot();
			Thread.sleep(1000);
			robot.mousePress(InputEvent.BUTTON1_MASK);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	@FXML
	private void mouseClickedEvent(MouseEvent mouseEvent) {
		commandsSender.sendClick(mouseEvent);
	}
	
	@FXML
	private void mouseMoveEvent(MouseEvent mouseEvent) {
		commandsSender.sendCursorMove(mouseEvent);
	}
	
	@FXML 
	private void keyTypedEvent() {
		
	}
}
