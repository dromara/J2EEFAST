package com.j2eefast.modules.sys.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.common.core.utils.AuthStateRedisCache;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.framework.shiro.LoginType;
import com.j2eefast.framework.shiro.UserToken;
import com.j2eefast.framework.sys.entity.SysAuthUserEntity;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.sys.mapper.SysUserMapper;
import com.j2eefast.framework.sys.service.SysAuthUserService;
import com.j2eefast.framework.utils.DictConfig;
import com.j2eefast.framework.utils.UserUtils;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 授权登录
 * @author zhouzhou
 * @date 2020-07-24 13:12:10
 */
@Slf4j
@Controller
public class SysAuthController extends BaseController {

    @Autowired
    private AuthStateRedisCache stateRedisCache;

    @Autowired
    private DictConfig dictConfig;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysAuthUserService sysAuthUserService;

    @RequestMapping("/auth/{source}")
    @ResponseBody
    public void renderAuth(@PathVariable("source") String source) throws IOException {
        log.info("进入render：" + source);
        String dictLabel = dictConfig.getLabel("sys_oauth",source);
        if(ToolUtil.isEmpty(dictLabel)){
            super.getHttpServletResponse().sendRedirect("../404.html");
            return;
        }
        JSONObject json =  JSONUtil.parseObj(dictLabel);
        AuthRequest authRequest = ToolUtil.getAuthRequest(source,
                json.getStr("clientId"),json.getStr("clientSecret"),json.getStr("redirectUri"),stateRedisCache);
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        log.info("authorizeUrl:"+authorizeUrl);
        super.getHttpServletResponse().sendRedirect(authorizeUrl);
    }

    /**
     * oauth平台中配置的授权回调地址，以本项目为例，在创建github授权应用时的回调地址应为：http://127.0.0.1:8443/oauth/callback/github
     */
    @RequestMapping("/auth/callback/{source}")
    public ModelAndView login(@PathVariable("source") String source, AuthCallback callback, HttpServletRequest request) {
        System.out.println("进入callback：" + source + " callback params：" + JSONUtil.parse(callback).toString());

        String dictLabel = dictConfig.getLabel("sys_oauth",source);
        if(ToolUtil.isEmpty(dictLabel)){
            Map<String, Object> map = new HashMap<>(1);
            map.put("errorMsg", "50001");
            return new ModelAndView("error", map);
        }
        JSONObject json =  JSONUtil.parseObj(dictLabel);
        AuthRequest authRequest = ToolUtil.getAuthRequest(source,
                json.getStr("clientId"),json.getStr("clientSecret"),json.getStr("redirectUri"),stateRedisCache);
        AuthResponse<AuthUser> response = authRequest.login(callback);
        System.out.println(JSONUtil.parse(response).toString());
        if (response.ok()) {
            if(source.equals("gitee")){
                response.getData().getUuid();
            }
            if(UserUtils.isLogin()){
                SysUserEntity user = sysUserMapper.findAuthByUuid(source+ response.getData().getUuid());
                if(!ToolUtil.isEmpty(user)){
                    return new ModelAndView("redirect:/index#sys/user/profile/oauth2?err=5001#_sysInfo");
                }
                //若已经登录则直接绑定系统账号
                SysAuthUserEntity authUser = new SysAuthUserEntity();
                authUser.setAvatar(response.getData().getAvatar());
                authUser.setCreateTime(new Date());
                authUser.setUuid(source+ response.getData().getUuid());
                authUser.setUserId(UserUtils.getUserId());
                authUser.setNickname(response.getData().getNickname());
                authUser.setUsername(response.getData().getUsername());
                authUser.setEmail(response.getData().getEmail());
                authUser.setSource(source);
                sysAuthUserService.saveAuthUser(authUser);
                return new ModelAndView("redirect:/index#sys/user/profile/oauth2#_sysInfo");
            }
            SysUserEntity user = sysUserMapper.findAuthByUuid(source+ response.getData().getUuid());
            if(!ToolUtil.isEmpty(user)){
                //绑定用户登录
                Subject subject = null;
                UserToken token = new UserToken(user.getUsername(), user.getPassword(), LoginType.FREE.getDesc(),false);
                subject = UserUtils.getSubject();
                subject.login(token);
                return new ModelAndView("redirect:/index");
            }else{
                //游客登录 演示网站暂时定死账号登录
                Subject subject = null;
                UserToken token = new UserToken("99999", "123456", LoginType.NORMAL.getDesc(),false);
                subject = UserUtils.getSubject();
                subject.login(token);
                return new ModelAndView("redirect:/index");
            }
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("errorMsg", response.getMsg());
        return new ModelAndView("404");
    }
}
