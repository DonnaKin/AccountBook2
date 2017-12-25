package accountBook;
import java.io.File;
import java.io.IOException; 
import java.net.URI; 
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage; 

public class FBShare { 
	public FBShare(){
		create();
		capture();
		try { 
			Desktop.getDesktop().browse(new URI("https://accountbook-d2dc0.firebaseapp.com")); 
		} catch (IOException e) {
			e.printStackTrace(); 
		} catch (URISyntaxException e) {
			e.printStackTrace(); 
		} 
	}
	public void capture() {
		try {
			// 전체 화면 Capture
			BufferedImage screencapture = new Robot()
					.createScreenCapture(new Rectangle(Toolkit
							.getDefaultToolkit().getScreenSize()));

			// JPEG 저장.
			File file = new File("D:/Files/javaAccountBook/AccountBook/firebase-AccountSolution/public/capture/screencapture.jpg");
			ImageIO.write(screencapture, "jpg", file);
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void create() {

	File theDir = new File("D:/Files/javaAccountBook/AccountBook/firebase-AccountSolution/public/capture");
	
	//해당 디렉토리가 없을경우 디렉토리를 생성한다.
	if (!theDir.exists()) {
	 System.out.println("creating directory: " + "capture");
	 boolean result = false;
	
	 try{
	     theDir.mkdir();
	     result = true;
	 } 
	 catch(SecurityException se){
	     //handle it
	 }        
	 if(result) {    
	     System.out.println("DIR created");  
	 }
	}
	}
}


