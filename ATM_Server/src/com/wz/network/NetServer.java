package com.wz.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Properties;

import com.wz.handle.KeyHandle;
import com.wz.tool.timeUtil;

//ֱ���ø���̳�Runable�ӿڣ�����Ҫ��run������ʵ��accpet
public class NetServer implements Runnable{
	private KeyHandle keyhandle; //SelectionKey������
	private Properties pro=new Properties(); //�����ļ���ȡ��
	private ServerSocketChannel serversocketchannel; //������ͨ��
	private ServerSocket serverSocket; //�������ͨ��������serverSocket
	private Selector selector; //ѡ����
	private String host; //������
	private int PORT; //�˿ں�
		
	public NetServer() {
		try {
//���Ƚ��г�ʼ������--->>>
			pro.load(new FileInputStream(new File("netconfig.properties"))); //���������ļ�
			PORT=Integer.parseInt(pro.getProperty("net.port")); //�������ļ���ȡ���˿ں�
			host=pro.getProperty("net.host"); //�������ļ���ȡ��������ַ
			keyhandle=new KeyHandle();	//����key�Ĵ�����
			serversocketchannel=ServerSocketChannel.open(); //����һ��ͨ��
			serversocketchannel.socket().bind(new InetSocketAddress(host, PORT)); //��ͨ����������socket���а󶨶˿�
			serversocketchannel.configureBlocking(true); //������ͨ������Ϊ����ģʽ
			selector=Selector.open(); //����һ��ѡ����
			System.out.println("***********�������Ѿ�����************");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

// �������������ͷ��˵��¼�����
	public void server()
	{
		SelectionKey key=null;
		while(true)
		{
			try {
				synchronized(this) {}
				if(selector.select()>0)
				{
					Iterator<SelectionKey> iterator=selector.selectedKeys().iterator();
					while(iterator.hasNext())
					{
						key=iterator.next();
						keyhandle.handle(key); //����SelectionKey
						iterator.remove();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void Accept()
	{
		new Thread(this).start();
	}

//���տͻ������Ӳ��ֵķ��� ---->
	public void run() {
		while(true)
		{
			try {
				SocketChannel socketchannel=serversocketchannel.accept(); //�ȴ��ͷ������ӣ����û�л�������
				socketchannel.configureBlocking(false); //���õ�������ͨ������Ϊ������ģʽ
				
				//�����ͬ���飬��server�е�ͬ�������Ӧ
				synchronized(this) {
					selector.wakeup(); //�˴���Ҫ����server()�����е�select()������Ļ����ܻᷢ������
					socketchannel.register(selector,SelectionKey.OP_READ); //�����ͨ��ע�����д�¼�
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
}



