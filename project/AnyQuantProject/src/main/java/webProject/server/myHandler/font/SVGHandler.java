package webProject.server.myHandler.font;

import org.apache.commons.io.IOUtils;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import webProject.resources.Resources;

/**
* AnyQuantProject/webProject.server.myHandler/SVGHandler.java
* @author cxworks
* 2016年5月9日 下午9:11:21
*/

public class SVGHandler implements Handler<RoutingContext> {

	@Override
	public void handle(RoutingContext event) {
		String path=event.request().path();
		path=path.substring(1);
		System.out.println(path);
		try {
			String svg=IOUtils.toString(Resources.class.getResourceAsStream(path));
			event.response().setChunked(true);
			event.response().putHeader("content-type", "image/svg+xml").write(svg).end();

			
		} catch (Exception e) {
			e.printStackTrace();
			event.fail(404);
		}
	}

}
