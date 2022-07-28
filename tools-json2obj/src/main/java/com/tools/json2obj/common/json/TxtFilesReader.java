package com.tools.json2obj.common.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tools.json2obj.common.BaseException;

/**
 * 文件名 ： TxtFilesReader.java
 * 包 名 ： org.eastone.utils.files
 * 描 述 ： 读取文件信息
 * 作 者 ： Tenghui.Wang
 * 时 间 ： 2016年3月1日 下午4:09:17
 * 版 本 ： V1.0
 */
public class TxtFilesReader {
	
	private static final Logger	logger				= LoggerFactory.getLogger(TxtFilesReader.class);
	
	static String				default_chartset	= "UTF8";
	
	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 */
	public void readFileByBytes(String fileName) {
		// 单字节读取
		File file = new File(fileName);
		InputStream in = null;
		try {
			logger.info("以字节为单位读取文件内容，一次读一个字节：");
			// 一次读一个字节
			in = new FileInputStream(file);
			int tempbyte;
			while ((tempbyte = in.read()) != -1) {
				System.out.write(tempbyte);
			}
			in.close();
		} catch (IOException e) {
			logger.error("读文件时出错:{}", e.getMessage());
			throw new BaseException(-1, "文件錯誤");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("关闭出错:{}", e.getMessage());
				}
			}
		}
		// 多字节读取
		try {
			logger.info("以字节为单位读取文件内容，一次读多个字节：");
			// 一次读多个字节
			byte[] tempbytes = new byte[100];
			int byteread = 0;
			in = new FileInputStream(fileName);
			TxtFilesReader.showAvailableBytes(in);
			// 读入多个字节到字节数组中，byteread为一次读入的字节数
			while ((byteread = in.read(tempbytes)) != -1) {
				System.out.write(tempbytes, 0, byteread);
			}
		} catch (Exception e) {
			logger.error("读文件时出错:{}", e.getMessage());
			throw new BaseException(-1, "文件錯誤");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("关闭出错:{}", e.getMessage());
				}
			}
		}
	}
	
	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 */
	public void readFileByChars(String fileName) {
		File file = new File(fileName);
		Reader reader = null;
		try {
			logger.info("以字符为单位读取文件内容，一次读一个字节：");
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				// 对于windows下，\r\n这两个字符在一起时，表示一个换行。
				// 但如果这两个字符分开显示时，会换两次行。
				// 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
				if (((char) tempchar) != '\r') {
					System.out.print((char) tempchar);
				}
			}
			reader.close();
		} catch (Exception e) {
			logger.error("读文件时出错:{}", e.getMessage());
			throw new BaseException(-1, "文件錯誤");
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				logger.error("关闭出错:{}", e.getMessage());
			}
		}

		try {
			logger.info("以字符为单位读取文件内容，一次读多个字节：");
			// 一次读多个字符
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(fileName));
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 同样屏蔽掉\r不显示
				if ((charread == tempchars.length) && (tempchars[tempchars.length - 1] != '\r')) {
					System.out.print(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							System.out.print(tempchars[i]);
						}
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("读文件时出错:{}", e.getMessage());
			throw new BaseException(-1, "文件錯誤");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("关闭出错:{}", e.getMessage());
				}
			}
		}
	}
	
	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 *
	 * @return
	 */
	public static List<String> readFileByLines(String fileName) {
		return readFileByLines(fileName, default_chartset);
	}
	
	public static List<String> readFileByLines(String fileName, String encoding) {
		File file = new File(fileName);
		BufferedReader reader = null;
		List<String> list = null;
		InputStreamReader read = null;
		try {
			logger.info("以行为单位读取文件内容，一次读一整行：");
			read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
			reader = new BufferedReader(read);
			String tempString = null;
			// int line = 1;
			list = new ArrayList<String>();
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				list.add(tempString);
				// line++;
			}
			reader.close();
		} catch (IOException e) {
			logger.error("读文件时出错:{}", e.getMessage());
			throw new BaseException(-1, "文件錯誤");
		} finally {
			if (read != null) {
				try {
					read.close();
				} catch (IOException e) {
					logger.error("关闭出错:{}", e.getMessage());
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("关闭出错:{}", e.getMessage());
				}
			}
		}
		return list;
	}
	
	/**
	 * 读取文件的第一行数据
	 * 方法名： readFileFirstLines
	 * 功 能： TODO(这里用一句话描述这个方法的作用)
	 * 参 数： @param fileName
	 * 参 数： @return
	 * 返 回： String
	 * 作 者 ： Tenghui.Wang
	 * @throws
	 */
	
	public static String readFileFirstLines(String fileName) {
		return readFileFirstLines(fileName, default_chartset);
	}
	
	public static String readFileFirstLines(String fileName, String encoding) {
		File file = new File(fileName);
		BufferedReader reader = null;
		String tempString = null;
		InputStreamReader read = null;
		try {
			logger.info("以行为单位读取文件内容，一次读一整行：");
			read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
			reader = new BufferedReader(read);
			tempString = reader.readLine();
			
			reader.close();
		} catch (IOException e) {
			logger.error("读文件时出错:{}", e.getMessage());
			throw new BaseException(-1, "文件錯誤");
		} finally {
			if (read != null) {
				try {
					read.close();
				} catch (IOException e) {
					logger.error("关闭出错:{}", e.getMessage());
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("关闭出错:{}", e.getMessage());
				}
			}
		}
		return tempString;
	}
	
	/**
	 * 功能：Java读取txt文件的内容
	 * 步骤：1：先获得文件句柄
	 * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流
	 * 4：一行一行的输出。readline()。
	 * 备注：需要考虑的是异常情况
	 *
	 * @param filePath
	 */
	public static List<String> readTxtFile(String filePath) {
		return readTxtFile(filePath, default_chartset);
	}
	
	public static List<String> readTxtFile(String filePath, String encoding) {
		List<String> strList = new ArrayList<String>();
		InputStreamReader read = null;
		BufferedReader bufferedReader = null;
		try {
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				strList = new ArrayList<String>();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					strList.add(lineTxt);
				}
				bufferedReader.close();
				read.close();
			} else {
				logger.info("找不到指定的文件");
				throw new BaseException(-1, "文件沒找到錯誤");
			}
		} catch (Exception e) {
			logger.error("读文件时出错:{}", e.getMessage());
			throw new BaseException(-1, "文件錯誤");
		} finally {
			try {
				if (read != null) {
					read.close();
				}
			} catch (IOException e) {
				logger.error("关闭出错:{}", e.getMessage());
			}
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}

			} catch (IOException e) {
				logger.error("关闭出错:{}", e.getMessage());
			}
		}
		return strList;
		
	}
	
	/**
	 * 随机读取文件内容
	 */
	public static void readFileByRandomAccess(String fileName) {
		RandomAccessFile randomFile = null;
		try {
			logger.info("随机读取一段文件内容：");
			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(fileName, "r");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 读文件的起始位置
			int beginIndex = (fileLength > 4) ? 4 : 0;
			// 将读文件的开始位置移到beginIndex位置。
			randomFile.seek(beginIndex);
			byte[] bytes = new byte[10];
			int byteread = 0;
			// 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
			// 将一次读取的字节数赋给byteread
			while ((byteread = randomFile.read(bytes)) != -1) {
				System.out.write(bytes, 0, byteread);
			}
		} catch (IOException e) {
			logger.error("读文件时出错:{}", e.getMessage());
			throw new BaseException(-1, "文件沒找到錯誤");
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e) {
					logger.error("关闭出错:{}", e.getMessage());
				}
			}
		}
	}
	
	/**
	 * 显示输入流中还剩的字节数
	 */
	private static void showAvailableBytes(InputStream in) {
		try {
			logger.info("当前字节输入流中的字节数为:" + in.available());
		} catch (IOException e) {
			logger.error("出错:{}", e.getMessage());
		}
	}
	
	/**
	 * 方法名： readToString
	 * 功 能： 读取整个文件
	 * 参 数： @param fileName
	 * 参 数： @param encoding
	 * 参 数： @return
	 * 返 回： String
	 * 作 者 ： Tenghui.Wang
	 * @throws
	 */
	public static String readToString(String fileName, String encoding) {
		File file = new File(fileName.trim());
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			StringBuffer sb = new StringBuffer();
			while ((in.read(filecontent)) != -1) {
				sb.append(new String(filecontent, encoding));
			}
			in.close();

			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			logger.error("The OS does not support " + encoding);
			throw new BaseException(-1, "字符集錯誤");
		} catch (FileNotFoundException e) {
			logger.error("文件未找到:{}", e.getMessage());
			throw new BaseException(-1, "文件沒找到錯誤");
		} catch (IOException e) {
			logger.error("读文件时出错:{}", e.getMessage());
			throw new BaseException(-1, "文件錯誤");
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("关闭出错:{}", e.getMessage());
			}
		}
	}
	
}
