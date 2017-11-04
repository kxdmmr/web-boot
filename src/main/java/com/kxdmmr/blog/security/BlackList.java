package com.kxdmmr.blog.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.util.Hashtable;
import java.util.Set;

/**
 * Created by kxdmmr on 2017/8/25.
 */
public class BlackList {
    private Logger LOG = Logger.getLogger(BlackList.class);
    private Hashtable<String,Entry> accessEntry;
    private Hashtable<String,Entry> blackList;
    long lastCleanAccess = System.currentTimeMillis();
    long lastCleanBlack = System.currentTimeMillis();
    @Value("${blacklist_max_access_num}") int blacklist_max_access_num;
    @Value("${blacklist_valid_millisecond}") long blacklist_valid_millisecond;
    @Value("${blacklist_entry_max_millisecond}") long blacklist_entry_max_millisecond;
    @Value("${blacklist_entry_max_num}") int blacklist_entry_max_num;

    private static class BlackListI{
        private static BlackList instance = new BlackList();
    }
    public static BlackList getInstance(){
        return BlackListI.instance;
    }
    public BlackList(){
        accessEntry = new Hashtable<String,Entry>();
        blackList = new Hashtable<String,Entry>();
    }

    public boolean gateway(String clientID){
        try {
            if (blackList.containsKey(clientID)) {
                return false;
            } else {
                return true;
            }
        }finally {
            record(clientID);
        }
    }

    /**
     * 用于记录在规定时间内所有ID的请求
     * 如果记录表中没有该ID，则添加之，如果有，则访问次数加1
     * 如果在规定时间内某ID访问次达到上限，则加入黑名单
     * 黑名单中的IP会在规定时间后释放掉
     * @param clientID 发起本次请求的客户端识别码
     */
    public void record(final String clientID){

        new Thread(){
            @Override
            public void run(){
                long now = System.currentTimeMillis();

                if(!accessEntry.containsKey(clientID)) {
                    accessEntry.put(clientID, new Entry(1));
                }else{
                    Entry e = accessEntry.get(clientID);
                    e.plus();
                    if(e.accessNum > blacklist_max_access_num) {
                        blackList.put(clientID, accessEntry.remove(clientID));
                        LOG.warn(String.format("Black list ADD ID: [%s]", clientID));
                    }
                }

                // 清理黑名单中到期的记录
                long validTime = blacklist_valid_millisecond;
                if((now - lastCleanBlack) > validTime){
                    LOG.warn("Ready clean black list, now size: "+blackList.size());
                    Set<String> keys = blackList.keySet();
                    for(String key:keys){
                        Entry e = blackList.get(key);
                        if((now - e.timeStamp) > validTime) {
                            blackList.remove(key);
                        }
                    }
                    lastCleanBlack = now;
                    LOG.warn("Clean complete black list, now size: "+blackList.size());
                }

                // 清理访问记录表中到期的记录
                validTime = blacklist_entry_max_millisecond;
                if((now - lastCleanAccess) > validTime || accessEntry.size() > blacklist_entry_max_num){
                    LOG.warn("Ready clean access entry list, now size: "+accessEntry.size());
                    Set<String> keys = accessEntry.keySet();
                    for(String key:keys){
                        Entry e = accessEntry.get(key);
                        if((now - e.timeStamp) > validTime)
                            accessEntry.remove(key);
                    }
                    lastCleanAccess = now;
                    LOG.warn("Clean complete access entry list, now size: "+accessEntry.size());
                }
            }
        }.start();

    }

    class Entry {
        long timeStamp;
        int accessNum;

        public Entry(int accessNum){
            this.timeStamp = System.currentTimeMillis();
            this.accessNum = accessNum;
        }

        public void plus(){
            this.timeStamp = System.currentTimeMillis();
            this.accessNum++;
        }


    }
}
