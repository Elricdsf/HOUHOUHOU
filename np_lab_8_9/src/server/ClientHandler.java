package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientHandler extends Thread{
	DatagramSocket ds = null;
	int port = 61121;

	byte[] buf = new byte[1024];

	int timeOut = 20000;
	int closeTime = 10000;
	
	boolean isWarning = true;
	
	
	public ClientHandler(DatagramSocket ds) {
		this.ds = ds;
		try {
			this.ds.setSoTimeout(timeOut);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			DatagramPacket dp = new DatagramPacket(buf, 0, buf.length);
			try {
				ds.receive(dp);
				System.out.println("Client's thread " + dp.getPort() + " is still alive");
				System.out.println(dp.getPort() + " Client "+dp.getAddress()+" says: " + new String(dp.getData(),0,dp.getLength()));
				send("Alive", dp.getAddress(), dp.getPort());
				// refresh flag
				isWarning = true;
			} catch(IOException e) {
				if(isWarning) {
					//no warning anymore
					isWarning = false;
					String warning = "System will be shut down within 10 seconds, if no further information exchanged";
					System.out.println(warning);
				} else {
					String closed = "Disconnected";
					System.out.println(closed);
					if(ds != null) {
						send("Disconnected", dp.getAddress(), dp.getPort());
						ds.close();
					}
					System.exit(0);
				}
			}
		}
		
	}

	public void send(String message, InetAddress address, Integer port) {
		try {
			byte[] buf = message.getBytes();
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
			ds.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
