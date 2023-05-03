package springboot.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan
public class HellobootApplication {
	@Bean
	public ServletWebServerFactory servletWebServerFactory() {
		return new TomcatServletWebServerFactory();
	}

	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	public static void main(String[] args) {
		run(HellobootApplication.class, args);
	}

	private static void run(Class<?> applicationClass, String... args) {
		/*--------- 스프링 컨테이너 작성 및 빈 초기화 시작 ---------*/
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
			@Override
			protected void onRefresh() {
				super.onRefresh();

				/*--------- 서블릿 컨테이너 실행 및 서블릿 등록 시작 ---------*/
				ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
				DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
				// 스프링 컨테이너 주입
				dispatcherServlet.setApplicationContext(this);

				WebServer webServer = serverFactory.getWebServer(servletContext -> {
					servletContext.addServlet("dispatcherServlet", dispatcherServlet).addMapping("/*");
				});
				webServer.start();
				/*--------- 서블릿 컨테이너 실행 및 서블릿 등록 끝 ---------*/
			}
		};
		// applicationContext 내부의 bean 등록 및 의존관계등록(알아서해줌)
		applicationContext.register(applicationClass);
		// 실제 생성
		applicationContext.refresh();
		/*--------- 스프링 컨테이너 작성 및 빈 초기화  끝 ---------*/
	}

}
