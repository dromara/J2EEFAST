package com.fast.modules.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.fast.common.core.utils.AssertUtil;
import com.fast.common.core.utils.CheckPassWord;
import com.fast.common.core.utils.FileUploadUtil;
import com.fast.common.core.utils.R;
import com.fast.common.core.utils.ToolUtil;
import com.fast.common.core.business.annotaion.BussinessLog;
import com.fast.common.core.enums.BusinessType;
import com.fast.framework.annotation.RepeatSubmit;
import com.fast.framework.sys.entity.SysUserEntity;
import com.fast.framework.sys.service.*;
import com.fast.framework.utils.AbstractController;
import com.fast.framework.utils.Constant;
import com.fast.framework.utils.Global;
import com.fast.framework.utils.ShiroUtils;
import cn.hutool.core.util.ReUtil;
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
@Controller
@RequestMapping("/sys/user/profile")
public class SysProfileController extends AbstractController {
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
        SysUserEntity user = ShiroUtils.getUserEntity();
        mmap.put("user", user);
        mmap.put("active",active);
        return urlPrefix + "/profile";
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar")
    public String avatar(ModelMap mmap){
        SysUserEntity user = ShiroUtils.getUserEntity();
        mmap.put("user", user);
        return urlPrefix + "/avatar";
    }

    /**
     * 保存头像
     */
    @BussinessLog(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public R updateAvatar(@RequestParam("avatarfile") MultipartFile file){
        try{
            if (!file.isEmpty()){
                String avatar = FileUploadUtil.uploadWeb(Global.getAvatarPath(), file);
                //avatar = "/profile/" + StrUtil.subSufByLength(avatar,avatar.indexOf(Global.getAvatarPath()) + 1);
                //StrUtil.indexOf(avatar,Global.getAvatarPath())
                //avatar.substring(avatar.indexOf(Global.getAvatarPath()),avatar.length());
                if (sysUserService.updateAvatar(getUserId(),avatar)){
                    SysUserEntity user = getUser();
                    user.setAvatar(avatar);
                    ShiroUtils.setSysUser(user);
                    return R.ok();
                }
            }
            return R.error(ToolUtil.message("sys.file.null"));
        }
        catch (Exception e)
        {
            logger.error("修改头像失败！", e);
            return R.error(e.getMessage());
        }
    }


    @BussinessLog(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateUser")
    @ResponseBody
    public R updateUser(SysUserEntity user) {
        AssertUtil.isBlank(user.getName(), ToolUtil.message("sys.user.name.tips"));
        AssertUtil.isBlank(user.getMobile(), ToolUtil.message("sys.user.phone.tips"));
        
        if(!ReUtil.isMatch(Constant.MOBILE_PHONE_NUMBER_PATTERN, user.getMobile())){
            return R.error(ToolUtil.message("sys.user.phone.improper.format"));
        }
        SysUserEntity loginUser = getUser();
        if(ToolUtil.isNotEmpty(user.getEmail()) && !ReUtil.isMatch(Constant.EMAIL_PATTERN, user.getEmail())){
            loginUser.setEmail(user.getEmail());
            return R.error(ToolUtil.message("sys.user.email.improper.format"));
        }

        // 用户
        boolean flag = sysUserService.updateUserName(getUserId(), user.getName(), user.getMobile(),user.getEmail());

        if (flag) {
            loginUser.setName(user.getName());
            loginUser.setMobile(user.getMobile());
            ShiroUtils.setSysUser(loginUser);
            return R.ok();
        }
        return R.error(ToolUtil.message("sys.update.error"));
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public R checkPassword(String password){
        // 原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        if (password.equals(getUser().getPassword())){
            return R.ok();
        }
        return R.error();
    }


    @BussinessLog(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    @RepeatSubmit
    public R resetPwd(String oldPassword, String newPassword){
        if(ToolUtil.isNotEmpty(oldPassword) && ToolUtil.isNotEmpty(newPassword)){
            oldPassword = ShiroUtils.sha256(oldPassword, getUser().getSalt());
            if (oldPassword.equals(getUser().getPassword())){
                String salt = ShiroUtils.randomSalt();
                String pwdSecurityLevel = CheckPassWord.getPwdSecurityLevel(newPassword).getValue();
                // 新密码
                newPassword = ShiroUtils.sha256(newPassword, salt);

                boolean flag = sysUserService.updatePassword(getUser().getUserId(), newPassword,salt,pwdSecurityLevel);

                if (!flag) {
                    return R.error(ToolUtil.message("sys.user.oldPasswordError"));
                }else{
                    //更新Shiro
                    SysUserEntity user = getUser();
                    user.setSalt(salt);
                    user.setPassword(newPassword);
                    user.setPwdSecurityLevel(pwdSecurityLevel);
                    ShiroUtils.setSysUser(user);
                    return R.ok();
                }
            }else{
                return R.error("修改密码失败，旧密码错误");
            }
        }
        else{
            return R.error("修改密码失败!");
        }
    }

}
