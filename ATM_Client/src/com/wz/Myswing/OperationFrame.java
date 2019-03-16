package com.wz.Myswing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.wz.util.FrameHolder;

public class OperationFrame extends JFrame{
	private JTable table; //���
	private DefaultTableModel model; //�洢������ݵı��ģ��
	
	
	public static void main(String[] args) {
		new OperationFrame().setVisible(true);
	}
	public OperationFrame()
	{
		FrameHolder.operationframe=this;
		setResizable(false);
		init();
	}
	public void init()
	{
		/*����Frame��ܵĳ�ʼ��*/
		setLayout(null);
		setSize(500,410);
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�Ĵ�С
		setLocation((int)(dimension.getWidth()/2.5), (int)(dimension.getHeight()/3.5)); //�ô��ڷ�������Ļ����
		/*���ĳ�ʼ��*/
		model=new DefaultTableModel() {	public boolean isCellEditable(int row, int column) {return false;} }; //��ʼ�����ģ��
		table=new JTable(model); //��ʼ�����
		JScrollPane tablePanel=new JScrollPane(table); //�������뵽scrollpanel��
		tablePanel.setBounds(10, 10, 465, 340); //����λ���Լ���С
		getContentPane().add(tablePanel);  //�������ӵ�������
	}
	public void initTable(String[][] message)
	{
		model.setDataVector(message, new String[] {"��ʷ��¼"});;
		table.setModel(model);
	}
}
