import java.io.*;
import java.net.*;

public class MaxClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
             System.out.println("Usage: java MaxClient <hostname>");
             return;
        }

            // get a datagram socket
        DatagramSocket socket = new DatagramSocket();

            // send request
        byte[] buf = new byte[256];
      
        InetAddress address =InetAddress.getByName(args[0]);
        DatagramPacket packet = new DatagramPacket(buf, buf.length,address , 4435);
        socket.send(packet);
    
            // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

	    // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Received set of number: " + received);
        //
        String[] stringArray = received.split(",");
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
        String re=Integer.toString(temp);
        buf=re.getBytes();
        
        String se = new String(buf);
        System.out.println("Max number: " + se);

        packet = new DatagramPacket(buf, buf.length,address , 4445);
        socket.send(packet);
    
        socket.close();
    }
}
