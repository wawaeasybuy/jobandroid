package com.example.ryo.job_employer.Utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015/11/10.
 */
public class PositionIndustryUtil {
    public static final String CHINA = "中国";
    public static final int TYPE_PROVINCE = 1;
    public static final int TYPE_CITY = 2;


    public static Hashtable<String, String[]> initPositionIndustryHashtable() {
        Hashtable<String,  String[]> tmpProvince = new Hashtable<String, String[]>();
        // 从小地区开始构建
//        tmpProvince.put("销售|客服|市场", new String[]{"销售业务", "销售管理", "销售行政/商务", "客服/售前/售后技术支持", "市场", "公关/媒介", "广告/会展"});
//        // 安徽省
//        tmpProvince.put("财务|人力资源|行政", new String[]{"财务/审计/税务", "人力资源", "行政/后勤/文秘"});
//        tmpProvince.put("项目|质量|高级管理", new String[]{"项目管理/项目协调", "质量管理/安全防护", "高级管理"});
        tmpProvince.put("互联网", new String[]{"软件/互联网开发/系统集成", "硬件开发", "互联网产品/运营管理", "IT质量管理/测试/配置管理", "IT运维/技术支持", "IT管理/项目协调", "电信/通信技术开发及应用"});
        tmpProvince.put("房产建筑", new String[]{"房地产开发/经纪/中介", "土木/建筑/装修/市政工程", "物业管理"});
        tmpProvince.put("金融财会", new String[]{"银行", "证券/期货/投资管理/服务", "保险", "信托/担保/拍卖/典当"});
        tmpProvince.put("外贸行业", new String[]{"采购/贸易", "交通运输服务", "物流/仓储"});
        tmpProvince.put("工业制造", new String[]{"生产管理/运营", "电子/电器/半导体/仪器仪表", "汽车制造", "汽车销售与服务","机械设计/制造/维修","服装/纺织/皮革设计/生产","技工/操作工、生物/制药/医疗器械","化工"});
//        tmpProvince.put("采购|贸易|交通|物流", new String[]{"采购/贸易", "交通运输服务", "物流/仓储"});
        tmpProvince.put("广告传媒", new String[]{"影视/媒体/出版/印刷", "艺术/设计"});
//        tmpProvince.put("咨询|法律|教育|翻译", new String[]{"采购/贸易", "交通运输服务", "物流/仓储"});
        tmpProvince.put("医药化学", new String[]{"商超/酒店/娱乐管理/服务","旅游/度假/出入境服务","烹饪/料理/食品研发","保健/美容/美发/健身","医院/医疗/护理","社区/居民/家政服务"});
//        tmpProvince.put("能源|环保|农业|科研", new String[]{"能源/矿产/地质勘查","环境科学/环保","农/林/牧/渔业","公务员/事业单位/科研机构"});
        tmpProvince.put("其他行业", new String[]{"实习生/培训生/储备干部","志愿者/社会工作者","兼职/临时、其他"});
        return tmpProvince;
    }
    public static String[] getPositionCategory(Hashtable<String,String[]> table,
                                             String args) {
        String[] arr = null;
        if (table == null) {
            return null;
        }
        arr=table.get(args);
        return arr;
    }


    public static String[] getIndustryCategory(Hashtable<String, ? extends Object> table) {
        String[] arr = null;
        Set<String> set = table.keySet();
        String[] tmp = new String[set.size()];
        Iterator<String> iterator = set.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            tmp[count++] = iterator.next().toString();
        }
        arr = tmp;
        return arr;
    }

    public static final List<String> arr2List(String[] arr) {
        List<String> list = null;
        if (arr != null) {
            list = new ArrayList<String>();
            for (int i = 0; i < arr.length; i++) {
                list.add(arr[i]);
            }
        }
        return list;
    }
}