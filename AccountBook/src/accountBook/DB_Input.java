package accountBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.ResultSetMetaData;

public class DB_Input {
	Connection connection;
	Statement statment;
	ResultSet resultSet;
	String queryString;
	
	String driverName = "org.gjt.mm.mysql.Driver";
	String url = "jdbc:mysql://10.96.122.199:3306/Account_DB?useUnicode=true&characterEncoding=utf8&useSSL = false"; // jdbc:mysql://localhost:3306/DB�̸� �� ������
	//String url = "jdbc:mysql://localhost:3306/Account_DB?useUnicode=true&characterEncoding=utf8&useSSL = false"; // jdbc:mysql://localhost:3306/DB�̸� �� ������
	String user = "account"; // �ڽ��� id
	//String password = "account"; // �ڽ��� pass
	
	
	//DB�ε� �޼ҵ�
	public DB_Input(){
		try{Class.forName(driverName);}
		catch (ClassNotFoundException e){System.out.println("[�ε� ����]\n" + e.getStackTrace());}
	}
	
	
	//Table Create �޼���
	public void tableCreate(String id, int flag){
		try{
			if(flag == 0){
		queryString = "CREATE TABLE calender_info_"+id+" ("
				+ "cnt int(3) auto_increment,"
				+ "date date,"
				+ "inOrout varchar(40),"
				+ "money int(20),"
				+ "p_use varchar(100),"
				+ "primary key(cnt))DEFAULT CHARSET=utf8";
			}
			else if(flag==1){
		queryString = "CREATE TABLE pounding_"+id+" ("
						+ "num int(3) auto_increment,"
						+ "title varchar(40),"
						+ "date date,"
						+ "money varchar(10),"
						+ "checkMember varchar(50) default ' ',"
						+ "primary key(num))DEFAULT CHARSET=utf8";
			}
		connect();
		
		// �� ���� [CRUD]
		statment.executeUpdate(queryString);
		System.out.println("Create Table");
		
		}catch (SQLException e){
			System.out.println("[create ���� ����]\n" + e.getStackTrace());
		}finally{
			// �� �ݱ�
			closeDatabase();
		}
	}

	
	//Insert�޼���
	public void productInsert(String queryString) {
		try{
			connect();
			System.out.println(queryString);
			// �� ���� [CRUD]
			statment.executeUpdate(queryString);
			System.out.println("Insert Table");
			
		}catch (SQLException e){
			System.out.println("[insert ���� ����]\n" + e.getStackTrace());
		}finally{
			// �� �ݱ�
			closeDatabase();
		}
	}
	
	int cnt=0;
	public String[] productSelectArray(String what, String queryString){
		String[] arrayResult = new String[cnt];
		int i=0;
		try {
			connect();
			System.out.println(queryString);
			resultSet = statment.executeQuery(queryString);
			
			if(what == "getP_use"){
				while(resultSet.next()){arrayResult[i] = resultSet.getString("p_use"); i++;}
			}else if(what == "getInOrOut"){
				while(resultSet.next()){arrayResult[i] = resultSet.getString("inOrout"); i++;}
			}else if(what == "getMoney"){
				while(resultSet.next()){arrayResult[i] = resultSet.getString("money"); i++;}
			}
		} catch (SQLException e){
			System.out.println("[select ���� ����]\n" + e.getStackTrace());
		}finally{
			// �� �ݱ�
			closeDatabase();
		}
			return arrayResult;
		}
	
	//select query�޼���
	public String productSelect(String what, String queryString){
		String result = "";
		try{
			connect();
			System.out.println(queryString);
			// �� ���� [CRUD]
			resultSet = statment.executeQuery(queryString);
			
				if(what == "getPass"){
					while(resultSet.next()){result = resultSet.getString("u_pass");}
				}else if(what == "getId"){
					while(resultSet.next()){result = resultSet.getString("id");}
				}else if(what == "getDuplicate"){
					while(resultSet.next()){result = resultSet.getString("u_id");}
				}else if(what == "getBalance"){
					while(resultSet.next()){result = resultSet.getString("balance");}
				}else if(what == "getClub"){
					while(resultSet.next()){result = resultSet.getString("club_name");}
				}else if(what == "getClubMember"){
					while(resultSet.next()){result = resultSet.getString("club_member");}
				}else if(what == "getCnt"){
					while(resultSet.next()){result = resultSet.getString("count(*)"); cnt = Integer.valueOf(result); }
				}else if(what == "getKeyCnt"){
					while(resultSet.next()){result = resultSet.getString("cnt");}
				}else if(what == "getinOrout"){
					while(resultSet.next()){result = resultSet.getString("inOrout");}
				}else if(what == "getMoney"){
					while(resultSet.next()){result = resultSet.getString("money");}
				}else if(what == "getDate"){
					while(resultSet.next()){result = resultSet.getString("date");}
				}

		}catch (SQLException e){
			System.out.println("[select ���� ����]\n" + e.getStackTrace());
		}finally{
			// �� �ݱ�
			closeDatabase();
		}
		return result;
	}
	
	
	//update & delete �޼���
	public int productUpdateDelete(String queryString){
		int resultValue = 0;
		try	{
			connect();
			// �� ���� [CRUD]
			System.out.println(queryString);
			resultValue = statment.executeUpdate(queryString);
		}catch (SQLException e){
			System.out.println("[update ���� ����]\n" + e.getStackTrace());
		}finally{
			// �� �ݱ�
			closeDatabase();
		}
		return resultValue;
	}
	
	
	//DB���� ���� �޼���
	public void connect() throws SQLException{
		// �� ���� [Connection]
		connection = DriverManager.getConnection(url, user, "");
		// �� ���� [Statement]
		statment = connection.createStatement();
	}
	
	
	//DB �ݴ� �޼���
	public void closeDatabase(){
		try{
			if( connection != null ){connection.close();}
			if( statment != null ){statment.close();}
			if( resultSet != null ){resultSet.close();}
		}catch (SQLException e){
			System.out.println("[�ݱ� ����]\n" +  e.getStackTrace());
		}
	}
	
}