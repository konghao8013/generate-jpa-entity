package me.konghao.generate.spring.data.jpa.entity;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import junit.framework.Assert;
import me.konghao.generate.spring.data.jpa.entity.dbentity.Config;
import me.konghao.generate.spring.data.jpa.entity.dbentity.DBTable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateJpaEntityApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private ReadConfig read;

	@Test
	public void TestReadConfig() {
		Config config = read.Read();
		System.out.println("===============================================");
		System.out.println(config.getUrl());
	}

	@Test
	public void TestReadConfigCurrent() {
		System.out.println(read.readConfigCurrent());
	}


	@Test
	public void TestGetTables() {
		 GenerateCode generate=new GenerateCode();
		List<DBTable> list = generate.getTables();
		System.out.println("==============generate.getTables()===============");
		System.out.println(list.get(0).getName());
	}
}
