package com.j2eefast.common.rabbit.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : zhouzhou
 * @description : rabbit 公用配置信息
 */
public class RabbitInfo {

    // queue 配置
    public static final String QUEUE_NAME = "spring.boot.simple.queue";
    public static final String QUEUE_DURABLE = "true";

    // exchange 配置
    public static final String EXCHANGE_NAME = "spring.boot.simple.exchange";
    public static final String EXCHANGE_TYPE = "topic";

    // routing key
    public static final String ROUTING_KEY = "springboot.simple.*";
    public static final String KEY = "springboot.simple.fast";

    public static Map<String, Object> getAddUserHard(){
        Map<String, Object> heads = new HashMap<>();
        heads.put("msgHead","ADDUSER");
        return heads;
    }

    public static Map<String, Object> getUpdateUserHard(){
        Map<String, Object> heads = new HashMap<>();
        heads.put("msgHead","UPDATAUSER");
        return heads;
    }

    public static Map<String, Object> getDelUserHard(){
        Map<String, Object> heads = new HashMap<>();
        heads.put("msgHead","DELUSER");
        return heads;
    }

    public static Map<String, Object> getAddRoleHard(){
        Map<String, Object> heads = new HashMap<>();
        heads.put("msgHead","ADDROLE");
        return heads;
    }

    public static Map<String, Object> getUpdateRoleHard(){
        Map<String, Object> heads = new HashMap<>();
        heads.put("msgHead","UPDATAROLE");
        return heads;
    }

    public static Map<String, Object> getDelRoleHard(){
        Map<String, Object> heads = new HashMap<>();
        heads.put("msgHead","DELROLE");
        return heads;
    }
}
