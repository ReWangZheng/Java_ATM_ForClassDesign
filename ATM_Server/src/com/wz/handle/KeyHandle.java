package com.wz.handle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.wz.network.NetServer;
import com.wz.tool.MyHolder;
import com.wz.tool.timeUtil;

/*SelectionKey������*/
public class KeyHandle
{
	private EventHandle eventHandle; //�ź��¼�������
	public KeyHandle()
	{
		eventHandle=new EventHandle();
	}
	
	public void handle(SelectionKey key) {
		if(key.isReadable())
		{
			SocketChannel socketchannel=(SocketChannel)key.channel();
			ByteBuffer bf=ByteBuffer.allocate(1024*10);
			try 
			{
					int	len;
					while((len=socketchannel.read(bf))>0)
					{
						String sign=new String(bf.array(),0,len); //��ȡ�ӿͻ��˷��������ź�
						bf.clear();
						eventHandle.sendSign(sign,key); //���źŽ��д���
					}
			} 
			catch (IOException e) 
			{
				if(key!=null)
				{
					try 
					{
						if (MyHolder.getKeyList().contains(key)) {
							MyHolder.getKeyList().remove(key);
							System.out.println(timeUtil.getTime()+"     ���û��뿪ϵͳ,��ǰ������:"+MyHolder.getKeyList().size()); //���Դ���
						}
						key.cancel();
						key.channel().close();
					} 
					
					catch (IOException e1) 
					{
						e1.printStackTrace();
					}
					
				}
			}
		}
	}
}