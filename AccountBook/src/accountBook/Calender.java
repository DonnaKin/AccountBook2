package accountBook;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;

class CalenderDataMenager{ // 6*7�迭�� ��Ÿ�� �޷� ���� ���ϴ� class
	static final int CAL_WIDTH = 7;
	final static int CAL_HEIGHT = 6;
	int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];
	int calYear;
	int calMonth;
	int calDayOfMon;
	final int calLastDateOfMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};
	int calLastDate;
	Calendar today = Calendar.getInstance();
	Calendar cal;
	String getDate;
	DB_Input ip = new DB_Input();
	
	public CalenderDataMenager(){ 
		setToday(); 
	}
	public void setToday(){
		calYear = today.get(Calendar.YEAR); 
		calMonth = today.get(Calendar.MONTH);
		calDayOfMon = today.get(Calendar.DAY_OF_MONTH);
		makeCalData(today);
	}
	private void makeCalData(Calendar cal){
		// 1���� ��ġ�� ������ ��¥�� ���� 
		int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7;
		if(calMonth == 1) calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);
		else calLastDate = calLastDateOfMonth[calMonth];
		// �޷� �迭 �ʱ�ȭ
		for(int i = 0 ; i<CAL_HEIGHT ; i++){
			for(int j = 0 ; j<CAL_WIDTH ; j++){
				calDates[i][j] = 0;
			}
		}
		// �޷� �迭�� �� ä���ֱ�
		for(int i = 0, num = 1, k = 0 ; i<CAL_HEIGHT ; i++){
			if(i == 0) k = calStartingPos;
			else k = 0;
			for(int j = k ; j<CAL_WIDTH ; j++){
				if(num <= calLastDate) calDates[i][j]=num++;
			}
		}
	}
	private int leapCheck(int year){ // �������� Ȯ���ϴ� �Լ�
		if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;
		else return 0;
	}
	public void moveMonth(int mon){ // ����޷� ���� n�� ���ĸ� �޾� �޷� �迭�� ����� �Լ�(1���� +12, -12�޷� �̵� ����)
		calMonth += mon;
		if(calMonth>11) while(calMonth>11){
			calYear++;
			calMonth -= 12;
		} else if (calMonth<0) while(calMonth<0){
			calYear--;
			calMonth += 12;
		}
		cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
		makeCalData(cal);
	}
}

public class Calender extends CalenderDataMenager{ // CalendarDataManager�� GUI + �޸��� + �ð�
	DB_Input ip = new DB_Input();
	// â ������ҿ� ��ġ��
	JFrame mainFrame;
	
	JPanel calOpPanel;
		JButton todayBut;
		JLabel todayLab;
		JButton MonClosing;
		JButton YearClosing;
		JLabel BalanceL;
		JButton lYearBut;
		JButton lMonBut;
		JLabel curMMYYYYLab;
		JButton nMonBut;
		JButton nYearBut;
		ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();
		ButtonListener ClosingButtons = new ButtonListener();
	
	JPanel calPanel;
		JButton weekDaysName[];
		JButton dateButs[][] = new JButton[6][7];
		listenForDateButs lForDateButs = new listenForDateButs(); 
	
	JPanel infoPanel;
		JLabel selectedDate;
		JPanel[] tosidePanel;
		JButton[] update;
		JButton[] delete;
		JTextArea[] titleArea;
		JTextArea[] contentArea;
		JTextArea[] moneyArea;
		JButton  btn_add;
		int cnt;
		
	JPanel frameSubPanelEast;
		
	//���, �޼���
	final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
	final String title = "�޷�";
	private JPanel menubar;
	private JButton home;
	private JButton pounder;
	private JButton calculator;
	private JButton mypage;

	static String id;
	
	DateButAction dateBtn = new DateButAction();
	public Calender(){};
	public Calender(String id) throws IOException{ //������� ������ ���ĵǾ� ����. �� �ǳ� ���̿� ���ٷ� ����
		this.id = id;		
		mainFrame = new JFrame(title);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1500,900);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		try{
			UIManager.setLookAndFeel ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//LookAndFeel Windows ��Ÿ�� ����
			SwingUtilities.updateComponentTreeUI(mainFrame) ;
		}catch(Exception e){
		}
		
		calOpPanel = new JPanel();
		calOpPanel.setBounds(0, 0, 1000, 120);
			todayBut = new JButton("Today");
			todayBut.setBounds(0, 0, 150, 150);
			todayBut.setToolTipText("Today");
			todayBut.setFont(new Font("��������", Font.PLAIN, 15));
			todayBut.addActionListener(lForCalOpButtons);
			
			todayLab = new JLabel(today.get(Calendar.MONTH)+1+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR));
			MonClosing = new JButton("�������");
			MonClosing.setFont(new Font("��������", Font.PLAIN, 15));
			MonClosing.addActionListener(ClosingButtons);
			YearClosing = new JButton("�������");
			YearClosing.setFont(new Font("��������", Font.PLAIN, 15));
			YearClosing.addActionListener(ClosingButtons);
			int balance= Integer.valueOf(ip.productSelect("getBalance","select balance from user_info where id="+id));
			BalanceL = new JLabel("���� �ܾ�: "+ balance);
			BalanceL.setFont(new Font("���������ڵ�", Font.PLAIN, 20));
			lYearBut = new JButton("<<");
			lYearBut.setToolTipText("Previous Year");
			lYearBut.addActionListener(lForCalOpButtons);
			lMonBut = new JButton("<");
			lMonBut.setToolTipText("Previous Month");
			lMonBut.addActionListener(lForCalOpButtons);
			curMMYYYYLab = new JLabel("<html><table width=100><tr><th><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</th></tr></table></html>");
			nYearBut = new JButton(">>");
			nYearBut.setToolTipText("Next Year");
			nYearBut.addActionListener(lForCalOpButtons);
			nMonBut = new JButton(">");
			nMonBut.setToolTipText("Next Month");
			nMonBut.addActionListener(lForCalOpButtons);

			GridBagLayout gbl_calOpPanel = new GridBagLayout();
			gbl_calOpPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
			gbl_calOpPanel.rowHeights = new int[]{15, 31, 0, 0};
			calOpPanel.setLayout(gbl_calOpPanel);
			//calOpPanel.add(nYearBut,calOpGC);
		
		calPanel=new JPanel();
		calPanel.setBounds(0, 120, 1020, 640);
			weekDaysName = new JButton[7];
			for(int i=0 ; i<CAL_WIDTH ; i++){
				weekDaysName[i]=new JButton(WEEK_DAY_NAME[i]);
				weekDaysName[i].setBorderPainted(false);
				weekDaysName[i].setContentAreaFilled(false);
				weekDaysName[i].setForeground(Color.WHITE);
				if(i == 0) weekDaysName[i].setBackground(new Color(200, 50, 50));
				else if (i == 6) weekDaysName[i].setBackground(new Color(50, 100, 200));
				else weekDaysName[i].setBackground(new Color(150, 150, 150));
				weekDaysName[i].setOpaque(true);
				weekDaysName[i].setFocusPainted(false);
				calPanel.add(weekDaysName[i]);
			}

			for(int i=0 ; i<CAL_HEIGHT ; i++){
				for(int j=0 ; j<CAL_WIDTH ; j++){
					dateButs[i][j]=new JButton();
					dateButs[i][j].setBorderPainted(false);
					dateButs[i][j].setContentAreaFilled(false);
					dateButs[i][j].setBackground(Color.WHITE);
					dateButs[i][j].setOpaque(true);
					dateButs[i][j].addActionListener(lForDateButs);
					calPanel.add(dateButs[i][j]);
				}
			}
			calPanel.setLayout(new GridLayout(0,7,2,2));
			calPanel.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
			showCal(); // �޷��� ǥ��

			infoPanel = new JPanel();
			infoPanel.setBounds(0, 0, 365, 20);
				infoPanel.setLayout(null);
				selectedDate = new JLabel("<Html><font size=3>"+(today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR)+"&nbsp;(Today)</html>", SwingConstants.LEFT);
				selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
				
		//calOpPanel, calPanel��  frameSubPanelWest�� ��ġ
		JPanel frameSubPanelWest = new JPanel();
		frameSubPanelWest.setBounds(0, 100, 1050, 750);
		//frameSubPanelWest.setBackground(Color.BLUE);
		Dimension calOpPanelSize = calOpPanel.getPreferredSize();
		calOpPanelSize.height = 100;
		frameSubPanelWest.setLayout(null);
		calOpPanel.setPreferredSize(calOpPanelSize);
		frameSubPanelWest.add(calOpPanel);
		
		GridBagConstraints calOpGC = new GridBagConstraints();
		calOpGC.gridheight = 2;
		calOpGC.weightx = 5;
		calOpGC.weighty = 5;
		calOpGC.gridx = 5;
		calOpGC.gridy = 2;
		calOpGC.insets = new Insets(5, 5, 5, 0);
		calOpGC.gridx = 1;
		calOpGC.gridy = 0;
		calOpPanel.add(todayBut,calOpGC);
		calOpGC.gridx = 2;
		calOpGC.gridy = 0;
		calOpPanel.add(MonClosing,calOpGC);
		calOpGC.gridx = 3;
		calOpGC.gridy = 0;
		calOpPanel.add(YearClosing,calOpGC);
		calOpGC.gridx = 4;
		calOpGC.gridy = 0;
		calOpPanel.add(BalanceL,calOpGC);
		calOpGC.gridx = 0;
		calOpGC.gridy = 2;
		calOpPanel.add(lYearBut,calOpGC);
		calOpGC.gridx = 1;
		calOpGC.gridy = 2;
		calOpPanel.add(lMonBut,calOpGC);
		calOpGC.gridx = 2;
		calOpGC.gridy = 2;
		calOpPanel.add(curMMYYYYLab,calOpGC);
		calOpGC.gridx = 3;
		calOpGC.gridy = 2;
		calOpPanel.add(nMonBut,calOpGC);
		calOpGC.gridx = 4;
		calOpGC.gridy = 2;
		calOpPanel.add(nYearBut,calOpGC);
		
		
		frameSubPanelWest.add(calPanel);

		//infoPanel, sidePanel��  frameSubPanelEast�� ��ġ
		frameSubPanelEast = new JPanel();
		frameSubPanelEast.setBounds(1050, 100, 450, 750);
		Dimension infoPanelSize=infoPanel.getPreferredSize();
		infoPanelSize.height = 50;
		infoPanelSize.setSize(50, 100);
		infoPanel.setPreferredSize(infoPanelSize);
		//frameSubPanelEast.setBackground(Color.PINK);
		frameSubPanelEast.setLayout(null);
		frameSubPanelEast.add(infoPanel);
		
		Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
		frameSubPanelWestSize.width = 1000;
		frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);
		
		mainFrame.getContentPane().setLayout(null);
		
		menubar = new JPanel();
		menubar.setBounds(0, 10, 1500, 70);
		//menubar.setBackground(Color.WHITE);
		mainFrame.getContentPane().add(menubar);
		menubar.setLayout(null);
		
		home = new JButton("Ȩ");
		home.setBounds(200, 12,  173, 47);
		home.setFont(new Font("��������", Font.PLAIN, 20));
		home.setForeground(new Color(51, 102, 153));
		home.addActionListener(ClosingButtons);
		menubar.add(home);
		
//		pounder = new JButton("ȸ��üũ");
//		pounder.setForeground(new Color(51, 102, 153));
//		pounder.setBounds(483, 12,  173, 47);
//		pounder.setFont(new Font("��������", Font.PLAIN, 20));
//		pounder.addActionListener(ClosingButtons);
//		menubar.add(pounder);
		
		calculator = new JButton("��ġ���̰���");
		calculator.setForeground(new Color(51, 102, 153));
		calculator.setFont(new Font("��������", Font.PLAIN, 20));
		calculator.setBounds(650, 12,  173, 47);
		calculator.addActionListener(ClosingButtons);
		menubar.add(calculator);
		
		mypage = new JButton("����������");
		mypage.setForeground(new Color(51, 102, 153));
		mypage.setBounds(1100, 12,  173, 47);
		mypage.addActionListener(ClosingButtons);
		mypage.setFont(new Font("��������", Font.PLAIN, 20));
		menubar.add(mypage);
		
		mainFrame.getContentPane().add(frameSubPanelWest);
		mainFrame.getContentPane().add(frameSubPanelEast);
		mainFrame.setVisible(true);

		focusToday(); //���� ��¥�� focus�� �� (mainFrame.setVisible(true) ���Ŀ� ��ġ�ؾ���)
		
	}
	
	private void focusToday(){
		if(today.get(Calendar.DAY_OF_WEEK) == 1)
			dateButs[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
		else
			dateButs[today.get(Calendar.WEEK_OF_MONTH)-1][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
	}
	
	public void showCal(){
		for(int n=0;n<CAL_HEIGHT;n++){
			for(int m=0;m<CAL_WIDTH;m++){
				
				String fontColor="black";
				if(m==0) fontColor="red";
				else if(m==6) fontColor="blue";
				
				//��¥��ư ������ �ش� ��, ��, �� �������� ������(getDate, getMonth, getYear == String type)
				dateButs[n][m].addActionListener(dateBtn);
				
				File f =new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDates[n][m]<10?"0":"")+calDates[n][m]+".txt");
				if(f.exists()){
					dateButs[n][m].setText("<html><font color="+fontColor+">"+calDates[n][m]+"</font></html>");
				}
				else dateButs[n][m].setText("<html><font color="+fontColor+">"+calDates[n][m]+"</font></html>");

				dateButs[n][m].removeAll();
				if(calMonth == today.get(Calendar.MONTH) &&	calYear == today.get(Calendar.YEAR) && calDates[n][m] == today.get(Calendar.DAY_OF_MONTH)){
					dateButs[n][m].setText("<html><font color=green>"+calDates[n][m]+"</font></html>");
					dateButs[n][m].setToolTipText("Today");
				}
				int isIn;
				String queary;
				
				if((calMonth+1)<10){
					queary = "SELECT COUNT(*) from calender_info_"+id+" where date="+(String.valueOf(calYear)+("0"+String.valueOf(calMonth+1))+String.valueOf(calDates[n][m]));
				}else if(((calMonth+1)<10) && (calDates[n][m] < 10)){
					queary = "SELECT COUNT(*) from calender_info_"+id+" where date="+(String.valueOf(calYear)+("0"+String.valueOf(calMonth+1))+("0"+String.valueOf(calDates[n][m])));
				}else if(calDates[n][m] < 10){
					queary = "SELECT COUNT(*) from calender_info_"+id+" where date="+(String.valueOf(calYear)+String.valueOf(calMonth+1)+("0"+String.valueOf(calDates[n][m])));
				}else{
					queary = "SELECT COUNT(*) from calender_info_"+id+" where date="+(String.valueOf(calYear)+String.valueOf(calMonth+1)+String.valueOf(calDates[n][m]));
				}isIn = Integer.parseInt(ip.productSelect("getCnt", queary));
				if(isIn > 0){
					JLabel listMark = new JLabel("<html><font color=orange>$</font></html>");
					dateButs[n][m].add(listMark);
				}
				
				if(calDates[n][m] == 0) dateButs[n][m].setVisible(false);
				else dateButs[n][m].setVisible(true);
			}
		}
	}
	
	private class ButtonListener extends JFrame implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==MonClosing){
				new MCTotal(id, String.valueOf(calMonth+1));
			}
			else if(e.getSource()==YearClosing){
				new YCTotal(id, String.valueOf(calYear));
			}else if(e.getSource()==home){
				mainFrame.dispose();
				try {
					new Calender(id);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if(e.getSource()==mypage){
				mainFrame.dispose();
				new UserInfo_gui(id);
			}
//			else if(e.getSource()==pounder){
//				new Recall(id);
//				mainFrame.dispose();
//			}
			else if(e.getSource()==calculator){
				mainFrame.dispose();
				new Calculator(id);
			}
		}
	}

	private class ListenForCalOpButtons implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == todayBut){
				setToday();
				lForDateButs.actionPerformed(e);
				focusToday();
			}
			else if(e.getSource() == lYearBut) moveMonth(-12);
			else if(e.getSource() == lMonBut) moveMonth(-1);
			else if(e.getSource() == nMonBut) moveMonth(1);
			else if(e.getSource() == nYearBut) moveMonth(12);
			curMMYYYYLab.setText("<html><table width=100><tr><th><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+" / "+calYear+"</th></tr></table></html>");
			showCal();
		}
	}
	
	private class listenForDateButs implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int k=0,l=0;
			for(int i=0 ; i<CAL_HEIGHT ; i++){
				for(int j=0 ; j<CAL_WIDTH ; j++){
					if(e.getSource() == dateButs[i][j]){ 
						k=i;l=j;
					}
				}
			}
	
			if(!(k ==0 && l == 0)) calDayOfMon = calDates[k][l]; //today��ư�� ���������� �� actionPerformed�Լ��� ����Ǳ� ������ ���� �κ�

			cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
			
			String dDayString = new String();
			int dDay=((int)((cal.getTimeInMillis() - today.getTimeInMillis())/1000/60/60/24));
			if(dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)) 
					&& (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
					&& (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))) dDayString = "Today"; 
			else if(dDay >=0) dDayString = "D-"+(dDay+1);
			else if(dDay < 0) dDayString = "D+"+(dDay)*(-1);
			
			selectedDate.setText("<Html><font size=3>"+(calMonth+1)+"/"+calDayOfMon+"/"+calYear+"&nbsp;("+dDayString+")</html>");
		}
	}
	
	private class DateButAction implements ActionListener{
			int i=0;
			public void actionPerformed(ActionEvent e) {
				frameSubPanelEast.removeAll();
				
				JButton eButton = ((JButton)e.getSource());
				getDate="";String temp="";String[] str1;
				if((eButton.getText()).contains("black")){str1 = new String(eButton.getText()).split("<html><font color=black>");}
				else if((eButton.getText()).contains("red")){str1 = new String(eButton.getText()).split("<html><font color=red>");}
				else if((eButton.getText()).contains("green")){str1 = new String(eButton.getText()).split("<html><font color=green>");}
				else{str1 = new String(eButton.getText()).split("<html><font color=blue>");}
				for(int i=0; i < str1.length ; i++)temp += str1[i];
				str1 = temp.split("</font></html>");
				if(Integer.valueOf(str1[0]) < 10){for(int i=0; i < str1.length ; i++)getDate += "0"+str1[i];}
				else {for(int i=0; i < str1.length ; i++)getDate += str1[i];};

				SideJPanel sidePanel = new SideJPanel(id, (String.valueOf(calYear)),(String.valueOf(calMonth+1)),getDate);
				
				String getDay;
				if((calMonth+1)<10){
					getDay = (String.valueOf(calYear))+("0"+String.valueOf(calMonth+1))+(String.valueOf(getDate));
				}else{
					getDay = (String.valueOf(calYear))+(String.valueOf(calMonth+1))+(String.valueOf(getDate));
				}System.out.println((String.valueOf(calYear))+(String.valueOf(calMonth+1))+getDate);

				String queary = "SELECT COUNT(*) from calender_info_"+id+" where date="+((String.valueOf(calYear))+(String.valueOf(calMonth+1))+getDate);
				cnt = Integer.parseInt(ip.productSelect("getCnt", queary));
				if(cnt != 0){
					String qeuryuse = "select p_use from calender_info_"+id+" where date="+((String.valueOf(calYear))+(String.valueOf(calMonth+1))+getDate);
					String qeuryinout = "select inOrout from calender_info_"+id+" where date="+((String.valueOf(calYear))+(String.valueOf(calMonth+1))+getDate);
					String qeurymoney = "select money from calender_info_"+id+" where date="+((String.valueOf(calYear))+(String.valueOf(calMonth+1))+getDate);
					String[] useArray = ip.productSelectArray("getInOrOut", qeuryinout);
					String[] MoneyArray = ip.productSelectArray("getMoney", qeurymoney);
					String[] InoutArray = ip.productSelectArray("getP_use", qeuryuse);
					int tosidePy=80,j=200;
					tosidePanel = new JPanel[cnt];
					update = new JButton[cnt];
					delete = new JButton[cnt];
					titleArea = new JTextArea[cnt];
					contentArea = new JTextArea[cnt];
					moneyArea = new JTextArea[cnt];
					for(i=0;i<cnt;i++){							
						tosidePanel[i] = new JPanel();
						tosidePanel[i].setLayout(null);
						
						update[i] = new JButton("����");
						update[i].setBounds(235, 40, 60, 23);
						update[i].setName(InoutArray[i]);
						update[i].setFont(new Font("��������", Font.PLAIN, 15));
						update[i].addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e1) {
								DB_Input ip = new DB_Input();
								JButton eUpBtn = ((JButton)e1.getSource());
								String qr ="select cnt from calender_info_"+id+" where date="+((String.valueOf(calYear))+(String.valueOf(calMonth+1))+getDate)+" and p_use='"+eUpBtn.getName()+"'";
								String key = ip.productSelect("getKeyCnt",qr);

								//�ش� ���� ������Ʈ �ϱ�
								String p_inout = ip.productSelect("getinOrout", "select inOrout from calender_info_"+id+" where cnt ="+key);
								int p_money = Integer.valueOf(ip.productSelect("getMoney", "select money from calender_info_"+id+" where cnt ="+key));
								
								System.out.println(p_money+" "+eUpBtn.getName()+" "+p_inout);
								Input_gui ig = new Input_gui(id, String.valueOf(calYear), String.valueOf(calMonth+1), getDate, String.valueOf(p_money), eUpBtn.getName(), p_inout, key);
								ig.setInput_gui();
								
								int balance3= Integer.valueOf(ip.productSelect("getBalance","select balance from user_info where id="+id));
								if(p_inout.equals("����")){
									System.out.println("���⳻������");
									balance3 += p_money;	
								}else if(p_inout.equals("����")){
									System.out.println("���Գ�������");
									balance3 -= p_money;
								}
								//�ܰ� ������Ʈ 
								String queryUpStr="UPDATE user_info SET balance="+balance3+" WHERE id="+id;
								System.out.println(queryUpStr);
								ip.productUpdateDelete(queryUpStr);
								
								showCal();
								dateBtn.actionPerformed(e);
							}
						});

						delete[i] = new JButton("����");
						delete[i].setFont(new Font("��������", Font.PLAIN, 15));
						delete[i].setBounds(235, 73, 60, 23);
						delete[i].setName(InoutArray[i]);
						delete[i].addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e1) {
								DB_Input ip = new DB_Input();
								JButton eDelBtn = ((JButton)e1.getSource());
								String qr ="select cnt from calender_info_"+id+" where date="+((String.valueOf(calYear))+(String.valueOf(calMonth+1))+getDate)+" and p_use='"+eDelBtn.getName()+"'";
								String key = ip.productSelect("getKeyCnt",qr);
								
								//�ܰ����� �ش� ���� �����ϱ�
								String p_inout = ip.productSelect("getinOrout", "select inOrout from calender_info_"+id+" where cnt ="+key);
								int p_money = Integer.valueOf(ip.productSelect("getMoney", "select money from calender_info_"+id+" where cnt ="+key));
								int balance2= Integer.valueOf(ip.productSelect("getBalance","select balance from user_info where id="+id));
								
								System.out.println(balance2);
								System.out.println(p_inout);
								System.out.println(p_money);
								
								if(p_inout.equals("����")){
									System.out.println("���⳻������");
									balance2 += p_money;	
								}else if(p_inout.equals("����")){
									System.out.println("���Գ�������");
									balance2 -= p_money;
								}
								System.out.println(balance2);
								//�ܰ� ������Ʈ 
								String queryUpStr="UPDATE user_info SET balance="+balance2+" WHERE id="+id;
								System.out.println(queryUpStr);
								ip.productUpdateDelete(queryUpStr);
								//������ �÷����� �����ϱ�
								ip.productUpdateDelete("delete from calender_info_"+id+" where cnt ="+key);
								
								showCal();
								dateBtn.actionPerformed(e);
							}
						});
						
						titleArea[i] = new JTextArea();
						titleArea[i].setFont(new Font("��������", Font.PLAIN, 15));
						titleArea[i].setBackground(Color.ORANGE);
						titleArea[i].setText(useArray[i]);
						titleArea[i].setBounds(12, 10, 207, 25);
						
						moneyArea[i] = new JTextArea();
						moneyArea[i].setFont(new Font("��������", Font.PLAIN, 15));
						moneyArea[i].setBackground(Color.WHITE);
						moneyArea[i].setText(MoneyArray[i]);
						moneyArea[i].setBounds(12, 35, 207, 25);
						
						contentArea[i] = new JTextArea();
						contentArea[i].setFont(new Font("��������", Font.PLAIN, 15));
						contentArea[i].setBackground(Color.GRAY);
						contentArea[i].setText(InoutArray[i]);
						contentArea[i].setBounds(12, 60, 207, 75);

						tosidePanel[i].add(update[i]);
						tosidePanel[i].add(delete[i]);
						tosidePanel[i].add(moneyArea[i]);
						tosidePanel[i].add(titleArea[i]);
						tosidePanel[i].add(contentArea[i]);
						
						tosidePanel[i].setBounds(33, tosidePy, 304, 143);
						tosidePanel[i].setFont(new Font("��������", Font.PLAIN, 15));
						tosidePanel[i].setBackground(Color.white);
						tosidePanel[i].setVisible(true);
						sidePanel.add(tosidePanel[i]);
						tosidePy+=j; 
					}
				}
					sidePanel.setVisible(true);
					Dimension sidePanelSize = sidePanel.getPreferredSize();
					sidePanel.setSize(new Dimension(440, 720));
					frameSubPanelEast.add(sidePanel);
					frameSubPanelEast.revalidate();
					frameSubPanelEast.repaint();
		}
	}	
}
	
class SideJPanel extends JPanel{
	public SideJPanel(String id, String setY, String setM, String setD){
		setLayout(null);
		setLocation(0, 30);
		setBorder(BorderFactory.createTitledBorder("������ �̺�Ʈ"));
		JButton btn_add = new JButton("+");
		btn_add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Input_gui in = new Input_gui(id,setY,setM,setD,"","","",null);
				in.setInput_gui();
				
			}
		});
		btn_add.setBounds(275, 24, 63, 35);
		add(btn_add);
	}
}