package my.netty.Authoritative.guide.chapter10.json;

import io.netty.handler.codec.http.FullHttpRequest;  

public class HttpJsonRequest {  
  
    private FullHttpRequest request;  
    private Object body;  
      
    public HttpJsonRequest(FullHttpRequest request, Object body) {  
        super();  
        this.request = request;  
        this.body = body;  
    }  
    public FullHttpRequest getRequest() {  
        return request;  
    }  
    public void setRequest(FullHttpRequest request) {  
        this.request = request;  
    }  
    public Object getBody() {  
        return body;  
    }  
    public void setBody(Object body) {  
        this.body = body;  
    }  
}  
