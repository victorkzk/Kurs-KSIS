package control;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

public class ImagesSender {
	
	private InetAddress ipAddress;
	private int port;
	private DatagramSocket datagramSocket;
	private Timer timer = new Timer();
	
	public ImagesSender(String ip, int port) {
		try {
			ipAddress = InetAddress.getByName(ip);
			datagramSocket = new DatagramSocket();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.port = port;
	}
	
	public void startSending() {
		final long SENDING_FREQUENCY = 100;
		TimerTask task = new TimerTask() {
			public void run() {
				sendScreenCapture();
			}
		};
		timer.schedule(task, SENDING_FREQUENCY);
	}
	
	public void stopSending() {
		timer.cancel();
		timer.purge();
	}
	
	public void sendScreenCapture() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(getScreenCapture(), "jpg", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			byte[] extImageArray = new byte[100000];
			System.arraycopy(imageInByte, 0, extImageArray, 0, imageInByte.length);
			baos.close();
			DatagramPacket datagramPacket = new DatagramPacket(extImageArray, extImageArray.length, ipAddress, port);
			datagramSocket.send(datagramPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private BufferedImage getScreenCapture() {
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
