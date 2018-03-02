import java.io.*; 
import  java.net.*;

public class Router0 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		 * This code was written with reference to a java socket programming tutorial and other references listed below
		 * http://www.javaworld.com/article/2077322/core-java/core-java-sockets-programming-in-java-a-tutorial.html
		 * https://docs.oracle.com/javase/7/docs/api/java/io/ObjectOutputStream.html
		 * https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html
		 * https://docs.oracle.com/javase/7/docs/api/java/io/ObjectInputStream.html
		 * https://systembash.com/a-simple-java-tcp-server-and-tcp-client/
		 */
 
        ObjectOutputStream output;
        ObjectInputStream input;
        String IP = "128.235.208.225";
        String routingtable = "Source Router | Router0 | Router1 | Router2 | Router3 |\n"
        		            + "   Router0    |    0    |    1    |    3    |    7    |";
        
        System.out.println("Printing client(Router0) routing table....");
        System.out.println("\n\n" + routingtable + "\n\n");
        
        System.out.println("Connecting to server(Router1)....");
        
        try{
        
        	Socket clientsocket = new Socket("128.235.208.201", 5001);
        	
        	output = new ObjectOutputStream(clientsocket.getOutputStream());
        	input = new ObjectInputStream(clientsocket.getInputStream());

        
        	System.out.println("Requesting routing table from server(Router1) using [IP: 128.235.208.201; Port: 5001;]\n");
        
        	Message request = new Message("", 1, 1, IP);

        	output.writeObject(request);
        	
        	System.out.println("Request sent successfully!\n");

        	Message response;
        
			response = (Message) input.readObject();
			System.out.println("Repsonse from Server below..\n");	
			response.printMessage();

       
        
			System.out.println("\nSending Routing Table to Server....");
        
			Message update = new Message(routingtable, 1, 3, IP);
        

        	output.writeObject(update);

        

        	System.out.println("\nThe update has been sent!");
        

      
        	output.close();
        	input.close();
        }
        
        catch(IOException | ClassNotFoundException e)
        {
        	System.out.println("There has been an error with the connection");
        }
       
	}

}
