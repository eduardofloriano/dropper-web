package br.com.dropper.web.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import net.coobird.thumbnailator.Thumbnails;

public class ImageUtil implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public byte[] forceResize(InputStream stream, int width, int height, String format) throws IOException{
	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Thumbnails.of(stream).forceSize(width, height).outputFormat(format).toOutputStream(baos);
		
		return baos.toByteArray();
		
	}
	
	public byte[] resize(InputStream stream, int width, int height, String format) throws IOException{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Thumbnails.of(stream).size(width, height).outputFormat(format).toOutputStream(baos);
		
		return baos.toByteArray();
		
	}

}
