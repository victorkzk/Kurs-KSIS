package control;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

public class ImagesReceiver {
	
	private Canvas canvas;
	private DatagramSocket datagramSocket;
	
	public ImagesReceiver(Canvas canvas, int port) {
		this.canvas = canvas;
		try {
			datagramSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void startReceive() {
		Thread receiveThread = new Thread(new Runnable()
        {
            public void run()
            {
                receiveDatagram();
            }
        });
        receiveThread.start();
	}
	
	private void receiveDatagram() {
		try {
			byte[] imageInByte = new byte[60000];
			DatagramPacket datagramPacket = new DatagramPacket(imageInByte, imageInByte.length);
			while (true) {
				datagramSocket.receive(datagramPacket);
				showImage(datagramPacket.getData());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void showImage(byte[] imageInByte) {
		try {
			InputStream in = new ByteArrayInputStream(imageInByte);
			BufferedImage bufferedImage = ImageIO.read(in);
        	Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        	canvas.getGraphicsContext2D().drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
