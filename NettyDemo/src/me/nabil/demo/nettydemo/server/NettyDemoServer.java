package me.nabil.demo.nettydemo.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class NettyDemoServer {
	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ServerBootstrap bootstrap = new ServerBootstrap(factory);

		
		/**
		 * ����Ĭ��ChannelPipeline�ܵ� ����ζ��ͬһ��Handlerʵ���������Channelͨ������
		 * ���ַ�ʽ����Handler������״̬�ĳ�Ա�����ǿ��Եģ����ҿ���������ܣ�
		 */
//		ChannelPipeline pipeline = bootstrap.getPipeline();
//		NettyDemoHandler demoHandler = new NettyDemoHandler();
//		NettyDemoDecoder decoder = new NettyDemoDecoder();
//		pipeline.addLast("decode", decoder);
//		pipeline.addLast("handler", demoHandler);
		
		/**
		 * ��Ĭ��channelPipeline,����ʵ�������ᱻ���channelͨ������
		 */
		NettyDemoServerPipeline pipelineFactory = new NettyDemoServerPipeline();
		bootstrap.setPipelineFactory(pipelineFactory);
		bootstrap.bind(new InetSocketAddress(888));
	}
}
