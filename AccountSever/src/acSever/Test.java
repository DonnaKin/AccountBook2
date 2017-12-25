package acSever;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Test {

	public static void main(String[] args) throws UnknownHostException {
		InetAddress ia = InetAddress.getByName("127.0.0.1");
		System.out.println(ia.getHostAddress());
		System.out.println(ia.getHostName());
	}
}
