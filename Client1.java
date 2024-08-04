package master;

	import java.io.*;
	import java.net.*;

	public class Client1 {
	    public static void main(String[] args) throws IOException {
	        Socket clientSocket = new Socket("localhost", 1234);
	        DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
	        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	        while (true) {
	            System.out.print("Enter your message: ");
	            String message = br.readLine();
	            dos.writeUTF(message);
	        }
	    }
	}


