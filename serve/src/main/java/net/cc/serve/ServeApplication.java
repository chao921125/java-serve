package net.cc.serve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServeApplication.class, args);
	}

}
