package com.wz.handle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import com.wz.Interface.UserServer;
import com.wz.bean.User;
import com.wz.network.NetServer;
import com.wz.server.UserServerImpl;

/*�¼���������*/
public class EventHandle {
	private UserServer userServer;
	private NetServer netServer;
	private final String EVENT_REGISTER="REGISTER";
	private final String EVENT_LOGIN="LOGIN";
	private final String EVENT_SAVEMONEY="SAVEMONEY";
	private final String EVENT_TRANSFER="TRANSFER";
	private final String EVENT_UPDATEPASSWD="UPDATEPASSWD";
	private final String EVENT_DRAWMONEY="DRAWMONEY";
	private final String REGEX=":#:#";
	public EventHandle()
	{
		userServer=new UserServerImpl();
	}
	public void sendSign(String sign,SelectionKey key)
	{
		String callback=null; //��ʼ������ֵ
		String split[]=sign.split(REGEX); //���źŽ��д���
		System.out.println(sign);
		switch(split[0])
		{
			case EVENT_REGISTER://ע���¼�
				callback=Register(split[1],split[2],split[3],Double.parseDouble(split[4]));
				CallBack(key, callback);
				break;
			case EVENT_LOGIN://��½�¼�
				callback=login(split[1],split[2]);
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
		}
	}
	
	//ȡǮ�¼�
	private String drawmoney(String username,  double money)
	{
		boolean result=userServer.drawMoney(username, money);
		if(result)
		{
			return "DRAWMONEY"+REGEX+"YES";
		}else {
			return "DRAWMONEY"+REGEX+"NO";
		}
	}
	//ע���¼�
	public String Register(String username, String passwd, String realname,double balance)
	{
		User user=User.CreateUser(username, passwd, realname, balance);
		if(user!=null)
		{
			userServer.register(user);
			return "REGISTER"+REGEX+"OK";
		}else {
			return "REGISTER"+REGEX+"NO";
		}
	}
	//��½�¼�
	public String login(String username,String passwd)
	{
		User user=userServer.querry(username, passwd);
		if(user!=null)
		{
			return "LOGIN"+REGEX+"YES"+REGEX+user.getUsername()+REGEX+user.getPasswd()+REGEX+user.getRealname()
			+REGEX+user.getBalance();
		}
		return "LOGIN"+REGEX+"NO";
	}
	
	//��Ǯ�¼�
	public String save(String username,double money)
	{
		userServer.saveMoney(username, money);
		return "SAVEMONEY"+REGEX+"OK";
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
			return "TRANSFER"+REGEX+"OK";
		}else {
			return "TRANSFER"+REGEX+"NO";
		}
	}
	
	
	
}
