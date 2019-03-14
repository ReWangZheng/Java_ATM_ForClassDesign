package com.wz.Myswing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.wz.bean.User;
import com.wz.util.ClientUtil;
import com.wz.util.FrameHolder;
import com.wz.util.Md5Util;
import com.wz.util.MessageUtil;

public class transferFrame extends JFrame implements ActionListener{
	private JTextField toFiled;
	private JFormattedTextField money;
	private JPasswordField passwordField;
	private User user;
	
	public transferFrame(User user)
	{
		FrameHolder.transferframe=this;
		this.user=user;
		init();
	}
	
	private void init()
	{
		//Frame��ʼ��
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�Ĵ�С
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //�ô��ڷ�������Ļ����
		setTitle("ת��");
		setSize(380, 400);
		//�����ʼ��
		toFiled=new JTextField();
		money=new JFormattedTextField(NumberFormat.getNumberInstance());
		passwordField=new JPasswordField();
		setLayout(new GridLayout(12, 1));
		
		//���������Frame��
		addBlankLine(2);
		Box box1=Box.createHorizontalBox();
		box1.add(Box.createHorizontalStrut(80));
		box1.add(new Label("��Ǯ�˻�:"));
		box1.add(toFiled);
		box1.add(Box.createHorizontalStrut(80));
		add(box1);
		addBlankLine(1);
		Box box2=Box.createHorizontalBox();
		box2.add(Box.createHorizontalStrut(80));
		box2.add(new Label("ת�˽��:"));
		box2.add(money);
		box2.add(Box.createHorizontalStrut(80));
		add(box2);
		addBlankLine(1);
		Box box3=Box.createHorizontalBox();
		box3.add(Box.createHorizontalStrut(80));
		box3.add(new Label("ȷ������:"));
		box3.add(passwordField);
		box3.add(Box.createHorizontalStrut(80));
		add(box3);
		JButton button =new JButton("ȷ��ת��");
		Box box4=Box.createHorizontalBox();
		box4.add(Box.createHorizontalStrut(160));
		box4.add(button);
		addBlankLine(1);
		add(box4);
		
		button.addActionListener(this);
		
	}
	private  void addBlankLine(int n)
	{
			for(int i=0;i<n;i++) {
				getContentPane().add(new Label());
			}
	}
	
	private void transerf(String from,String to,double money)
	{
		if(!Md5Util.ToMd5code(passwordField.getText()).equals(user.getPasswd())){
			JOptionPane.showMessageDialog(this, "������������������������", "��ʾ", JOptionPane.OK_OPTION);
			passwordField.setText("");
			return;
		}
		ClientUtil.client.sendMessage(MessageUtil.transferSign(from,to , money));	
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(((JButton)e.getSource()).getText())
		{
			case "ȷ��ת��":{
				transerf(user.getUsername(),toFiled.getText(),Double.parseDouble(money.getValue()+""));
				break;
			}
		}
		
	}
	
	public void clear()
	{
		toFiled.setText("");
		money.setText("");
		passwordField.setText("");
	}
		
}
	
