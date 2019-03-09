package com.wz.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Properties;

import com.wz.handle.ErrorHandle;
import com.wz.handle.KeyHandle;
import com.wz.util.MessageUtil;


public class NetClient implements Runnable {
	private SocketChannel socketChannel; //����ͨ��
	private Selector selector;//ѡ����
	private int PORT; //�˿ں�
	private String HOST; // ������
	private Properties pro; //�����ļ�������
	private KeyHandle keyhandle; //SelectionKey������
	public NetClient(){
		try {
			pro=new Properties(); //��ʼ����Դ������
			pro.load(new FileInputStream(new File("netconfig.properties"))); //���������ļ�
			PORT=Integer.parseInt(pro.getProperty("net.port")); //�������ļ��ж�ȡ�˿ں�
			HOST=pro.getProperty("net.host"); //�������ļ��ж�ȡ������
			keyhandle=new KeyHandle();	//��ʼ��SelectionKey������
			socketChannel=SocketChannel.open(); //���һ��NIOͨ��
			selector=Selector.open(); //��ʼ��ѡ����
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//���񷽷�����Ҫ��һ�����߳������
	public void server()
	{
		while(true)
		{
			try {
				if(selector.select()>0)
				{
					Iterator<SelectionKey> iterator=selector.selectedKeys().iterator(); //��ô洢SelectionKey����ĵ�����
					while(iterator.hasNext()) //�Ե��������б���
					{
						SelectionKey key=iterator.next();
						keyhandle.handle(key); //��selectionKey���д���
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//�����߳�����
	public void connect()
	{
		new Thread(this).start();
	}
	
	
	//��Ϊ������������ʽ�ģ�����Ҫ�������߳̽�������
	public void run() {
			try {
				socketChannel.connect(new InetSocketAddress(HOST, PORT)); //����ʽ��������
				socketChannel.configureBlocking(false);
				socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);//��ͨ������ע��
			} catch (IOException e) {
				ErrorHandle.sendMessage(ErrorHandle.ConnectError); //�������������ʧ������
			}//��ͨ������Ϊ������
	}
	
	
	//������������ź�
	public void sendMessage(String sign)
	{
		try {
			socketChannel.write(ByteBuffer.wrap(sign.getBytes())); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
