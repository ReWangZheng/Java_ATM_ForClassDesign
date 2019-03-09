package com.wz.Myswing;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.wz.network.NetClient;
import com.wz.util.ClientUtil;
import com.wz.util.FrameHolder;
import com.wz.util.MessageUtil;


//��½����
public class LogFrame extends JFrame implements ActionListener{
	private userPanel userpanle;
	private passwdPanel passwaPanel;
	private funcPanel funcpanel;
	public LogFrame()
	{
		userpanle=new userPanel();
		passwaPanel=new passwdPanel();
		funcpanel=new funcPanel();
		setTitle("��½"); //���ñ���
		setSize(300, 300);  //���ô��ڴ�С
		setLayout(new GridLayout(7, 0));
		addBlankLine(2);
		init();
		setVisible(true);//����Ϊ�ɼ�
	}
	
	private void init()//�Ƚ���һЩ������ĳ�ʼ��
	{
		FrameHolder.logFrame=this;
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�Ĵ�С
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //�ô��ڷ�������Ļ����
		setDefaultCloseOperation(EXIT_ON_CLOSE); //����Ĭ�Ϲر�ģʽ
		getContentPane().add(userpanle);
		getContentPane().add( passwaPanel);
		getContentPane().add(funcpanel);
		funcpanel.logbutton.addActionListener(this);
		funcpanel.registerbutton.addActionListener(this);
		passwaPanel.passwdField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyCode()==10) {
					login();
				}
			}
		});
	}
	
	public void setPassFiled(String str)
	{
		passwaPanel.passwdField.setText(str);
	}
	
	//Ϊ���񲼾�������
	public  void addBlankLine(int n)
	{
			for(int i=0;i<n;i++) {
				getContentPane().add(new Label());
			}
	}
	
	//����������͵�½����
	public void login()
	{
		String username=userpanle.nameField.getText();
		String passwd=passwaPanel.passwdField.getText();
		if(username.equals("") || passwd.equals(""))
		{
			JOptionPane.showMessageDialog(this,"�˺Ż������벻��Ϊ��", "��ʾ", JOptionPane.OK_OPTION);
			return;
		}
		ClientUtil.client.sendMessage(MessageUtil.loginSgin(username, passwd));
	}
	
	//��ע�����
	public void register()
	{
		setVisible(false);
		new RegisterFrame().setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		Button button=(Button) e.getSource();
		String name=button.getName();
		if(name.equals(funcpanel.logbutton.getName()))
		{
			login();
		}
		
		if(name.equals(funcpanel.registerbutton.getName()))
		{
			register();
		}
	}
	
}


class userPanel extends Panel
{
	public JTextField nameField;
	public userPanel()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		nameField=new JTextField(12);
		add(new Label("�˺ţ�"));
		add(nameField);
		
	}
}

class passwdPanel extends Panel
{
	public JPasswordField passwdField;
	public passwdPanel()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		passwdField=new JPasswordField(12);
		add(new Label("���룺"));
		add(passwdField);
	}
}

class funcPanel extends Panel
{
	public Button logbutton;
	public Button registerbutton;
	public funcPanel()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		logbutton=new Button("��½");
		registerbutton=new Button("ע��");
		add(logbutton);
		add(registerbutton);
	}
}
