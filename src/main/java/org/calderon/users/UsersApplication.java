package org.calderon.users;

import dev.jhonc.lib.demolib.config.AppConfig;
import dev.jhonc.lib.demolib.controller.advice.ExceptionApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AppConfig.class, ExceptionApi.class})
public class UsersApplication {
	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}
}
