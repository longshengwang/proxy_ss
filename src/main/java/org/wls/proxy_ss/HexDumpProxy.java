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

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

public class HexDumpProxy {

    public static void main(String[] args) throws Exception {



        Options options = new Options();
        options.addRequiredOption("p", "proxy-uri", true, "需要代理的URL. 例如: https://210.63.204.29");
        options.addRequiredOption("l", "local-port", true, "本地提供服务的端口号");
        options.addRequiredOption("a", "agency-uri", true, "shadowssocks的本地代理地址,例如: 127.0.0.1:1086");
        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("show this help message and exit program")
                .build());


        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("proxy-ss", options, false);
            return;
        }
        if (cmd.hasOption('h') || cmd.hasOption("--help")) {
            formatter.printHelp("proxy-ss", options, false);
            return;
        }

        String PROXY_HOST_STR = cmd.getOptionValue("p");
        String LOCAL_PORT = cmd.getOptionValue("l");
        String AGENCY_HOST_STR = cmd.getOptionValue("a");

        Map<String, String> param = formatRemoteParam(PROXY_HOST_STR);

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new HexDumpProxyInitializer(param.get("host"), Integer.parseInt(param.get("port")), param.get("ssl").equals("true"), AGENCY_HOST_STR))
             .childOption(ChannelOption.AUTO_READ, false)
             .bind(Integer.parseInt(LOCAL_PORT)).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static Map<String, String> formatRemoteParam(String proxyStr){
        Map<String, String> param = new HashMap<>();

        String[] splitArr = proxyStr.split(":");
        if(splitArr.length == 1){
            param.put("port", "80");
            param.put("host", proxyStr);
            param.put("ssl", "false");
        }

        if(splitArr.length == 2){
            if(splitArr[0].equals("http")){
                param.put("ssl", "false");
                param.put("port", "80");
                param.put("host", splitArr[1].substring(2));
            } else if(splitArr[0].equals("https")){
                param.put("ssl", "true");
                param.put("port", "443");
                param.put("host", splitArr[1].substring(2));
            } else {
                param.put("host", splitArr[0]);
                param.put("ssl", "false");
                param.put("port", splitArr[1]);
            }
        }

        if(splitArr.length == 3){
            if(splitArr[0].equals("http")){
                param.put("ssl", "false");
            } else if(splitArr[0].equals("https")){
                param.put("ssl", "true");
            }
            param.put("host", splitArr[1].substring(2));
            param.put("port", splitArr[2]);
        }
        return param;
    }


}
