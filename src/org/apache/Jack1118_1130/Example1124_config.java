package org.apache.Jack1118_1130;

import java.sql.SQLException;

import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Table;
import prefuse.data.expression.Predicate;
import prefuse.data.expression.parser.ExpressionParser;
import prefuse.data.io.DataIOException;
import prefuse.data.io.sql.ConnectionFactory;
import prefuse.data.io.sql.DatabaseDataSource;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
/**
 * 接收界面配置数据
 * @author Jack
 *
 */

public class Example1124_config {

	public String nodeSql;
	public String edgeSql;
	public String strConfig;
	public String databaseName;
	public String username;
	public String password;
	public String portNumber;
	
	public String getStrConfig() {
		return strConfig;
	}
	public void setStrConfig(String strConfig) {
		this.strConfig = strConfig;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
