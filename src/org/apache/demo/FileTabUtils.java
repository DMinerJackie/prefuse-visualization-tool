package org.apache.demo;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import prefuse.Display;

public class FileTabUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	/**
	 * 执行包括渲染、Action、Display的prefuse最小执行单元
	 * @return
	 */
	public static Display preShow(String label){
		
		GraphUtils.setRenderer(label);

		GraphUtils.setAction();

		GraphUtils.startRun();
		
		return GraphUtils.setDisplay();
	}
	
	/**
	 * 存储为图片选项卡
	 * @param image
	 */
	public static void saveAsAction(BufferedImage image){

		JFileChooser chooser = new JFileChooser();//文件保存对话框
		chooser.setCurrentDirectory(new File("."));

		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File file) {
				String name = file.getName();
				return name.toLowerCase().endsWith(".jpg");
			}

			@Override
			public String getDescription() {
				return "*.jpg";
			}
		};
		chooser.addChoosableFileFilter(filter);
		//		chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("文本文件（*.jpg）", "jpg"));
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File oFile = new File(chooser.getSelectedFile() + ".jpg");
			try {
				ImageIO.write(image, "jpeg", oFile);//保存图像文件
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
