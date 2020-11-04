import java.io.*;
import java.util.Scanner;

import common.*;

public class ServerConsole implements ChatIF {
	Scanner fromConsole;
	EchoServer server;
	final public static int DEFAULT_PORT = 5555;
	
	public ServerConsole(int port){
		
		server = new EchoServer(port);
		fromConsole = new Scanner(System.in);
		try {
            server.listen();
        } catch (IOException e) {
            System.out.println("ERROR - Could not listen for clients!");
        }
	}
	
	public void accept(){
		try{
			 
			String message;

			while (true){
				message = fromConsole.nextLine();
				server.handleMessageFromServerUI(message);
			}
		} 
		catch (Exception ex) 
		{
			System.out.println
			("Unexpected error while reading from console!");
		}
	}
	
	public void display(String message){
		System.out.println("SERVER MSG>" + message);
    }
	 public static void main(String[] args) {
	        int port = 0; //Port to listen on

	        try {
	            port = Integer.parseInt(args[0]); //Get port from command line
	        } catch (ArrayIndexOutOfBoundsException e) {
	            port = DEFAULT_PORT; //Set port to 5555
	        }

	        ServerConsole serv = new ServerConsole(port);
	        serv.accept();
	    }
	
}
