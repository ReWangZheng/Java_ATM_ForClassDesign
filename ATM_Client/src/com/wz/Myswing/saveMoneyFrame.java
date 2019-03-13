package com.wz.Myswing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.text.MaskFormatter;

import com.wz.bean.User;
import com.wz.network.NetClient;
import com.wz.util.ClientUtil;
import com.wz.util.FrameHolder;
import com.wz.util.Md5Util;
import com.wz.util.MessageUtil;

public class saveMoneyFrame extends JFrame implements ActionListener{
	private JFormattedTextField textField; //������ĸ�ʽ��
	private JPasswordField passwordField;	//��������������
	private JButton button; //ȷ����ť
	private User user; //��ǰ�û�
	
	public saveMoneyFrame(User user) {
		FrameHolder.sMoneyFrame=this; //��ӵ�FrameHolder��
		this.user=user;  //��ʼ��user

		init();
	}
	
	private void init()
	{
		textField= new JFormattedTextField(NumberFormat.getInstance()); //��Nub��ʼ����ʽ����
		passwordField=new JPasswordField(); //��ʼ�������
		button=new JButton("����"); //��ʼ����ť
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�Ĵ�С
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //�ô��ڷ�������Ļ����
		setLayout(new GridLayout(9, 1)); //����Ϊ���񲼾�
		setSize(250, 300);
		setTitle("ȡǮ");
		
		addBlankLine(2); //������������
		
		Box box=Box.createHorizontalBox(); //�����������ʽ����
		box.add(Box.createHorizontalStrut(20)); //��������������
		box.add(new JLabel("��")); //��ӱ�ǩ
		box.add(textField); //���������
		box.add(Box.createHorizontalStrut(50)); //��������������
		add(box); //����������
		addBlankLine(1); //�м����һ��
		Box box2=Box.createHorizontalBox(); //��������Ĳ���
		box2.add(Box.createHorizontalStrut(20)); //��������������
		box2.add(new JLabel("���룺")); //��ӱ�ǩ
		box2.add(passwordField); //��������
		box2.add(Box.createHorizontalStrut(50)); //��������
		add(box2);//�����������ӽ����񲼾���
		addBlankLine(1); //��ӿ���

		//�ڰѰ�ť�ŵ���ͨ�������
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.add(button);
		add(panel);
		button.addActionListener(this);
	}
	
	//��ӿ��еķ���
	private  void addBlankLine(int n)
	{
			for(int i=0;i<n;i++) {
				getContentPane().add(new Label());
			}
	}

	//������
	public void actionPerformed(ActionEvent e) {
		JButton button=(JButton)e.getSource();

		
		if(button.getText().equals("����"))
		{	
			if(!Md5Util.ToMd5code(passwordField.getText()).equals(user.getPasswd())){
				JOptionPane.showMessageDialog(this, "������������������������", "��ʾ", JOptionPane.OK_OPTION);
				passwordField.setText("");
				return;
			}
			System.out.println(textField.getValue());
			ClientUtil.client.sendMessage(MessageUtil.saveMoneySign(user.getUsername(),Double.parseDouble(textField.getValue()+"")));
			
		}
	}
	public void clear()
	{
		textField.setText("");
		passwordField.setText("");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}	
