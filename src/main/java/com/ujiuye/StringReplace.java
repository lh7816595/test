package com.ujiuye;

import java.util.Map;
import org.apache.commons.text.StringSubstitutor;

public class StringReplace {

	/**
	 * 替换字符串的内容
	 * 
	 * @param params
	 * @param context
	 * @return
	 */
	public static String replace(Map<String, Object> params, String context) {
		StringSubstitutor stringSubstitutor = new StringSubstitutor(params, "[", "]");
		return stringSubstitutor.replace(context);
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String FistWordLowUp(String str) {
		char[] chars = str.toCharArray();
		if (chars[0] >= 'a' && chars[0] <= 'z') {
			chars[0] = (char) (chars[0] - 32);
		}
		return new String(chars);
	}

	/**
	 * 去掉表里面的_，紧接_后的字母大写 如：item_cat转为ItemCat
	 * 
	 * @param str
	 * @return
	 */
	public static String removeUp(String str) {
		String[] ss = str.split("_");

		StringBuilder sb = new StringBuilder();

		if (ss != null && ss.length > 0) {
			for (String temp : ss) {
				sb.append(FistWordLowUp(temp));
			}
		} else {
			sb.append(FistWordLowUp(str));
		}

		return sb.toString();
	}

	/**
	 * 去掉表里面的_，第一个不大写，其余大写 如：item_cat转为itemCat
	 * 
	 * @param str
	 * @return
	 */
	public static String removeUpFromTwo(String str) {

		String[] ss = str.split("_");

		StringBuilder sb = new StringBuilder();

		if (ss != null && ss.length > 0) {
			for (int i = 0; i < ss.length; i++) {
				if (i == 0) {
					sb.append(ss[i]);
				} else {
					sb.append(FistWordLowUp(ss[i]));
				}
			}
		} else {
			sb.append(str);
		}

		return sb.toString();
	}

}
