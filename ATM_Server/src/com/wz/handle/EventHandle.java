package com.wz.handle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Timer;

import com.sun.corba.se.impl.legacy.connection.SocketFactoryConnectionImpl;
import com.wz.Interface.UserServer;
import com.wz.bean.User;
import com.wz.network.NetServer;
import com.wz.server.OperationServerImpl;
import com.wz.server.UserServerImpl;
import com.wz.tool.MyHolder;
import com.wz.tool.timeUtil;

/*�¼���������*/
public class EventHandle {
	private UserServer userServer;
	private OperationServerImpl operationserver=new OperationServerImpl();
	private final String EVENT_REGISTER="REGISTER";
	private final String EVENT_LOGIN="LOGIN";
	private final String EVENT_SAVEMONEY="SAVEMONEY";
	private final String EVENT_TRANSFER="TRANSFER";
	private final String EVENT_UPDATEPASSWD="UPDATEPASSWD";
	private final String EVENT_DRAWMONEY="DRAWMONEY";
	private final String EVENT_OPERATION="OPERATION";
	private final String REGEX=":#:#";
	public EventHandle()
	{
		userServer=new UserServerImpl();
	}
	public void sendSign(String sign,SelectionKey key)
	{
		String callback=null; //��ʼ������ֵ
		String split[]=sign.split(REGEX); //���źŽ��д���
		System.out.println(timeUtil.getTime()+"     ��������:"+sign);
		switch(split[0])
		{
			case EVENT_REGISTER://ע���¼�
				callback=Register(split[1],split[2],split[3],Double.parseDouble(split[4]));
				CallBack(key, callback);
				break;
			case EVENT_LOGIN://��½�¼�
				key.attach(split[1]);
				callback=login(split[1],split[2],key);
				CallBack(key,callback);
				break;
			case EVENT_SAVEMONEY://��Ǯ�¼�
				callback=save(split[1], Double.parseDouble(split[2]));
				CallBack(key, callback);
				break;
			case EVENT_UPDATEPASSWD://�޸������¼�
				callback=updatePasswd(split[1], split[2]);
				CallBack(key,callback);
				break;
			case EVENT_TRANSFER://ת���¼�
				callback=transfer(split[1], split[2], Double.parseDouble(split[3]));
				CallBack(key,callback);
				break;
			case EVENT_DRAWMONEY://ȡǮ�¼�
				callback=drawmoney(split[1],Double.parseDouble(split[2]));
				CallBack(key,callback);
				break;
			case EVENT_OPERATION://ȡǮ�¼�
				callback=querryOperation(split[1]);
				CallBack(key,callback);
				break;
		}
	}
	
	//ȡǮ�¼�
	private String drawmoney(String username,  double money)
	{
		boolean result=userServer.drawMoney(username, money);
		if(result)
		{
			return "DRAWMONEY"+REGEX+"OK"+REGEX+userServer.querryBalance(username);
		}else {
			return "DRAWMONEY"+REGEX+"NO";
		}
	}
	//ע���¼�
	public String Register(String username, String passwd, String realname,double balance)
	{
		User user=User.CreateUser(username, passwd, realname, balance);
		boolean result=userServer.register(user);
		if(result)
		{
			return "REGISTER"+REGEX+"YES"+REGEX+user.getUsername()+REGEX+user.getPasswd()+REGEX+user.getRealname()+REGEX+user.getBalance();
		}else {
			return "REGISTER"+REGEX+"NO";
		}
	}
	//��½�¼�
	public String login(String username,String passwd,SelectionKey key)
	{

		User user=userServer.querry(username, passwd);
		if(user!=null)
		{
			if (MyHolder.getKeyList().IsOnline(username))
			{
				return "LOGIN"+REGEX+"NO"+REGEX+"ISONLINE";
			}else
			{
				MyHolder.getKeyList().add(key);
			}
			
			System.out.println(timeUtil.getTime()+"     ���û�����ϵͳ,��ǰ������:"+MyHolder.getKeyList().size()); //���Դ���
			return "LOGIN"+REGEX+"YES"+REGEX+user.getUsername()+REGEX+user.getPasswd()+REGEX+user.getRealname()
			+REGEX+user.getBalance();
		}
		return "LOGIN"+REGEX+"NO";
	}
	
	//��Ǯ�¼�
	public String save(String username,double money)
	{
		userServer.saveMoney(username, money);
		return "SAVEMONEY"+REGEX+"OK"+REGEX+userServer.querryBalance(username);
	}
	
	//�����źŷ���
	public void CallBack(SelectionKey Key,String callback)
	{
		SocketChannel socketChannel=(SocketChannel)Key.channel();
		try {
			socketChannel.write(ByteBuffer.wrap(callback.getBytes()));
		} catch (IOException e) {
			if(Key!=null)
			{
				Key.cancel();
			}
		}
	}
	//�޸������¼�
	public String updatePasswd(String username,String passwd)
	{
		boolean result=userServer.updatePasswd(username, passwd);
		if(result)
		{
			return "UPDATEPASSWD"+REGEX+"OK";
		}else {
			return "UPDATEPASSWD"+REGEX+"NO";
		}
	}
	//ת���¼�
	public String transfer(String from,String to,double money)
	{
		System.out.println(from +"ת�˸�"+to +"  "+money+"Ԫ");
		boolean result=userServer.transfer(from, to, money);
		if(result)
		{
			if (MyHolder.getKeyList().IsOnline(to)) {
				NotifyUser(to);
			}
			return "TRANSFER"+REGEX+"OK"+REGEX+userServer.querryBalance(from);
		}else {
			return "TRANSFER"+REGEX+"NO";
		}
	}
	//��ѯ������¼�¼�
	public String querryOperation(String username)
	{
		String operaiton=userServer.getOperation(username);
		return "OPERATION"+REGEX+"OK"+REGEX+operaiton;
	}
	//����û����ߣ�֪ͨ�û��յ�ת��
	public void NotifyUser(String username)
	{
		SocketChannel socketChannel=(SocketChannel) MyHolder.getKeyList().getUserKey(username).channel();
		try {
			String newBalance=userServer.querryBalance(username)+"";
			socketChannel.write(ByteBuffer.wrap(("GETMONEY"+REGEX+newBalance).getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
