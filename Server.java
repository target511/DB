// KwangWoon University
// Computer Software
// 2014726003
// ¿ÀÃ¶¼ö
// DB HW2

import java.sql.*;
import java.io.*;
import java.net.*;

public class Server{

	public static void main(String[] args)
	{	
		// ----- Connect DB ----- //
		Connection conn = null;
		
		java.sql.Statement st = null;
		ResultSet rs = null;

		try {
			String url = "jdbc:mysql://localhost:3306/hw2";
			String ID = "root";
			String PW = "33csbasswd";
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, ID, PW);
			st = conn.createStatement();
			System.out.println("Connect DB ... [OK]");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		// ----- Socket ----- //
		try {
			ServerSocket server = new ServerSocket(10001);
			System.out.println("Waiting Clinet ... ");
			Socket sock = server.accept();
			
			InetAddress inetaddr = sock.getInetAddress();
			System.out.println(inetaddr.getHostAddress() + " Connected.");
			
			OutputStream out = sock.getOutputStream();
			InputStream in = sock.getInputStream();
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            String line = null;
            
            while((line = br.readLine()) != null) {
            	System.out.println("Requirement from Client : " + line);
            	
            	// ----- Send Result to Client ----- //
            	rs = st.executeQuery(line);
            	if (st.execute(line)) {
            		rs = st.getResultSet();
            	}
            	
            	while (rs.next()) {
            		ResultSetMetaData md = rs.getMetaData();
            		
            		for (int i = 1; i < md.getColumnCount(); i++) {
            			pw.print(rs.getString(i) + "\t");
            		}
            		pw.print(rs.getString(md.getColumnCount()) + "\n");
            		pw.flush();
            	}
            	pw.println("EOF");
        		pw.flush();
            }
            
            pw.close();
            br.close();
            sock.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static int getResultSetSize(ResultSet resultSet) {
	    int size = -1;

	    try {
	        resultSet.last(); 
	        size = resultSet.getRow();
	        resultSet.beforeFirst();
	    } catch(SQLException e) {
	        return size;
	    }

	    return size;
	}
}

		
		
