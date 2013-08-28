package me.nabil.sample.reactordemo.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * ��Ӧ��ģʽ ���ڽ�����û����ʲ�������
 * 
 * �ٸ����ӣ�������������
 * 
 * ��ͳ�̳߳���������һ������(����)ȥһ������Ա(�߳�)
 * ��Ӧ��ģʽ�����������˵�˵�ʱ�򣬷���Ա�Ϳ���ȥ�к����������ˣ��ȿ��˵���˲ˣ�ֱ���к�һ��������Ա��
 * 
 * @author linxcool
 */
public class Reactor implements Runnable {
	public final Selector selector;
	public final ServerSocketChannel serverSocketChannel;

	public Reactor(int port) throws IOException {
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		InetSocketAddress inetSocketAddress = new InetSocketAddress(
				InetAddress.getLocalHost(), port);
		serverSocketChannel.socket().bind(inetSocketAddress);
		serverSocketChannel.configureBlocking(false);

		// ��selectorע���channel
		SelectionKey selectionKey = serverSocketChannel.register(selector,
				SelectionKey.OP_ACCEPT);

		// ����selectionKey��attache���ܰ�Acceptor ��������飬����Acceptor
		selectionKey.attach(new Acceptor(this));
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				// Selector�������channel��OP_ACCEPT��READ�¼����������б����ͻ���С�
				while (it.hasNext()) {
					// ��һ���¼� ��һ�δ���һ��accepter�߳�
					// �Ժ󴥷�SocketReadHandler
					SelectionKey selectionKey = it.next();
					dispatch(selectionKey);
					selectionKeys.clear();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����Acceptor��SocketReadHandler
	 * 
	 * @param key
	 */
	void dispatch(SelectionKey key) {
		Runnable r = (Runnable) (key.attachment());
		if (r != null) {
			r.run();
		}
	}

}
