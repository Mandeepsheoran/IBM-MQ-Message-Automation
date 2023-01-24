package com.das.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.das.constants.FrameworkConstants;

public class FileReaderUtil {
	
	private FileReaderUtil() {}
	
	public static String readXMLDataFromFile(){
		Path path =Paths.get(FrameworkConstants.getPnrFilePath());
		try {
			return new String(Files.readAllBytes(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
