package com.wz.Interface;

import com.wz.bean.User;

public interface UserServer {
	public boolean register(User user); //ע���˺ţ����ע��ɹ�����true,���ע��ʧ��,����false
	public User querry(String username,String passwd); //��ѯ
	public boolean drawMoney(User user,double money); //ȡǮ���������㹻��ȡǮ�ɹ����������ȡǮ���򷵻�false
	public boolean transfer(User from,String to,double money); //ת�ˣ����ת�˳ɹ���ȡ����true�����ʧ���򷵻�false
	public void saveMoney(User user,double money); //��Ǯ
}