package my.netty.official.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
/**
 * Handles a server-side channel.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg){
		ByteBuf in = (ByteBuf)msg;
		try{
			while(in.isReadable()){
				System.out.println((char) in.readByte());
				System.out.flush();
			}
		}finally{
			ReferenceCountUtil.release(msg);
		}
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
		cause.printStackTrace();
		ctx.close();
	}
}
