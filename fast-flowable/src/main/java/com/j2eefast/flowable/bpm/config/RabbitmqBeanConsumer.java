package com.j2eefast.flowable.bpm.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.rabbit.constant.RabbitBeanInfo;
import com.j2eefast.common.rabbit.constant.RabbitInfo;
import com.j2eefast.flowable.bpm.entity.GroupEntity;
import com.j2eefast.flowable.bpm.entity.GroupUserEntity;
import com.j2eefast.flowable.bpm.entity.UserEntity;
import com.j2eefast.flowable.bpm.mapper.GroupMapper;
import com.j2eefast.flowable.bpm.service.GroupService;
import com.j2eefast.flowable.bpm.service.GroupUserService;
import com.j2eefast.flowable.bpm.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;

import javax.annotation.Resource;

/**
 * <p>消息消费接收,同步用户信息</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-23 15:34
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
@Component
@Slf4j
public class RabbitmqBeanConsumer {


	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupUserService groupUserService;
	@Autowired
	private UserService userService;

	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = RabbitInfo.QUEUE_NAME, durable = RabbitInfo.QUEUE_DURABLE),
			exchange = @Exchange(value = RabbitInfo.EXCHANGE_NAME, type = RabbitInfo.EXCHANGE_TYPE),
			key = RabbitInfo.ROUTING_KEY)
	)
	@RabbitHandler
	public void onMessage(Message message, Channel channel) throws Exception {
		MessageHeaders headers = message.getHeaders();
		try{
			String heade = (String) headers.get("msgHead");
			String  info = (String) message.getPayload();
			if(ToolUtil.isNotEmpty(heade) && heade.equals("ADDROLE")){
				JSONObject json = JSONArray.parseObject(info);
				GroupEntity group = new GroupEntity();
				group.setId(String.valueOf(json.getLongValue("id")));
				group.setName(json.getString("roleName"));
				group.setRev(1);
				group.setType(json.getString("roleKey"));
				groupService.add(group);
			}else if(ToolUtil.isNotEmpty(heade) && heade.equals("DELROLE")){
				String[] ids = info.split(",");
				for(String id: ids){
					groupService.delById(id);
				}
				//groupService.delById(String.valueOf(json.getLongValue("")));
			}else if(ToolUtil.isNotEmpty(heade) && heade.equals("ADDUSER")){
				JSONObject json = JSONArray.parseObject(info);
				UserEntity user = new UserEntity();
				user.setId(String.valueOf(json.getLongValue("id")));
				user.setDispalyName(json.getString("name"));
				user.setFirstName(json.getString("name"));
				user.setLastName(json.getString("name"));
				user.setEmail(json.getString("email"));
				user.setRev(1);
				userService.add(user);
				JSONArray s = json.getJSONArray("roleIdList");
				for(int i=0; i< s.size(); i++){
					Long l = s.getLong(i);
					GroupUserEntity guser = new GroupUserEntity();
					guser.setGroupId(String.valueOf(l));
					guser.setUserId(user.getId());
					groupUserService.add(guser);
				}
			}else if(ToolUtil.isNotEmpty(heade) && heade.equals("UPDATAUSER")){
				JSONObject json = JSONArray.parseObject(info);
				UserEntity user = new UserEntity();
				user.setId(String.valueOf(json.getLongValue("id")));
				user.setDispalyName(json.getString("name"));
				user.setFirstName(json.getString("name"));
				user.setLastName(json.getString("name"));
				user.setEmail(json.getString("email"));
				user.setRev(1);
				userService.updateUser(user);
				groupUserService.delByUserId(user.getId());
				JSONArray s = json.getJSONArray("roleIdList");
				for(int i=0; i< s.size(); i++){
					Long l = s.getLong(i);
					GroupUserEntity guser = new GroupUserEntity();
					guser.setGroupId(String.valueOf(l));
					guser.setUserId(user.getId());
					groupUserService.add(guser);
				}
			}else if(ToolUtil.isNotEmpty(heade) && heade.equals("DELUSER")){
				String[] ids = info.split(",");
				for(String id: ids){
					groupUserService.delByUserId(id);
					userService.delById(id);
				}
			}
		}catch (Exception e){
			log.error("接收消息异常",e);
		}finally {
			// 获取消息头信息和消息体
			log.info("msgInfo:{} ; payload:{} ", headers.get("msgHead"), message.getPayload());
			//  DELIVERY_TAG 代表 RabbitMQ 向该Channel投递的这条消息的唯一标识ID，是一个单调递增的正整数
			Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
			// 第二个参数代表是否一次签收多条,当该参数为 true 时，则可以一次性确认 DELIVERY_TAG 小于等于传入值的所有消息
			channel.basicAck(deliveryTag, false);
		}
	}
}
