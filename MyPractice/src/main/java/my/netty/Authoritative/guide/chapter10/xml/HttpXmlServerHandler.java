package my.netty.Authoritative.guide.chapter10.xml;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

public class HttpXmlServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
		HttpXmlRequest xmlRequest = (HttpXmlRequest)msg;
        Order order = (Order) xmlRequest.getBody();
        System.out.println("Http server receive request : " + order);
        dobusiness(order);
        ctx.writeAndFlush(new HttpXmlResponse(null,order));
	}

	private void dobusiness(Order order) {
		order.getCustomer().setFirstName("狄");
		order.getCustomer().setLastName("仁杰");
		List<String> midNames = new ArrayList<String>();
		midNames.add("李元芳");
		order.getCustomer().setMiddleNames(midNames);
		Address address = order.getBillTo();
		address.setCity("洛阳");
		address.setCountry("大唐");
		address.setState("河南道");
		address.setPostCode("123456");
		order.setBillTo(address);
		order.setShipTo(address);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		// 在链路没有关闭并且出现异常的时候发送给客户端错误信息
		if (ctx.channel().isActive()) {
			sendError(ctx, INTERNAL_SERVER_ERROR);
		}
	}

	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status,
				Unpooled.copiedBuffer("失败: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

}

