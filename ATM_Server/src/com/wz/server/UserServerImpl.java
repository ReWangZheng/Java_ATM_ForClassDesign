package com.wz.server;

import com.wz.Interface.UserDao;
import com.wz.Interface.UserServer;
import com.wz.bean.User;
import com.wz.dao.UserDaoImpl;

public class UserServerImpl implements UserServer {
	UserDao dao=new UserDaoImpl();
	public boolean register(User user) {
		if(dao.userIsExist(user.getUsername()))
		{
			return false;
		}else
		{
			//�����ݿ��в�������
			dao.insert(user.getUsername(), user.getPasswd(), user.getRealname(), user.getBalance());
			//���²�����¼��
			OperationServerImpl.update(user, OperationServerImpl.REGISTER_OPERATION, 
											 null, null, null);
			return true;
		}
	}

	public User querry(String username, String passwd) {
			//������ؿգ���˵���˺Ż����û���������
			return dao.querryMessage(username, passwd);
	}

	
	public boolean drawMoney(User user,double hmoney) {
		//�Ȼ�ȡ��ǰ���
		double current=user.getBalance();
		//�����ǰ���С���û����,ֱ�ӷ���false
		if(current<hmoney)
		{
			return false;
		}else {
			//��������������������������������û����
			user.setBalance(current-hmoney);
			//���Ҹ���user��
			dao.updatebalance(user.getUsername(),user.getBalance());
			//���Ҹ���operation��¼��
			OperationServerImpl.update(user, OperationServerImpl.DRAWMONEY_OPERATION, 
											 hmoney, null, null);
		}
		return true;
	}

	public boolean transfer(User from, String to,double hmoney) {
		//�õ���ǰ���
		double current=from.getBalance();
		//�����жϣ������ǰ���С��ת�������߱�ת�˵��˺Ų����ڻ��߱�ת�˵��˺ž��ǵ�ǰ�˺ţ���ô��ֱ�ӷ���false
		if(current<hmoney || !dao.userIsExist(to) || from.getUsername().equals(to))
		{
			return false;
		}else {
			//�����������������
			
			//���µ�ǰuser�����
			from.setBalance(current-hmoney);
			//����user��
			dao.updatebalance(from.getUsername(),from.getBalance());
			
			dao.updatebalance(to, dao.queryBalanceByname(to)+hmoney);
			//����operation��¼������ʹ����updatebalance�����ط�������Ϊ�޷�����˺�to��User����
			OperationServerImpl.update(from, OperationServerImpl.TRANSFER_OPERATION, 
											 hmoney, to, null); //�˴��Ǹ��µ�ǰuser�����operation��¼
			
			OperationServerImpl.update(to, OperationServerImpl.GETMONEY_OPERATION, 
					 hmoney, null, from.getUsername()); //�˴��Ǹ��±�ת�˵�user��operation��¼
			return true;
		}
		
	}

	public void saveMoney(User user, double money) {
		if(dao.userIsExist(user.getUsername()))
		{	
			//����user�����
			user.setBalance(user.getBalance()+money);
			//����user��
			dao.updatebalance(user.getUsername(), user.getBalance());
			//����operation��¼��
			OperationServerImpl.update(user, OperationServerImpl.SAVEMONEY_OPERATION, user.getBalance(), null, null);
		}
		
	
		
	}

	public boolean updatePasswd(User user, String newpasswd) {
		if(dao.userIsExist(user.getUsername()))
		{
			dao.updatePasswd(user.getUsername(), newpasswd);
			return true;
		}
		return false;
	}

	
}
