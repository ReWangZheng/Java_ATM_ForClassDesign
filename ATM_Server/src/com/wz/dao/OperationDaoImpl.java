package com.wz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.wz.tool.dbPool;

public class OperationDaoImpl implements com.wz.Interface.OperationDao 
{
	//�˹�����Ĳ�����������UseDao��������Ĳ��������,�����UseDao����̰߳�ȫ������Ҳ��û��Ҫ�ٴ�����
	
	private dbPool db=new dbPool(); //��ȡ���ݿ����ӳ�
	//���������ע���ʱ����ã���Ϊ�˺ų�ʼ��һ��operation
	public void InitOperation(String username)
	{
		Connection con=db.getConenction();
		PreparedStatement ps=null;
		try {
			ps=con.prepareStatement("insert into user_operation values(?,null)");
			ps.setString(1, username);
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
	
	//��������Ϣд��operation����
	public void OperationUpdate(String username, String OpeMessage) {
		Connection con=db.getConenction();
		PreparedStatement ps=null;
		try {
			ps=con.prepareStatement("update user_operation set operation=concat_ws(';#;#',operation,?) where username=?");
			ps.setString(2, username);
			ps.setString(1, OpeMessage);
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
		
}		
