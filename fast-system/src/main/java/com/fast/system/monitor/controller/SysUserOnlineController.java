package com.fast.system.monitor.controller;

import com.fast.common.core.utils.PageUtil;
import com.fast.common.core.utils.R;
import com.fast.common.core.utils.RedisUtil;
import com.fast.common.core.utils.ToolUtil;
import com.fast.common.core.enums.BusinessType;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.framework.sys.entity.SysUserEntity;
import com.fast.framework.sys.service.SysUserService;
import com.fast.framework.utils.AbstractController;
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
 * @Package: com.fast.system.monitor.controller
 * @ClassName: SysUserOnlineController
 * @Author: ZhouHuan Emall:18774995071@163.com
 * @Description:
 * @Date: 2019/12/23 17:58
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/online")
public class SysUserOnlineController extends AbstractController {
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
    public R monitor(@RequestParam Map<String, Object> params) {
//        PageUtil page = new PageUtil();//sysUserService.queryPage(params);

        Collection<Session> list = ((DefaultSessionManager) ((DefaultSecurityManager) SecurityUtils
                .getSecurityManager()).getSessionManager()).getSessionDAO().getActiveSessions();
        List<SysUserEntity> userList = new ArrayList<>();

//        for (Object sysuser : page.getList()) {
//            SysUserEntity user0 = (SysUserEntity) sysuser;
//            user0.setLoginStatus(-1);
            for (Session session : list) {
                Subject s = new Subject.Builder().session(session).buildSubject();
                if (s.isAuthenticated()) {
                    SysUserEntity user = (SysUserEntity) s.getPrincipal();
//                    if (user.getUsername().equals(user0.getUsername())) {
//                        user0.setIp(session.getHost());
//                        user0.setSid(session.getId().toString());
//                        user0.setLoginTime(session.getStartTimestamp());
//                        user0.setLoginStatus(1);
//                    }
                    user.setIp(session.getHost());
                    user.setSid(session.getId().toString());
                    user.setLoginTime(session.getStartTimestamp());
                    user.setLoginStatus(ToolUtil.isNotEmpty(user.getLoginStatus())?user.getLoginStatus():1);
                    userList.add(user);
                }
            }
//        }
//        List<SysUserEntity> users = (List<SysUserEntity>) page.getList();
//        users = users.stream().sorted(Comparator.comparing(SysUserEntity::getLoginStatus).reversed()).collect(Collectors.toList());
        //users = users.stream().sorted(Comparator.comparing(SysUserEntity::getLoginStatus)).collect(Collectors.toList());

//        page.setList(users);
        return R.ok().put("page", new PageUtil(userList,userList.size(),50,1));
    }


    @BussinessLog(title = "用户管理", businessType = BusinessType.FORCE)
    @RequestMapping(value = "/batchForceLogout", method = RequestMethod.POST)
    @RequiresPermissions("sys:online:forceLogout")
    @ResponseBody
    public R monitorOut(String[] ids) {

        try{
            for(String sessionId: ids){
                if (getUser().getSid().equals(sessionId)) {
                    return R.error("自己不能踢自己下线操作!");
                }
                Collection<Session> list = ((DefaultSessionManager) ((DefaultSecurityManager) SecurityUtils
                        .getSecurityManager()).getSessionManager()).getSessionDAO().getActiveSessions();
                for (Session session : list) {
                    Subject s = new Subject.Builder().session(session).buildSubject();
                    if (s.isAuthenticated()) {
                        SysUserEntity user = (SysUserEntity)s.getPrincipal();
                        if (session.getId().toString().equals(sessionId)) {
                            redisUtil.set("sys:session:" +  user.getUsername(), "00002", RedisUtil.MINUTE);
                            session.stop();
                            s.logout();
                        }
                    }
                }
            }
            return R.ok();
        }catch (Exception e){
            logger.error("提出用户异常!",e);
            return R.error("提出用户异常!");
        }
    }
}
