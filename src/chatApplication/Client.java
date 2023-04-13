package chatApplication;
import java.io.*;
import java.net.*;

public class Client {
	
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
	
	public Client() {
		try {
			System.out.println("request to server");
			socket=new Socket("127.0.0.1",7777);
			System.out.println("conected to server");
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
				}System.out.println("connection is closed");
			}catch(Exception e) {
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
					  System.out.println("server terminated");
					  socket.close();
					  break;
				      }
				    System.out.println("Server:"+msg);
				  
				}}catch (IOException e) {
					
					 // e.printStackTrace();
					System.out.println("connection is closed");
				   } 
			
		};
		new Thread(r1).start();
	}
	
	






public static void main(String args[]) {
	System.out.println("this is client....");
	new Client();
	  
	}
}
