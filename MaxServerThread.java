import java.io.*;
import java.net.*;
import java.util.*;

public class MaxServerThread extends Thread {

    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean nextSetNumber = true;
    
    MaxServer maxServer=new MaxServer();

    public MaxServerThread() throws IOException {
	this("MaxServer Thread");
    }

    public MaxServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(4435);

        try {
            in = new BufferedReader(new FileReader("one-liners.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not open quote file. Serving time instead.");
        }
    }

    public void run() {

        while (nextSetNumber) {
            try {
                byte[] buf = new byte[256];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                // figure out response
                String dString = null;
                if (in == null)
                    dString = new Date().toString();
                else
                    dString = getNextSet();

                buf = dString.getBytes();

		// send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
                byte[] buf1=new byte[256];
                packet= new DatagramPacket(buf1, buf1.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("receive Maximum Number:"+received);
                int result=Integer.parseInt(received);
                
                if(maxServer.getA()==0){
                	maxServer.setA(result);
                }
                else{
                	maxServer.setB(result);
                }
                
                if(maxServer.getA()>maxServer.getB()){
                	System.out.println("Maximum till now:"+maxServer.getA());
                }
                else{
                	System.out.println("Maximum till now:"+maxServer.getB());
                }
                
                
            } catch (IOException e) {
                e.printStackTrace();
		nextSetNumber = false;
            }
        }
        socket.close();
    }

    protected String getNextSet() {
        String returnValue = null;
        try {
            if ((returnValue = in.readLine()) == null) {
                in.close();
		nextSetNumber = false;
                returnValue = "No more Set of Numbers. Goodbye.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        }
        return returnValue;
    }
}


