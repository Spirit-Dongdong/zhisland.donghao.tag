package tag;


import com.util.AnalyzerUtil;
import com.util.TxtUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by Spirit on 16/6/23.
 */
public class PrepareTag {

    public static String[] commonTexts = {
        //华夏基石e洞察
        "来源：华夏基石e洞察（ID：chnstonewx）",
        "华夏基石e洞察原创，如需转载请通过向华夏基石e洞察公众号后台申请授权",
        "管理咨询及内容合作：szy20121014（微信）",
        "文章仅作者个人观点，如来源标注有误请告知，我们及时予以更正/删除",
        "人才经济时代，80%的企业高管面临知识更新！中国人民大学商学院、中国人民大学劳人院、华夏基石管理咨询集团、美国康奈尔大学劳工学院特联合升级打造——中国首席人才官（CHO），这里有最前沿、最系统、最实用的人力资源管理系统课程。",
        "报名申请：15011525812（薛老师）",
        "索取《中国首席人才官（CHO）班招生简章》请致电薛老师",
        "点击“阅读原文”链接了解招生详情",

        //春暖花开
        "文：陈春花 华南理工大学教授、博导",
        "内容选自《大学的意义》",
        "长按二维码选择购买",
        "也可以点击“阅读原文”购买",
        "如需转载，请联系春暖花开花小蜜",
        "ID：CNHK_HuaXiaoMi",
        "春 / 暖 / 花 / 开",
        "管理践行者陈春花的自媒体",
        "管理思想、商业评论、著作连载、人生感悟",
        "点上方绿标即可收听主播纪懋雷朗读音频",

        //吴晓波频道
        "本文图片来源于网络",
        "若涉及版权问题",
        "请于后台留言联系",
        "文中股市、期货内容仅供参考",
        "不构成投资建议",
        "你想看哪条新闻的点评观点",
        "有哪些新闻会给你的生活带来改变",
        "用少于100个字写下你感兴趣的新闻",
        "有可能被当值主编选中进行点评哦",
        "点击下图 即可报名",

        //深蓝财经网
        "ps：大家可以加个人微信号ksana2000，推荐访谈对象，主要是媒体人、前媒体人以及新媒体圈优秀的创业者。",
        ""




    };

//    public static String[] commonTexts = {
//            "以下内容属于第三方推广内容，不代表华尔街见闻观点，不构成投资建议，请独立判断与决策",
//            "了解更多汇丰如何助力您业务发展，请浏览www.business.hsbc.com.cn",
//            "喜欢就在下面点个",
//            "点击阅读原文，链接至扑克投资家（puoke.com）获取更多资讯",
//            "喜欢就转发和点吧",
//            "期待你精彩解答点击左下方【阅读原文】",
//            "大空头【点击进入】滑动到最下方留言板，查看更多精彩解答",
//            "《运联商业评论》文章仅作者个人观点",
//            "如来源标注有误请告知",
//            "我们及时予以更正/删除管理咨询及内容合作：szy20121014（微信）",
//            "华夏基石e洞察",
//            "潮汐问答集锦",
//            "不 知 不 觉 潮 汐 · 扑 克 问 答 栏 目 已 经",
//            "扑 克 小 骑 士 每 天 看 到 各 位 积 极 地 在 后 台 提 问 ， 针 对 同 一 个 问 题 在 留 言 板 各 抒 己 见 ， 内 心 觉 得 很 温 暖 ， 每 天 通 过 微 信 推 出 问 答 栏 目 是 有 意 义 有 温 度 的 。",
//            "当 初 建 立 这 个 栏 目 的 初 衷 就 是 想 让 各 位 都 参 与 进 来",
//            "我 们 提 供 平 台",
//            "万 小 伙 伴 都 可 以 从 我 们 这 里 有 所 收 获",
//            "不 仅 仅 每 天 能 收 到 我 们 独 家 精 选 的 文 章",
//            "也 能 在 问 答 栏 目 中 找 到 提 问 和 回 答 的 快 感",
//            "问 答 栏 目 还 会 继 续",
//            "等 你 的 问 题",
//            "等 你 的 答 案",
//            "潮 汐 · 扑 克 问 答 往 期 回 顾",
//            "潮汐·扑克问答集锦第",
//            "点击阅读原文找回来",
//            "若转载请回复 授权 查看须知",
//            "否则一律举报",
//            "广告投放QQ",
//            "【END】",
//            "《超级IP：互联网新物种方法论》如果流量可以流动如果数据只是孤独我渴望人格像影子抽离出故事万物生长自己走路穿越闪耀的场景看到无所谓的流行时间里有所有人的步伐有无意义的所在此刻让直播沉默若想了解学习更多的《超级IP》的方法论",
//            "投稿及白名单转载",
//            "主办与合作联合主办",
//            "场景实验室、洛可可设计集团、吴晓波频道、京东众筹、时趣互动",
//            "文章仅代表作者个人观点管理咨询及内容合作",
//            "szy20121014（微信）",
//            "领教工坊协办：诺梵、爱办公特别协办",
//            "点击以下头像点击获取：独家音频内容",
//            "跟着大咖去耶鲁游学，和全球顶级教授探讨会员专享",
//            "用1800元，林莉带你啃冯卫东10万元品牌战略课精选好书",
//            "网络关系已经过饱，赶紧简化出最重要的人",
//            "［招贤纳士］新媒体合伙人、总编辑、编辑、主笔产品经理、新媒体运营、技术运营、电商运营、活动运营商务BD、平面设计、漫画师、文案策划简历请发至bijixia@foxmail.com",
//            "点击大咖名字，查看精彩笔记",
//            "李善友丨傅盛｜余晨丨龚焱丨徐新",
//            "阎焱丨俞敏洪丨李丰｜蔡文胜丨段永朝丨罗振宇",
//            "罗胖｜吴伯凡｜宗毅｜吴声｜伊光旭丨李欣频｜王东岳",
//            "合作伙伴：混沌研习社｜创业邦｜领英中国｜36氪｜腾讯｜京东｜正和岛｜中欧｜微链",
//            "秦朔朋友圈微信公众号",
//            "填写报名表提交联系人",
//            "即刻点击阅读原文填写报名表加入我们吧",
//            "点击“阅读原文”了解及购买本书转载请联系编辑",
//            "文章仅代表作者个人观点管理咨询及内容合作",
//            "作者：陈春花，北京大学国发院教授",
//            "HR实名俱乐部（ID：hr_club）",
//            "华夏基石e洞察经HR实名俱乐部授权",
//            "商务合作｜请联系微信号",
//            "点击左下方【阅读原文】，栏目故事等你",
//            "在征得您同意的情况下",
//            "我们在留言区将您的解答分享出来",
//            "帮助到更多的人",
//            "点击阅读原文即刻参与",
//            "请致电薛老师点击“阅读原文”链接了解招生详情",
//            "（薛老师）索取《中国首席人才官（CHO）班招生简章",
//            "扑克百家是扑克投资家的活动运营平台",
//            "专注于大宗产业及金融市场热点话题讨论",
//            "定期邀请业内专家大佬进行主题分享",
//            "本文来自第",
//            "点击右下方【】，期待你精彩解答",
//            "点击左下方【阅读原文】",
//            "栏目故事等你",
//            "管理践行者陈春花的自媒体管理思想、商业评论、著作连载、人生感悟",
//            "（本文完）如需转载，请联系春暖花开花小蜜",
//            "推荐阅读点击下列标题",
//            "阅读更多冯叔专栏",
//            "致命诱惑｜董事会｜吃苦",
//            "火箭公司美女老板 ｜ 大学 ｜ 经济适用男活在未来",
//            "电灯泡＋ ｜ 底线 ｜ 辛德勒老炮儿 ｜ 赌王 ｜ 泼妇 ｜ 凤姐",
//            "主编邮箱－shoujiayin@890media.com",
//            "商务联系－邮箱wuxin@890media.com",
//            "期扑克百家",
//            "请点击阅读原文",
//            "转载请后台联系或发邮件至rym@puoke.com",
//            "点击下方按键",
//            "滑动到最下方留言板，查看更多精彩解答",
//            "如何参与",
//            "即可报名参加",
//            "如有疑问请联系，客服微信",
//            "视频来源于网络",
//            "如需转载请标明出处",
//            "本篇图文内容由华商韬略综合整理自网络文献",
//            "点击本页底部“阅读原文”进入报名页",
//            "完整填写报名信息",
//            "添加“扑克小秘书”微信",
//            "并告知您的真实姓名和付款截图",
//            "恭喜您",
//            "完成以上三步即报名成功",
//            "小秘书会在活动前两天开始陆续邀请您进入线上直播专用群",
//            "商务合作：顾天涯",
//            "电话＋86 186-1633-5129",
//            "微信号 mindcherisher",
//            "版权问题、商务合作、读者投稿",
//            "阅读原文",
//            "微信更新好",
//            "记得置顶哦",
//            "小伙伴们点击参与",
//            "有独家礼品赠送哦",
//            "本周扑克百家，如约而至",
//            "ID：hr_club",
//            "文章仅作者个人观点",
//            "如来源标注有误请告知",
//            "我们及时予以更正/删除管理咨询及内容合作",
//            "长按识别二维码",
//            "根据爱奇艺视频整理成文"
//    };

    public static String regex = "《.*?》";
    public static Pattern p = Pattern.compile(regex);


    public static String outputFile = "/Users/Spirit/lda.txt";
//    public static String ids = "/Users/Spirit/ids.txt";

    public static String[] articlePath = {
            "/Users/Spirit/PycharmProjects/python-crawler/tag/news",
            "/Users/Spirit/PycharmProjects/python-crawler/tag/article",
//            "/Users/Spirit/PycharmProjects/python-crawler/tag/new_article",
//            "/Users/Spirit/PycharmProjects/python-crawler/tag/tb_news",
//            "/Users/Spirit/PycharmProjects/python-crawler/tag/test"
    };

    public static String preprocess(String contentSrc) {
        String text = getText(contentSrc);
        for (String s : commonTexts) {
            text = text.replaceAll(s, "");
        }


        return text;
    }

    private static String getText(String contentSrc) {
        Document doc = Jsoup.parse(contentSrc);
        String text = doc.text();
        return text;
    }

    public static void go() {
        int count = 0;
        StringBuffer sb = new StringBuffer();
        for (String path : articlePath) {
            File dir = new File(path);
            File[] articles = dir.listFiles();
            for (File a : articles) {
                String id = a.getName();
                String content = TxtUtil.getFileContent(a.getAbsolutePath());

                for (String s : commonTexts) {
                    content = content.replaceAll(s, "");
                }

//                Matcher m = p.matcher(content);
//                while (m.find()) {
//                    content = content.replaceAll(m.group(), "");
//                }

//                String result = AnsjUtil.segment(content).trim();
                String result = AnalyzerUtil.getAnalyzeResult(content, " ");

                if (result.split(" ").length < 10) {
                    continue;
                }
//                TxtUtil.writeToFile(id, ids, true);
                TxtUtil.writeToFile(result, outputFile, true);
//                sb.append(result).append("\n");
                count++;

//                if (count % 500 == 0) {
//                    System.out.println(count);
//                    TxtUtil.writeToFile(sb.toString(), outputFile, true);
//                    sb = new StringBuffer();
//                }

            }

        }
        System.out.println(count);
//        TxtUtil.writeToFile(sb.toString(), outputFile, true);

    }



    public static void main(String[] args) {
        go();
    }
}
