package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import control.ImagesSender;

public class FormHandler implements Initializable{
	
	@FXML
	private Canvas canvas;
	@FXML
	private Label label;
	
	public void initialize (URL url, ResourceBundle sourceBundle) {
		
	}
	
	@FXML
	private void mouseClickedEvent(MouseEvent mouseEvent) {
		MouseButton mb = mouseEvent.getButton();
		ImagesSender imageSender = new ImagesSender("127.0.0.1", 54321);
		imageSender.startSending();
/*		Thread myThready = new Thread(new Runnable()
        {
            public void run()
            {
            	for (int i = 0; i < 1000000; i++) {
	            	BufferedImage capture = sender.getScreenCapture();
	            	GraphicsContext gc = canvas.getGraphicsContext2D();
	            	Image image = SwingFXUtils.toFXImage(capture, null);
	            	gc.drawImage(image, 0, 0);
	            	try {
						Thread.sleep(5);
					} catch (Exception e) {
						e.printStackTrace();
					}
	            }
            }
	    });
		myThready.start();*/
	}
	
	@FXML 
	private void keyTypedEvent() {
		label.setText("1");
	}
}
