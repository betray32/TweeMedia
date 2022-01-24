package cl.twitter.TweeMedia.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( "cl.twitter.TweeMedia.*" )
public class TweeMediaApplication {
	public static void main(String[] args) {
		SpringApplication.run(TweeMediaApplication.class, args);
	}
}
