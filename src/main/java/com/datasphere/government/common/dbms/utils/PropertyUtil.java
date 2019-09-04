package com.datasphere.government.common.dbms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class PropertyUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties props;

    static {
        loadProps();
    }

    synchronized static private void loadProps(){
        logger.info("加载[attach.properties] ......");
        props = new Properties();
        String filePath = System.getProperty("user.dir")+File.separator+"dlresources"+File.separator+ "attach.properties";
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("未找到[attach.properties]");
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if(in != null) in.close();
            } catch (IOException e) {
                logger.error("attach.properties-文件流关闭出现异常");
            }
        }
        logger.info("加载[attach.properties]完成! \r\n"+props);
    }

    public static String getProperty(String key){
        if(null == props) loadProps();
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == props) loadProps();
        return props.getProperty(key, defaultValue);
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}
