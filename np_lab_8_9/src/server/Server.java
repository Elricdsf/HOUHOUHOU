package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {

	byte[] buf = new byte[1024];
	int port = 61121;
	DatagramSocket ds;
	DatagramPacket dp;
	InetAddress ip;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Server();
	}
	
	public Server() {
		
		System.out.println("Server is running, waiting for new clients ");
		try {
			
			InetAddress ip = InetAddress.getByName("localhost");
			
			while(true){
			
				DatagramSocket ds = new DatagramSocket();
			
				DatagramPacket dp = new DatagramPacket(buf, 0, buf.length, ip, port);
				
				clientHandler handler = new clientHandler(ds,dp);
								
				Thread thread = new Thread(handler);
				
				thread.start();
			}
		} catch (IOException e) {
				e.getMessage();
		} 
//		finally {
//			if(ds != null) {
//				ds.close();
//			}
//			System.exit(0);
//		}
		} 
	
}
