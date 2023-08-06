package pers.swd.rpc.consumer.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

public class RpcClientHandler extends SimpleChannelInboundHandler<String> implements Callable {


    private ChannelHandlerContext context;

    private String requestMsg;

    private String responseMsg;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    @Override
    protected synchronized void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        responseMsg = msg;
        // 唤醒等待的线程
        notify();
    }

    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(requestMsg);
        // 线程等待
        wait();
        return responseMsg;
    }


    public void setRequestMsg(String requestMsg) {
        this.requestMsg = requestMsg;
    }
}
