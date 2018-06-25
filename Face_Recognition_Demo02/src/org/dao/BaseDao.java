package org.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * 
 * 项目名称：test_face_photo 类名称：BaseDao 类描述： 数据库连接共用类 创建人：Mu Xiongxiong
 * 创建时间：2017-9-22 下午6:56:18 修改人：Mu Xiongxiong 修改时间：2017-9-22 下午6:56:18 修改备注：
 * 
 * @version
 * 
 */
public class BaseDao {

	private static Connection con = null;

	// 数据库驱动
//	private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	//设置连接MySQL驱动
	private static String driver = "com.mysql.jdbc.Driver"; 

	// 连接字符串
//	private static String url = "jdbc:sqlserver://localhost:1433; DatabaseName=facedb";

	//设置连接MySQL URL
	private static String url = "jdbc:mysql://localhost:3306/facedb";
	
	// 数据库用户名
	private static String user = "root";

	// 密码
	private static String pwd = "root";

	// 初始化PreparedStatement，编译sql语句
	private static PreparedStatement ps = null;

	// 初始化ResultSet数据集
	private static ResultSet rs = null;

	/**
	 * 
	 * @Description: 该方法的主要作用：打开连接，连接数据库 @Title:
	 * getConnection @param @return @param @throws
	 * ClassNotFoundException @param @throws SQLException 设定文件 @return
	 * 返回类型：Connection @throws
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		if (con == null) {
			con = DriverManager.getConnection(url, user, pwd);
		}
		return con;
	}

	/**
	 * 
	 * @Description: 该方法的主要作用：增删改通用的方法 @Title: executeUpdate @param @param
	 * sql @param @param prams @param @return @param @throws
	 * ClassNotFoundException @param @throws SQLException 设定文件 @return
	 * 返回类型：int @throws
	 */
	public static int executeUpdate(String sql, List<Object> prams) throws ClassNotFoundException, SQLException {
		int rel = 0;
		con = getConnection();
		ps = con.prepareStatement(sql);
		if (prams != null) {
			for (int i = 0; i < prams.size(); i++) {
				ps.setObject(i + 1, prams.get(i));
			}
		}
		rel = ps.executeUpdate();
		return rel;
	}

	/**
	 * 
	 * @Description: 该方法的主要作用：查询的通用方法 @Title: executeQuery @param @param
	 * sql @param @param prams @param @return 设定文件 @return
	 * 返回类型：ResultSet @throws
	 */
	public static ResultSet executeQuery(String sql, List<Object> prams) throws ClassNotFoundException, SQLException {
		con = getConnection();
		ps = con.prepareStatement(sql);
		if (prams != null) {
			for (int i = 0; i < prams.size(); i++) {
				ps.setObject(i + 1, prams.get(i));
			}
		}
		rs = ps.executeQuery();
		return rs;
	}

	/**
	 * 
	 * @Description: 该方法的主要作用：关闭资源 @Title: closeConnnection @param 设定文件 @return
	 * 返回类型：void @throws
	 */
	public static void closeConnnection() {
		// 关闭之前先判断是否为null
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (con != null) {
			try {
				con.close();
				con = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * @Description: 该方法的主要作用：测试连接数据库与否 @Title: main @param @param args
	 * 设定文件 @return 返回类型：void @throws
	 */
	public static void main(String[] args) {
		try {
			System.out.println(getConnection());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}