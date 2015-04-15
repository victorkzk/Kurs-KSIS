package application;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import control.ImagesSender;

public class FormHandler implements Initializable{
	
	@FXML
	private Canvas canvas;
	
	public void initialize (URL url, ResourceBundle sourceBundle) {
		canvas.addEventFilter(MouseEvent.M, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				mouseEvent.
			}
		});
	}
	
	@FXML
	private void updScreenCaptures() {
		ImagesSender sender = new ImagesSender();
		Thread myThready = new Thread(new Runnable()
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
		myThready.start();
	}
}
