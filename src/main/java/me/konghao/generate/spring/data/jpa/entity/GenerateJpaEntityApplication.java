package me.konghao.generate.spring.data.jpa.entity;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import me.konghao.generate.spring.data.jpa.entity.server.GenerateCode;

@SpringBootApplication
public class GenerateJpaEntityApplication {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(GenerateJpaEntityApplication.class, args);

		GenerateCode generate = new GenerateCode();
		generate.buildCode();
	}
}
