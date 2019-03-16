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
			OperationServerImpl.update(user.getUsername(), OperationServerImpl.REGISTER_OPERATION, 
											 null, null, null);
			return true;
		}
	}

	public User querry(String username, String passwd) {
			//������ؿգ���˵���˺Ż����û���������
			return dao.querryMessage(username, passwd);
	}

	
	public boolean drawMoney(String username,double hmoney) {
		//�Ȼ�ȡ��ǰ���
		double current=dao.queryBalanceByname(username);
		//�����ǰ���С���û����,ֱ�ӷ���false
		if(current<hmoney)
		{
			return false;
		}else {
			//���Ҹ���user��
			dao.updatebalance(username,current-hmoney);
			//���Ҹ���operation��¼��
			OperationServerImpl.update(username, OperationServerImpl.DRAWMONEY_OPERATION, 
											 hmoney, null, null);
		}
		return true;
	}

	public boolean transfer(String username, String to,double hmoney) {
		//�õ���ǰ���
		double current=dao.queryBalanceByname(username);
		//�����жϣ������ǰ���С��ת�������߱�ת�˵��˺Ų����ڻ��߱�ת�˵��˺ž��ǵ�ǰ�˺ţ���ô��ֱ�ӷ���false
		if(current<hmoney || !dao.userIsExist(to) || username.equals(to))
		{
			return false;
		}else {
			//�����������������
			
			//����user��
			dao.updatebalance(username,current-hmoney);
			
			dao.updatebalance(to, dao.queryBalanceByname(to)+hmoney);
			//����operation��¼����Ϊ�޷�����˺�to��User����
			OperationServerImpl.update(username, OperationServerImpl.TRANSFER_OPERATION, 
											 hmoney, to, null); //�˴��Ǹ��µ�ǰuser�����operation��¼
			
			OperationServerImpl.update(to, OperationServerImpl.GETMONEY_OPERATION, 
					 hmoney, null,username); //�˴��Ǹ��±�ת�˵�user��operation��¼
			return true;
		}
		
	}

	public void saveMoney(String username, double money) {
		if(dao.userIsExist(username))
		{	
			//����user��
			dao.updatebalance(username, dao.queryBalanceByname(username)+money);
			//����operation��¼��
			OperationServerImpl.update(username, OperationServerImpl.SAVEMONEY_OPERATION, money, null, null);
		}
		
	
		
	}

	public boolean updatePasswd(String username, String newpasswd) {
		if(dao.userIsExist(username))
		{
			dao.updatePasswd(username, newpasswd);
			return true;
		}
		return false;
	}

	public double querryBalance(String name) {
		
		return dao.queryBalanceByname(name);
	}

	public String getOperation(String username) {
		return dao.getOperation(username);
	}

	
}
