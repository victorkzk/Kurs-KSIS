package control;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class ImagesReceiver {
	
	GraphicsContext gc;
	
	public ImagesReceiver(GraphicsContext gc) {
		this.gc = gc;
	}
	
	public void startReceive() {
		Thread receiveThready = new Thread(new Runnable()
        {
            public void run()
            {
                receiveDatagram();
            }
        });
        receiveThready.start();
	}
	
	private void receiveDatagram() {
		try {
			byte[] imageInByte = new byte[100000];
			DatagramPacket datagramPacket = new DatagramPacket(imageInByte, imageInByte.length);
			DatagramSocket datagramSocket = new DatagramSocket();
			while (true) {
				datagramSocket.receive(datagramPacket);
				showImage(datagramPacket.getData());
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showImage(byte[] imageInByte) {
		try {
			InputStream in = new ByteArrayInputStream(imageInByte);
			BufferedImage bufferedImage = ImageIO.read(in);
        	Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        	gc.drawImage(image, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
