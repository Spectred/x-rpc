package pers.swd.rpc.consumer.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import pers.swd.rpc.consumer.handler.RpcClientHandler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RpcClient {

    private String ip;
    private int port;

    private EventLoopGroup group;

    private Channel channel;

    private RpcClientHandler rpcClientHandler = new RpcClientHandler();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public RpcClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        initClient();
    }

    public void initClient() {
        try {
            // 1. 创建线程组
            group = new NioEventLoopGroup();
            // 2. 创建启动助手
            Bootstrap bootstrap = new Bootstrap();
            // 3. 设置参数
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3_000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();

                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());

                            pipeline.addLast(rpcClientHandler);
                        }
                    });
            // 4. 连接netty服务端
            channel = bootstrap.connect(ip, port).sync().channel();
        } catch (Exception e) {
            e.printStackTrace();
            this.close();
        }
    }

    public void close() {
        if (channel != null) channel.close();
        if (group != null) group.shutdownGracefully();
    }

    public Object send(String msg) throws ExecutionException, InterruptedException {
        rpcClientHandler.setRequestMsg(msg);
        Future future = executorService.submit(rpcClientHandler);
        return future.get();
    }
}
