package springboot.helloboot;

public class HelloController {
    public String hello(String name) {
        return "Hello " + name;
    }
}
