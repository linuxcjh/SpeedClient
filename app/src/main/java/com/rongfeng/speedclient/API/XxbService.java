package com.rongfeng.speedclient.API;

/**
 * AUTHOR: Alex
 * DATE: 17/11/2015 21:34
 */
public interface XxbService {


    //审批列表
    String SEARCHFLOWBYALLLIST = "searchFlowByAllList";

    //流程申请类型基础数据
    String SEARCHFLOWTYPELIST = "searchFlowTypeList";

    //新增审批
    String INSERTFLOW = "insertFlow";

    //加班类型基础数据
    String SEARCHOVERTIMELIST = "searchOverTimeList";

    //请假类型基础数据
    String SEARCHLEAVELIST = "searchLeaveList";

    //交通工具类型基础数据
    String SEARCHTRANSPORTLIST = "searchTransportList";

    //审批详情
    String SEARCHFLOWBYID = "searchFlowById";

    //选择人：常用，同事，部门
    String SEARCHCHOOSEROPTION = "searchChooserOption";

    //根据部门ID 查询人员
    String SEARCHUSERINFOBYDEPARTMENTID = "searchUserInfoByDepartmentId";

    //图片上传
    String UPLOADFILE = "uploadFile";

    //审批保存
    String SAVEAPPROVEDFLOW = "saveApprovedFlow";

    //审批撤回
    String TOBACKEDITFLOW = "toBackEditFlow";

    //审批编辑
    String UPDATEEDITFLOW = "updateEditFlow";

    //筛选中审批状态基础数据
    String SEARCHFLOWSTATELIST = "searchFlowStateList";

    //筛选中审批类型基础数据
    String SEARCHMYFLOWTYPELIST = "searchMyFlowTypeList";

    //验证撤回
    String CHECKSTATEFLOW = "checkStateFlow";

    //考勤
    String INSERTSIGNIN = "insertSignIn";

    //登录
    String LOGINPHONE = "loginPhone";

    //用户考勤设置
    String ATTENDANCESETUSER = "attendanceSetUser";

    //查询月考勤（日历用）
    String SEARCHATTENDANCEBYMONTH = "searchAttendanceByMonth";

    //个人考勤
    String SEARCHPERSONAGEATTENDANCE = "searchPersonageAttendance";

    //版本升级
    String GETPHONEVERSION = "getPhoneVersion";

    //根据关键字查询用户
    String SEARCHUSERBYKEYWORD = "searchUserByKeyWord";

    //客户新增
    String INSERTCUSTOMER = "insertCustomer";

    //客户列表
    String SEARCHCUSTOMERLIST = "searchCustomerList";

    //提供客户级别、行业、区域
    String SEARCHCUSTOMERSELECTITEMS = "searchCustomerSelectItems";

    //客户详情
    String GETCUSTOMERINFOBYID = "getCustomerInfoById";

    //关注
    String SETFOCUS = "setFocus";

    //编辑保存客户信息
    String UPDATECUSTOMER = "updateCustomer";

    //客户唯一校验
    String UNIQUEVERIFYOFCUSTOMERNAME = "uniqueVerifyOfCustomerName";

    //联系人
    String SEARCHCONTACTSLIST = "searchContactsList";

    //解除联系人绑定
    String UNCONNECTCONTACTSTOCUSTOMER = "unConnectContactsToCustomer";

    //为客户绑定联系人
    String CONNECTCONTACTSTOCUSTOMER = "connectContactsToCustomer";

    //联系人详情
    String GETCONTACTSBYID = "getContactsById";
    //验证签到
    String VERIFYSIGNIN = "verifySignIn";

    //查询其他签到 详情
    String SEARCHSIGNINOTHER = "searchSignInOther";

    //联系人编辑
    String UPDATECONTACTS = "updateContacts";

    //添加标签
    String INSERTLABEL = "insertLabel";

    //删除标签
    String REMOVELABEL = "removeLabel";

    //新增联系人、线索
    String INSERTCONTACTS = "insertContacts";

    //线索列表
    String SEARCHSALESLEADSLIST = "searchSalesLeadsList";

    //新线索
    String SEARCHSALESLEADSLISTOFNEW = "searchSalesLeadsListOfNew";

    //线索详情
    String GETSALESLEADSINFOBYID = "getSalesLeadsInfoById";

    //线索编辑
    String UPDATESALESLEADS = "updateSalesLeads";

    //放入公海
    String INSERTTOSHAREPOOL = "insertToSharePool";

    //转为客户
    String CONVERTTOCUSTOMER = "convertToCustomer";

    //线索统计
    String COUNTSALESLEADSINFO = "countSalesLeadsInfo";

    //客户历史记录
    String SEARCHCUSTOMERHISTORYLIST = "searchCustomerHistoryList";

    //客户分布
    String SEARCHCUSTOMERLISTBYCOORDINATES = "searchCustomerListByCoordinates";

    //附近联系人
    String SEARCHCONTACTSLISTBYCOORDINATES = "searchContactsListByCoordinates";


    //我的日志
    String LOADWORKLOG = "loadWorkLog";

    //查看别人日志列表
    String SEARCHWORKLOGLIST = "searchWorkLogList";

    //拜访类型
    String GETCUSTOMERVISITTYPESELECTITEMS = "getCustomerVisitTypeSelectItems";

    //拜访客户
    String INSERTCUSTOMERVISITRECORD = "insertCustomerVisitRecord";

    //商机列表
    String SEARCHBNSOPPLIST = "searchBnsOppList";

    //添加商机
    String INSERTBNSOPP = "insertBnsOpp";

    //商机选择阶段-选择项
    String SEARCHBNSOPPSTAGE = "searchBnsOppStage";

    //商机详情
    String SEARCHBNSOPPDETAIL = "searchBnsOppDetail";

    //商机编辑
    String UPDATEBNSOPP = "updateBnsOpp";

    //商机编辑
    String REMOVEBNSOPP = "removeBnsOpp";

    //商机编辑
    String SEARCHPRODUCTLIST = "searchProductList";

    //客户拜访详情
    String GETCUSTOMERVISITBYRECORDID = "getCustomerVisitByRecordId";

    //选择产品
    String SELECTPRODUCTLIST = "selectProductList";

    //编辑商机产品（金额和数量）
    String UPDATEBOPRODUCT = "updateBoProduct";

    //保存商机产品
    String INSERTBOPRODUCT = "insertBoProduct";

    //删除商机产品
    String REMOVEBOPRODUCT = "removeBoProduct";

    //保存日志
    String SAVEWORKLOG = "saveWorkLog";

    //保存日志评论
    String SAVEWORKLOGCOMMENT = "saveWorkLogComment";

    //日志消息提醒
    String LOADMSGWORKLOGLIST = "loadMsgWorkLogList";

    //日志标记
    String LOADDATEWORKLOGLIST = "loadDateWorkLogList";

    //合同列表
    String SEARCHCUSTOMERCONTRACTLIST = "searchCustomerContractList";

    //查询商机历史动态
    String SEARCHBNSOPPHISTORYLIST = "searchBnsOppHistoryList";

    //查询商机历史详情
    String SEARCHBNSOPPHISTORYDETAIL = "searchBnsOppHistoryDetail";

    //产品详情
    String SEARCHPRODUCTDETAIL = "searchProductDetail";

    //合同类型
    String SELECTCUSTOMERCONTRACTTYPE = "selectCustomerContractType";

    //付款方式
    String SELECTPAYMENTMETHOD = "selectPaymentMethod";

    //新增合同
    String INSERTCUSTOMERCONTRACT = "insertCustomerContract";

    //合同详情
    String SEARCHCUSTOMERCONTRACTDETAIL = "searchCustomerContractDetail";

    //提醒
    String SEARCHREMINDTYPE = "searchRemindType";

    //编辑合同
    String UPDATECUSTOMERCONTRACT = "updateCustomerContract";

    //删除合同
    String REMOVECUSTOMERCONTRACT = "removeCustomerContract";

    //2回款计划列表《合同详情的回款计划》
    String SEARCHCUSTOMERPAYMENTPLANLIST = "searchCustomerPaymentPlanList";

    //添加回款计划
    String INSERTCUSTOMERPAYMENTPLAN = "insertCustomerPaymentPlan";

    //回款方式
    String SELECTPLANMODEL = "selectPlanModel";

    //编辑回款计划
    String UPDATECUSTOMERPAYMENTPLAN = "updateCustomerPaymentPlan";

    //删除回款计划
    String REMOVECUSTOMERPAYMENTPLAN = "removeCustomerPaymentPlan";

    //回款记录列表《合同详情的回款记录-收款记录》
    String SEARCHCUSTOMERRECEIVEINFOLIST = "searchCustomerReceiveInfoList";

    //添加回款记录
    String INSERTCUSTOMERRECEIVEINFO = "insertCustomerReceiveInfo";

    //编辑回款记录
    String UPDATECUSTOMERRECEIVEINFO = "updateCustomerReceiveInfo";

    //删除回款记录
    String REMOVECUSTOMERRECEIVEINFO = "removeCustomerReceiveInfo";

    //客户回款列表
    String SEARCHCUSTOMERDETAILPAYMENTPLANLIST = "searchCustomerDetailPaymentPlanList";

    //根据月份查询某月日程的日历信息
    String SEARCHCALENDARWITHMONTH = "searchCalendarWithMonth";

    //根据月份查询某月日程的日历信息
    String SEARCHSCHEDULELISTWITHDATE = "searchScheduleListWithDate";

    //添加日程
    String INSERTSCHEDULE = "insertSchedule";

    //编辑日程
    String UPDATESCHEDULE = "updateSchedule";

    //客户筛选
    String GETFILTEROFCUSTOMER = "getFilterOfCustomer";
    //日程详细
    String GETSCHEDULEINFOWITHID = "getScheduleInfoWithId";

    //删除日程
    String DELETESCHEDULEWITHID = "deleteScheduleWithId";

    // 联系人筛选
    String GETFILTEROFCONTACTS = "getFilterOfContacts";

    // 线索筛选
    String GETFILTEROFSALESLEADS = "getFilterOfSalesLeads";

    //获取系统时间
    String GETSERVICEDATETIME = "getServiceDateTime";

    //审批筛选
    String GETFILTEROFFLOW = "getFilterOfFlow";

    //客户搜索
    String AUTOCOMPLETEOFSEARCHER = "autoCompleteOfSearcher";

    //注册验证码
    String GETREGISTERCODE = "getRegisterCode";

    //获取服务协议
    String TOAGREEMENTPAGE = "toAgreementPage";

    //重置密码
    String RESETPWD = "resetPwd";

    //获取忘记密码验证码
    String FORGETPWDCODE = "forgotPwdCode";

    //查询行业
    String SEARCHTENEMENTINDUSTRY = "searchTenementIndustry";

    //注册
    String REGISTERTENEMENT = "registerTenement";

    //日志消息简易
    String SEARCHMSGWORKLOGBYID = "searchMsgWrokLogById";

    //查询近13个月的销售业绩统计
    String SEARCHTOTALOFSALES = "searchTotalOfSales";

    //查询某月的销售业绩统计
    String SEARCHRANKOFSALESWITHDATE = "searchRankOfSalesWithDate";

    //查询近13个月的签约数量统计 折线图+个人签约【已测试】
    String SEARCHTOTALOFSIGNED = "searchTotalOfSigned";

    // 查询某月的签约数量统计
    String SEARCHRANKOFSIGNEDWITHDATE = "searchRankOfSignedWithDate";

    //查询近13个月的拜访客户数量统计
    String SEARCHTOTALOFVISITED = "searchTotalOfVisited";

    //查询某月的拜访客户数量统计
    String SEARCHRANKOFVISITEDWITHDATE = "searchRankOfVisitedWithDate";

    //我的销售 商机漏斗和销售进度统计
    String SEARCHTOTALOFMYSALES = "searchTotalOfMySales";

    //商机列表《我的销售》
    String SEARCHBNSOPPLISTFORMYSALES = "searchBnsOppListForMySales";

    //我的里面上传头像
    String UPDATEUSERIMAGE = "updateUserImage";

    //修改密码
    String UPDATEPWD = "updatePwd";


    //我的销售商机
    String SEARCHBNSOPPLISTFORMYSALESBNSOPP = "searchBnsOppListForMySalesBnsOpp";
    //我的销售合同
    String SEARCHCUSTOMERCONTRACTLISTFORMYSALES = "searchCustomerContractListForMySales";
    //我的销售回款列表
    String SEARCHLISTOFPAYBACK = "searchListOfPayback";
    //我的销售回款统计
    String SEARCHTOTALOFPAYBACK = "searchTotalOfPayback";
    //企业管理销售进度
    String SEARCHTOTALOFBIZMANAGE = "searchTotalOfBizManage";
    //查询近13个月的考勤记录 折线图统计数据
    String SEARCHTOTALOFATTENDANCE = "searchTotalOfAttendance";
    //根据类型和月份查询考勤记录信息
    String SEARCHATTENDANCEWITHDATEANDTYPE = "searchAttendanceWithDateAndType";

    //推送
    String PUSHDINDING = "pushDinding";

    //接口名称：rapportInfoLoadList
    String RAPPORTINFOLOADLIST = "rapportInfoLoadList";

    //上报立马上报内容加载 接口
    String RAPPORTINFOLOADADD = "rapportInfoLoadAdd";

    //上报节点选择
    String GETRAPPORTHIERARCHY = " getRapportHierarchy";

    //上报审核人选择 接口
    String SELECTRAPPORTINFOAPPROVER = " selectRapportInfoApprover";

    //上报查看编辑内容加载 接口
    String RAPPORTINFOLOADEDIT = "rapportInfoLoadEdit";

    //上报查看编辑编辑保存 接口
    String RAPPORTINFOEDITSAVE = "rapportInfoEditSave";

    //上报立马上报提交保存 接口
    String RAPPORTINFOSAVENOW = "rapportInfoSaveNow";

    //获取首页数据
    String SEARCHPROMPTBYALLAPP = "searchPromptByAllApp";

    //获取首页数据 新
    String SEARCHPROMPTBYALLAPPWORK = "searchPromptByAllAppWork";

    //查询日程列表信息
    String SEARCHSCHEDULELISTPROMPTAPP = "searchScheduleListPromptApp";

    // 查询日志列表信息
    String SEARCHWORKLOGLISTPROMPTAPP = "searchWorkLogListPromptApp";

    //查询审批列表
    String SEARCHFLOWLISTPROMPTAPP = "searchFlowListPromptApp";

    //查询企业公告
    String SEARCHAPNNOTIFICATIONLISTPROMPTAPP = "searchApnNotificationListPromptApp";

    //查询公告详情
    String SEARCHAPNNOTIFICATIONDETAILPROMPTAPP = "searchApnNotificationDetailPromptApp";

    //查询报数列表
    String SEARCHRAPPORTLISTPROMPTAPP = "searchrApportListPromptApp";

    //更改读的状态
    String UPDATEPROMPTTYPE = "updatePromptType";

    //上报 列表查看历史记录日历控件 点击日历控件返回对应列表
    String RAPPORTINFOLOADHISTORYLIST = "rapportInfoLoadHistoryList";

    //上报 列表查看历史记录 日历控件
    String RAPPORTINFOLOADDATELCON = "rapportInfoLoadDateLCon";

    //查看 —— 查看列表
    String VIEWLISTRAPPORTINFO = "viewListRapportInfo";

    //查看 —— 查看列表 —— 列表详细 —— 修改记录列表
    String VIEWESTATUSLISTRAPPORTINFO = "viewEStatusListRapportInfo";

    //查看 —— 查看列表 —— 列表详细 —— 未上报列表
    String VIEWWSTATUSLISTRAPPORTINFO = "viewWStatusListRapportInfo";

    //商机筛选
    String GETFILTEROFBNSOPP = "getFilterOfBnsOpp";

    //合同筛选
    String GETFILTEROFCONTRACT = "getFilterOfContract";

    //报表详情
    String VIEWEDITDETAILSRAPPORTINFO = "viewEditDetailsRapportInfo";

    //报数查看 —— 查看列表 —— 列表详细
    String VIEWDETAILSRAPPORTINFO = "viewDetailsRapportInfo";

    //报数查看 —— 查看图表
    String VIEWCHARTRAPPORTINFO = "viewChartRapportInfo";


    //查看 —— 查看列表 —— 列表详细——下钻节点
    String VIEWDETAILSRAPPORTINFONEXT = "viewDetailsRapportInfoNext";

    //报数审核列表
    String EXAMINELISTRAPPORTINFO = "examineListRapportInfo";

    //审核 —— 审核列表 —— 审核 —— 审批状态选择
    String EXAMINGETSTATUS = "examinGetStatus";

    //审核 —— 审核列表 —— 审核 —— 审批保存
    String EXAMINESAVERAPPORTINFO = "examineSaveRapportInfo";


    //删除日志评论
    String DELETEWORKLOGCOMMENT = "deleteWorkLogComment";

    //查询近31天的客户拜访数折线图统计
    String SEARCHTOTALOFDAILYVISITED = "searchTotalOfDailyVisited";

    //根据日期查询每日拜访详情
    String SEARCHLISTOFDAILYVISITED = "searchListOfDailyVisited";

    //上传登陆数据
    String SELECTTENEMENTLOGIN = "selectTenementLogin";

    //回款数据
    String GETFILTEROFCUSTOMERPAYMENTPLAN = "getFilterOfCustomerPaymentPlan";

    //服务器时间
    String GETSERVERTIME = "getServerTime";

    //部门选择
    String SEARCHDEPARTMENTBYALL = "searchDepartmentByAll";

    //按部门查人员
    String SEARCHUSERBYDEPARTMENTBYID = "searchUserByDepartmentById";

    //业绩看板
    String SEARCHPERFORMANCE = "searchPerformance";

    //企业通讯录
    String SEARCHADDRESSBOOK = "searchAddressBook";

    //企业通讯录查询部门
    String SEARCHDEPARTMENTBYALLBOOK = "searchDepartmentByAllBook";

    //新增线索
    String SEARCHSALESLEADSLISTPERFORMANCE = "searchSalesLeadsListPerformance";

    //新增客户列表
    String SEARCHCUSTOMERPAGINGBYPERFORMANCE = "searchCustomerPagingByPerformance";

    //拜访客户列表
    String SEARCHVISITRECORDPERFORMANCE = "searchVisitRecordPerformance";

    //新增商机
    String SEARCHBNSOPPLISTPERFORMANCE = "searchBnsOppListPerformance";

    //业绩产品
    String SEARCHPRODUCTLISTPERFORMANCE = "searchProductListPerformance";

    //回款详情
    String SEARCHPAYMENTPLANRECEIVEDETAIL = "searchPaymentPlanReceiveDetail";

    //回款默认期次
    String SEARCHPAYMENTPLANDEFAULTNUM = "searchPaymentPlanDefaultNum";

    //协作人
    String SEARCHTEAMWORKUSERS = "searchTeamworkUsers";

    //日志提醒
    String SEARCHWORKLOGLIGHTSPOT = "searchWorkLogLightspot";

    //应用提醒
    String SEARCHAPPLYLIGHTSPOT = "searchApplyLightspot";

    //企业管理 合同筛选
    String GETFILTEROFBUSINESSMANAGE = "getFilterOfBusinessManage";

    //企业管理 合同列表
    String SEARCHCONTRACTLISTFORBUSINESSMANAGE = "searchContractListForBusinessManage";

    //回款方式
    String SELECTRECEIVEMODEL = "selectReceiveModel";

    //查询部门考勤 当天情况
    String SEARCHUSERALLATTENDANCE = "searchUserAllAttendance";

    //来自工作台查报表详情
    String VIEWMSGDETAILSAPPROVEDRCORDID = "viewMsgDetailsApprovedRcordId";

    //获取快速体验验证码
    String GETREGISTERVERIFICATION = "getRegisterVerification";

    //获取角色
    String GETVERIFICATIONROLETYPE = "getVerificationRoleType";

    //体验注册
    String REGISTERVERIFICATIONINFORMATION = "registerVerificationInformation";

    //商机分布统计
    String SEARCHTOTALOFDISTRIBUTION = "searchTotalOfDistribution";

    //验证接口
    String CHECKCURRENTACCOUNT = "checkCurrentAccount";

    //品类
    String SEARCHCATEGORY = "searchCategory";

    //区域
    String SEARCHREGIONALL = "searchRegionAll";

    //客户价值统计
    String SEARCHCUSTOMERVALUESTATISTIC = "searchCustomerValueStatistic";

    //业务分析-拜访
    String SEARCHANALYZECUSTOMERVISITED = "searchAnalyzeCustomerVisited";

    //客户价值客户列表
    String SEARCHCUSTOMERWORTHLIST = "searchCustomerWorthList";

    //业务分析-商机
    String SEARCHANALYZEOPPS = "searchAnalyzeOpps";

    //业务分析-合同
    String SEARCHANALYZECONTRACT = "searchAnalyzeContract";

    //业务分析-回款
    String SEARCHANALYZERECEIVE = "searchAnalyzeReceive";

    //审批人信息
    String SEARCHUSERINFOBYID = "searchUserInfoById";

    //审批信息数量提醒
    String SEARCHFLOWLIGHTSPOT = "searchFlowLightspot";

    //CRM首页数据
    String SEARCHCRMTOTAL = "searchCRMTotal";

    //CRM销售业绩
    String SEARCHCRMPERFORMANCEDEPARTMENTLIST = "searchCRMPerformanceDepartmentList";

    //CRM 销售简报
    String SEARCHCRMREPORTDEPARTMENTLIST = "searchCRMReportDepartmentList";

    //CRM 销售进度
    String SEARCHCRMPLANLIST = "searchCRMPlanList";

    //CRM-销售进度部门详细列表
    String SEARCHCRMPLANDEPARTMENTDETAILEDLIST = "searchCRMPlanDepartmentDetailedList";

    //CRM-销售业绩 预测
    String SEARCHCRMPERFORMANCEOPPLIST = "searchCRMPerformanceOppList";

    //CRM-销售业绩 合同
    String SEARCHCRMPERFORMANCECONTACTSLIST = "searchCRMPerformanceContactsList";

    //CRM-销售业绩 回款
    String SEARCHCRMPERFORMANCEPAYMENTPLANLIST = "searchCRMPerformancePaymentPlanList";

    //CRM-销售进展 12月部门详情
    String SEARCHCRMPLANDEPARTMENTDETAILEDYEAR = "searchCRMPlanDepartmentDetailedYear";

    //CRM-销售进展 12月个人详情
    String SEARCHCRMPLANPERSONDETAILEDYEAR = "searchCRMPlanPersonDetailedYear";

    //CRM-销售简报 新增线索
    String SEARCHCRMREPORTNEWCLEWLIST = "searchCRMReportNewClewList";

    //CRM-销售简报 新增客户列表
    String SEARCHCRMREPORTNEWCUSTOMERLIST = "searchCRMReportNewCustomerList";


    //CRM-销售简报 新增商机列表
    String SEARCHCRMREPORTNEWOPPLIST = "searchCRMReportNewOppList";


    //CRM-销售简报 新增拜访列表
    String SEARCHCRMREPORTNEWVISITLIST = "searchCRMReportNewVisitList";

    //CRM-销售简报 状态变化客户列表
    String SEARCHCRMREPORTCHANGECUSTOMERLIST = "searchCRMReportChangeCustomerList";

    //CRM-销售简报 状态变化商机列表
    String SEARCHCRMREPORTCHANGEOPPLIST = "searchCRMReportChangeOppList";

    //CRM 销售业绩-部门到人员列表
    String SEARCHCRMPERFORMANCEPERSONLIST = "searchCRMPerformancePersonList";


    String SEARCHCATEGORYLIST = "searchCategoryList";

    String INSERTCSR = "insertCsr";

    String SEARCHCSR = "searchCsr";

    String GETCSRBYID = "getCsrById";

    String SEARCHCSRCONTACTBYCSRID = "searchCsrContactByCsrId";

    String INSERTFOLLOWUP = "insertFollowup";

    String INSERTCSRBUSINESS = "insertCsrBusiness";

    String INSERTCSRCON = "insertCsrCon";

    String INSERTCSRGATHERING = "insertCsrGathering";

    String SEARCHNOTE = "searchNote";

    String SEARCHSKREMINDBYMONTH = "searchSkRemindByMonth";

    String SEARCHSKREMIND = "searchSkRemind";

    String INSERTSKREMIND = "insertSkRemind";

    String SEARCHCSRBUSINESS = "searchCsrBusiness";

    String SEARCHCSRCON = "searchCsrCon";

    String INSERTNOTE = "insertNote";

    String SEARCHFOLLOWUP="searchFollowup";

    String SEARCHCSRCOUNTSTATISTICSDOWN="searchCsrCountStatisticsDown";

    String SEARCHCSRCOUNTSTATISTICSTOP="searchCsrCountStatisticsTop";
}
