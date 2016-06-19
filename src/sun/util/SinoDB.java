package sun.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sun.model.SimpleSchema;



public class SinoDB {
    /**
	 * 数据保存
	 */
    public static boolean save(SimpleSchema schema) {
        return insert(schema, true);
    }

    public static boolean save(SimpleSchema schema, boolean batchFlag) {
        return insert(schema,batchFlag);
    }

    public static boolean insert(SimpleSchema schema) {
        return insert(schema, true);
    }

    public static boolean insert(SimpleSchema schema, boolean batchFlag) {

		boolean save = false;
		Connection conn = getConnection(batchFlag);
		PreparedStatement stmt = null;

		Class cl = schema.getClass();
		Field[] fields = cl.getDeclaredFields();
		ArrayList<String> cols = getAllColumen(schema);

		StringBuffer sbs = new StringBuffer();
		StringBuffer sbv = new StringBuffer();
		sbs.append("insert into ");
		sbs.append(cl.getSimpleName().substring(0,
				cl.getSimpleName().lastIndexOf("Schema")));
		sbs.append("(");
		for (int i = 0; i < cols.size(); i++) {
			sbs.append(cols.get(i));
			sbv.append("?");
			if (i != cols.size() - 1) {
				sbs.append(",");
				sbv.append(",");
			}
		}

		sbs.append(") values (");
		sbs.append(sbv);
		sbs.append(")");

		try {
		 	 System.out.println(sbs.toString().toLowerCase());
			 String sql = sbs.toString().toLowerCase();
			 stmt = conn.prepareStatement(sql);
			 for (int i = 0; i < cols.size(); i++) {
				for (Field field : fields) {
					if (cols.get(i).equalsIgnoreCase(field.getName())) {
						field.setAccessible(true);
						String colType = field.getType().getName();
						Object value =field.get(schema);
						if (colType.indexOf("String") != -1
								|| colType.indexOf("Date") != -1
							) {
							
							if (value == null  || value.equals("") || value == ""  || "null".equalsIgnoreCase((String) value)) {
									 value = null;
							   }
							}
						stmt.setObject(i + 1, value);
						break;
					}
				}
			}

			int result = stmt.executeUpdate();

			if (result > 0) {
				save = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			closeDBConnection(conn, stmt, null, batchFlag);
		} finally {
			closeDBConnection(conn, stmt, null, batchFlag);
		}
		return save;
	}

    /**
     * 单表操作传入true
     * @param schema
     * @return
     */
    public static boolean delete(SimpleSchema schema) {
        return delete(schema, true);
    }

    /**
     * 数据删除
     */
    public static boolean delete(SimpleSchema schema, boolean batchFlag) {

		boolean delete = false;
		Connection conn = getConnection(batchFlag);
		PreparedStatement stmt = null;

		Class cl = schema.getClass();
		Field[] fields = cl.getDeclaredFields();
		ArrayList<String> cols = getAllColumen(schema);

		StringBuffer sbs = new StringBuffer();
		StringBuffer sbv = new StringBuffer();
		sbs.append("delete from ");
		sbs.append(cl.getSimpleName().substring(0,
				cl.getSimpleName().lastIndexOf("Schema")));

		sbs.append(" where 1=? ");
		try{

			ArrayList<Object> stv = new ArrayList<Object>();
			stv.add(1);
			for (int i = 0; i < cols.size(); i++) {
				for (Field field : fields) {
					if (cols.get(i).equalsIgnoreCase(field.getName())) {
						field.setAccessible(true);
						String colType = field.getType().getName();
						Object value = field.get(schema);
				
						if (colType.indexOf("String") != -1
								|| colType.indexOf("Date") != -1) {
							if (value != null
									&& !String.valueOf(value).trim().equals("")) {
								stv.add(value);
								sbs.append(" and "+field.getName() + "= ? ");
							}
						} else if (colType.indexOf("int") != -1
								|| colType.indexOf("double") != -1
								|| colType.indexOf("float") != -1) {

							if (value != null
									&& !String.valueOf(value).trim().equals("")) {
								String isZero = String.valueOf(value);
								if (!isZero.equals("0")
										&& !isZero.equals("0.0")) {
									sbs.append(" and "+field.getName() + "= ? and");
									stv.add(value);
								}
							}
						}						
						break;
					}

				}
			}
			
			String sql = sbs.toString().toLowerCase();
			System.out.println(sql);
			if(stv.size() == 1){
				System.out.println("不能进行全表删除");
				throw new SQLException("不能进行全表删除");
			}
			
			stmt = conn.prepareStatement(sql);
			for(int i=0;i<stv.size() ; i++){
			   stmt.setObject(i + 1, stv.get(i));
			}


			int result = stmt.executeUpdate();

			if (result > 0) {
				delete = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			closeDBConnection(conn, stmt, null, batchFlag);
		} finally {
			closeDBConnection(conn, stmt, null, batchFlag);
		}
		return delete;
	}

    /**
     * 单表更新
     * @param schema
     * @return
     */
    public static boolean update(SimpleSchema schema) {
        return update(schema, true);
    }

    /**
     * 数据修改： 按主键更新:
     */
    public static boolean update(SimpleSchema schema, boolean batchFlag) {

		boolean update = false;
		Connection conn = getConnection(batchFlag);
		PreparedStatement stmt = null;

		Class cl = schema.getClass();
		Field[] fields = cl.getDeclaredFields();
		ArrayList<String> cols = getAllColumen(schema);

		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE ");
		sb.append(cl.getSimpleName().substring(0,
				cl.getSimpleName().lastIndexOf("Schema")));
		sb.append(" SET ");
		for (int i = 0; i < cols.size(); i++) {
			sb.append(cols.get(i));
			sb.append(" = ?");
			if (i != cols.size() - 1) {
				sb.append(",");
			}
		}

		sb.append(" WHERE ");
		String[] spks = schema.getPK();
		for (String spk : spks) {
			sb.append(spk);
			sb.append(" = ?");

			if (!spk.equals(spks[spks.length - 1])) {
				sb.append(" and ");
			}
		}

		try {
			String sql = sb.toString().toLowerCase();
			System.out.println(sql);
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < cols.size(); i++) {
				for (Field field : fields) {
					if (cols.get(i).equalsIgnoreCase(field.getName())) {
						field.setAccessible(true);
						Object value = field.get(schema);
						
						if (value == null  || value.equals("") || value == ""  || "null".equalsIgnoreCase((String) ""+value)) {
						     value = null;
					    }
						stmt.setObject(i + 1, value);
						break;
					}
				}
			}
			for (int i = 0; i < spks.length; i++) {
				for (Field field : fields) {
					if (field.getName().equalsIgnoreCase(spks[i])) {
						field.setAccessible(true);
						Object value = field.get(schema);
						if (value == null) {
							System.out.println("主键" + spks[i] + "值不能为空!");
							throw new SQLException("主键" + spks[i] + "值不能为空!");
						}
						stmt.setObject(cols.size() + (i + 1), value);
						break;
					}
				}
			}

			int result = stmt.executeUpdate();
			if (result > 0) {
				update = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			closeDBConnection(conn, stmt, null, batchFlag);
		} finally {
			closeDBConnection(conn, stmt, null, batchFlag);
		}
		return update;
	}

	/**
	 * 全表查询
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static ArrayList getList(Class<?> cl) throws InstantiationException, IllegalAccessException {

		ArrayList<SimpleSchema> arr1 = new ArrayList<SimpleSchema>();
		ArrayList<SimpleSchema> arr = new ArrayList<SimpleSchema>();
		Connection conn = getConnection(true);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Field[] fields = cl.getDeclaredFields();

		ArrayList<String> cols = getAllColumen((SimpleSchema)cl.newInstance());

		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		int sqlsplit = cols.size() - 1;
		for (int i = 0; i < cols.size(); i++) {
			sb.append(cols.get(i));
			if (i != sqlsplit) {
				sb.append(",");
			}
		}

		sb.append(" from ");
		sb.append(cl.getSimpleName().substring(0,
				cl.getSimpleName().lastIndexOf("Schema")));
		System.out.println(sb.toString());

		try {
			String sql = sb.toString().toLowerCase();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {

				Object obj = cl.newInstance();
				for (String col : cols) {
					for (Field field : fields) {
						if (col.equalsIgnoreCase(field.getName())) {
							field.setAccessible(true);

							String colType = field.getType().getName();
							Object value;
							if (colType.indexOf("int") != -1) {
								value = rs.getInt(field.getName());
							} else if (colType.indexOf("double") != -1) {

								value = rs.getDouble(field.getName());
							} else {
								value = rs.getString(field.getName());
							}
							field.set(obj, value);
							break;
						}
					}
				}
				arr.add((SimpleSchema) obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			closeDBConnection(conn, stmt, rs);
		} finally {
			closeDBConnection(conn, stmt, rs);
		}
		return arr;
	}


	/**
	 * 主键查询
	 */
	public static SimpleSchema getInfo(SimpleSchema schema) {

		Connection conn = getConnection(true);
		PreparedStatement stmt = null;
		ResultSet rs = null;

		Class cl = schema.getClass();
		Field[] fields = cl.getDeclaredFields();
		ArrayList<String> cols = getAllColumen(schema);

		/*
		 * 组装SQL
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("select * ");
		sb.append(" from ");
		sb.append(cl.getSimpleName().substring(0,
				cl.getSimpleName().lastIndexOf("Schema")));
		sb.append(" where ");
		String[] spks = schema.getPK();
		for (String spk : spks) {
			sb.append(spk);
			sb.append(" = ? ");
			if (!spk.equals(spks[spks.length - 1])) {
				sb.append(" and ");
			}
		}

		/*
		 * 执行SQL并返回
		 */
		try {
			//Object obj = cl.newInstance();
			Object obj = schema;
			String sql = sb.toString().toLowerCase();
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < spks.length; i++) {

				for (Field field : fields) { // 还是使用反射取出正确
					if (field.getName().equalsIgnoreCase(spks[i])) {
						field.setAccessible(true);
						Object value = field.get(schema);
						if (value == null) {
							System.out.println("主键" + spks[i] + "值不能为空!");
							throw new SQLException("主键" + spks[i] + "值不能为空!");
						}
						stmt.setObject(i + 1, value);
						break;
					}
				}

			}
			System.out.println(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				for (String col : cols) {
					for (Field field : fields) {
						if (col.equalsIgnoreCase(field.getName())) {
							field.setAccessible(true);
							String colType = field.getType().getName();
							Object value;
							if (colType.indexOf("int") != -1) {
								value = rs.getInt(field.getName());
							} else if (colType.indexOf("double") != -1) {

								value = rs.getDouble(field.getName());
							} else {
								value = rs.getString(field.getName());
							}
							field.set(obj, value);
							break;
						}
					}
				}

				return (SimpleSchema) obj;

			}
		} catch (Exception e) {
			e.printStackTrace();
			closeDBConnection(conn, stmt, rs);
		} finally {
			closeDBConnection(conn, stmt, rs);
		}
		return null;
	}
	
	
	/**
	 * 数据查询
	 */
	public static ArrayList query(SimpleSchema schema) {

		
		ArrayList<SimpleSchema> arr = new ArrayList<SimpleSchema>();
		Connection conn = getConnection(true);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Class cl = schema.getClass();
		Field[] fields = cl.getDeclaredFields();
		ArrayList<String> cols = getAllColumen(schema);
		
		StringBuffer sbs = new StringBuffer();
		StringBuffer sbv = new StringBuffer();
		sbs.append("select * from ");
		sbs.append(cl.getSimpleName().substring(0,
				cl.getSimpleName().lastIndexOf("Schema")));

		sbs.append(" where 1=1 ");
		try{
			ArrayList<Object> stv = new ArrayList<Object>();
			for (int i = 0; i < cols.size(); i++) {
				for (Field field : fields) {
					if (cols.get(i).equalsIgnoreCase(field.getName())) {
						field.setAccessible(true);
						String colType = field.getType().getName();
						Object value = field.get(schema);
				
						if (colType.indexOf("String") != -1
								|| colType.indexOf("Date") != -1) {
							if (value != null
									&& !String.valueOf(value).trim().equals("")) {
								stv.add(value);
								sbs.append(" and "+field.getName() + "= ? ");
							}
						} else if (colType.indexOf("int") != -1
								|| colType.indexOf("double") != -1
								|| colType.indexOf("float") != -1) {

							if (value != null
									&& !String.valueOf(value).trim().equals("")) {
								String isZero = String.valueOf(value);
								if (!isZero.equals("0")
										&& !isZero.equals("0.0")) {
									sbs.append(" and "+field.getName() + "= ? and");
									stv.add(value);
								}
							}
						}						
						break;
					}

				}
			}
			
			String sql = sbs.toString().toLowerCase();
			
			System.out.println(sql);
			if(stv.size() == 0){
				System.out.println("不能进行全表查询");
				throw new SQLException("不能进行全表查询");
			}
			
			stmt = conn.prepareStatement(sql);
			for(int i=0;i<stv.size() ; i++){
			   stmt.setObject(i + 1, stv.get(i));
			}

			
			rs = stmt.executeQuery();
	
			
			while (rs.next()) {

				Object obj = cl.newInstance();
				for (String col : cols) {
					for (Field field : fields) {
						if (col.equalsIgnoreCase(field.getName())) {
							field.setAccessible(true);

							String colType = field.getType().getName();
							Object value;
							if (colType.indexOf("int") != -1) {
								value = rs.getInt(field.getName());
							} else if (colType.indexOf("double") != -1) {

								value = rs.getDouble(field.getName());
							} else {
								value = rs.getString(field.getName());
							}
							field.set(obj, value);
							break;
						}
					}
				}
				arr.add((SimpleSchema) obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
			closeDBConnection(conn, stmt, null);
		} finally {
			closeDBConnection(conn, stmt, null);
		}
		return arr;
	}
	
	
	/**
	 * 数据查询
	 */
	public static SimpleSchema findOne(SimpleSchema schema) {

		SimpleSchema oneSchema = null ;
		boolean delete = false;
		Connection conn = getConnection(true);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Class cl = schema.getClass();
		Field[] fields = cl.getDeclaredFields();
		ArrayList<String> cols = getAllColumen(schema);
		
		StringBuffer sbs = new StringBuffer();
		StringBuffer sbv = new StringBuffer();
		sbs.append("select * from ");
		sbs.append(cl.getSimpleName().substring(0,
				cl.getSimpleName().lastIndexOf("Schema")));

		sbs.append(" where 1=? ");
		try{
			ArrayList<Object> stv = new ArrayList<Object>();
			stv.add(1);
			for (int i = 0; i < cols.size(); i++) {
				for (Field field : fields) {
					if (cols.get(i).equalsIgnoreCase(field.getName())) {
						field.setAccessible(true);
						String colType = field.getType().getName();
						Object value = field.get(schema);
				
						if (colType.indexOf("String") != -1
								|| colType.indexOf("Date") != -1) {
							if (value != null
									&& !String.valueOf(value).trim().equals("")) {
								stv.add(value);
								sbs.append(" and "+field.getName() + "= ? ");
							}
						} else if (colType.indexOf("int") != -1
								|| colType.indexOf("double") != -1
								|| colType.indexOf("float") != -1) {

							if (value != null
									&& !String.valueOf(value).trim().equals("")) {
								String isZero = String.valueOf(value);
								if (!isZero.equals("0")
										&& !isZero.equals("0.0")) {
									sbs.append(" and "+field.getName() + "= ? and");
									stv.add(value);
								}
							}
						}						
						break;
					}
				}
			}
			sbs.append(" limit 1");
			String sql = sbs.toString().toLowerCase();
			
			System.out.println(sql);
			if(stv.size() == 1){
				System.out.println("不能进行全表查询");
				throw new SQLException("不能进行全表查询");
			}
			
			stmt = conn.prepareStatement(sql);
			for(int i=0;i<stv.size() ; i++){
			   stmt.setObject(i + 1, stv.get(i));
			}
			rs = stmt.executeQuery();
			while (rs.next()) {

			//	oneSchema = (SimpleSchema) cl.newInstance();
				for (String col : cols) {
					for (Field field : fields) {
						if (col.equalsIgnoreCase(field.getName())) {
							field.setAccessible(true);

							String colType = field.getType().getName();
							Object value;
							if (colType.indexOf("int") != -1) {
								value = rs.getInt(field.getName());
							} else if (colType.indexOf("double") != -1) {

								value = rs.getDouble(field.getName());
							} else {
								value = rs.getString(field.getName());
							}
							field.set(schema, value);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			closeDBConnection(conn, stmt, null);
		} finally {
			closeDBConnection(conn, stmt, null);
		}
		return schema;
	}

	/**
	 * 返回表的列集合
	 */
	public static ArrayList<String> getAllColumen(SimpleSchema schema) {
		ArrayList<String> arr = new ArrayList<String>();
		String[] columns = schema.getColumn();
		int column = columns.length;
			for (int i = 0; i < column; i++) {
				String col = columns[i];
				arr.add(col);
			}
		return arr;
	}

    /**
     * 统一管理连接
     * true:从连接池中获取连接
     * @return
     */
    public static Connection getConnection(boolean flag){
    	if(flag){
    	DBUtil dbUtil = new DBUtil();
    	return	dbUtil.getMySqlConnection();
    	}else{
    		return null;
    	}	
    }

    /**
     * 释放链接池资源
     */
    public static void closeDBConnection(Connection conn,
                                         PreparedStatement stmt, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多表操作关闭连接对象
     * @param conn
     * @param stmt
     * @param rs
     * @param batchFlag
     *   true:关闭Connection对象
     *   false:不关闭
     */
    public static void closeDBConnection(Connection conn,
                                         PreparedStatement stmt, ResultSet rs, boolean batchFlag){
        if(batchFlag){
            closeDBConnection(conn, stmt, rs);
        }else {
            closeDBConnection(null, stmt, rs);
        }
    }



}
