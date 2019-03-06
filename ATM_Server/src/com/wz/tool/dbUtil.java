package com.wz.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class dbUtil {
	//JDBC�������ļ�
	private static Properties pro=new Properties();

	//ע��sql����
	static {
		try 
		{
			//���������ļ�
			pro.load(new FileInputStream(new File("jdbc_Config.properties")));
			//��̬�ļ���ָ����
			Class.forName(pro.getProperty("jdbc.driver"));			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	//��ȡ���ݿ����ӵķ���
	public static Connection CreateConnection()
	{
		Connection con=null;
		try 
		{
			con=DriverManager.getConnection(pro.getProperty("jdbc.url"), pro.getProperty("jdbc.username"), pro.getProperty("jdbc.passwd"));
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return null;
	}
}