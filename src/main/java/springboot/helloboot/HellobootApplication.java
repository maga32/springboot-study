package springboot.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class HellobootApplication {
	@Bean
	public HelloController helloController(HelloService helloService) {
		return new HelloController(helloService);
	}

	@Bean
	public HelloService helloService() {
		return new SimpleHelloService();
	}

	public static void main(String[] args) {
		/*--------- 스프링 컨테이너 작성 및 빈 초기화 시작 ---------*/
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
			@Override
			protected void onRefresh() {
				super.onRefresh();

				/*--------- 서블릿 컨테이너 실행 및 서블릿 등록 시작 ---------*/
				ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
				WebServer webServer = serverFactory.getWebServer(servletContext -> {
					servletContext.addServlet("dispatcherServlet",
							new DispatcherServlet(this)
					).addMapping("/*");
				});
				webServer.start();
				/*--------- 서블릿 컨테이너 실행 및 서블릿 등록 끝 ---------*/
			}
		};
		// applicationContext 내부의 bean 등록 및 의존관계등록(알아서해줌)
		applicationContext.register(HellobootApplication.class);
		// 실제 생성
		applicationContext.refresh();
		/*--------- 스프링 컨테이너 작성 및 빈 초기화  끝 ---------*/


	}

}
