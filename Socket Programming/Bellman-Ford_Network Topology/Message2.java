public class Message2 implements java.io.Serializable {

	int[][] table;
	int version;
	int type;
	String identifier;
		
	public Message2(int[][] msg, int msgversion, int msgtype, String msgident) {
		
		table = msg;
		version = msgversion;
		type = msgtype;
		identifier = msgident;			
	}
	
	public Message2() {
		
		table = new int[4][4];
		version = 0;
		type = 0;
		identifier = "";			
	}
	
	public int getType(){ //returns an integer array of header information 
		
		return type;

	}
	
	public int getVersion(){ //returns an integer array of header information 
		
		return version;

	}
	
	public String getIdentifier(){
		
		return identifier;
	}
	
	public int[][] getBody(){
		
		return table;
	}
	
	public void printMessage(){
		
		System.out.println("Version:" + version + "\nType:" + type + "\nIdentifier:" + identifier + "\nRouting Table:\n\n" + table);
	}
}
