package control;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Iterator;

public class ImagesSender {

	private InetAddress ipAddress;
	private int port;
	private DatagramSocket datagramSocket;
	//private Timer timer = new Timer();
	
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
		final long SENDING_FREQUENCY = 20;
		
		Thread sendThread = new Thread(new Runnable()
        {
            public void run()
            {
                while (true) {
                	sendScreenCapture();
                	try {
                		Thread.sleep(SENDING_FREQUENCY);
                	} catch (InterruptedException e) {
                		e.printStackTrace();
                	}
                }
            }
        });
        sendThread.start();
        
		/*TimerTask task = new TimerTask() {
			public void run() {
				sendScreenCapture();
			}
		};
		timer.schedule(task, SENDING_FREQUENCY);*/
	}
	
/*public void stopSending() {
		timer.cancel();
		timer.purge();
	}*/
	
	public void sendScreenCapture() {
		try {
			byte[] imageInByte = getScreenCaptureInByteArray();
			byte[] extImageArray = new byte[60000];
			System.arraycopy(imageInByte, 0, extImageArray, 0, imageInByte.length);
			DatagramPacket datagramPacket = new DatagramPacket(extImageArray, extImageArray.length, ipAddress, port);
			datagramSocket.send(datagramPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] getScreenCaptureInByteArray() {
		final float quality = 0.1f;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		try {
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(screenSize));
			Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg");
			ImageWriter writer = (ImageWriter) writers.next();
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(32768);
			ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
			writer.setOutput(ios);
			writer.write(null, new IIOImage(image, null, null), param);
			ios.flush();
			return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
}
