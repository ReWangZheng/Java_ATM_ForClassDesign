package com.wz.Interface;

import com.wz.bean.User;

public interface UserServer {
	public boolean register(User user); //ע���˺ţ����ע��ɹ�����true,���ע��ʧ��,����false
	public User querry(String username,String passwd); //��ѯ
	public boolean drawMoney(String username,double money); //ȡǮ���������㹻��ȡǮ�ɹ����������ȡǮ���򷵻�false
	public double querryBalance(String name);
	public boolean transfer(String username,String to,double money); //ת�ˣ����ת�˳ɹ���ȡ����true�����ʧ���򷵻�false
	public void saveMoney(String username,double money); //��Ǯ
	public boolean updatePasswd(String username,String newpasswd);//��������
	public String getOperation(String username);
}