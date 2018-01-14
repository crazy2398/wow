package com.xutao.games.recorder;

import java.awt.image.BufferedImage;
import java.io.File;

public class Screenshot {
	private final BufferedImage image;
	private final File file;
	
	Screenshot(BufferedImage image,File file){
		this.image = image;
		this.file = file;
	}

	public BufferedImage getImage() {
		return image;
	}

	public File getFile() {
		return file;
	}
}
