package com.sunland.TrafficAccident.view_controller;



import java.util.HashMap;

public class V_Config {

    public static final int ACTIVITY_PARK_FUNCTION = 1;
    public static final int CREATE_NEW_RECORD = 2;
    public static final int SUPPLEMENT_RECORD = 3;
    //hopActivityForResult request flag
    public static final int REQUEST_IMG = 0x01;
    public static final int REQUEST_PARK = 0x02;
    public static final int REQUEST_CLLX = 0x03;
    public static final int REQUEST_HPZL = 0x04;

    public static final int GOOGLE_COLOR_ONE = 0;
    public static final int GOOGLE_COLOR_TWO = 1;
    public static final int GOOGLE_COLOR_THREE = 2;
    public static final int GOOGLE_COLOR_FOUR = 3;

    public static final String URL_SP = "URL_SP";


    // TODO: 2019/2/26/026 后续迁移到数据库或者存储在本地文件
    public static HashMap<String, String> CLZT = new HashMap<>();

    static {
        CLZT.put("001", "未派单");
        CLZT.put("002", "已派单未签收");
        CLZT.put("004", "待拖已签收");
        CLZT.put("008", "拖移中");
        CLZT.put("012", "停入");
        CLZT.put("016", "转出申请");
        CLZT.put("020", "待定转出申请");
        CLZT.put("024", "待定转出审批");
        CLZT.put("028", "待定转出移交");
        CLZT.put("032", "待定转出接收");
        CLZT.put("036", "报废审批");
        CLZT.put("040", "事故车报废归档");
        CLZT.put("044", "转出审批");
        CLZT.put("048", "已转出");
        CLZT.put("052", "回收接收");
        CLZT.put("056", "违法车报废归档");
        CLZT.put("060", "修改申请");
        CLZT.put("064", "申报申请");
        CLZT.put("068", "放行审批");
        CLZT.put("072", "放车");
        CLZT.put("076", "离去");
    }

    public static HashMap<String, String> APPROVAL_PURPOSE = new HashMap<>();

    static {
        APPROVAL_PURPOSE.put("01", "申请放行");
        APPROVAL_PURPOSE.put("02", "申请转出");
        APPROVAL_PURPOSE.put("52", "申请修改");
        APPROVAL_PURPOSE.put("03", "申请报废");
        APPROVAL_PURPOSE.put("04", "申请申报");
        APPROVAL_PURPOSE.put("05", "申请报废");
    }

    /**
     * 人员类别
     */
    public static HashMap<String, String> RYLB = new HashMap<>();

    static {
        RYLB.put("1", "民警");
        RYLB.put("2", "拖车司机");
        RYLB.put("3", "停车场管理员");
        RYLB.put("4", "停车场门卫");
    }

    /**
     * 施救情况
     */

    public static HashMap<String, String> SJQK = new HashMap<>();

    static {
        SJQK.put("0", "拖车进入");
        SJQK.put("1", "代开进入");
        SJQK.put("2", "押送进入");
        SJQK.put("3", "指定进入");
    }

    /**
     * 卸载情况
     */

    public static HashMap<String, String> XZQK = new HashMap<>();

    static {
        XZQK.put("1", "卸载");
        XZQK.put("2", "未卸载");
    }

    /**
     * 超载情况
     */
    public static HashMap<String, String> CZQK = new HashMap<>();

    static {
        CZQK.put("1", "超载");
        CZQK.put("2", "未超载");
    }

    /**
     * 扣车原因
     */

    public static HashMap<String, String> KCYY = new HashMap<>();

    static {
        KCYY.put("1", "违法");
        KCYY.put("2", "事故");
        KCYY.put("6", "其他");
        KCYY.put("3", "简易事故");
        KCYY.put("4", "一般事故");
    }

    /**
     * 号牌种类
     */
    public static HashMap<String, String> HPZL = new HashMap<>();

    static {
        HPZL.put("01", "大型汽车");
        HPZL.put("02", "小型汽车");
        HPZL.put("03", "使馆汽车");
        HPZL.put("04", "境外汽车");
        HPZL.put("06", "外籍汽车");
        HPZL.put("07", "普通摩托车");
        HPZL.put("08", "轻便摩托车");
        HPZL.put("09", "使馆摩托车");
        HPZL.put("10", "领馆摩托车");
        HPZL.put("11", "境外摩托车");
        HPZL.put("12", "外籍摩托车");
        HPZL.put("13", "低速车");
        HPZL.put("14", "挂车");
        HPZL.put("16", "教练汽车");
        HPZL.put("17", "教练摩托车");
        HPZL.put("18", "试验汽车");
        HPZL.put("19", "试验摩托车");
        HPZL.put("20", "临时入境汽车");
        HPZL.put("21", "临时入境摩托车");
        HPZL.put("22", "临时行驶车");
        HPZL.put("23", "警用汽车");
        HPZL.put("24", "原农机号牌");
        HPZL.put("26", "香港入出境车");
        HPZL.put("27", "澳门入出境车");
        HPZL.put("31", "武警号牌");
        HPZL.put("32", "军队号牌");
        HPZL.put("41", "大型新能源汽车");
        HPZL.put("42", "小型新能源汽车");
        HPZL.put("99", "其它号牌");
    }

    public final static String[] APPROVAL_STATUS = new String[]{"待审核", "已通过", "已驳回"};

    public final static String[] REQUEST_STATUS = new String[]{"审核中", "通过", "未通过"};

    /**
     * 强制措施类型
     */
    public static HashMap<String, String> QZCSLX = new HashMap<>();

    static {
        QZCSLX.put("1", "扣留机动车");
        QZCSLX.put("2", "扣留非机动车");
        QZCSLX.put("3", "扣留驾驶证");
        QZCSLX.put("4", "扣留行驶证");
        QZCSLX.put("5", "收缴物品");
        QZCSLX.put("6", "拖移机动车");
        QZCSLX.put("7", "检验血液/尿样");
        QZCSLX.put("9", "其他");
        QZCSLX.put("y", "约束至酒醒");
    }

    public static final int HOP_SOURCE_AC_QRSCAN_RESULT = 3;
    public static final int HOP_SOURCE_AC_FUNCTION = 2;

}