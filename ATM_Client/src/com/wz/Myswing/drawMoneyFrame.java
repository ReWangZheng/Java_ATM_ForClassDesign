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

public class drawMoneyFrame extends JFrame implements ActionListener{
	private JFormattedTextField textField;
	private JPasswordField passwordField;
	private JButton button;
	private User user;
	public drawMoneyFrame(User user) {
		FrameHolder.drawmoneyframe=this;
		this.user=user;
		setSize(250, 300);
		init();
	}
	
	private void init()
	{
		//��ʼ���������
		textField= new JFormattedTextField(NumberFormat.getInstance());
		passwordField=new JPasswordField();
		button=new JButton("ȡ��");

		//��ʼ�������
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�Ĵ�С
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //�ô��ڷ�������Ļ����
		setLayout(new GridLayout(9, 1));
		setSize(250, 300);
		setTitle("ȡǮ");
		
		//���������������
		addBlankLine(2);
		Box box=Box.createHorizontalBox();
		box.add(Box.createHorizontalStrut(20));
		box.add(new JLabel("��"));
		box.add(textField);
		box.add(Box.createHorizontalStrut(50));
		add(box);
		
		addBlankLine(1); //��ӿ���
		
		//������������������
		Box box2=Box.createHorizontalBox();
		box2.add(Box.createHorizontalStrut(20));
		box2.add(new JLabel("���룺"));
		box2.add(passwordField);
		box2.add(Box.createHorizontalStrut(50));
		add(box2);
		
		addBlankLine(1); //��ӿ���
		
		//��ȷ����ť��������
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		panel.add(button);
		add(panel);
		button.addActionListener(this);
	}
	
	//��ӿ��еķ���
	public  void addBlankLine(int n)
	{
			for(int i=0;i<n;i++) {
				getContentPane().add(new Label());
			}
	}

	//������
	public void actionPerformed(ActionEvent e) {
		JButton button=(JButton)e.getSource();

		
		if(button.getText().equals("ȡ��"))
		{	
			if(!Md5Util.ToMd5code(passwordField.getText()).equals(user.getPasswd())){
				JOptionPane.showMessageDialog(this, "������������������������", "��ʾ", JOptionPane.OK_OPTION);
				passwordField.setText("");
				return;
			}
			
		}
	}
	
	public void clear()
	{
		textField.setText("");
		passwordField.setText("");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}	
