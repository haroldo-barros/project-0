package bankingApplication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bankingApplication.model.JointAccount;
import bankingApplication.model.SingleAccount;
import bankingApplication.util.ConnectionUtil;
import bankingApplication.util.ScannerUtil;

public class UserDao {
	
	public void persistSingle(SingleAccount s) {
		
		int accountId = -1;
		int userId = -1;
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String userQuery = "insert into users (name, username, password) " 
			+ "values(?,?,?)";
			PreparedStatement pstUser = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
			
			pstUser.setString(1, s.getName());
			pstUser.setString(2, s.getUsername());
			pstUser.setString(3, s.getPassword());
			pstUser.executeUpdate();
			ResultSet rsUser = pstUser.getGeneratedKeys();
			if(rsUser.next()) {
				userId = rsUser.getInt(1);
			}
			
			
			String acntQuery = "insert into account (balance, accounttype)"
					+ "values (?,?)";
			PreparedStatement pstAcnt = conn.prepareStatement(acntQuery, Statement.RETURN_GENERATED_KEYS);
			
			pstAcnt.setDouble(1, s.getBalance());
			pstAcnt.setString(2, s.getAccountType());
			pstAcnt.executeUpdate();
			ResultSet rsAcnt = pstAcnt.getGeneratedKeys();
			
			if(rsAcnt.next()) {
				accountId = rsAcnt.getInt(1);
			}
			
			if(accountId > 0 && userId > 0) {
				
				String mapQuery = "insert into mapping (userid, accountid) values (?,?)";
				PreparedStatement pstMap = conn.prepareStatement(mapQuery);
				pstMap.setInt(1, userId);
				pstMap.setInt(2, accountId);
				pstMap.executeUpdate();
				
			}else {
				conn.rollback();
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void persistJoint(List<JointAccount> j, double balance, String type) {
		
		int accountId = -1;
		List<Integer> userId = new ArrayList<>();
		int counter = 0;
		//List<Integer> userId = null;
		
		//System.out.println(j.toString());
		//int accnt = ScannerUtil.getInput();
		
		String userQuery = "insert into users (name, username, password) " 
				+ "values(?,?,?)";
		String acntQuery = "insert into account (balance, accounttype)"
				+ "values (?,?)";
		String mapQuery = "insert into mapping (userid, accountid) values (?,?)";
		
		try (Connection conn = ConnectionUtil.getConnection()){
			
			for(JointAccount l : j) {

				PreparedStatement pstUser = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
						
				pstUser.setString(1, l.getName());
				pstUser.setString(2, l.getUsername());
				pstUser.setString(3, l.getPassword());
				pstUser.executeUpdate();
				ResultSet rsUser = pstUser.getGeneratedKeys();
				if(rsUser.next()) {
					userId.add(rsUser.getInt(1));
					
				}
			}
				
				PreparedStatement pstAcnt = conn.prepareStatement(acntQuery, Statement.RETURN_GENERATED_KEYS);
				
				pstAcnt.setDouble(1, balance);
				pstAcnt.setString(2, type);
				pstAcnt.executeUpdate();
				ResultSet rsAcnt = pstAcnt.getGeneratedKeys();
				
				if(rsAcnt.next()) {
					accountId = rsAcnt.getInt(1);
				}
				
				
				System.out.println(userId.toString());
				System.out.println(userId);
				for(JointAccount l : j) {
					
					if(accountId > 0 && userId.get(counter) > 0) {
						PreparedStatement pstMap = conn.prepareStatement(mapQuery);
						pstMap.setInt(1, userId.get(counter));
						pstMap.setInt(2, accountId);
						pstMap.executeUpdate();
						++counter;
						
					}else {
						conn.rollback();
					}
				}
				
			conn.close();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public boolean validateLogin(String username, String password) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String validUser = "select username, password from users where (username) = (?)";
			PreparedStatement pstUser = conn.prepareStatement(validUser);
			
			pstUser.setString(1, username);
			
			ResultSet rsUser = pstUser.executeQuery();
			
			String orUname = "", orPass = "";
			String type = "";
		    while (rsUser.next()) {
		        orUname = rsUser.getString("username");
		        orPass = rsUser.getString("password");
		    } 
		    if (orPass.equals(password)) {
		    	System.out.println("user found");
		    	
		    	rsUser.close();
		        return true;
		    } else {
		    	System.out.println("record Not found");
		    	return false;
		    }
		    
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String getAccountType(String username, String password) {
		try (Connection conn = ConnectionUtil.getConnection()) {	
			String typeSql = "select * from account ac "
					+ "join mapping m on ac.id = m.accountid "
	    			+ "join users u on ac.id = m.id "
	    			+ "where u.username = (?) AND u.password = (?); ";
			
			
	    	PreparedStatement pstType = conn.prepareStatement(typeSql);
	    	pstType.setString(1, username);
	    	pstType.setString(2, password);
	    	//int num = pstType.executeUpdate();
	    	ResultSet rsAcntType =pstType.executeQuery();
	    	
	    	String type = "";
	    	if(rsAcntType.next()) {
	    		type = rsAcntType.getString("accounttype");
	    	}
	    	//rsAcntType.getstr
	    	//String type = "";
	    	//while(rsAcntType.next()) {
	    		//String type = rsAcntType.getString("accounttype");
	    	//}
	    	
	    	return type;
	    	
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SingleAccount userSingleAccount(String user, String pass) {
		
		try (Connection connection = ConnectionUtil.getConnection()) {

			int accountId= 0; 
			String name ="" ;
			String username="";
			String password="";
			double balance=0;
			String actType="";
			
			String sql = "select name, username, password,accountid, balance, accounttype from account ac "
					+ "join mapping m on ac.id = m.accountid "
					+ "join users u on u.id = m.userid "
					+ "where u.username = (?) AND u.password = (?);";
			
			PreparedStatement pst = connection.prepareStatement(sql);
			
			pst.setString(1, user);
			pst.setString(2, pass);
			
			ResultSet resultSet = pst.executeQuery();
			
			
			while (resultSet.next()) {
				name = resultSet.getString("name");
				username = resultSet.getString("username");
				password = resultSet.getString("password");
				accountId = resultSet.getInt("accountid");
				balance = resultSet.getDouble("balance");
				actType = resultSet.getString("accounttype");	
			}

			SingleAccount users = new SingleAccount(name, username, password, accountId, balance, actType);
			return users;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	
	public JointAccount userJointAccount(String user, String pass) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			
			int accountId= 0; 
			String name ="" ;
			String username="";
			String password="";
			double balance=0;
			String actType="";
			
			String sql = "select name, username, password,accountid, balance, accounttype from account ac "
					+ "join mapping m on ac.id = m.accountid "
					+ "join users u on u.id = m.userid "
					+ "where u.username = (?) AND u.password = (?);";
			
			PreparedStatement pst = connection.prepareStatement(sql);
			
			pst.setString(1, user);
			pst.setString(2, pass);
			
			ResultSet resultSet = pst.executeQuery();
			
			
			while (resultSet.next()) {
				name = resultSet.getString("name");
				username = resultSet.getString("username");
				password = resultSet.getString("password");
				accountId = resultSet.getInt("accountid");
				balance = resultSet.getDouble("balance");
				actType = resultSet.getString("accounttype");	
			}

			JointAccount users = new JointAccount(name, username, password, accountId, balance, actType);
			return users;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}

	public void updateData(SingleAccount ac) {
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String query = "update account set balance = (?)" //+ ac.getBalance()
			+ " where id = (?)"; //+ ac.getAccountID();  
			
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setDouble(1, ac.getBalance());
			pst.setDouble(2, ac.getAccountID());
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateData(JointAccount ac) {
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String query = "update account set balance = (?)" //+ ac.getBalance()
			+ " where id = (?)"; //+ ac.getAccountID();  
			
			PreparedStatement pst = conn.prepareStatement(query);
			pst.setDouble(1, ac.getBalance());
			pst.setDouble(2, ac.getAccountID());
			pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void viewBalance(SingleAccount ac) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "select username,accountid, balance from account ac "
					+ "join mapping m on ac.id = m.accountid "
					+ "join users u on u.id = m.userid "
					+ "where accountid = (?) and username = (?);";

			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, ac.getAccountID());
			statement.setString(2, ac.getUsername());
			
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
		
				System.out.println("-------------- User -------------");
				System.out.println("| ID |   User Name    |  Balance |");
				System.out.printf("| %2d | %-14s | %7.2f  |%n", 
							resultSet.getInt("accountid"), resultSet.getString("username"), 
							resultSet.getDouble("balance"));
				System.out.println("----------------------------------");
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public void viewBalance(JointAccount ac) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "select username,accountid, balance from account ac "
					+ "join mapping m on ac.id = m.accountid "
					+ "join users u on u.id = m.userid "
					+ "where accountid = (?) and username = (?);";

			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, ac.getAccountID());
			statement.setString(2, ac.getUsername());
			
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
		
				System.out.println("-------------- User -------------");
				System.out.println("| ID |   User Name    |  Balance |");
				System.out.printf("| %2d | %-14s | %7.2f  |%n", 
							resultSet.getInt("accountid"), resultSet.getString("username"), 
							resultSet.getDouble("balance"));
				System.out.println("----------------------------------");
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	
	public void deleteAccount(SingleAccount ac) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			//  get the users table id
			String sqlUsersTable ="select users.id from users "
			+ "join mapping on users.id = mapping.userid "
			+ "join account on account.id = mapping.accountid "
			+ "where account.id = (?);";
			
			PreparedStatement pstUsersTable = conn.prepareStatement(sqlUsersTable);
			pstUsersTable.setInt(1, ac.getAccountID());
			
			int usersId = 0;
			ResultSet rsUsersTable = pstUsersTable.executeQuery();
			if(rsUsersTable.next()) {
				usersId = rsUsersTable.getInt("id");
			}
			
			//  delete row from account id
			String sqlDeleteAcntId = "delete from account where id = (?)";
			PreparedStatement pstDeleteAcntId = conn.prepareStatement(sqlDeleteAcntId);
			pstDeleteAcntId.setInt(1, ac.getAccountID());
			pstDeleteAcntId.executeUpdate();
			
			
			// delete row from users id
			String sqlDeleteUsersId = "delete from users where id = (?);";
			
			PreparedStatement pstDeleteUsersId = conn.prepareStatement(sqlDeleteUsersId);
			pstDeleteUsersId.setInt(1, usersId);
			pstDeleteUsersId.executeUpdate();
						
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public void deleteAccount(JointAccount ac) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
//			String sql ="delete users, account "
//					+ "from users u join mapping m on u.id = m.userid "
//					+ "join account ac on ac.id = m.accountid "
//					+ "where ac.id = (?);";
			System.out.println("in delete joint account");
//			PreparedStatement pst = conn.prepareStatement(sql);
//			pst.setInt(1, ac.getAccountID());
//			pst.executeUpdate();
						
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	
	public boolean validateAccoutNum(int n) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			String validUser = "select id, accounttype, balance from account \r\n" + 
					"where id = (?)";
			PreparedStatement pst = conn.prepareStatement(validUser);
			
			pst.setInt(1, n);
			
			ResultSet rsValidate = pst.executeQuery();

			int num =0;
			if (rsValidate.next()) {
				num = rsValidate.getInt("id");
			}
			System.out.println(num);

		    if (num != 0) {
		    	System.out.println("account found");
		        return true;
		    } else {
		    	System.out.println("account not found");
		    	return false;
		    }
		    
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void transferMoney(SingleAccount ac, int n, double amount) {
		
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			//get money being transfer to account balance
			String strBal = "select balance from account where id = (?)";
			PreparedStatement pstBal = conn.prepareStatement(strBal);
			pstBal.setInt(1, n);;
			
	    	ResultSet rsBal =pstBal.executeQuery();
	    	
	    	double balance = 0.0;
	    	if(rsBal.next()) {
	    		balance = rsBal.getDouble("balance");
	    	}
			
			//add to account that money is being tranfer to
			String strTransfer = "update account set balance = " + (balance + amount)
					+ "where id = (?)";
			PreparedStatement pstTransfer = conn.prepareStatement(strTransfer);
			
			pstTransfer.setInt(1, n);
			pstTransfer.executeUpdate();
			
			//withdraw from current account holder
			String strUpdateAcnt = "update account set balance = " + (ac.getBalance() - amount)
					+ "where id = (?)";
			PreparedStatement pstUpdateAcnt = conn.prepareStatement(strUpdateAcnt);
			
			pstUpdateAcnt.setInt(1, ac.getAccountID());
			pstUpdateAcnt.executeUpdate();
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	
	public void transferMoney(JointAccount ac, int n, double amount) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			//get money being transfer to account balance
			String strBal = "select balance from account where id = (?)";
			PreparedStatement pstBal = conn.prepareStatement(strBal);
			pstBal.setInt(1, n);;
			
	    	ResultSet rsBal =pstBal.executeQuery();
	    	
	    	double balance = 0.0;
	    	if(rsBal.next()) {
	    		balance = rsBal.getDouble("balance");
	    	}
			
			//add to account that money is being tranfer to
			String strTransfer = "update account set balance = " + (balance + amount)
					+ "where id = (?)";
			PreparedStatement pstTransfer = conn.prepareStatement(strTransfer);
			
			pstTransfer.setInt(1, n);
			pstTransfer.executeUpdate();
			
			//withdraw from current account holder
			String strUpdateAcnt = "update account set balance = " + (ac.getBalance() - amount)
					+ "where id = (?)";
			PreparedStatement pstUpdateAcnt = conn.prepareStatement(strUpdateAcnt);
			
			pstUpdateAcnt.setInt(1, ac.getAccountID());
			pstUpdateAcnt.executeUpdate();
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
}






















