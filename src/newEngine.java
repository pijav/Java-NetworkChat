import java.io.*;
import java.net.*;
import java.net.UnknownHostException;

public class newEngine {
	ChatGUI parent = new ChatGUI();	
	
	private DataOutputStream outbound = null;
	private Socket clientSocket = null;
	private ChatClientThread client    = null;
		
	public void connect(String serverName, int serverPort){
		System.out.println("Establishing connection. Please wait ...");
	     try{
	    	 clientSocket = new Socket(serverName, serverPort);
	         System.out.println("Connected: " + clientSocket);
	         open(); 
	         
	      }
	      catch(UnknownHostException uhe){  
	    	  System.out.println("Host unknown: " + uhe.getMessage());
	      }
	      catch(IOException ioe){  
	    	  System.out.println("Unexpected exception: " + ioe.getMessage());
	      }   
	}
	
	public void open() throws IOException{
		try{
		outbound = new DataOutputStream(clientSocket.getOutputStream());
        client = new ChatClientThread(this, clientSocket);
		System.out.println("Stream is opened");
		
		}
		catch(IOException ioe){  
			System.out.println("Error opening output stream: " + ioe);
		} 	 
	}
	
	public void send(){
	    	try{
	    		outbound.writeUTF(parent.getMessage());
	    		outbound.flush();
	    		parent.setMessage("");
	    	}
	    	catch(IOException ioe){ 
	    		System.out.println("Sending error: " + ioe.getMessage());
	        }
	    }
	
    public void handle(String msg) throws IOException{  
    	if (".bye".equals(msg)){  
    		System.out.println("Got BYE message, closing...");  
    		close(); 
    	}
        else{
        	parent.addToDisplay(msg); 
        }
    }
    
	public void close(){  
    	try{
    		if (outbound != null)  outbound.close();
    		if (clientSocket    != null)  clientSocket.close();
    	}
    	catch(IOException ioe){  
    		System.out.println("Error closing ...");
    	}
    	finally {
    		client.close();
    		System.out.println("Closing thread...");    		
    		client.stopMe();
    		System.out.println("Thread closed");   
    	}
    }
	public String transferMessageToGUI(String msg){
		return msg;
	}
}
