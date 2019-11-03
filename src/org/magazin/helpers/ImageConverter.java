package org.magazin.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public  class ImageConverter{
	private static ClassLoader classLoader = ImageConverter.class.getClassLoader();
	public static byte[] toByteArray(String imageFileName) {
		File file;
		
		if (imageFileName.contains(":")) {
			file = new File(imageFileName);
       	
        }
		else {
			file = new File(classLoader.getResource(imageFileName).getFile());
		}
		
		if(file.equals(null)) return null; 
		try{
			byte[] fileContent = Files.readAllBytes(file.toPath());
	
			return fileContent;
		}
		catch(IOException e){ return null;}
	}
	

}
