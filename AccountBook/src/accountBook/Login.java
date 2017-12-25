package accountBook;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
 
public class Login extends JFrame{
    public JButton btnLogin;
    public JButton btnJoin;
    public JPasswordField passText;
    public JTextField userText;
	String id;
	public JPanel panel;
	Image img = null;
	File sourceimage;
    public Login() {
    	getContentPane().setBackground(Color.WHITE);
    	Container contentPane = getContentPane();
    	contentPane.setLayout(null);
        // panel
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 361, 406, 206);
        placeLoginPanel(panel);
        
        JPanel imgPanel = new JPanel();
        imgPanel.setBackground(Color.WHITE);
        imgPanel.setBounds(83, 39, 236, 223);
        getContentPane().add(imgPanel);
        
        // add
		try {
			sourceimage = new File(".//img//logo.png");
			img = ImageIO.read(sourceimage);
		} catch (IOException e) {
			e.printStackTrace();
		}
        JLabel imgL = new JLabel(new ImageIcon(img));
        imgPanel.add(imgL);
        
        contentPane.add(imgPanel);
        contentPane.add(panel);
        
        JLabel lblNewLabel = new JLabel("\uB3D9\uC544\uB9AC \uD68C\uACC4 \uC7A5\uBD80");
        lblNewLabel.setBackground(Color.WHITE);
        lblNewLabel.setForeground(new Color(51, 102, 153));
        lblNewLabel.setFont(new Font("나눔고딕", Font.BOLD, 30));
        lblNewLabel.setBounds(99, 293, 220, 35);
        getContentPane().add(lblNewLabel);
        
        // 프레임 setting
        setTitle("동아리회계장부");
        setSize(424, 614);
		Dimension screen =Toolkit.getDefaultToolkit().getScreenSize(); // 모니터화면의 해상도 얻기
		Dimension f1_size = super.getSize(); // 프레임크기
		  
		int left = (screen.width / 2) - (f1_size.width / 2);
		int top = (screen.height / 2) - (f1_size.height /2 );
		
		setLocation(left,  top); // left, top 설정
		setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
   
    
    public void placeLoginPanel(JPanel panel){
        panel.setLayout(null);
        
        JLabel userLabel = new JLabel("아이디");
        userLabel.setForeground(Color.GRAY);
        userLabel.setFont(new Font("나눔고딕", Font.PLAIN, 20));
        userLabel.setBounds(74, 12, 80, 25);
        panel.add(userLabel);
       
        JLabel passLabel = new JLabel("비밀번호");
        passLabel.setForeground(Color.GRAY);
        passLabel.setFont(new Font("나눔고딕", Font.PLAIN, 20));
        passLabel.setBounds(74, 68, 80, 25);
        panel.add(passLabel);
       
        userText = new JTextField(20);
        userText.setFont(new Font("나눔고딕", Font.PLAIN, 15));
        userText.setBounds(168, 12, 160, 25);
        panel.add(userText);
       
        passText = new JPasswordField(20);
        passText.setFont(new Font("나눔고딕", Font.PLAIN, 15));
        passText.setBounds(168, 68, 160, 25);
        panel.add(passText);
        
		try {
			sourceimage = new File(".//img//btnLogin.png");
			img = ImageIO.read(sourceimage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		btnLogin = new JButton(new ImageIcon(img));
		btnLogin.setBorderPainted(false);
		btnLogin.setOpaque(true);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setBounds(44, 144, 137, 41);
        btnLogin.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		DB_Input in = new DB_Input();
        		//pass가 db에 있는지 확인
        		String pass="";
        		String queryString = "SELECT U_PASS FROM user_info WHERE U_ID = '"+userText.getText()+"'";
        		System.out.println(queryString);
        		 pass = in.productSelect("getPass", queryString);
        		 System.out.println(pass);
        		if(passText.getText().equals(pass)/*||("".equals(in.productSelect("getPass",queryString)) != true)*/){
            	    id="";
        			String query = "SELECT ID FROM user_info WHERE U_ID='"+userText.getText()+"'";
            		id = in.productSelect("getId", query);
        			try {
						Calender cl = new Calender(id);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			dispose();
        		}else{
        			JOptionPane.showMessageDialog(null, "잘못된 정보입니다.로그인 실패");
        		}
        	}
        });
        panel.add(btnLogin);
		try {
			sourceimage = new File(".//img//btnJoin.png");
			img = ImageIO.read(sourceimage);
		} catch (IOException e) {
			e.printStackTrace();
		}
        btnJoin = new JButton(new ImageIcon(img));
        btnJoin.setBorderPainted(false);
        btnJoin.setOpaque(true);
        btnJoin.setContentAreaFilled(false);
        btnJoin.setBounds(231, 144, 137, 41);
        btnJoin.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		new Join(id, 0,"","","","");
        	}
        });
        panel.add(btnJoin);
    }
}
