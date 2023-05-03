package springboot.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class HellobootApplication {

	public static void main(String[] args) {
		/*--------- 스프링 컨테이너 작성 및 빈 초기화 시작 ---------*/
		GenericWebApplicationContext applicationContext = new GenericWebApplicationContext();
		// application Context에 bean 등록 및 의존관계등록(알아서해줌)
		applicationContext.registerBean(HelloController.class);
		applicationContext.registerBean(SimpleHelloService.class);
		// 실제 생성
		applicationContext.refresh();
		/*--------- 스프링 컨테이너 작성 및 빈 초기화  끝 ---------*/

		/*--------- 서블릿 컨테이너 실행 및 서블릿 등록 시작 ---------*/
		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		WebServer webServer = serverFactory.getWebServer(servletContext -> {
			servletContext.addServlet("dispatcherServlet",
				new DispatcherServlet(applicationContext)
			).addMapping("/*");
		});
		webServer.start();
		/*--------- 서블릿 컨테이너 실행 및 서블릿 등록 끝 ---------*/
	}

}
