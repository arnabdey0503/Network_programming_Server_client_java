import java.io.*;
import java.net.*;


public class Client1 {

	Socket sock;
	OutputStream sendStream;
	InputStream recvStream;
	String request;
	String response;
	
	Client1(String server,int port) throws IOException, UnknownHostException
	{
		sock=new Socket(server, port);
		sendStream=sock.getOutputStream();
		recvStream=sock.getInputStream();
	}
	
	//void makeRequest(){
	//	int a=10;
	//	request=a+"";
		
	//}
	
	void sendRequest(){
		try{
			byte [] sendBuff =new byte[request.length()];
			sendBuff=request.getBytes();
			sendStream.write(sendBuff,0,sendBuff.length);
			
		}
		catch(IOException ex){
			System.out.println("IOException in sendRequest");
		}
	}
	
	void getResponse(){
		try{
			int dataSize;
			while((dataSize=recvStream.available())==0);
			byte[] recvBuff=new byte[dataSize];
			recvStream.read(recvBuff,0,dataSize);
			response=new String(recvBuff,0,dataSize);
			System.out.println("Array of number="+response);
			
		}catch(IOException ex){
			System.out.println("IOException in getResponse");
		}
	}
	
	
	void useResponse(){
		 String[] stringArray = response.split(",");
	        int[] intArray = new int[stringArray.length];
	        for (int i = 0; i < stringArray.length; i++) {
	           String numberAsString = stringArray[i];
	           intArray[i] = Integer.parseInt(numberAsString);
	        }
	        int temp=0;
	        for(int i=0;i<intArray.length;i++){
	        	if(intArray[i]>=temp){
	        		temp=intArray[i];
	        	}
	        }
	        request=temp+"";
	}
	
	void close(){
		try{
			sendStream.close();
			recvStream.close();
			sock.close();	
		}
		catch(IOException ex){
			System.out.println("IOException in close");
		}
	}
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		final int servPort=3333;
		final String servName="LocalHost";
		Client1 client=new Client1(servName,servPort);
		client.getResponse();
		client.useResponse();
		//client.makeRequest();
		client.sendRequest();
		
		client.close();
	}

}
