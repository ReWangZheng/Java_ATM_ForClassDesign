package com.wz.Interface;

import com.wz.bean.User;

public interface UserDao {
	public void insert(String username,String passwd,String realname,double balance); //ע���˺�ʱʹ��
	public void updatePasswd(String username,String newpasswd);  //�޸�����ʱʹ��						  
	public void updatebalance(String username,double balance);	 //�������ʱʹ��
	public User querryMessage(String username,String passwd);    //��ѯʱ��ʹ��
	public boolean userIsExist(String passwd);//ע��ʱʹ�ã��жϸ��˺��Ƿ��Ѿ�����
	
}