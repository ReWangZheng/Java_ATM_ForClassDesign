package com.wz.dao;

import java.sql.Connection;
import java.sql.SQLException;

import java.sql.*;
import com.wz.Interface.UserDao;
import com.wz.bean.User;
import com.wz.tool.Md5Util;
import com.wz.tool.dbPool;

public class UserDaoImpl implements UserDao {
	private dbPool dbpool=new dbPool();
	public void insert(String username, String passwd, String realname, double balance) {
		
		passwd=Md5Util.ToMd5code(passwd); //������ת��һ��
		Connection con=dbpool.getConenction(); //�õ�����
		PreparedStatement ps=null;
		//����Ϊ�������
		try {
			ps=con.prepareStatement("insert into user values(null,?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, passwd);
			ps.setString(3,realname);
			ps.setDouble(4,balance);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void updatePasswd(String username, String newpasswd) {
		Connection con=dbpool.getConenction(); //��ȡ����
		newpasswd=Md5Util.ToMd5code(newpasswd); //������ת��һ��
		PreparedStatement ps=null;
		//����Ϊupdate����������
		try {
			ps=con.prepareStatement("update user set passwd=? where username=?");
			ps.setString(1, newpasswd);
			ps.setString(2, username);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void updatebalance(String username, double balance) {
		Connection con=dbpool.getConenction(); //��ȡ����
		PreparedStatement ps=null;
		//����Ϊupdate���������
		try {
			ps=con.prepareStatement("update user set balance=? where username=?");
			ps.setDouble(1, balance);
			ps.setString(2, username);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public User querryMessage(String username, String passwd) {
		Connection con=dbpool.getConenction();
		passwd=Md5Util.ToMd5code(passwd);
		ResultSet rs=null;
		PreparedStatement ps=null;
		User user=null;
		String realname=null;
		double balance=0;
		try {
			ps=con.prepareStatement("select * from user where username=? and passwd=?");
			ps.setString(1, username);
			ps.setString(2, passwd);
			rs=ps.executeQuery();
			while(rs.next())
			{
				realname=rs.getString(4);
				balance=rs.getDouble(5);
				user=User.CreateUser(username, passwd, realname, balance);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	public boolean userIsExist(String username) {
		Connection con=dbpool.getConenction();
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean isExist=false; //�����洢����ֵ
		try {
			
			ps=con.prepareStatement("select username from user where username=?");
			ps.setString(1, username);
			rs=ps.executeQuery();
			
			isExist=rs.next(); //����ѯ�����ݣ�����ڣ���û�в�ѯ�����ݣ��򲻴���
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return isExist;
	}
	@Override
	public double queryBalanceByname(String name) {
		Connection con=dbpool.getConenction();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			
			ps=con.prepareStatement("select balance from user where username=?");
			ps.setString(1, name);
			rs=ps.executeQuery();
			if(rs.next())
			{
				return rs.getDouble(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		

		
		return -100;
	}
	public String ToRealName(String username) {
		Connection con=dbpool.getConenction();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			
			ps=con.prepareStatement("select realname from user where username=?");
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next())
			{
				return rs.getString(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public String getOperation(String username) {
		Connection connection=dbpool.getConenction();
		PreparedStatement ps=null;
		ResultSet rs=null;
			try {
				ps=connection.prepareStatement("select * from user_operation where username=?");
				ps.setString(1, username);
				rs=ps.executeQuery();
				if (rs.next()) {
					return rs.getString(2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					rs.close();
					ps.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			}
	
			
		
			
		
		return null;
	}
	
	

}
