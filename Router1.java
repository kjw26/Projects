import java.io.*;
import java.net.*;

public class Router1 {

	public static void main(String[] args) {
		

        String routingtable = "Source Router | Router0 | Router1 | Router2 | Router3 |\n"
        		            + "   Router1    |    1    |    0    |    1    | unknown |";
        String IP = "128.235.208.201";
        
        System.out.println("Printing server(Router1) routing table....");
        System.out.println("\n\n" + routingtable + "\n\n");
        

        
        try{
        
        	ServerSocket serversocket = new ServerSocket(5001);
        	Socket socket = serversocket.accept();
        	
        	ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        	ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        	
            System.out.println("Waiting for request....\n");

        	for(int i=0; i<2; i++){
        		Message incomingMessage = (Message) input.readObject();
			
			
				if(incomingMessage.type == 1){
				
					System.out.println("Recieved a routing table request " + incomingMessage.getIdentifier() + ". Sending routing table....\n");
					Message response = new Message(routingtable, 1, 2, IP);
					output.writeObject(response);
					System.out.println("Response successfully sent!\n");
				}
			
				else if(incomingMessage.type == 3){
		
					System.out.println("Recieved a routing table update from " + incomingMessage.getIdentifier() + ". Printing routing table....\n");
					System.out.println(incomingMessage.getBody());
				} 
			
				else{
				
					System.out.println("Error! Unrecognized message type");
				}
				
        	}
        	System.out.println("\nConnection closed.");
        	output.close();
        	input.close();
        	socket.close();
        }
        catch (ClassNotFoundException | IOException e) 
        {

			e.printStackTrace();
			
		} 


}
}
