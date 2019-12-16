package com.ujiuye;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

@SuppressWarnings("all")
public class MakeCodeMain {

	public static void main(String[] args) throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-dao.xml");

		DruidDataSource datasource = (DruidDataSource) context.getBean("dataSource");
		DruidPooledConnection conn = datasource.getConnection();

		// 定义生成模块名称：商品管理模块
		String modelName = "secrill";
		
		// 从spring配置文件获取改模块需要生成的表集合
		Set<String> tableSet = (Set<String>) context.getBean("secrill_ tableset");

		// 循环全部表
		for (String tableName : tableSet) {
			
			// 获取指定表的全部字段和说明
			List<String> listcolumn = getColumnCommentByTableName(tableName, conn);
			// 删除第一列，主键列
			String primarykey = listcolumn.remove(0);
			System.out.println("【表名称:" + tableName + "】");
			// 去除表名称的前缀：tb_
			tableName = tableName.substring(3);
			
			// 生成后台代码
			GeneratorSource.makeService(modelName, tableName, primarykey);
			GeneratorSource.makeServiceImpl(modelName, tableName, listcolumn, primarykey);
			GeneratorSource.makeController(modelName, tableName, primarykey);
			
			// 生成前端代码
			GeneratorSource.makeJsController(tableName);
			GeneratorSource.makeJsService(tableName);
			GeneratorSource.makeHtml(tableName, listcolumn, primarykey);
		}

		conn.close();
	}

	// 获取指定数据表的列名、类型、注释
	public static List<String> getColumnCommentByTableName(String tableName, DruidPooledConnection conn)
			throws SQLException {
		List<String> list = new ArrayList<String>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("show full columns from " + tableName);
		while (rs.next()) {
			// 字段名称~字段类型~字段注释
			list.add(rs.getString("Field") + "~" + rs.getString("Type") + "~" + rs.getString("Comment"));
		}

		rs.close();
		stmt.close();
		return list;
	}

}

