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
	
	private GraphicsContext gc;
	private DatagramSocket datagramSocket;
	
	public ImagesReceiver(GraphicsContext gc, int port) {
		this.gc = gc;
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
        	gc.drawImage(image, 0, 0, ImagesSender.IMG_WIDTH, ImagesSender.IMG_HEIGHT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
