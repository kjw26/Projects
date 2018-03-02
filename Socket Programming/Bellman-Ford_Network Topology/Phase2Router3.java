import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class Phase2Router3 {

	public static void main(String[] args) throws UnknownHostException {
		

        int[][] routingtable = {{0,35,35,35},
        						{35,0,35,35},
        						{35,35,0,35},
        						{7,35,2,0} };
        int[][] goal = 	{{0, 1, 2, 4},
						{1, 0, 1, 3},
						{2, 1, 0, 2},
						{4, 3, 2, 0}};
        
        int counter =0;
        
        String myIP = "128.235.209.205";
        String IP1 = "128.235.208.225";
        String IP2 = "128.235.209.204";
        String IP0 = "128.235.208.201";
        int[][] temparray = new int[4][4];
        
        InetAddress address1 = InetAddress.getByName(IP1);
        InetAddress address2 = InetAddress.getByName(IP2);
        InetAddress address0 = InetAddress.getByName(IP0);
        
        System.out.println("Printing routing table....\n\n");
        
        for(int[] arr : routingtable){
        	System.out.println(Arrays.toString(arr));
        }
        
        try{
        
        	System.out.println("Opening socket");
        	DatagramSocket socket = new DatagramSocket(6561);

   	
            System.out.println("Pause here and enter any string when all four routers are running. Inital routing tables should have been printed \n");
            
            Scanner scan = new Scanner(System.in);
            
            String answer;
            answer = scan.nextLine();
            
            if(answer == " ")
            {
            	System.out.println("Non valid answer...terminating");
            	System.exit(0);
            }
            
            System.out.println("Sending packet to router1");
        	Send(socket, address1, 6561 , routingtable, myIP);
        	System.out.println("Sending packet to router2");
        	Send(socket, address2, 6561 , routingtable, myIP);
        	System.out.println("Sending packet to router0");
        	Send(socket, address0, 6561 , routingtable, myIP);
            
        	while(!Arrays.deepEquals(routingtable, goal)){
    				
        			byte[] data = new byte[1024];
        			DatagramPacket receivePacket = new DatagramPacket(data, data.length);
        			socket.receive(receivePacket);
        			Message2 incmsg = (Message2) deserialize(receivePacket.getData());
        			
    				int arr[][] = incmsg.getBody();

    				if(incmsg.getIdentifier().compareTo(IP1) == 0){
    					System.out.println("recieved message from router1\n");
        		        for(int[] list : arr){
        		        	System.out.println(Arrays.toString(list));
        		        }
    					for(int j=0; j<4; j++){
    						routingtable[1][j] = arr[1][j];
    					}
    				}
    			
	    			if(incmsg.getIdentifier().compareTo(IP2) == 0){
	    				System.out.println("recieved message from router2\n");
	    		        for(int[] list : arr){
	    		        	System.out.println(Arrays.toString(list));
	    		        }
	    				for(int j=0; j<4; j++){
	    					routingtable[2][j] = arr[2][j];
	    				}
	    			}	
	    			
	    			if(incmsg.getIdentifier().compareTo(IP0) == 0){
	    				System.out.println("recieved message from router0\n");
	    		        for(int[] list : arr){
	    		        	System.out.println(Arrays.toString(list));
	    		        }
	    				for(int j=0; j<4; j++){
	    					routingtable[0][j] = arr[0][j];
	    				}
	    			}
	    			System.out.println("Routing table after merging with most recently received table");
            		for(int[] arrs : routingtable){
            			System.out.println(Arrays.toString(arrs));
            		}
            		temparray = routingtable;
	            	routingtable = BF(routingtable);
	            	System.out.println("Routing table AFTER BellmanFord");
            		for(int[] arrs : routingtable){
            			System.out.println(Arrays.toString(arrs));

            		}  	
            		

                    System.out.println("Sending packet to router1");
                	Send(socket, address1, 6561 , routingtable, myIP);
                	System.out.println("Sending packet to router2");
                	Send(socket, address2, 6561 , routingtable, myIP);
                	System.out.println("Sending packet to router0");
                	Send(socket, address0, 6561 , routingtable, myIP);

	           }  
        	System.out.println("Success!");

        }
        catch (ClassNotFoundException | IOException e) 
        {

			e.printStackTrace();
			
		} 


	}
	
	static int[][] BF(int[][] arr){
		
		int[] minarr = new int[4];
		int[][] arr1 = new int[4][4];
		arr1=arr;
	
		for(int i=0; i<4; i++){
			minarr[0] = arr1[3][0] + arr1[0][i];
			minarr[1] = arr1[3][1] + arr1[1][i];
			minarr[2] = arr1[3][2] + arr1[2][i];
			minarr[3] = arr1[3][3] + arr1[3][i];
			
			int min = min(minarr);
			
			if(arr1[3][i] > min)
			{
				arr[3][i] =min;
			}
		}
		
		return arr1;
	}
	
	static int min(int[] arr){
		
		int min = arr[0];
		
		for(int i=0; i<4;i++){
			
			if(arr[i] < min)
				min = arr[i];
		}
		
		return min;
	}
	
	public static void Send (DatagramSocket socket, InetAddress addr, int port, int[][] arr, String IP) throws IOException{
		Message2 msg = new Message2 (arr, 1, 3 , IP);
		
		byte[] serializedMsg;
		
		serializedMsg = serialize(msg);	
		
		DatagramPacket sendPacket = new DatagramPacket (serializedMsg, serializedMsg.length, addr, port);
		
		socket.send(sendPacket);
	}
	
	
	//the code below based upon the stack overflow reference in the references section.
	
	public static byte[] serialize(Object obj) throws IOException {
		
		try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
			
			try(ObjectOutputStream o = new ObjectOutputStream(b)){
				
				o.writeObject(obj);
			}
			
			return b.toByteArray();
		}
	}

	public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		
		try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
			
			try(ObjectInputStream o = new ObjectInputStream(b)){
				
				return o.readObject();
			}
		}
	}

		
}