package control;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ImagesSender {
	
	public BufferedImage getScreenCapture() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		try {
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(screenSize));
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
}
