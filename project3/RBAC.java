import java.util.*;
import java.io.*;
import java.sql.*;

public class RBAC {
	static Connection con;
	static int isAdmin = 0;
	static String currUser = null;
	public RBAC() {
		try {
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
		} catch ( ClassNotFoundException e ) {
			e.printStackTrace();
		}
		try {
			con = DriverManager.getConnection( "jdbc:oracle:thin:@claros.cs.purdue.edu:1524:strep", "wang1247", "GSWntZEF" );
		} catch ( SQLException e ){
			e.printStackTrace();
		}
	}
	public static void login (String username, String password) {	
		String query = "SELECT * " + "FROM USERS WHERE username = " 
			+ "\'" + username + "\' " 
			+ "AND " + "password = " + "\'" + password + "\'";
		//System.out.println(query);
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				if (username.equals("admin")) { // current user is admin
					isAdmin = 1;
				} else {
					isAdmin = 0;
				}
				currUser = username;
				System.out.println("Login successful");
			} else {
				System.out.println("Invalid login");
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void createRole(String roleName, String encKey) {
		if (isAdmin == 1) { // current user is admin
			int enc = Integer.parseInt(encKey);
			String update = "INSERT INTO ROLES VALUES (\'" 
				+ roleName + "\', " + enc + ")";
			//System.out.println(update);
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(update);
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Authorization failure");
		}
	}
	public static void createUser(String username, String password) {
		if (isAdmin == 1) {
			String update = "INSERT INTO USERS VALUES (\'"
				+ username + "\', " + 
				"\'" + password + "\')";
			//System.out.println(update);
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(update);
				stmt.close();
				System.out.println("User created successfully");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Authorization failure");
		}
	}
	public static void grantRole(String username, String roleName) {
		if (isAdmin == 1) {
			String update = "INSERT INTO USERROLES VALUES (\'"
				+ username + "\', " + 
				"\'" + roleName + "\')";
			//System.out.println(update);
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(update);
				stmt.close();
				System.out.println("Role assigned successfully");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Authorization failure");
		}
	}
	public static void grantPrivilege(String privName, String roleName, String tableName) {
		if (isAdmin == 1) {
			String update = "INSERT INTO ROLEPRIVILEGES VALUES (\'"
				+ roleName + "\', " + 
				"\'" + tableName + "\', " +
				"\'" + privName + "\')";
			//System.out.println(update);
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(update);
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Authorization failure");
		}
	}
	public static void revokePrivilege(String privName, String roleName, String tableName) {
		if (isAdmin == 1) {
			String update = "DELETE FROM ROLEPRIVILEGES WHERE ROLENAME = \'"
				+ roleName + "\' AND TABLENAME = " + 
				"\'" + tableName + "\' AND PRIVNAME = " + 
				"\'" + privName + "\'";
			//System.out.println(update);
			try {
				Statement stmt = con.createStatement();
				stmt.executeUpdate(update);
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Authorization failure");
		}
	}
	public static void insertInto(String tableName, String values, int columnNo, String ownerRole) {
		int hasPriv = 0;
		String role = null;
		String query1 = "SELECT * FROM USERROLES WHERE USERNAME = " + "\'" + currUser + "\'";
		//System.out.println(query1);
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query1);
			while (rs.next()) {
				role = rs.getString("ROLENAME");
				String query2 = "SELECT * FROM ROLEPRIVILEGES WHERE ROLENAME = " + 
					"\'" + role + "\' AND TABLENAME = " + "\'" + tableName + "\'";
				//System.out.println(query2);
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery(query2);
				while (rs2.next()) {
					String privilege = rs2.getString("PRIVNAME");
					if (privilege.equals("INSERT") && hasPriv == 0) {
						hasPriv = 1;
						break;
					}
				}
				rs2.close();
				stmt2.close();
				if (hasPriv == 1) {
					break;
				}
			}
			//System.out.println("hasPrive = " + hasPriv);
			if (hasPriv == 1) {
				System.out.println(columnNo);
				int encKey = 0;
				String query3 = "SELECT * FROM ROLES WHERE ROLENAME = " + "\'" + role + "\'";
				Statement stmt3 = con.createStatement();
				ResultSet rs3 = stmt3.executeQuery(query3);
				if (rs3.next()) {
					encKey = rs3.getInt("ENCRYPTIONKEY");
				}
				String[] val = values.split(",");
				if (columnNo > 0) {
					for (int i = 0; i < val.length; i ++) {
						if (i+1 == columnNo) {
							val[i] = encrypt(val[i], encKey);
						}
					}
				}
				String v = processValues(val);
				v +=  columnNo + ",\'" + ownerRole + "\'";
				String update = "INSERT INTO " + tableName + " VALUES (" + v + ")";
				//System.out.println(update);
				Statement stmt4 = con.createStatement();
				stmt4.executeUpdate(update);
				stmt4.close();
			} else {
				System.out.println("Authorization failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String processValues(String[] values) {
		String ret = "";
		for (String s : values) {
			if (isInteger(s)) {
				ret += s + ",";
			} else {
				ret += "\'" + s + "\',";
			}
		}
		return ret;
	}
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public static void selectStar(String tableName) {
		int hasPriv = 0;
		int isOwner = 0;
		String role = null;
		ArrayList<String> attributes = new ArrayList<String>();
		try {	
			String query1 = "SELECT * FROM USERROLES WHERE USERNAME = " + "\'" + currUser + "\'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query1);
			//System.out.println(query1);
			while (rs.next()) {
				role = rs.getString("ROLENAME");
				String query2 = "SELECT * FROM ROLEPRIVILEGES WHERE ROLENAME = " + "\'" + role + "\' AND TABLENAME = " + "\'" + tableName + "\'";
				//System.out.println(query2);
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery(query2);
				while (rs2.next()) {
					String privilege = rs2.getString("PRIVNAME");
					if (privilege.equals("SELECT") && hasPriv == 0) {
						hasPriv = 1;
						break;
					}
				}
				rs2.close();
				stmt2.close();
				if (hasPriv == 1) {
					break;
				}
			}
			rs.close();
			stmt.close();
			stmt = con.createStatement();
			rs = stmt.executeQuery(query1);
			while (rs.next()) {
				role = rs.getString("ROLENAME");
				String query2 = "SELECT * FROM " + tableName + " WHERE OWNER = \'" + role + "\'";
				//System.out.println(query2);
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery(query2);
				if (rs2.next()) {
					isOwner = 1;
					break;
				}
				rs2.close();
				stmt2.close();
			}
			rs.close();
			stmt.close();
			if (hasPriv == 1) {
				tableName = tableName.toUpperCase();
				switch (tableName) {
					case "MOVIE":
						attributes.add("movie_title");
						attributes.add("genre");
						attributes.add("encryptedColumn");
						attributes.add("owner");
						break;
					case "AWARDS_EVENT":
						attributes.add("event_name");
						attributes.add("venue");
						attributes.add("encryptedColumn");
						attributes.add("owner");
						break;
					case "NOMINATION":
						attributes.add("event_name");
						attributes.add("movie_title");
						attributes.add("category");
						attributes.add("won");
						attributes.add("encryptedColumn");
						attributes.add("owner");
						break;
					case "USERS":
						attributes.add("username");
						attributes.add("password");
						break;
					case "ROLES":
						attributes.add("roleName");
						attributes.add("encryptionKey");
						break;
					case "USERROLES":
						attributes.add("userName");
						attributes.add("roleName");
						break;
					case "ROLEPRIVILEGES":
						attributes.add("roleName");
						attributes.add("tableName");
						attributes.add("privName");
						break;
				}
				String query3 = "SELECT * FROM " + tableName;
				Statement stmt3 = con.createStatement();
				ResultSet rs3 = stmt3.executeQuery(query3);
				int printedAtt = 0;
				while (rs3.next()) {
					String name = "";
					if (printedAtt == 0) {
						for (int i = 0; i < attributes.size(); i ++) {
							String attName = attributes.get(i);
							if (!attName.equals("encryptedColumn") && !attName.equals("owner")) {
								name += attName + ", ";
							}
						}
						name = name.substring(0, name.length()-2);
						System.out.println(name);
						printedAtt = 1;
					} 
					String value = "";
					int key = 0;
					//System.out.println(isOwner);
					for (int i = 0; i < attributes.size(); i ++) {
						String att = rs3.getString(attributes.get(i));
						int colNo = rs3.getInt("encryptedColumn");
						if (i+1 == colNo && isOwner == 1) {
							// find the enc key
							String query4 = "SELECT * FROM ROLES WHERE ROLENAME = \'" + role + "\'";
							//System.out.println(query4);
							Statement stmt4 = con.createStatement();
							ResultSet rs4 = stmt4.executeQuery(query4);
							if (rs4.next()) {
								key = rs4.getInt("ENCRYPTIONKEY");
							}
							att = decrypt(att, key);
							rs4.close();
							stmt4.close();
						}
						if (!attributes.get(i).equals("encryptedColumn") && !attributes.get(i).equals("owner")) {
							value += att + ", ";
						}
					}

					System.out.println(value.substring(0, value.length()-2));
				}
				rs3.close();
				stmt3.close();
			} else {
				System.out.println("Authorization failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String encrypt(String col, int enc) {
		char[] c = col.toCharArray();
		for (int i = 0; i < c.length; i ++) {
			c[i] += enc;
		}
		return new String(c);
	}
	public static String decrypt(String col, int dec) {
		char[] c = col.toCharArray();
		for (int i = 0; i < c.length; i ++) {
			c[i] -= dec;
		}
		return new String(c);
	}
	public static void main( String [] args ) {
		RBAC rbac = new RBAC();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			//BufferedReader br = new BufferedReader(new FileReader("testcases.txt"));
			String input = null;
			//Scanner scanner = new Scanner(System.in);
			while (true) {
			//while (scanner.hasNext() && (input = br.readLine()) != null) {
				//System.out.println(input);
				//scanner = new Scanner(System.in);
				input = br.readLine();
				String[] com = input.split(" ");
				if (input.indexOf("LOGIN") == 0 && com.length == 3) {
					login(com[1], com[2]);
				}
				if (input.indexOf("CREATE ROLE") == 0 && com.length == 4) {
					createRole(com[2], com[3]);
				} 
				if (input.indexOf("CREATE USER") == 0 && com.length == 4) {
					createUser(com[2], com[3]);
				}
				if (input.indexOf("GRANT ROLE") == 0 && com.length == 4) {
					grantRole(com[2], com[3]);
				} 
				if (input.indexOf("SELECT *") == 0 && com.length == 4) {
					selectStar(com[3]);
				}
				if (input.indexOf("GRANT PRIVILEGE") == 0 && com.length == 7) {
					grantPrivilege(com[2], com[4], com[6]);
				}
				if (input.indexOf("REVOKE PRIVILEGE") == 0 && com.length == 7) {
					revokePrivilege(com[2], com[4], com[6]);
				}
				if (input.indexOf("INSERT INTO") == 0 && com.length >= 7) {
					int l = input.indexOf("(");
					int r = input.indexOf(")");
					if (l >= 0 && r >= 0 && r - l > 0) {
						String values = input.substring(l+1, r);
						int colNo = Integer.parseInt(com[com.length-2]);
						insertInto(com[2], values, colNo, com[com.length-1]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
