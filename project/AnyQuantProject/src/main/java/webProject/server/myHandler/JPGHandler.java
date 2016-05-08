package webProject.server.myHandler;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;


import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;
import webProject.resources.Resources;



/**
* AnyQuantProject/webProject.server.myHandler/ImageHandler.java
* @author cxworks
* 2016年5月3日 下午11:47:07
*/
public class JPGHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext event) {
		String fileName=event.request().path();
		fileName=fileName.substring(1);
		InputStream inputStream = null;
		ByteArrayOutputStream outputStream=null;
		try {
			inputStream=Resources.class.getResourceAsStream(fileName);
			outputStream=new ByteArrayOutputStream();
			
			byte[] buf = new byte[1024];
		      int numBytesRead = 0;
		      while ((numBytesRead = inputStream.read(buf)) != -1) {
		      outputStream.write(buf, 0, numBytesRead);
		      }
		      byte[] data=outputStream.toByteArray();
		      
			event.response().setChunked(true);
			
			event.response().putHeader("content-type", "image/jpeg").write(Buffer.buffer(data)).end();
		} catch (IOException|NullPointerException e) {
			
			event.response().setChunked(true);
			event.response().end("resources unavaliable");
		}
		finally {
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException | NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		}
		
	}

}
