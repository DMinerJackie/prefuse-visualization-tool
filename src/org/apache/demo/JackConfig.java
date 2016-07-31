package org.apache.demo;

import java.util.Properties;

/**
 * 全局配置文件类
 * @author Jack
 *
 */
public class JackConfig extends Properties{
	private static JackConfig config = null;
	
	private JackConfig(){
		setDefault();
	}
	

	public static JackConfig getConfig(){
		if(config == null){
			config = new JackConfig();
		}
		return config;
	}
	
	private void setDefault() {
		setProperty("field.selected", "");
        setProperty("shape.current", "LabelShape.Arc2D");
//        setProperty("data.delimiter", ".");
//        setProperty("data.graph.nodeGroup", "nodes");
//        setProperty("data.graph.edgeGroup", "edges");
//        setProperty("data.visual.fieldPrefix", "_");
	}

}
