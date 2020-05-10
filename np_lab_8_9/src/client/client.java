package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class client {
	
	Scanner scanner = null;
	int port = 61121;
	DatagramPacket sendPacket;
	DatagramPacket receivePacket;
	DatagramSocket ds;
	InetAddress ip;
	String dns = "netprog1.csit.rmit.edu.au";
	
	public static void main(String[] args) {
		new client();// TODO Auto-generated method stub
		
	}
	public client() {
		try {
			ds = new DatagramSocket();
			ip = InetAddress.getByName(dns);
			scanner = new Scanner(System.in);
			
			System.out.println("Client running");
			
			String greet = "Hello";
			byte[] buf = greet.getBytes();
            sendPacket = new DatagramPacket(buf, buf.length, ip, port);
            ds.send(sendPacket);
			
            // first step to receive server's message: whether to keep sending message
            // to server or just close the connection(if exceed the timeout in server)
            // receive message and respond to client
            receive();
            
            // if system is not closed, then let client keep sending message.
            send();
		}catch (IOException e) {
			e.getMessage();
		}finally {
			scanner.close();
			if(ds != null) {
				ds.close();
				System.out.println("disconnected");
				System.exit(0);
			}
		}
	}
	
	public void receive() throws IOException {
		byte[] message = new byte[1024];
		receivePacket = new DatagramPacket(message, message.length);
			while(true) {
				ds.receive(receivePacket);
				String reply = new String(message,0,message.length);
				if(reply.equals("Alive")) {
					System.out.println("Pls keep sending message or system would shut down within 30 seconds");
				}else if(reply.equals("Disconnected")) {
					System.out.println("ByeBye");
					if(ds != null) {
						ds.close();
						System.exit(0);
					}
				}else {
					System.out.println(reply);
				}
			}
	}
	
	public void send() throws IOException {
		String message = scanner.nextLine();
		byte[] buf = message.getBytes();
		sendPacket = new DatagramPacket(buf,buf.length,ip,port);
		ds.send(sendPacket);
		System.out.println("Pls enter: ");
	}
}
