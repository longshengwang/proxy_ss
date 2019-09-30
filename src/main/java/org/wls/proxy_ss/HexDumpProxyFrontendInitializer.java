package org.wls.proxy_ss;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.proxy.Socks5ProxyHandler;
import io.netty.handler.ssl.*;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;


/**
 * Created by wls on 2019/9/30.
 */
public class HexDumpProxyFrontendInitializer extends ChannelInitializer<SocketChannel> {
    private final boolean ssl;
    final SslContext sslCtx;
    Channel inboundChannel;
    String agency;

    public HexDumpProxyFrontendInitializer(Channel inboundChannel, boolean ssl, String agency) throws SSLException {
        this.ssl = ssl;
        this.inboundChannel = inboundChannel;
        this.agency = agency;

        if (ssl) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

//            SslProvider provider = OpenSsl.isAlpnSupported() ? SslProvider.OPENSSL : SslProvider.JDK;
//            sslCtx = SslContextBuilder.forClient()
//                    .sslProvider(provider)
//                /* NOTE: the cipher filter may not include all ciphers required by the HTTP/2 specification.
//                 * Please refer to the HTTP/2 specification for cipher requirements. */
//                    .ciphers(Http2SecurityUtil.CIPHERS, SupportedCipherSuiteFilter.INSTANCE)
//                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
//                    .applicationProtocolConfig(new ApplicationProtocolConfig(
//                            ApplicationProtocolConfig.Protocol.ALPN,
//                            // NO_ADVERTISE is currently the only mode supported by both OpenSsl and JDK providers.
//                            ApplicationProtocolConfig.SelectorFailureBehavior.NO_ADVERTISE,
//                            // ACCEPT is currently the only mode supported by both OpenSsl and JDK providers.
//                            ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT,
//                            ApplicationProtocolNames.HTTP_2,
//                            ApplicationProtocolNames.HTTP_1_1))
//                    .build();
        } else {
            sslCtx = null;
        }
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        String[] splitStr = agency.split(":");
        System.out.println("翻墙的代理IP:" + splitStr[0] + ":" + splitStr[1]);
        socketChannel.pipeline().addLast("ss", new Socks5ProxyHandler(new InetSocketAddress(splitStr[0], Integer.parseInt(splitStr[1]))));

        if (ssl) {
            socketChannel.pipeline().addLast(sslCtx.newHandler(socketChannel.alloc()));
        }
        socketChannel.pipeline().addLast("handler", new HexDumpProxyBackendHandler(this.inboundChannel));
    }
}
