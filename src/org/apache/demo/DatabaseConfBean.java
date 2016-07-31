package org.apache.demo;

public class DatabaseConfBean {
	/**
	 * 配置名称
	 */
	public String strConfig;
	/**
	 * 数据库名称
	 */
	public String database;
	/**
	 * 用户名
	 */
	public String username;
	/**
	 * 密码
	 */
	public String pwd;
	/**
	 * 端口号
	 */
	public String portNumber;
	/**
	 * 节点查询
	 */
	public String nodeSql;
	/**
	 * 边查询
	 */
	public String edgeSql;
	
	public String getStrConfig() {
		return strConfig;
	}
	public void setStrConfig(String strConfig) {
		this.strConfig = strConfig;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}
	public String getNodeSql() {
		return nodeSql;
	}
	public void setNodeSql(String nodeSql) {
		this.nodeSql = nodeSql;
	}
	public String getEdgeSql() {
		return edgeSql;
	}
	public void setEdgeSql(String edgeSql) {
		this.edgeSql = edgeSql;
	}

}
