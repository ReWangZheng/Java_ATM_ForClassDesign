package com.wz.Myswing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.naming.InitialContext;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.wz.bean.User;
import com.wz.util.FrameHolder;

public class MesFrame extends JFrame{
	public static void main(String[] args) {
		new MesFrame(User.CreateUser("admin", "woainima..", "����", 8523.3));
	}
	
	private User user; //��Ҫչʾ���û�
	private MessagePanel mespanel; //չʾ��Ϣ���
	private buttonPanel fp; //���ܰ�ť���
	private MouseAdapter mouseadapate; //���������
	public MesFrame(User user)
	{
		FrameHolder.mesFrame=this; //���ö������hoder
		this.user=user; 
		mespanel=new MessagePanel();
		fp=new buttonPanel();
		setSize(500, 300);
		InitMouseAdapter();
		init();
	}
	
	//��ʼ������
	private void init()
	{
		setTitle("�𾴵�"+user.getRealname()+",��ӭ��ʹ�ñ�ϵͳ");
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�Ĵ�С
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //�ô��ڷ�������Ļ����
		setDefaultCloseOperation(EXIT_ON_CLOSE); //����Ĭ�Ϲر�ģʽ
		setLayout(new GridLayout(1, 2));
		add(mespanel);
		add(fp);

		//����Ϣ����չʾ
		mespanel.username.setText(user.getUsername());
		mespanel.realname.setText(user.getRealname());
		mespanel.balance.setText(user.getBalance()+"");
		
		//��Ӽ�����
		fp.savemoney.addMouseListener(mouseadapate);
		fp.drawmoney.addMouseListener(mouseadapate);
		fp.transfer.addMouseListener(mouseadapate);
		fp.querry.addMouseListener(mouseadapate);
	}
	
	
	
	
	//������ʱ�������
	public void setuserBalance(double money)
	{
		mespanel.balance.setText(money+"");
	}
	
	//��ʼ������������ķ���
	private void InitMouseAdapter() 
	{
		 mouseadapate=new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JLabel lable=(JLabel) e.getSource();
				System.out.println(lable.toString());
				switch(lable.getText())
				{
					case "��Ǯ":{
						saveMoney();
						break;
					}
					case "��ѯ":{
						querryHistory();
						break;
					}
					case "ȡǮ":{
						drawMoney();
					}
					case "ת��":{
						transfer();
						break;
					}
					
				}
			};
		}; 
	}
	
	//���ʹ�Ǯ����ķ���
	private void saveMoney()
	{
		if(FrameHolder.sMoneyFrame==null){
			new saveMoneyFrame(user).setVisible(true);;
		}else
		{
			FrameHolder.sMoneyFrame.setVisible(true);
		}
	}
	
	//���Ͳ鿴��ʷ��¼����ķ���
	private void querryHistory()
	{
		
	}
	
	//����ȡǮ����ķ���
	private void drawMoney()
	{
		
	}
	
	//����ת������ķ���
	private void transfer()
	{
		
	}
	
}

class MessagePanel extends JPanel{
	public JLabel username=new JLabel();
	public JLabel realname=new JLabel();
	public JLabel balance=new JLabel();
		
	public MessagePanel()
	{	
		setLayout(new GridLayout(7,1));
		JPanel p1=new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
		p1.add(new JLabel());
		p1.add(new JLabel("�û�����"));
		p1.add(username);
		
		JPanel p2=new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
		p2.add(new JLabel());
		p2.add(new JLabel("��    ����"));
		p2.add(realname);
		
		JPanel p3=new JPanel(new FlowLayout(FlowLayout.LEFT,10, 3));
		p3.add(new JLabel());
		p3.add(new JLabel("��    �"));
		p3.add(balance);
		add(new JLabel());
		add(p1);
		add(p2);
		add(p3);
		setBorder(new EtchedBorder(Color.black, Color.black));
		
	}	
}	

class buttonPanel extends JPanel{
	public JLabel savemoney=new JLabel("��Ǯ");
	public JLabel drawmoney=new JLabel("ȡǮ");
	public JLabel transfer=new JLabel("ת��");
	public JLabel querry=new JLabel("��ѯ");
	public buttonPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
		add(savemoney);
		add(drawmoney);
		add(transfer);
		add(querry);
		MouseListener listener=new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				JLabel lable=(JLabel) e.getSource();
				lable.setForeground(Color.red);
				lable.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				JLabel lable=(JLabel) e.getSource();
				lable.setForeground(new Color(51, 51, 51));
			}
		};
		savemoney.addMouseListener(listener);
		drawmoney.addMouseListener(listener);
		transfer.addMouseListener(listener);
		querry.addMouseListener(listener);
	}
}
