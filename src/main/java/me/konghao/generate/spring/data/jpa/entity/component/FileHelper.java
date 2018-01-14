package me.konghao.generate.spring.data.jpa.entity.component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class FileHelper {
	// 判断文件是否存在
	public void fileExists(String path) {
		File file = new File(path);
		if (file.exists()) {
			System.out.println("file exists");
		} else {
			System.out.println("file not exists, create it ...");
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 判断文件夹是否存在
	public void dirExists(String path) {
		File dir = new File(path);
		if (dir.exists()) {
			if (dir.isDirectory()) {
				System.out.println("dir exists");
			} else {
				System.out.println("the same name file exists, can not create dir");
			}
		} else {
			System.out.println("dir not exists, create it ...");
			dir.mkdir();
		}

	}

	public void textToFile(final String name, final String strBuffer) {
		try {
			FileHelper f = new FileHelper();
			String fileDir = System.getProperty("user.dir") + "\\entity\\";
			f.dirExists(fileDir);
			String filePath = fileDir + name;
			f.fileExists(filePath);
			// 创建文件对象
			File fileText = new File(filePath);
			// 向文件写入对象写入信息
			FileWriter fileWriter = new FileWriter(fileText);

			// 写文件
			fileWriter.write(strBuffer);
			// 关闭
			fileWriter.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
	}

}
