/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.wls.proxy_ss;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HexDumpProxyInitializer extends ChannelInitializer<SocketChannel> {

    private final String remoteHost;
    private final String agency;
    private final int remotePort;
    private final boolean ssl;

    public HexDumpProxyInitializer(String remoteHost, int remotePort, boolean ssl, String agency) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        this.ssl = ssl;
        this.agency = agency;
    }

    @Override
    public void initChannel(SocketChannel ch) {

//        ch.pipeline().addLast(new HttpServerCodec());
//        ch.pipeline().addLast(new HttpObjectAggregator(65536));
//        ch.pipeline().addLast(new ChunkedWriteHandler());
//        ch.pipeline().addLast(new HexDumpProxyFrontendReplaceHeader());
//        ch.pipeline().addLast(new Test());

        ch.pipeline().addLast(
                new LoggingHandler(LogLevel.INFO),
                new HexDumpProxyFrontendHandler(remoteHost, remotePort, ssl, agency));
    }
}
