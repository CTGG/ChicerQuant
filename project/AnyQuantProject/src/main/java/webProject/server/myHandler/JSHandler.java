package webProject.server.myHandler;

import java.io.File;
import java.io.IOException;

import com.mchange.io.FileUtils;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import webProject.resources.Resources;

/**
* AnyQuantProject/webProject.server.myHandler/JSHandler.java
* @author cxworks
* 2016年5月4日 上午9:30:34
*/
public class JSHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext event) {
		String fileName=event.request().getParam("file");
		
		try {
			File file=new File(Resources.class.getResource("js/"+fileName).getPath());
			String js=FileUtils.getContentsAsString(file);
			event.response().setChunked(true);
			event.response().putHeader("content-type", "text/javascript").write(js).end();
		} catch (IOException|NullPointerException e) {
			event.response().setChunked(true);
			event.response().end("resources unavaliable");
		}
	}

}
