package com.ady01.demo4.jksj.util;

import com.ady01.demo4.jksj.column.ColumnCollector;
import com.ady01.demo4.jksj.column.ColumnCollectorRequest;
import com.ady01.demo4.jksj.column.ColumnCollectorResponse;
import com.ady01.demo4.jksj.detail.ArticleCollectorRequest;
import com.ady01.demo4.jksj.detail.ArticleCollectorResponse;
import com.ady01.demo4.jksj.dto.ColumnDto;
import com.ady01.demo4.jksj.detail.ArticleCollector;
import com.ady01.util.FrameUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * <b>description</b>： <br>
 * <b>time</b>：2019-04-21 09:48 <br>
 * <b>author</b>： 微信公众号：路人甲Java，专注于java技术分享（带你玩转 爬虫、分布式事务、异步消息服务、任务调度、分库分表、大数据等），喜欢请关注！
 */
public class CollectorUtil {
    public static final String COOKIE_VALUE = "_ga=GA1.2.978878894.1585646512; _gid=GA1.2.935166062.1585646512; SERVERID=1fa1f330efedec1559b3abbcb6e30f50|1585735838|1585731281; GRID=8acd0b6-0da13e4-e8730a6-dce88bf; LF_ID=1585646522823-2552720-3256660; GCID=8acd0b6-0da13e4-e8730a6-dce88bf; GCESS=BAsCBAAIAQMDBMsLg14BBPhGHQAMAQEFBAAAAAAKBAAAAAACBMsLg14GBFRGfLsEBAAvDQAJAQEHBGkbZj0-; _gat=1";

    /**
     * 采集一个系列的课程信息
     *
     * @param cid 课程编号
     * @return
     */
    public static ColumnCollectorResponse getColumnCollectorResponse(long cid) {
        ColumnCollectorRequest request = ColumnCollectorRequest.builder().cid(cid + "").build();
        return new ColumnCollector().collect(request);
    }

    /**
     * 采集一个系列的课程信息
     *
     * @param cid  课程编号
     * @param size 需采集的课程文章数量
     * @return
     */
    public static ColumnCollectorResponse getColumnCollectorResponse(long cid, int size) {
        ColumnCollectorRequest request = ColumnCollectorRequest.builder().cid(cid + "").size(size).build();
        return new ColumnCollector().collect(request);
    }

    /**
     * 采集一个课程中具体谋篇文章的内容
     *
     * @param id 文章编号
     * @return
     */
    public static ArticleCollectorResponse getArticleCollectorResponse(long id) {
        ArticleCollectorRequest request = ArticleCollectorRequest.builder().id(id).build();
        return new ArticleCollector().collect(request);
    }

    /**
     * 采集一个系列的课程所有文章内容
     *
     * @param cid 课程编号
     * @return
     */
    public static ColumnDto articleList(long cid) {
        List<ArticleCollectorResponse> articleCollectorResponseList = FrameUtil.newArrayList();
        ColumnCollectorResponse columnCollectorResponse = CollectorUtil.getColumnCollectorResponse(cid, 1000);
        List<ColumnCollectorResponse.Article> articleList = columnCollectorResponse.getArticleList();
        if (columnCollectorResponse != null && CollectionUtils.isNotEmpty(articleList)) {
            for (ColumnCollectorResponse.Article article : articleList) {
                ArticleCollectorResponse articleCollectorResponse = CollectorUtil.getArticleCollectorResponse(article.getId());
                articleCollectorResponseList.add(articleCollectorResponse);
            }
        }
        return ColumnDto.builder().columnCollectorResponse(columnCollectorResponse).articleCollectorResponseList(articleCollectorResponseList).build();
    }


    public static Map<String, String> headerMap() {
        Map<String, String> map = FrameUtil.newHashMap();
        map.put("Accept", "application/json, text/plain, */*");
        map.put("Accept-Encoding", "gzip, deflate, br");
        map.put("Accept-Language", "zh-CN,zh;q=0.9");
        map.put("Connection", "keep-alive");
        map.put("Content-Type", "application/json");
        map.put("Cookie", COOKIE_VALUE);
        map.put("Host", "time.geekbang.org");
        map.put("Origin", "https://time.geekbang.org");
        map.put("Referer", "https://time.geekbang.org/column/126");
        map.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        return map;
    }


}
