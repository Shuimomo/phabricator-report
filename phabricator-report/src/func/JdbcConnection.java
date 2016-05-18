package func;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcConnection { 

	private String DBURL;
	private String DBUSER;
	private String DBPASS;
	private Statement streamConnection;
	private ResultSet streamResponse;
	private ResultSet res;

	public JdbcConnection(){
		super();
	}
	
	/**
	 * constructor	 
	 * @param String url
	 * @param String user
	 * @param String password
	 */
	public JdbcConnection(String url, String user, String pw){
		super();
		DBURL=url;
		DBUSER = user;
		DBPASS = pw;
	}

	/**
	 * open connection
	 * if cannot, send a message and exit
	 */
	public Connection openConnection(){
		Connection con=null;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			this.streamConnection = con.createStatement();
		}
		catch(Exception e) { 
			System.out.println("Cannot access database at : " +DBURL);
			System.exit(1);
		}
		return con;
	}

	/**
	 * close connection
	 */
	public void close() {
		try{
			if(this.streamResponse != null)
				this.streamResponse.close();
			this.streamConnection.close();
		}
		catch (SQLException e) { e.printStackTrace(); }  
	}

	private ResultSet  executeQuery(String sqlRequest) throws Exception {
		return this.streamResponse = this.streamConnection.executeQuery(sqlRequest);
	}
	
	/**
	 * execute the request and return the result in a List<String>
	 * @param String request
	 * @return List<String> result
	 */
	public List<String> execute(String request) throws Exception{
		List<String> result= new ArrayList<String>();
		try {
			res = this.executeQuery(request);
			res.beforeFirst();
			while (res.next()) {
				result.add(res.getString(1));
			}
		} catch (Exception e){
			e.printStackTrace();
		};
		return result;
	}
}