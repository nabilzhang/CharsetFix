package me.nabil.sample.reactordemo.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class SocketReadHandler implements Runnable {
	private SocketChannel socketChannel;

	public SocketReadHandler(Selector selector, SocketChannel socketChannel)
			throws IOException {
		this.socketChannel = socketChannel;
		socketChannel.configureBlocking(false);

		SelectionKey selectionKey = socketChannel.register(selector, 0);

		// ��SelectionKey��Ϊ��Handler ��һ�����¼�����ʱ�������ñ����run������
		// �ο�dispatch(SelectionKey key)
		selectionKey.attach(this);

		// ͬʱ��SelectionKey���Ϊ�ɶ����Ա��ȡ��
		selectionKey.interestOps(SelectionKey.OP_READ);
		selector.wakeup();
	}

	/**
	 * �����ȡ����
	 */
	@Override
	public void run() {
		ByteBuffer inputBuffer = ByteBuffer.allocate(1024);
		inputBuffer.clear();
		try {
			socketChannel.read(inputBuffer);
			// �����̳߳� ������Щrequest
			// requestHandle(new Request(socket,btt));
			System.out.println(inputBuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
