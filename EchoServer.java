// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
  
  public void handleMessageFromServerUI(String message){
	  if(message.charAt(0)=='#') {
		  try {
			  String[] splittedMessage = message.split(" ", 2);
			  switch (splittedMessage[0]){ 
			  	  case "#quit" : System.exit(0);
			  	  	break;	
				  case "#stop" : stopListening();
				  	break;
				  case "#close": close();
				  	break;
				  case "#setport":
				  	if(!isListening()&& this.getNumberOfClients() < 1) {
				  		setPort(Integer.parseInt(splittedMessage[1].replace("<", "").replace(">", "")));

				  	}
					else{
						throw new IOException("Please close the server before setting port");
					}
				  	break;
				  case "#start":
					if(!isListening()) {
						listen();
				  	}
					else{
						throw new IOException("The server is currently listening");
					}
					break;
				  case "#getport":
					  System.out.println("Port: "+ getPort());
					  break;
				  default:
					  throw new IOException("Invalid Command"); 
				  	
			  }
		  }
		  catch(IOException e) {
			  System.out.println(e);
		  }
	  }
	  else {
		  sendToAllClients("SERVER MSG>" + message);
		  System.out.println("> "+message);
	  }

  }
  	
	  

    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  protected void clientConnected(ConnectionToClient client) {
	  System.out.println("A Client has connected");
  }
  
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  System.out.println("A Client has disconnected.");
  }
  
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
	  clientDisconnected(client);
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
