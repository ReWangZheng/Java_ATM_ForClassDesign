package com.wz.handle;

import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.wz.Myswing.MesFrame;
import com.wz.Myswing.OperationFrame;
import com.wz.bean.User;
import com.wz.util.ClientUtil;
import com.wz.util.FrameHolder;
import com.wz.util.Md5Util;
import com.wz.util.MessageUtil;

public class callbackHandle {
	public callbackHandle() {
		
	}

	public void handle(String sign) {
		String[] split=sign.split(MessageUtil.REGEX);
		switch(split[0])
		{
		case "LOGIN": //��½�¼�
			Login(split);
			break;
		case "REGISTER"://ע���¼�
			regetster(split);
			break;
		case "SAVEMONEY": //��Ǯ�¼�
			savemoney(split);
			break;
		case "DRAWMONEY"://ȡǮ�¼�
			draomoney(split);
			break;
		case "TRANSFER"://ת���¼�
			transfer(split);
			break;
		case "UPDATEPASSWD": //�޸������¼�����δ���Ӵ˹���
			break;
		case "OPERATION": //��ѯ��ʷ��¼�¼�
			operation(split);
			break;
		case "GETMONEY"://�յ�ת���¼�
			getMoney(split);
			break;
		}
	}

	private void getMoney(String[] split) {
		JOptionPane.showMessageDialog(FrameHolder.mesFrame, "���յ�һ��ת��", "��ʾ", JOptionPane.OK_OPTION);
		double balance=Double.parseDouble(split[1]);
		FrameHolder.mesFrame.setuserBalance(balance);
	}

	//��ѯ��ʷ��¼�¼�����
	private void operation(String[] split) {
		if(split[1].equals("OK")){
			String[] message=split[2].split(MessageUtil.OERATION_REGEX);
			String[][] mess=new String[message.length][1];
			for(int i=0;i<mess.length;i++)
			{
				mess[i][0]=message[i];
				
			}
			if (FrameHolder.operationframe==null) {
				OperationFrame operationFrame=new OperationFrame();
				operationFrame.initTable(mess);
				operationFrame.setVisible(true);
			}else {
				FrameHolder.operationframe.initTable(mess);
				FrameHolder.operationframe.setVisible(true);
			}
			
		}else {
			JOptionPane.showMessageDialog(FrameHolder.drawmoneyframe, "ת��ʧ�ܣ���ȷ���û������Ƿ���ȷ�������˻�����Ƿ����", "��ʾ", JOptionPane.OK_OPTION);
		}
	}
	//����ת�������¼�����
	private void transfer(String[] split) {
		if(split[1].equals("OK")){
			FrameHolder.transferframe.setVisible(false);
			FrameHolder.transferframe.clear();
			FrameHolder.mesFrame.setuserBalance(Double.parseDouble(split[2]));
			JOptionPane.showMessageDialog(FrameHolder.mesFrame, "ת�˳ɹ���", "��ʾ", JOptionPane.OK_OPTION);
		}else {
			JOptionPane.showMessageDialog(FrameHolder.drawmoneyframe, "ת��ʧ�ܣ���ȷ���û������Ƿ���ȷ�������˻�����Ƿ����", "��ʾ", JOptionPane.OK_OPTION);
			
		}
	}
	//����ȡǮ�����¼�����
	private void draomoney(String[] split) {
		if(split[1].equals("OK")){
			FrameHolder.drawmoneyframe.setVisible(false);
			FrameHolder.drawmoneyframe.clear();
			FrameHolder.mesFrame.setuserBalance(Double.parseDouble(split[2]));
			JOptionPane.showMessageDialog(FrameHolder.mesFrame, "ȡǮ�ɹ���", "��ʾ", JOptionPane.OK_OPTION);
		}else {
			JOptionPane.showMessageDialog(FrameHolder.drawmoneyframe, "ȡǮʧ�ܣ���ȷ�������˻�����Ƿ����", "��ʾ", JOptionPane.OK_OPTION);
		}
	}
	//�����Ǯ�����¼�����
	private void savemoney(String[] split) {
		if(split[1].equals("OK")){
			FrameHolder.sMoneyFrame.setVisible(false);
			FrameHolder.sMoneyFrame.clear();
			FrameHolder.mesFrame.setuserBalance(Double.parseDouble(split[2]));
			JOptionPane.showMessageDialog(FrameHolder.mesFrame, "��Ǯ�ɹ���", "��ʾ", JOptionPane.OK_OPTION);
		}else {
			JOptionPane.showMessageDialog(FrameHolder.sMoneyFrame, "��Ǯʧ�ܣ�������������", "��ʾ", JOptionPane.OK_OPTION);
		}
	}
	//����ע�������¼�����
	private void regetster(String[] split) {
		if(split[1].equals("YES")) {
			int i=JOptionPane.showConfirmDialog(FrameHolder.RegisFrame, "��ϲ�����˺�ע��ɹ����Ƿ�ֱ�ӵ�½?", "��ʾ", JOptionPane.CANCEL_OPTION);
			//YES=0 NO=2
			if(i==0)
			{
				FrameHolder.RegisFrame.setVisible(false);
				ClientUtil.client.sendMessage(MessageUtil.loginSgin(split[2], split[3]));
			}else if(i==2)
			{
				FrameHolder.RegisFrame.setVisible(false);
				FrameHolder.logFrame.setVisible(true);
				FrameHolder.logFrame.setUserFiled(split[2]);
			}
		}else if(split[1].equals("NO")) {
			JOptionPane.showMessageDialog(FrameHolder.logFrame, "�û����Ѿ����ڣ������һ���û����ٳ���ע��", "��ʾ", JOptionPane.OK_OPTION);
		}
	}

	//�����½�����¼�����
	private void Login(String[] split) {
		if(split[1].equals("NO"))
		{
			if(split.length>=3&&split[2].equals("ISONLINE"))
			{
				JOptionPane.showMessageDialog(FrameHolder.logFrame, "�˺��Ѿ��ڱ𴦵�½��", "��ʾ", JOptionPane.OK_OPTION);
				return;
			}
			JOptionPane.showMessageDialog(FrameHolder.logFrame, "�˺Ż��������!", "��ʾ", JOptionPane.OK_OPTION);
			FrameHolder.logFrame.setPassFiled("");
		}
		else if(split[1].equals("YES")){
			JOptionPane.showMessageDialog(FrameHolder.logFrame, "��½�ɹ�", "��ʾ", JOptionPane.OK_OPTION);
			User user=User.CreateUser(split[2], split[3], split[4], Double.parseDouble(split[5]));
			FrameHolder.logFrame.setVisible(false);
			new MesFrame(user).setVisible(true);
		}
	}

}
