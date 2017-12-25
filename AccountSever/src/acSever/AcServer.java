package acSever;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class AcServer {
	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(5000);
		System.out.println("서버 대기중");
		
		Socket so = ss.accept();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader br = new BufferedReader(new InputStreamReader(so.getInputStream()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));
		String get_msg;
		
		while(true){
			System.out.println(">>>클라이언트를 기다리는 중<<<");
			get_msg = br.readLine();
			System.out.println("보낸 주소 : "+so.getInetAddress());
			System.out.println("보낸 내용 : "+get_msg);
		}
	}

}
