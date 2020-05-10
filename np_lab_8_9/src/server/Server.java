package server;

import java.io.IOException;
import java.net.DatagramSocket;

public class Server {

	int port = 61121;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Server();
	}
	
	public Server() {
		
		System.out.println("Server is running, waiting for new clients ");
		try {
			DatagramSocket ds = new DatagramSocket(port);

			ClientHandler handler = new ClientHandler(ds);

			handler.start();
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
