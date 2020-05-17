package com.j2eefast.flowable.bpm.enums;

/**
 * <p>审批意见的类型</p>
 *
 * @author: zhouzhou
 * @date: 2020-04-23 22:26
 * @web: http://www.j2eefast.com
 * @version: 1.0.1
 */
public enum  CommentTypeEnum {
    SP("审批"),
    BH("驳回"),
    CH("撤回"),
    ZC("暂存"),
    QS("签收"),
    WP("委派"),
    ZH("知会"),
    ZY("转阅"),
    YY("已阅"),
    ZB("转办"),
    QJQ("前加签"),
    HJQ("后加签"),
    XTZX("系统执行"),
    TJ("提交"),
    CXTJ("重新提交"),
    SPJS("审批结束"),
    LCZZ("流程终止"),
    SQ("授权"),
    CFTG("重复跳过"),
    XT("协同"),
    PS("评审");
    private String name;//名称

    /**
     * 通过type获取Msg
     *
     * @param type
     * @return
     * @Description:
     */
    public static String getEnumMsgByType(String type) {
        for (CommentTypeEnum e : CommentTypeEnum.values()) {
            if (e.toString().equals(type)) {
                return e.name;
            }
        }
        return "";
    }
    private CommentTypeEnum(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
