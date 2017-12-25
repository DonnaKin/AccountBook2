package accountBook;

import java.awt.Choice;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class Recall_Input{
	String date;
	public Recall_Input(String id){
		  JFrame jf_input = new JFrame("�Է�");
		  jf_input.getContentPane().setLayout(null);
		  
		  JPanel input = new JPanel();
	      input.setBounds(0, 0, 352, 346);
	      input.setLayout(null);
	      
	      //����
	      JLabel title = new JLabel("����");
	      title.setBounds(23, 53, 57, 15);
	      input.add(title);
	      
	      JTextField tf_title = new JTextField();
	      tf_title.setBounds(109, 43, 206, 36);
	      input.add(tf_title);
	      tf_title.setColumns(10);
	      
	      //��¥
	      JLabel date = new JLabel("��¥");
	      date.setBounds(23, 137, 57, 15);
	      input.add(date);
	      
	      Choice YearCh = new Choice();
	      YearCh.setFont(new Font("��������", Font.PLAIN, 15));
	      YearCh.setBounds(110, 126, 60, 5);
	      for(int k =-5; k<2;k++){YearCh.add(String.valueOf(2017+k));}
			input.add(YearCh);
			
			Choice MonCh = new Choice();
			MonCh.setFont(new Font("��������", Font.PLAIN, 15));
			MonCh.setBounds(180, 126, 60, 5);
			for(int k = 1; k <= 12; k++){
				if(k < 10)MonCh.add("0"+String.valueOf(k));
				else MonCh.add(String.valueOf(k));
			}
			input.add(MonCh);
			
			Choice DayCh = new Choice();
			DayCh.setFont(new Font("��������", Font.PLAIN, 15));
			DayCh.setBounds(250, 126, 60, 5);
			for(int k = 1; k <= 31; k++){
				if(k < 10)DayCh.add("0"+String.valueOf(k));
				else DayCh.add(String.valueOf(k));
			}input.add(DayCh);
	      
		  //�ݾ�
		  JLabel money = new JLabel("�ݾ�");
	      money.setBounds(23, 212, 57, 15);
	      input.add(money);
	      
	      JTextField tf_money = new JTextField();
	      tf_money.setBounds(109, 202, 206, 36);
	      input.add(tf_money);
	      tf_money.setColumns(10);
	      
	      JButton add = new JButton("�߰�");
	      add.setBounds(117, 279, 108, 36);
	      add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			      DB_Input dbinput = new DB_Input();
			      String queryString;
			      
			      String titleTxt = title.getText();
			      String dateTxt = YearCh.getItem(YearCh.getSelectedIndex())+MonCh.getItem(MonCh.getSelectedIndex())+DayCh.getItem(DayCh.getSelectedIndex());
			      String moneyTxt = money.getText();
			      
			      queryString = "Insert into pounding_"+id+"(title, date, money) values ('"+titleTxt+"', '"+dateTxt+"', '"+moneyTxt+"')";
			      dbinput.productInsert(queryString);
			      jf_input.dispose();
			}
	      });
	      input.add(add);

	      
	}
}
