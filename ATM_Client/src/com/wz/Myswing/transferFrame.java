package com.wz.Myswing;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.wz.util.FrameHolder;

public class transferFrame extends JFrame{
	
	public static void main(String[] args) {
		new transferFrame().setVisible(true);
	}
	
	public transferFrame()
	{
		FrameHolder.transferframe=this;
		init();
	}
	
	private void init()
	{
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�Ĵ�С
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //�ô��ڷ�������Ļ����
		setTitle("ת��");
		setSize(500, 500);
	}
	
	

}
