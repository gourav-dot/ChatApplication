package chatApplication;
import java.io.*;
import java.net.*;

public class Server {
	ServerSocket server;
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	public Server() {
		try {
			server=new ServerSocket(7777);
			System.out.println("Server is ready to accept connection");
			System.out.println("Waiting...........");
			socket=server.accept();	
			
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream());
			startReading();
			startWriting();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

	public void startWriting() {
		//thread-get data from user
		Runnable r2=()->{
			System.out.println("writer started.....");
			try {
				while(!socket.isClosed()) {
				
					BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
					String content=br1.readLine();
					out.print(content);
					out.flush();
					if(content.equals("exit")) {
						socket.close();
						break; 
					}
				}}catch(Exception e) {
					e.printStackTrace();
				}
			
			
		};
		new Thread(r2).start();
	}



	public void startReading(){
		//thread -read data from user
		Runnable r1=()->{
			System.out.println("reder started.....");
			try {
				while(true) {
				  String msg = null;
				
					msg = br.readLine();
				 
				    if(msg.equals("exit")){
					  System.out.println("client terminated");
					 socket.close();
					  break;
				      }
				    System.out.println("Client:"+msg);
				  
				} }catch (IOException e) {
					// TODO Auto-generated catch block
					  e.printStackTrace();
				   } 
			
		};
		new Thread(r1).start();
		
	}



	public static void main(String args[]) {
		System.out.println("server started...............");
		new Server();
	}
}
