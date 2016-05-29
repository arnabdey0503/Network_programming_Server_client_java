import java.io.*;

public class MaxServer {
	public  int a=0;
	public  int b=0;
	
    public static void main(String[] args) throws IOException {
        new MaxServerThread().start();
       
    }

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public void setA(int a) {
		this.a = a;
	}

	public void setB(int b) {
		this.b = b;
	}
}