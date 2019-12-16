package com.ujiuye;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

public class GeneratorSource {

	private static String path_1 = "com";
	private static String path_2 = "baidu";

	/**
	 * 生成Service接口代码
	 * 
	 * @param tableName
	 * @param tableComment
	 */
	public static void makeService(String modelName, String tableName, String primarykey) {

		String srcJava = "";
		
		// 替换字符串中的占位符
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("comment", tableName);
		params.put("author", "senqi");
		params.put("Table2", StringReplace.removeUp(tableName));
		params.put("table2", StringReplace.removeUpFromTwo(tableName));
		params.put("path_1", path_1);
		params.put("path_2", path_2);
		params.put("package", path_1 + "." + path_2 + "." + modelName);
		
		// 获取主键名称、类型
		String[] keys = primarykey.split("~");
		if (keys != null) {
			params.put("PrimaryKeyName", StringReplace.removeUpFromTwo(keys[0]));
			// 根据数据库主键类型判断生成的主键类型
			if (keys[1].startsWith("varchar")) {
				params.put("PrimaryKeyType", "String");
			}
			if (keys[1].startsWith("bigint")) {
				params.put("PrimaryKeyType", "Long");
			}
			if (keys[1].startsWith("int")) {
				params.put("PrimaryKeyType", "Integer");
			}
		}

		// 读取Service模板文件
		try {
			String src = IOUtils
					.toString(new FileInputStream(".\\src\\main\\resources\\template\\[Table2]Service.java"), "utf-8");
			// 替换模板文件里面的标记
			srcJava = StringReplace.replace(params, src);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (srcJava != null && !"".equals(srcJava)) {
			// 创建目录保存源码
			File f1 = new File("./src/main/java/" + path_1 + "/" + path_2 + "/" + modelName + "/service/");
			if (!f1.exists()) {
				f1.mkdirs();
			}
			// 创建源码
			File f2 = new File(f1, StringReplace.removeUp(tableName) + "Service.java");
			try {
				// 保存源码
				IOUtils.write(srcJava, new FileOutputStream(f2), "utf-8");
				System.out.println("Service源码生成OK");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 生成Service实现类代码
	 * 
	 * @param tableName
	 * @param tableComment
	 */
	public static void makeServiceImpl(String modelName, String tableName, List<String> columns, String primarykey) {
		String srcJava = "";
		// 替换字符串中的占位符
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("comment", tableName);
		params.put("author", "senqi");
		params.put("Table2", StringReplace.removeUp(tableName));
		params.put("table2", StringReplace.removeUpFromTwo(tableName));
		params.put("path_1", path_1);
		params.put("path_2", path_2);
		params.put("package", path_1 + "." + path_2 + "." + modelName);
		
		// 获取主键名称、类型
		String[] keys = primarykey.split("~");
		if (keys != null) {
			params.put("PrimaryKeyName", StringReplace.removeUpFromTwo(keys[0]));
			// 根据数据库主键类型判断生成的主键类型
			if (keys[1].startsWith("varchar")) {
				params.put("PrimaryKeyType", "String");
			}
			if (keys[1].startsWith("bigint")) {
				params.put("PrimaryKeyType", "Long");
			}
			if (keys[1].startsWith("int")) {
				params.put("PrimaryKeyType", "Integer");
			}

		}
		
		String queryParms = "";
		// 读取列集合
		for (String str : columns) {
			try {
				String src = IOUtils.toString(new FileInputStream(".\\src\\main\\resources\\template\\条件查询.txt"), "utf-8");
				if (str.indexOf("~") > 0) {
					String[] ss = str.split("~");
					// 根据列名更新查询条件，只处理字符串类型的模糊查询
					if (ss[1].startsWith("varchar")) {
						Map<String, Object> querymap = new HashMap<String, Object>();
						querymap.put("Column2", StringReplace.removeUp(ss[0]));
						queryParms += StringReplace.replace(querymap, src);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		params.put("queryParms", queryParms);

		// 读取Service模板文件
		try {
			String src = IOUtils.toString(
					new FileInputStream(".\\src\\main\\resources\\template\\[Table2]ServiceImpl.java"), "utf-8");

			// 替换模板文件里面的标记
			srcJava = StringReplace.replace(params, src);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (srcJava != null && !"".equals(srcJava)) {
			// 创建目录保存源码
			File f1 = new File("./src/main/java/" + path_1 + "/" + path_2 + "/" + modelName + "/service/impl/");
			if (!f1.exists()) {
				f1.mkdirs();
			}
			// 创建源码
			File f2 = new File(f1, StringReplace.removeUp(tableName) + "ServiceImpl.java");
			try {
				IOUtils.write(srcJava, new FileOutputStream(f2), "utf-8");
				System.out.println("ServiceImpl源码生成OK");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成Controller接口代码
	 * 
	 * @param tableName
	 * @param tableComment
	 */
	public static void makeController(String modelName, String tableName, String primarykey) {
		String srcJava = "";
		// 替换字符串中的占位符
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("comment", tableName);
		params.put("author", "senqi");
		params.put("Table2", StringReplace.removeUp(tableName));
		params.put("table2", StringReplace.removeUpFromTwo(tableName));
		params.put("path_1", path_1);
		params.put("path_2", path_2);
		params.put("path_3", modelName);
		params.put("package", path_1 + "." + path_2 + "." + modelName);
		
		// 获取主键名称、类型
		String[] keys = primarykey.split("~");
		if (keys != null) {
			params.put("PrimaryKeyName", StringReplace.removeUpFromTwo(keys[0]));
			// 根据数据库主键类型判断生成的主键类型
			if (keys[1].startsWith("varchar")) {
				params.put("PrimaryKeyType", "String");
			}
			if (keys[1].startsWith("bigint")) {
				params.put("PrimaryKeyType", "Long");
			}
			if (keys[1].startsWith("int")) {
				params.put("PrimaryKeyType", "Integer");
			}

		}

		// 读取Service模板文件
		try {
			String src = IOUtils.toString(
					new FileInputStream(".\\src\\main\\resources\\template\\[Table2]Controller.java"), "utf-8");

			// 替换模板文件里面的标记
			srcJava = StringReplace.replace(params, src);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (srcJava != null && !"".equals(srcJava)) {
			// 创建目录保存源码
			File f1 = new File("./src/main/java/" + path_1 + "/" + path_2 + "/" + modelName + "/controller/");
			if (!f1.exists()) {
				f1.mkdirs();
			}
			// 创建源码
			File f2 = new File(f1, StringReplace.removeUp(tableName) + "Controller.java");
			try {
				IOUtils.write(srcJava, new FileOutputStream(f2), "utf-8");
				System.out.println("Controller源码生成OK");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成JsController接口代码
	 * 
	 * @param tableName
	 * @param tableComment
	 */
	public static void makeJsController(String tableName) {
		String srcJava = "";
		// 替换字符串中的占位符
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("comment", tableName);
		params.put("table2", StringReplace.removeUpFromTwo(tableName));

		// 读取Service模板文件
		try {
			String src = IOUtils
					.toString(new FileInputStream(".\\src\\main\\resources\\template\\[table2]Controller.js"), "utf-8");

			// 替换模板文件里面的标记
			srcJava = StringReplace.replace(params, src);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (srcJava != null && !"".equals(srcJava)) {
			// 创建目录保存源码
			File f1 = new File("./src/main/webapp/js/controller/");
			if (!f1.exists()) {
				f1.mkdirs();
			}
			// 创建源码
			File f2 = new File(f1, StringReplace.removeUpFromTwo(tableName) + "Controller.js");
			try {
				IOUtils.write(srcJava, new FileOutputStream(f2), "utf-8");
				System.out.println("JsController源码生成OK");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 生成JsService接口代码
	 * 
	 * @param tableName
	 * @param tableComment
	 */
	public static void makeJsService(String tableName) {
		String srcJava = "";
		// 替换字符串中的占位符
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("comment", tableName);
		params.put("table2", StringReplace.removeUpFromTwo(tableName));

		// 读取Service模板文件
		try {
			String src = IOUtils.toString(new FileInputStream(".\\src\\main\\resources\\template\\[table2]Service.js"),
					"utf-8");

			// 替换模板文件里面的标记
			srcJava = StringReplace.replace(params, src);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (srcJava != null && !"".equals(srcJava)) {
			// 创建目录保存源码
			File f1 = new File("./src/main/webapp/js/service/");
			if (!f1.exists()) {
				f1.mkdirs();
			}
			// 创建源码
			File f2 = new File(f1, StringReplace.removeUpFromTwo(tableName) + "Service.js");
			try {
				IOUtils.write(srcJava, new FileOutputStream(f2), "utf-8");
				System.out.println("JsService源码生成OK");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 生成Html前端页面代码
	 * 
	 * @param tableName
	 * @param tableComment
	 * @param columns
	 */
	public static void makeHtml(String tableName, List<String> columns, String primarykey) {
		String srcJava = "";
		// 替换字符串中的占位符
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("comment", tableName);
		params.put("table2", StringReplace.removeUpFromTwo(tableName));
		params.put("path_2", "youlexuan");
		
		// 获取主键名称
		String[] keys = primarykey.split("~");
		if (keys != null) {
			params.put("key", StringReplace.removeUpFromTwo(keys[0]));
		}
		
		String titleParms = "";
		String columnParms = "";
		String formParms = "";
		// 读取列集合
		for (String str : columns) {
			try {
				String titlesrc = IOUtils.toString(new FileInputStream(".\\src\\main\\resources\\template\\页面表格标题.txt"),
						"utf-8");
				String tablesrc = IOUtils.toString(new FileInputStream(".\\src\\main\\resources\\template\\页面表格内容.txt"),
						"utf-8");
				String formsrc = IOUtils
						.toString(new FileInputStream(".\\src\\main\\resources\\template\\编辑表单.txt"), "utf-8");
				
				if (str.indexOf("~") > 0) {
					String[] ss = str.split("~");
					// 根据列名更新查询条件
					Map<String, Object> querymap = new HashMap<String, Object>();
					if (ss.length == 3) {
						querymap.put("columnComment", ss[2]);
						titleParms += StringReplace.replace(querymap, titlesrc);
					}
					querymap.put("column2", StringReplace.removeUpFromTwo(ss[0]));
					columnParms += StringReplace.replace(querymap, tablesrc);

					formParms += StringReplace.replace(querymap, formsrc);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		params.put("offcntitle", titleParms);
		params.put("offcntable", columnParms);
		params.put("offcnform", formParms);

		// 读取hTML模板文件
		try {
			String src = IOUtils.toString(new FileInputStream(".\\src\\main\\resources\\template\\[table2].html"),
					"utf-8");

			// 替换模板文件里面的标记
			srcJava = StringReplace.replace(params, src);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (srcJava != null && !"".equals(srcJava)) {
			// 创建目录保存源码
			File f1 = new File("./src/main/webapp/admin/");
			if (!f1.exists()) {
				f1.mkdirs();
			}
			// 创建源码
			File f2 = new File(f1, tableName + ".html");
			try {
				IOUtils.write(srcJava, new FileOutputStream(f2), "utf-8");
				System.out.println("html源码生成OK");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
