package accountBook;

import java.util.StringTokenizer;

public class UserInfo{
	static public String Idresult;
	static public String Clubresult;
	static public String Memberresult;
	static public String Balresult;
	static public String Passresult;
	static public String[] member;
	static public int length;
	public UserInfo(String id){
		DB_Input dbInput = new DB_Input();
		String queryUserId = "select u_id from user_info where id="+id;
		String queryClub = "select club_name from user_info where id="+id;
		String queryClubMember = "select club_member from user_info where id="+id;
		String queryBalance = "select balance from user_info where id="+id;
		String queryPass = "select u_pass from user_info where id="+id;
		
		Idresult =dbInput.productSelect("getDuplicate", queryUserId);
		System.out.println(Idresult);
		Clubresult =dbInput.productSelect("getClub", queryClub);
		System.out.println(Clubresult);
		Passresult =dbInput.productSelect("getPass", queryPass);
		System.out.println(Passresult);
		Memberresult =dbInput.productSelect("getClubMember", queryClubMember);
		StringTokenizer st = new StringTokenizer(Memberresult,",");
		length = st.countTokens();
		member = new String[length];
		for(int i=0; i < length;i++){
			member[i] = st.nextToken();
		}
		Balresult =dbInput.productSelect("getBalance", queryBalance);
		System.out.println(Balresult);
	}
}

