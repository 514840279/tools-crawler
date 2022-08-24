package com.tools.json2obj.service;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * 文件名 ： Ocr.java
 * 包 名 ： com.tools.json2obj.service
 * 描 述 ： TODO(用一句话描述该文件做什么)
 * 机能名称：
 * 技能ID ：
 * 作 者 ： Administrator
 * 时 间 ： 2022年8月24日 下午4:11:08
 * 版 本 ： V1.0
 */
public class Ocr {

	/**
	 * 方法名： readFile
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param path
	 * 参 数： @return
	 * 返 回： String
	 * 作 者 ： Administrator
	 * @throws
	 */
	public static String readFile(String path) {
		ITesseract instance = new Tesseract();
		// 指定训练数据集合的路径
		instance.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
		// 指定为中文识别
//		instance.setLanguage("chi_sim");
		
		// 指定识别图片
		File imgDir = new File(path);
		long startTime = System.currentTimeMillis();
		String ocrResult = null;
		try {
			ocrResult = instance.doOCR(imgDir);

			// 输出识别结果
			System.out.println("OCR Result: \n" + ocrResult + "\n 耗时：" + (System.currentTimeMillis() - startTime) + "ms");
		} catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imgDir.delete();
		return ocrResult;
	}
	
}
