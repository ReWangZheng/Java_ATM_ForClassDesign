package com.wz.handle;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.wz.util.FrameHolder;

public class ErrorHandle {
	public static final String ConnectError="CONNECTERROR";
	public static final String InterruptError="SERVICINTERINTERRUPT";
	public static void sendMessage(String errorType) {
		switch (errorType) {
		case ConnectError:
			ConnectErrorHandle();
			break;
		case InterruptError:
			InterruptErrorHandle();
			break;
		}
	}
	private static void InterruptErrorHandle() {
		Component parent=null;
		if (FrameHolder.mesFrame!=null)
			parent=FrameHolder.mesFrame;
		else
			parent=FrameHolder.logFrame;
		JOptionPane.showMessageDialog(parent, "Զ�̷��������ֹ���");
		System.exit(0);
	}
	private static void ConnectErrorHandle() {
		JOptionPane.showMessageDialog(FrameHolder.logFrame, "���ӷ�����ʧ��");
		System.exit(0);
	}

}
