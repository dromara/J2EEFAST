package com.j2eefast.modules.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.j2eefast.common.core.base.entity.LoginUserEntity;
import com.j2eefast.common.core.utils.AssertUtil;
import com.j2eefast.common.core.utils.CheckPassWord;
import com.j2eefast.common.core.utils.FileUploadUtil;
import com.j2eefast.common.core.utils.ResponseData;
import com.j2eefast.common.core.utils.ToolUtil;
import com.j2eefast.common.core.business.annotaion.BussinessLog;
import com.j2eefast.common.core.enums.BusinessType;
import com.j2eefast.framework.annotation.RepeatSubmit;
import com.j2eefast.framework.sys.entity.SysUserEntity;
import com.j2eefast.framework.sys.service.*;
import com.j2eefast.common.core.controller.BaseController;
import com.j2eefast.framework.utils.Constant;
import com.j2eefast.framework.utils.Global;
import com.j2eefast.framework.utils.UserUtils;
import cn.hutool.core.util.ReUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 个人资料
 * @author zhouzhou
 * @date 2020-03-07 13:41
 */
@Slf4j
@Controller
@RequestMapping("/sys/user/profile")
public class SysProfileController extends BaseController {
    private String urlPrefix = "modules/sys/user/profile";

    @Autowired
    private SysUserService sysUserService;

    /**
     * 个人信息
     */
    @GetMapping("/{active}")
    public String profile(@PathVariable("active") String active,ModelMap mmap) {
        if(ToolUtil.isNotEmpty(active) && "info".equals(active)){
            active = "info";
        }else {
            active = "password";
        }
        LoginUserEntity user = UserUtils.getUserInfo();
        mmap.put("user", user);
        mmap.put("active",active);
        return urlPrefix + "/profile";
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar")
    public String avatar(ModelMap mmap){
        LoginUserEntity user = UserUtils.getUserInfo();
        mmap.put("user", user);
        return urlPrefix + "/avatar";
    }

    /**
     * 保存头像
     */
    @BussinessLog(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public ResponseData updateAvatar(@RequestParam("avatarfile") MultipartFile file){
        try{
            if (!file.isEmpty()){
                String avatar = FileUploadUtil.uploadWeb(Global.getAvatarPath(), file);
                if (sysUserService.updateAvatar(UserUtils.getUserId(),avatar)){
                    LoginUserEntity user = UserUtils.getUserInfo();
                    user.setAvatar(avatar);
                    UserUtils.reloadUser(user);
                    return success();
                }
            }
            return error(ToolUtil.message("sys.file.null"));
        }
        catch (Exception e){
            log.error("修改头像失败！", e);
            return error(e.getMessage());
        }
    }


    @BussinessLog(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateUser")
    @ResponseBody
    public ResponseData updateUser(SysUserEntity user) {
        AssertUtil.isBlank(user.getName(), ToolUtil.message("sys.user.name.tips"));
        AssertUtil.isBlank(user.getMobile(), ToolUtil.message("sys.user.phone.tips"));
        
        if(!ReUtil.isMatch(Constant.MOBILE_PHONE_NUMBER_PATTERN, user.getMobile())){
            return error(ToolUtil.message("sys.user.phone.improper.format"));
        }
        LoginUserEntity loginUser = UserUtils.getUserInfo();
        if(ToolUtil.isNotEmpty(user.getEmail()) && !ReUtil.isMatch(Constant.EMAIL_PATTERN, user.getEmail())){
            loginUser.setEmail(user.getEmail());
            return error(ToolUtil.message("sys.user.email.improper.format"));
        }

        // 用户
        boolean flag = sysUserService.updateUserName(loginUser.getId(), user.getName(), user.getMobile(),user.getEmail());

        if (flag) {
            loginUser.setName(user.getName());
            loginUser.setMobile(user.getMobile());
            UserUtils.reloadUser(loginUser);
            return success();
        }
        return error(ToolUtil.message("sys.update.error"));
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public ResponseData checkPassword(String password){
        LoginUserEntity loginUser = UserUtils.getUserInfo();
        // 原密码
        password = UserUtils.sha256(password, loginUser.getSalt());
        if (password.equals(loginUser.getPassword())){
            return success();
        }
        return error("不匹配!");
    }


    @BussinessLog(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    @RepeatSubmit
    public ResponseData resetPwd(String oldPassword, String newPassword){
        LoginUserEntity loginUser = UserUtils.getUserInfo();
        if(ToolUtil.isNotEmpty(oldPassword) && ToolUtil.isNotEmpty(newPassword)){
            oldPassword = UserUtils.sha256(oldPassword, loginUser.getSalt());
            if (oldPassword.equals(loginUser.getPassword())){
                String salt = UserUtils.randomSalt();
                String pwdSecurityLevel = CheckPassWord.getPwdSecurityLevel(newPassword).getValue();
                // 新密码
                newPassword = UserUtils.sha256(newPassword, salt);

                boolean flag = sysUserService.updatePassWord(loginUser.getId(), newPassword,salt,pwdSecurityLevel);

                if (!flag) {
                    return error(ToolUtil.message("sys.user.oldPasswordError"));
                }else{
                    //更新Shiro
                    loginUser.setSalt(salt);
                    loginUser.setPassword(newPassword);
                    loginUser.setPwdSecurityLevel(pwdSecurityLevel);
                    UserUtils.reloadUser(loginUser);
                    return success();
                }
            }else{
                return error("修改密码失败，旧密码错误");
            }
        }
        else{
            return error("修改密码失败!");
        }
    }

}
