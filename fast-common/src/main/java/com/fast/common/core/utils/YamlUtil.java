package com.fast.common.core.utils;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import cn.hutool.core.util.StrUtil;

/**
 * Yml 处理类
 * @author zhouzhou
 * @date 2020-03-11 20:52
 */
public class YamlUtil
{
    public static Map<?, ?> loadYaml(String fileName) throws FileNotFoundException
    {
        InputStream in = YamlUtil.class.getClassLoader().getResourceAsStream(fileName);
        return ToolUtil.isNotEmpty(fileName) ? (LinkedHashMap<?, ?>) new Yaml().load(in) : null;
    }

    
    public static void dumpYaml(String fileName, Map<?, ?> map) throws IOException
    {
        if (ToolUtil.isNotEmpty(fileName))
        {
            FileWriter fileWriter = new FileWriter(YamlUtil.class.getResource(fileName).getFile());
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            yaml.dump(map, fileWriter);
        }
    }

    public static Object getProperty(Map<?, ?> map, Object qualifiedKey)
    {
        if (map != null && !map.isEmpty() && qualifiedKey != null)
        {
            String input = String.valueOf(qualifiedKey);
            if (!"".equals(input))
            {
                if (input.contains(StrUtil.DOT))
                {
                    int index = input.indexOf(StrUtil.DOT);
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1, input.length());
                    return getProperty((Map<?, ?>) map.get(left), right);
                }
                else if (map.containsKey(input))
                {
                    return map.get(input);
                }
                else
                {
                    return null;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static void setProperty(Map<?, ?> map, Object qualifiedKey, Object value)
    {
        if (map != null && !map.isEmpty() && qualifiedKey != null)
        {
            String input = String.valueOf(qualifiedKey);
            if (!input.equals(""))
            {
                if (input.contains(StrUtil.DOT))
                {
                    int index = input.indexOf(StrUtil.DOT);
                    String left = input.substring(0, index);
                    String right = input.substring(index + 1, input.length());
                    setProperty((Map<?, ?>) map.get(left), right, value);
                }
                else
                {
                    ((Map<Object, Object>) map).put(qualifiedKey, value);
                }
            }
        }
    }
}
