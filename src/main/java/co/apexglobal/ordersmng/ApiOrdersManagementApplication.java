package co.apexglobal.ordersmng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableCaching
public class ApiOrdersManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiOrdersManagementApplication.class, args);
	}

}
