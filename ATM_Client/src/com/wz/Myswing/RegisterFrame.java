package com.wz.Myswing;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.wz.util.ClientUtil;
import com.wz.util.FrameHolder;
import com.wz.util.MessageUtil;

public class RegisterFrame extends JFrame implements ActionListener {
	private JFrame last;
	private userRegister userp=new userRegister();
	private passwdRegister passwd=new passwdRegister();
	private passwd2Register passwd2=new passwd2Register();
	private realnamePanel realnamep=new realnamePanel();
	private registerFunc funcp=new registerFunc();
	
	public RegisterFrame() {
		setSize(300, 300);  //���ô��ڴ�С
		setResizable(false);
		setTitle("��½"); //���ñ���
		setLayout(new GridLayout(7, 0));
		init();
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				last.setVisible(true);
			}
		});
		
		
		this.last=FrameHolder.logFrame;
	}
	
	private void init()
	{
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�Ĵ�С
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //�ô��ڷ�������Ļ����
		addBlankLine(1);
		FrameHolder.RegisFrame=this;
		getContentPane().add(userp);
		getContentPane().add(passwd);
		getContentPane().add(passwd2);
		getContentPane().add(realnamep);
		getContentPane().add(funcp);
		funcp.register.addActionListener(this);
	}
	
	
	
	public  void addBlankLine(int n)
	{
			for(int i=0;i<n;i++) {
				getContentPane().add(new Label());
			}
	}

	public void actionPerformed(ActionEvent e) {
		Button button=(Button)e.getSource(); //�õ��¼�Դ��ť
		
		if(button.getName().equals(funcp.register.getName()))
		{
			register();
		}
	}
	
	public void register()
	{
		String name=userp.nameField.getText();
		String password=passwd.passwdField.getText();
		String realname=realnamep.realnameField.getText();
		String password2=passwd2.passwd2Field.getText();
		
		//��У������������Ƿ�Ϻ��淶
		if(name.equals("") || passwd.equals("") || realname.equals("") || password.equals("") || passwd2.equals(""))
		{
			JOptionPane.showMessageDialog(this, "��ȷ����Ϣ��д��ȷ", "��ʾ", JOptionPane.OK_OPTION);
			return;
		}
		if(!password.equals(password2))
		{
			JOptionPane.showMessageDialog(this, "�����������벻��ͬ������������", "��ʾ", JOptionPane.OK_OPTION);
			passwd2.passwd2Field.setText("");
			return;
		}
		if(name.length()<6)
		{
			JOptionPane.showMessageDialog(this, "�û������Ϸ����û�������������λ", "��ʾ", JOptionPane.OK_OPTION);
			userp.nameField.setText("");
			return;
		}else if(name.indexOf(':')!=-1 && name.indexOf('#')!=-1) {
			JOptionPane.showMessageDialog(this, "�û������Ϸ������ð�����:������#���������ַ�", "��ʾ", JOptionPane.OK_OPTION);
			userp.nameField.setText("");
			return;
		}
		if(password.length()<6)
		{
			JOptionPane.showMessageDialog(this, "���벻�Ϻ��淶�����볤��������λ", "��ʾ", JOptionPane.OK_OPTION);
			passwd.passwdField.setText("");
			return;
		}else if(name.indexOf(':')!=-1 && name.indexOf('#')!=-1) {
			JOptionPane.showMessageDialog(this, "���벻�Ϸ������ð�����:������#���������ַ�", "��ʾ", JOptionPane.OK_OPTION);
			passwd.passwdField.setText("");
			return;
		}
		
		
		//����������������ע��
		ClientUtil.client.sendMessage(MessageUtil.registerSign(name, password, realname, 0));
	}
	

	
}

class userRegister extends Panel
{
	public JTextField nameField;
	public userRegister()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		nameField=new JTextField(12);
		add(new Label("��       �ţ�"));
		add(nameField);
	}
}

class passwdRegister extends Panel
{
	public JPasswordField passwdField;
	public passwdRegister()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		passwdField=new JPasswordField(12);
		add(new Label("��       �룺"));
		add(passwdField);
	}
}

class passwd2Register extends Panel
{
	public JPasswordField passwd2Field;
	public passwd2Register()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		passwd2Field=new JPasswordField(12);
		add(new Label("ȷ�����룺"));
		add(passwd2Field);
	}
}

class realnamePanel extends Panel
{
	public JTextField realnameField;
	public realnamePanel()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		realnameField=new JTextField(12);
		add(new Label("��       ����"));
		add(realnameField);
	}
}

class registerFunc extends Panel
{
	public Button register;
	public registerFunc()
	{
		setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
		register=new Button("ȷ��ע��");
		add(register);
	}
}


