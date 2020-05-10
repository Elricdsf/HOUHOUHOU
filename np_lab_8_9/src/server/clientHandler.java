package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class clientHandler extends Thread{
	DatagramSocket ds = null;
	
	DatagramPacket dp = null;
	
	int timeOut = 20000;
	
	int closeTime = 10000;
	
	boolean isWarning = true;
	
	
	public clientHandler(DatagramSocket ds, DatagramPacket dp) {
		this.ds = ds;
		this.dp = dp;
	}
	
	public void run() {
		while(true) {
			try {
				ds.setSoTimeout(timeOut);
				ds.receive(dp);
			} catch(IOException e) {
				if(isWarning) {
					//no warning anymore
					isWarning = false;
					String warning = "System will be shut down within 10 seconds, if no further information exchanged";
					try {
						ds.setSoTimeout(closeTime);
						System.out.println(warning);
						send(warning);
						continue;
					}catch(SocketException se) {
						se.getMessage();
					}	
				}else {
					String closed = "Disconnected";
					System.out.println(closed);
					send(closed);
					if(ds != null) {
						ds.close();
						System.exit(0);
					}
				}
			}
			System.out.println("Client's thread " + dp.getPort() + " is still alive");
			System.out.println(dp.getPort() + " Client "+dp.getAddress()+" says: " + new String(dp.getData(),0,dp.getLength()));
			// refresh flag
			isWarning = true;
			send("Alive");
		}
		
	}
	
	public void send(String message) {
		try {
			ds.send(dp);
		} catch (IOException e) {
			e.getMessage();
		}
	}
	
}
