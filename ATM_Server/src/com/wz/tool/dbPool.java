package com.wz.tool;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import com.wz.Exception.OutOfRangeException;
import sun.font.CreatedFontTracker;

//���ݿ����ӳأ����ٴ�����������ʱ��
public class dbPool {
	private int SIZE_MAX=6; // ���ӳ��������������
	private ArrayList<Connection> pool=new ArrayList<>(); //����������ӵ�˳���
	private int currentSize=0;   //��¼��ǰ���ӳ��е�����
	//Ĭ�Ϲ�����
	public dbPool()
	{
		//�Ƚ����ӳ������1/3
		for(int i=0;i<SIZE_MAX /3 ;i++)
		{
			pool.add(CreatProxyConnection());
			currentSize++;
		}
	}
	
	//����ָ�����������ӳ�
	public dbPool(int size,int currentsize) throws OutOfRangeException
	{
		this.SIZE_MAX=size;
		this.currentSize=currentsize;
		if(this.currentSize>this.SIZE_MAX)
		{
			throw new OutOfRangeException(); //�׳�������Χ���쳣
		}
		
		for(int i=0;i<currentSize ;i++)
		{
			pool.add(CreatProxyConnection());
		}
	}
	
	//�޸����ӳ��е�������������ķ���
	public void resetMaxcap(int max)
	{
		this.SIZE_MAX=max;
	}
	
	//��õ�ǰ���ӳ�ʣ�����������ķ���
	public int getCurrentSize()
	{
		return pool.size();
	}
	
	//ȡ����,Ҫ��֤�̰߳�ȫ
	public synchronized Connection getConenction()
	{
		//������ӳز�Ϊ�յĻ�,ֱ��ȡ������
		if(!pool.isEmpty())
		{
			int last=pool.size()-1;
			return pool.remove(last);
		}
		
		//������ӳ�Ϊ�յĻ�,�ʹ���һ�����ӷ��س�ȥ
		return CreatProxyConnection();
		
	}
	private synchronized void releaseCon(Connection con)
	{
		currentSize=pool.size();
		//�����ǰ���ӳ��е�������С����������ͽ����ӷŵ����ӳ���,����������رյ�����
		if(currentSize<SIZE_MAX)
		{
			pool.add(con);
		}else {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//����������̬���������
	private  Connection CreatProxyConnection()
	{

		//ͨ����̬������Connection�е�close���������Ż�
		Connection con=dbUtil.CreateConnection();
		Connection proCon=(Connection)Proxy.newProxyInstance(con.getClass().getClassLoader(),con.getClass().getInterfaces(),new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if(method.getName().equals("close")) {
					releaseCon(con);
					return null;
				}else {
					return method.invoke(con, args);
				}
			}
		});
		return proCon;
		
	}
	
	

}
