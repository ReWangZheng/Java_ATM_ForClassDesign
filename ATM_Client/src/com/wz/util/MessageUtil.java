package com.wz.util;

public class MessageUtil {
	//client.sendMessage("REGISTER:#:#text03:#:#woainma..:#:#������:#:#95636");
	//client.sendMessage("LOGIN:#:#text0008:#:#woainima..");
	//client.sendMessage("SAVAMONEY:#:#text03:#:#85615");
	final public  static  String REGEX=":#:#"; //�ָ���
	final public  static  String OERATION_REGEX=";#;#"; //�ָ���
	
	public static String loginSgin(String username,String passwd) //�õ���½�ź�
	{
		return "LOGIN"+REGEX+username+REGEX+passwd;
	}
	
	public static String saveMoneySign(String username,double money)//�õ���Ǯ�ź�
	{
		return "SAVEMONEY"+REGEX+username+REGEX+money;
	}
	public static String registerSign(String username,String passwd,String realname,double balance)//�õ�ע���ź�
	{
		return "REGISTER"+REGEX+username+REGEX+passwd+REGEX+realname+REGEX+balance;
	}
	
	public static String transferSign(String from,String to,double money)//�õ�ת���ź�
	{
		return "TRANSFER" +REGEX+from+REGEX+to+REGEX+money;
	}
	
	public static String drawmoneySign(String username,double money)//�õ�ȡǮ�ź�
	{
		return "DRAWMONEY" +REGEX+username+REGEX+money;
	}
	
	public static String updatepasswdSign(String username,String newpasswd)//�õ��޸������ź�
	{
		return "UPDATEPASSWD" +REGEX+username+REGEX+newpasswd;
	}
	
	public static String querryOperationSign(String username)//�õ��޸������ź�
	{
		return "OPERATION" +REGEX+username;
	}
	
}
