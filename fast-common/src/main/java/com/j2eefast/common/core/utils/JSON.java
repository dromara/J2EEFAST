package com.j2eefast.common.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * JSON处理类
 * @author zhouzhou
 * @date 2020-03-12 15:18
 */
public class JSON extends com.alibaba.fastjson.JSON{
	
	private static final ObjectMapper 					OBJECT_MAPPER					= new ObjectMapper();
	private static final ObjectWriter 					OBJECT_WRITER 					= OBJECT_MAPPER.writerWithDefaultPrettyPrinter();
	
	public static void marshal(File file, Object value) throws Exception
    {
        try{
        	OBJECT_WRITER.writeValue(file, value);
        }
        catch (JsonGenerationException e){
            throw new Exception(e);
        }
        catch (JsonMappingException e){
            throw new Exception(e);
        }
        catch (IOException e){
            throw new Exception(e);
        }
    }

    public static void marshal(OutputStream os, Object value) throws Exception
    {
        try{
        	OBJECT_WRITER.writeValue(os, value);
        }
        catch (JsonGenerationException e){
            throw new Exception(e);
        }
        catch (JsonMappingException e){
            throw new Exception(e);
        }
        catch (IOException e){
            throw new Exception(e);
        }
    }

    public static String marshal(Object value) throws Exception{
        try{
            return OBJECT_WRITER.writeValueAsString(value);
        }
        catch (JsonGenerationException e){
            throw new Exception(e);
        }
        catch (JsonMappingException e){
            throw new Exception(e);
        }
        catch (IOException e){
            throw new Exception(e);
        }
    }
}
