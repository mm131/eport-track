package com.njeport.track.common.constants;

/**
 * @author kongming
 * @date 2020/01/05
 */
public interface EndPointConstants {
    String SBC_PORT_URL = "http://www.njedi.cn/NJGSERVICE/customerservice/customerservice!querySbcPort.action";
    String SINGLE_BOX_URL = "http://www.njedi.cn/NJGSERVICE/singlebox/singlebox!querySingleboxInfo_N.action";
    String UNIT_LOG_URL = "http://www.njedi.cn/NJGSERVICE/singlebox/singlebox!queryUnitLog_N.action";
    String CLEARANCE_URL = "http://www.njedi.cn/NJGSERVICE/voyagesearch/voyagesearch!queryCustomsClearanceInquiries.action";
    String CAPTCHA_URL = "http://www.njedi.cn/NJGSERVICE/common/common!getCode.action";
    String LOGIN_URL = "http://www.njedi.cn/NJGSERVICE/loginnjg.jsp";
    String REPORT_URL = "http://www.njedi.cn/NJGSERVICE/emptybox/yardemptybox!queryReportInfo.action";
    String YARD_INFO_URL = "http://www.njedi.cn/NJGSERVICE/emptybox/yardemptybox!queryYardInfo.action";
    String BOX_QUERY_URL = "http://www.njedi.cn/NJGSERVICE/checkapply/checkapply!queryExangeCheckApplyInfo.action";
    String CTN_CHECK_URL = "http://www.njedi.cn/NJGSERVICE/ctncheck/ctncheck!queryContainerrecord.action";
    String CTN_CHECK_DETAIL_URL = "http://www.njedi.cn/NJGSERVICE/ctncheck/ctncheck!queryctnInfo.action";

    String YUNLSP_LOGIN_URL = "https://nsso.yunlsp.com/dologin";
    String YUNLSP_ADD_BILLNO_URL = "https://supertrack.yunlsp.com/api/newtrack/selectByUserIdAndBillNo";
    String YUNLSP_TRACK_BILLNO_URL = "https://supertrack.yunlsp.com/api/hbtrack/startTrackRecord";
    String YUNLSP_RECYCLE_BILLNO_URL = "https://supertrack.yunlsp.com/api/newtrack/beatchRecycle";

    String YUNLSP_LADING_INFO_URL = "https://supertrack.yunlsp.com/api/exportDetails/ladingInformation";
    String YUNLSP_SHIP_INFO_URL = "https://supertrack.yunlsp.com/api/exportDetails/shipInformation";
    String YUNLSP_MANIFEST_URL = "https://supertrack.yunlsp.com/api/exportDetails/choPrearrangedManifestByBillNo";
    String YUNLSP_CARGO_URL = "https://supertrack.yunlsp.com/api/exportDetails/cargoInformation";
    String reSendYunDi="http://www.njedi.cn/NJGSERVICE/emptybox/yardemptybox!SelfServiceReissue.action";

    String SHIPXY_LOGIN_URL = "http://www.shipxy.com";
    String SHIPXY_MMSI_URL = "http://searchv3.shipxy.com/shipdata/search3.ashx";
    String SHIPXY_GET_SHIP_URL = "http://www.shipxy.com/ship/GetShip";

    String OOCL_STEP1_URL = "https://moc.oocl.com/admin/login/ul_sign_in.jsf";
    String OOCL_STEP2_URL = "https://moc.oocl.com/admin/login/ul_sign_in.jsf;jsessionid=";
    String OOCL_STEP3_URL = "http://moc.oocl.com/admin/partymenu/pm_main.jsf";
    String OOCL_STEP4_URL = "https://moc.oocl.com/admin/vermas/vermas_upload.jsf";
    String OOCL_UPLOAD_VGM_URL = "https://moc.oocl.com/securityfiling/vermas/vermas_upload.jsf";

    String YUNDI_SEARCH="http://www.njedi.cn/NJGSERVICE/emptybox/yardemptybox!queryReportInfo.action";

    String WAIDAI_LOGIN_URL= "http://sa.penavico.com.cn:8040/ccf-certification-app/GatewayService4Json?timestamp=1602741405956";
    String WAIDAI_QUERY_URL="http://sa.penavico.com.cn:8040/sasweb-linerexport/cjf.service.linerexport.ManifestOfWebService/query?timestamp=1602747411628";
    String WAIDAI_SEND_URL="http://sa.penavico.com.cn:8040/sasweb-linerexport/cjf.service.linerexport.ManifestOfWebService/sendEdi?timestamp=1605665526221";

    String MINSHENG_LOGIN_URL= "http://www.mssco.net/EipWh/UserControl/SetClientInfo.aspx";
    String MINSHENG_BEFORE_QUERY_URL="http://www.mssco.net/EipWh//Partner/BlOperate/wfBlEdiConfirmFinder.aspx";
    String MINSHENG_QUERY_URL="http://www.mssco.net/EipWh//Partner/BlOperate/wfBlEdiConfirmFinder.aspx";

}
