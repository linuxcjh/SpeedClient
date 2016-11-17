package com.rongfeng.speedclient.common;

/**
 * Created by 唐磊 on 2015/11/5.
 * 常量类
 */
public interface Constant {

    /**
     * 选择对话框单选
     */
    public static final int SINGLESELECTION = 0x001;

    /**
     * 审批类型
     */
    public static String str[] = {"采购申请", "出差申请", "加班申请", "请假申请", "借款申请", "入职申请", "调休申请", "费用申请", "报销申请", "会签申请", "用车申请", "其他申请"};

    /**
     * 采购审请
     */
    public static final int PURCHASE_CONTENT_INDEX = 0x11;
    /**
     * 出差申请
     */
    public static final int BUSINESS_TRIP_CONTENT_INDEX = 0x12;
    /**
     * 加班申请
     */
    public static final int WORK_OVER_TIME_CONTENT_INDEX = 0x13;
    /**
     * 请假申请
     */
    public static final int VACATE_CONTENT_INDEX = 0x14;
    /**
     * 借款申请
     */
    public static final int LEND_MONEY_CONTENT_INDEX = 0x15;
    /**
     * 入职申请
     */
    public static final int ENTRY_WORK_CONTENT_INDEX = 0x16;
    /**
     * 调休申请
     */
    public static final int HAVE_HOLIDAY_CONTENT_INDEX = 0x17;
    /**
     * 费用申请
     */
    public static final int FEE_CONTENT_INDEX = 0x18;
    /**
     * 报销申请
     */
    public static final int REIMBURSEMENT_CONTENT_INDEX = 0x19;
    /**
     * 会签申请
     */
    public static final int COUNTERSIGNATURE_CONTENT_INDEX = 0x20;
    /**
     * 用车申请
     */
    public static final int USE_VEHICLE_CONTENT_INDEX = 0x21;
    /**
     * 其他申请
     */
    public static final int OTHER_CONTENT_INDEX = 0x21;
    /**
     * 审批人
     */
    public static final int APPROVAL_CONTENT_INDEX = 0x22;
    /**
     * 抄送人
     */
    public static final int CONTACT_CONTENT_INDEX = 0x23;
    /**
     * 同行人
     */
    public static final int COM_PERSON_INDEX = 0x24;


    /**
     * 系统图库选择图片
     **/
    public static int SELECT_PICTURE = 0x31;

    /**
     * 调用系统相机拍照
     **/
    public static int CAMERA_REQUEST_CODE = 0x32;

    /**
     * 对话框确定
     */
    public static final int CONFIRMDIALOG = 0x33;


    /**
     * 搜索popuwindos 选中项返回
     */
    public static final int SEARCHPOPUPWINDOWRESULT = 0x34;

    /**
     * 添加每日日志
     */
    public static final int ADDDAILYLOG = 0x35;

    /**
     * 编辑每日日志
     */
    public static final int EDITDAILYLOG = 0x36;

    /**
     * 查看其他人日志
     */
    public static final int LOOKOTHERLOG = 0x37;

    /**
     * 查看日志消息列表
     */
    public static final int LOGMSGNUM = 0x40;

    /**
     * 审批详情到编辑
     */

    public static final int APPROVALTOEDIT = 0x41;
    /****
     * 活動搜索
     */
    public static final int ACTIVITYSEEK = 0x42;

    /**
     * 通知下载
     **/
    int NOTICE_DOWNLOAD = 0x1114;
    /**
     * 从联系人选择
     */
    int CONTACT_SELECT_RESULT = 0x35;

    /**
     * 版本更新广播接收
     */
    String BROADCAST_DISAPPEAR_SIGN_DOWNLOAD_APP = "DISAPPEAR_SIGN_BROADCAST_RECEIVER_DOWNLOAD_APP";

    /**
     * 版本更新
     */
    String IS_UPDATE = "isUpdate";

    /**
     * 选择省市区名称
     */
    String AREA_NAME = "areaName";

    /**
     * 选择省市区ID
     */
    String AREA_ID = "areaId";


    int DELETEITEM = 0X36;

    //注册选择行业

    int CHOICE_INDUSTRY = 0x38;

    //注册添加其他行业

    int CHOICE_OTHER_INDUSTRY = 0x39;

    //行数
    int ROWS = 10;

    /**
     * 首页数据更新
     */

    public static final int FIRST_SCHEDULE = 0x42;
    public static final int FIRST_LOG = 0x43;
    public static final int FIRST_NOTICE = 0x44;
    public static final int FIRST_APPRO = 0x45;
    public static final int FIRST_NUMBER = 0x46;


    /**
     * 提醒
     */

    String WORK_LOG_ALARM = "work_log_alarm";
    String WORK_ON_ALARM = "work_on_alarm";
    String WORK_DOWN_ALARM = "work_down_alarm";


    String WORK_ATT_ALARM_STATUS = "work_att_alarm_status";//考勤提醒状态
    String WORK_LOG_ALARM_STATUS = "work_log_alarm_status";//工作日志提醒状态
    String REPEAT_ALARM_STATUS = "repeat_alarm_status";//重复提醒状态

    /**
     * 开关状态
     */
    String ON = "on";
    String OFF = "off";

    /*
     * 重复日
     */
    String MONDAY = "monday";
    String TUESDAY = "tuesday";
    String WEDNESDAY = "wednesday";
    String THURSDAY = "thursday";
    String FRIDAY = "friday";
    String SATURDAY = "saturday";
    String SUNDAY = "sunday";


    /**
     * 选择合同模板
     */
    int SELECT_CONTRACT_TEMPLATE = 0x110;


    /**
     * 是否切换租户状态
     */
    String IS_CHANGE_TENEMENT = "is_change_tenement";

    /**
     * 选择位置requestCode
     */
    public static final int SELECT_POSITION_CLIENT_REQUEST_CODE = 0x13;


}
