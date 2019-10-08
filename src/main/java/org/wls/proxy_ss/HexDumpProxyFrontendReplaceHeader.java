package org.wls.proxy_ss;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

import java.net.SocketAddress;

/**
 * Created by wls on 2019/9/30.
 */
public class HexDumpProxyFrontendReplaceHeader extends SimpleChannelInboundHandler<FullHttpRequest> {


//    @Override
//    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress,
//                        SocketAddress localAddress, ChannelPromise promise) throws Exception {
//
//        System.out.println("=================== HexDumpProxyFrontendReplaceHeader ==");
//        ctx.connect(remoteAddress, localAddress, promise);
//    }
//

    /*public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("=================== HexDumpProxyFrontendReplaceHeader == write >>>");
        System.out.println(msg);
        System.out.println("=================== HexDumpProxyFrontendReplaceHeader == write <<<");

//        HttpContent content = new DefaultHttpContent((ByteBuf)msg);
//        System.out.println(content.);
//        new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, )
//        if(msg instanceof HttpRequest){
//            System.out.println("这是http reqeust");
//        }

//        ByteBuf b = (ByteBuf)msg;
//        PooledUnsafeDirectByteBuf httpRequest = (PooledUnsafeDirectByteBuf)msg;
//        System.out.println(httpRequest.uri());

        ctx.write(msg, promise);
    }*/

    public HexDumpProxyFrontendReplaceHeader(){
        super(false);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println("========== 1");
        System.out.println(msg);
        System.out.println(msg.headers());
        System.out.println("========== 2");

//        msg.headers().set("host","126.com");
//        DefaultFullHttpRequest(msg.protocolVersion(), msg.method(), msg.uri(),)
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

//    @Override
//    protected void encodeInitialLine(ByteBuf buf, HttpMessage message) throws Exception {
//        System.out.println("=================== HexDumpProxyFrontendReplaceHeader == encodeInitialLine >>>");
//        System.out.println(message.headers());
//        System.out.println("=================== HexDumpProxyFrontendReplaceHeader == encodeInitialLine <<<");
//    }

//    @Override
//    protected void encodeInitialLine(ByteBuf buf, HttpRequest message) throws Exception {
//        System.out.println("=================== HexDumpProxyFrontendReplaceHeader == encodeInitialLine >>>>>>>>>>>>>>");
//    }

//    @Override
//    protected void encodeInitialLine(ByteBuf buf, FullHttpRequest message) throws Exception {
//        System.out.println("=================== HexDumpProxyFrontendReplaceHeader == encodeInitialLine >>>>>>>>>>>>>>");
//    }

//    @Override
//    protected boolean isDecodingRequest() {
//        return false;
//    }
//
//    @Override
//    protected HttpMessage createMessage(String[] initialLine) throws Exception {
//        return null;
//    }
//
//    @Override
//    protected HttpMessage createInvalidMessage() {
//        return null;
//    }
}
