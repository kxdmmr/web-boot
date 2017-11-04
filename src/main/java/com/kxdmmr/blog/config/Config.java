package com.kxdmmr.blog.config;

import com.kxdmmr.blog.security.AttackInterceptor;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashSet;
import java.util.Properties;

/**
 * Created by kxdmmr on 2017/8/25.
 * 全局配置
 */
public class Config {
    Logger LOG = Logger.getLogger(AttackInterceptor.class);
    private static String configfile = "blog.conf";

    private Properties pro = new Properties();
    private HashSet<String> options = new HashSet<>();

    private static class ConfigInstance {
        private static Config instance = new Config();
    }

    public static Config getInstance() {

        return ConfigInstance.instance;
    }

    public Config addOption(String key){
        this.options.add(key);
        return this;
    }
    public Config load(String file) {
        InputStream in = getStream(file);
        if(in == null)
            return null;
        try {
            this.pro.load(in);
        } catch (IOException e) {
            LOG.error(String.format("Load config file %s failed: %s", file, e.getMessage()));
            return null;
        } finally {
        	try {
				in.close();
			} catch (IOException e) {
				LOG.error(String.format("Failed close InputStream: %s.", file), e);
			}
        }
        if(!pro.keySet().containsAll(options)) {
            LOG.error("Missing config options, Please check config file: " + file);
            pro.clear();
            return null;
        }

        LOG.info(String.format("Loaded config file: [%s]", file));
        configfile = file;
        return this;
    }

    public Config load(){
        return load(configfile);
    }

    public void reload() {
        this.pro.clear();
        load();
    }

    @SuppressWarnings("resource")
	public InputStream getStream(String file){

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        }catch(FileNotFoundException e){
            LOG.warn(String.format("Not found config file: %s, Try search classpath", file));
            inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(file);
        }
        return inputStream;
    }
    public Config setConfigfile(String file) {
        this.pro.clear();
        return load(file);
    }

    public static String getConfigfile() {
        return new String(configfile);
    }

    public String get(String key) {
        return pro.getProperty(key);
    }

    public String getOr(String key, String value) {
        return pro.getProperty(key, value);
    }

    public int getInt(String key) {
        int v = -1;
        String o = this.pro.getProperty(key);
        try {
            v = Integer.valueOf(o);
        } catch (NumberFormatException e) {
            LOG.error(String.format("getInt(): Parse Integer [%s:%s], %s", key, o,e.getMessage()));
        }
        return v;
    }

    public long getLong(String key) {
        long v = -1L;
        String o = this.pro.getProperty(key);
        try {
            v = Long.valueOf(o);
        } catch (NumberFormatException e) {
            LOG.error(String.format("getLong(): Parse Integer [%s:%s], %s", key, o,e.getMessage()));
        }
        return v;
    }

    public Config set(String key, String value) {
        Object r = this.pro.setProperty(key, value);
        if (r == null)
            this.pro.put(key, value);
        return this;
    }
}
