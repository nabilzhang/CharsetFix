package me.nabil.app.charset.encoding.fix;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 乱码字符传修正处理
 * 
 * @author nabil
 *
 */
public class CharsetDecoder {
	/**
	 * 
	 * 该方法根据输入的乱码进行处理，若乱码修正后满足包含（中文，字母，数字，-，_）
	 * 则返回该编码名称和处理后的字符串组成的Map,输出字符串为utf-8
	 * 
	 * Map的key为{@link Charset#name()}
	 * 
	 * @param inputstr 乱码字符串
	 * @return
	 */
	public static Map<String, String> decode(String inputstr) {
		Map<String, String> retMap = new HashMap<String, String>();
		Map<String, Charset> availableCharsets = Charset.availableCharsets();
		Iterator<Entry<String, Charset>> iterator = availableCharsets
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Charset> entry = iterator.next();
			String charsetName = entry.getKey();
			Charset charset = entry.getValue();
			if (Charset.isSupported(charsetName)) {
				try {
					String trans = new String(
							inputstr.getBytes(charset.name()), "utf-8");
					if (checkCN(trans)) {
						System.out.println(charsetName + trans);
						retMap.put(charsetName, trans);
					}

				} catch (Exception e) {
					System.err.println(e);
				}

			}

		}
		return retMap;
	}

	/**
	 * 这里是根据正则表达式来判断是否已经转换正确
	 * 
	 * @param inputStr
	 * @return
	 */
	private static boolean checkCN(String inputStr) {
		Pattern p = Pattern.compile("^[\u4E00-\u9FA5a-zA-Z0-9_-]+$");
		Matcher m = p.matcher(inputStr);
		return m.matches();

	}

	public static void main(String args[]) {
		String testInput = "娴眰-鐐瑰嚮璺宠浆";
		Map<String, Charset> availableCharsets = Charset.availableCharsets();
		Iterator<Entry<String, Charset>> iterator = availableCharsets
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Charset> entry = iterator.next();
			String charsetName = entry.getKey();
			Charset charset = entry.getValue();
			if (Charset.isSupported(charsetName)) {
				try {
					String trans = new String(
							testInput.getBytes(charset.name()), "utf-8");
					if (checkCN(trans)) {
						System.out.println(charsetName + trans);
					} else {
						// System.out.println(charsetName+trans);
					}
				} catch (Exception e) {
					System.err.println(e);
				}

			}

		}
	}
}
