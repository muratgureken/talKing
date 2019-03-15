package mg.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class userdao extends registerInfo{
	private static String url="jdbc:postgresql://127.0.0.1:5432/talktome";
	private static String username="postgres";
	private static String password="sifre123";
	private Connection conn;

	public userdao() throws SQLException{
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, username, password); //try/catch'e gerek yok, metot throwable yapildi.
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		conn.close();
	}

	public void bringAllRegisters() throws SQLException
	{
		//registerInfos'daki linkedlist'leri doldur
		int count=0;
		Statement stmt;
		stmt = conn.createStatement();
		String sql = "select id, usrname, connInfo, socket from clientinfo";
		System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println(sql);
                
                getIdList().clear();
                getUsrList().clear();
                getConnStateList().clear();
                getSocketList().clear();
                
		while(rs.next())
		{
			getIdList().set(count, rs.getInt("id"));
			getSocketList().set(count, rs.getInt("socket"));
			getUsrList().set(count, rs.getString("usrname"));
			getConnStateList().set(count, rs.getBoolean("connInfo"));
			count++;
		}

	}

	public void updateRegister(int id, int socket, boolean connState) throws SQLException
	{
		//hem database hem deregisterInfos'daki linkedlist'leri update et.
		int index = getIdList().indexOf(id);
		getSocketList().set(index, socket);
		getConnStateList().set(index, connState);
		Statement stmt;
		stmt = conn.createStatement();
		String sql = "update clientinfo set socket="+socket+", connInfo="+connState+" where id="+id;
		stmt.executeUpdate(sql);
	}

        public void insertRegister(int id, String name, String password) throws SQLException
	{
		//hem database hem deregisterInfos'daki linkedlist'leri update et.
		Statement stmt;
		stmt = conn.createStatement();
		String sql = "insert into clientinfo(id, usrname, password,connInfo) values ("+id+", '"+name+"','"+password+"',true)";
		stmt.executeUpdate(sql);
	}
        
	public void makeAllRegistersPassive() throws SQLException
	{
		int size = getIdList().size();

		for(int i=0; i<size; i++)
		{
			getConnStateList().set(i, false);
		}

		Statement stmt;
		stmt = conn.createStatement();
		String sql = "update clientinfo set connInfo=false";
		stmt.executeUpdate(sql);
	}
}
