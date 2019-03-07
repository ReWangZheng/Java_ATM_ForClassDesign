package com.wz.server;
import com.wz.Interface.OperationDao;
import com.wz.bean.User;
import com.wz.dao.OperationDaoImpl;
import com.wz.tool.timeUtil;

public class OperationServerImpl {
	public final static int REGISTER_OPERATION=0;
	public final static int DRAWMONEY_OPERATION=1;
	public final static int TRANSFER_OPERATION=2;
	public final static int SAVEMONEY_OPERATION=3;
	public final static int GETMONEY_OPERATION=4;
	private final static OperationDao dao=new OperationDaoImpl();
	
	//����1:��������
	//����2:����ѡ�� ��ѡ0 1 2 3 4
	//����3:������Ǯ�ı仯������Ҫ��д�ò���
	//����4:��ת�ˣ�����Ҫ��дת�˸�˭
	//����4:���յ�ת�ˣ�����Ҫ��д�ò���
	public static void update(User user,Integer Operation,Double balance,String toname,String from)
	{
		String mes=null;
		switch(Operation)
		{
		case REGISTER_OPERATION:
			mes=timeUtil.getTime()+" : "+"��ע�����˺ţ���ӭ��ʹ�ñ�����ϵͳ";
			dao.OperationUpdate(user.getUsername(),mes);
			break;
		case DRAWMONEY_OPERATION:
			mes=timeUtil.getTime()+" : "+"��ȡ����"+balance+"Ԫ����ʣ"+user.getBalance()+"Ԫ";
			dao.OperationUpdate(user.getUsername(),mes);
			break;
		case TRANSFER_OPERATION:
			mes=timeUtil.getTime()+" : "+"����"+toname+"ת����"+balance+"Ԫ"+"����ʣ"+user.getBalance()+"Ԫ";
			dao.OperationUpdate(user.getUsername(),mes);
			break;
		case SAVEMONEY_OPERATION:
			mes=timeUtil.getTime()+" : "+"��������"+balance+"Ԫ"+"����ʣ"+user.getBalance()+"Ԫ";
			dao.OperationUpdate(user.getUsername(),mes);
			break;
		case GETMONEY_OPERATION:
			mes=timeUtil.getTime()+" : "+from+"����ת����"+balance+"Ԫ"+"����ʣ"+user.getBalance()+"Ԫ";
			dao.OperationUpdate(user.getUsername(),mes);
			break;
		}
		
	}
	
}
