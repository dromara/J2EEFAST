package com.j2eefast.system.monitor.controller;

import com.j2eefast.common.core.base.entity.LoginUserEntity;
import com.j2eefast.common.core.utils.PageUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.RedisUtil;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.sys.service.SysUserService;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.framework.utils.UserUtils;
import com.j2eefast.system.monitor.entity.OnlineEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProjectName: fast
 * @Package: com.j2eefast.system.monitor.controller
 * @ClassName: SysUserOnlineController
 * @Author: zhouzhou Emall:18774995071@163.com
 * @Description:
 * @Date: 2019/12/23 17:58
 * @Version: 1.0
 */
@Slf4j
@Controller
@RequestMapping("/sys/online")
public class SysUserOnlineController extends BaseController {
    private String urlPrefix = "modules/monitor";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtil redisUtil;

    @RequiresPermissions("sys:online:view")
    @GetMapping()
    public String online() {
        return urlPrefix + "/online";
    }

    /**
     * 获取用户监控
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:online:list")
    @ResponseBody
    public ResponseData monitor(@RequestParam Map<String, Object> params) {
        Collection<Session> list = ((DefaultSessionManager) ((DefaultSecurityManager) SecurityUtils
                .getSecurityManager()).getSessionManager()).getSessionDAO().getActiveSessions();
        List<OnlineEntity> userList = new ArrayList<>();
            for (Session session : list) {
                Subject s = new Subject.Builder().session(session).buildSubject();
                if (s.isAuthenticated()) {
                    OnlineEntity online = new OnlineEntity();
                    LoginUserEntity user = (LoginUserEntity) s.getPrincipal();
                    online.setUserId(user.getId());
                    online.setLoginIp(session.getHost());
                    online.setName(user.getName());
                    online.setUsername(user.getUsername());
                    online.setCompName(user.getCompName());
                    online.setLoginLocation(user.getLoginLocation());
                    online.setLoginStatus(ToolUtil.isNotEmpty(user.getLoginStatus())?user.getLoginStatus():1);
                    online.setSId(session.getId().toString());
                    online.setLoginTime(session.getStartTimestamp());
                    userList.add(online);
                }
            }

        return success(new PageUtil(userList,userList.size(),50,1));
    }


    @BussinessLog(title = "用户管理", businessType = BusinessType.FORCE)
    @RequestMapping(value = "/batchForceLogout", method = RequestMethod.POST)
    @RequiresPermissions("sys:online:forceLogout")
    @ResponseBody
    public ResponseData monitorOut(String[] ids) {

        try{
            for(String sessionId: ids){
                if (UserUtils.getSession().getId().equals(sessionId)) {
                    return error("自己不能踢自己下线操作!");
                }
                Collection<Session> list = ((DefaultSessionManager) ((DefaultSecurityManager) SecurityUtils
                        .getSecurityManager()).getSessionManager()).getSessionDAO().getActiveSessions();
                for (Session session : list) {
                    Subject s = new Subject.Builder().session(session).buildSubject();
                    if (s.isAuthenticated()) {
                        LoginUserEntity user = (LoginUserEntity)s.getPrincipal();
                        if (session.getId().toString().equals(sessionId)) {
                            redisUtil.set("sys:session:" +  user.getUsername(), "00002", RedisUtil.MINUTE);
                            session.stop();
                            s.logout();
                        }
                    }
                }
            }
            return success();
        }catch (Exception e){
            log.error("提出用户异常!",e);
            return error("提出用户异常!");
        }
    }
}
