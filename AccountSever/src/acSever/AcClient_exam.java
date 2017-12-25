package acSever;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;

public class AcClient_exam {

	public static void main(String[] args) throws IOException {
		InetAddress ia = null;
		try { 
			ia = InetAddress.getByName("127.0.0.1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Socket so = new Socket(ia, 5000);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new InputStreamReader(so.getInputStream()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
		
		while(true){
			System.out.println(">>>서버에 보내는 중<<<");
			String send_msg = in.readLine();
			bw.write(send_msg+"\n");
			bw.flush();
		}
	}

}
