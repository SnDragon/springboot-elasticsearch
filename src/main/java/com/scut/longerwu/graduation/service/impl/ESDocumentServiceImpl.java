package com.scut.longerwu.graduation.service.impl;

import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.service.*;
import com.scut.longerwu.graduation.vo.*;
import org.apache.log4j.*;
import org.elasticsearch.action.index.*;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.transport.*;
import org.elasticsearch.common.text.*;
import org.elasticsearch.common.xcontent.*;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.*;
import org.elasticsearch.search.fetch.subphase.highlight.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;

@Service("esDocumentService")
public class ESDocumentServiceImpl implements ESDocumentService {

    @Autowired
    private TransportClient client;

    private Logger logger = LogManager.getLogger(ESDocumentServiceImpl.class);

    private final String STANDARD_INDEX = "standard_index";

    private final String INDEX = STANDARD_INDEX;

    private final String TYPE = "standard";

//    @Override
//    public IndexResponse addDocument(Document document) throws IOException {
//        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
//                .field("author", document.getAuthor())
//                .field("title", document.getTitle())
//                .field("content", document.getContent())
//                .field("publicDate", document.getPublicDate())
//                .field("wordCount", document.getWordCount())
//                .endObject();
//        IndexResponse response = client.prepareIndex(this.INDEX, this.TYPE).setSource(builder).get();
//        return response;
//    }

    /**
     * 添加文档
     * @param standard
     * @return
     * @throws IOException
     */
    @Override
    public IndexResponse addStandard(Standard standard) throws IOException {
        StandardInfo standardInfo = standard.getStandardInfo();
        XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                .field("content", standard.getContent())
                .field("number", standardInfo.getNumber())
                .field("c_name", standardInfo.getcName())
                .field("e_name", standardInfo.geteName())
                .field("applicable_range", standardInfo.getApplicableRange())
                .field("ccs", standardInfo.getCcs())
                .field("ics", standardInfo.getIcs())
                .field("status", standardInfo.getStatus())
                .field("ref_standards", standardInfo.getRefStandards())
                .field("carry_out_date", standardInfo.getCarryOutDate())
                .field("publish_date", standardInfo.getPublishDate())
                .field("abolish_date", standardInfo.getAbolishDate())
                .field("scope", standardInfo.getScope())
                .endObject();
        IndexResponse response = client.prepareIndex(this.INDEX, this.TYPE)
                .setSource(builder)
                .get();
        return response;
    }

//    @Override
//    public List<ESResultItem> searchByIndex(String content) {
//
//        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
//        if (content != null) {
//            boolBuilder.must(QueryBuilders.matchQuery("content", content));
//        }
//        //增加highlight
//        HighlightBuilder hlBuilder = new HighlightBuilder();
//        hlBuilder.preTags("<tag>");
//        hlBuilder.postTags("</tag>");
//        hlBuilder.field("content");
//
//        SearchRequestBuilder builder = this.client.prepareSearch(this.INDEX)
//                .setTypes(this.TYPE)
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                .setQuery(boolBuilder)
//                .setFrom(0)
//                .setSize(10);
//        if (content != null) {
//            builder.highlighter(hlBuilder);
//        }
//        System.out.println(String.valueOf(builder));
//        SearchResponse response = builder.get();
//        List<ESResultItem> esResultItemList = new ArrayList<>();
//        for (SearchHit searchHit : response.getHits()) {
//            Map<String, Object> map = searchHit.getSourceAsMap();
//            if (content != null) {
////                map.put("highlights",Arrays.toString(searchHit.getHighlightFields().get("content").getFragments()));
////                map.put("content","");
//
//                ESResultItem item = new ESResultItem();
//                item.setTitle((String) map.get("number"));
//                item.setHighlights(Arrays.toString(searchHit.getHighlightFields().get("content").getFragments()));
//
//                esResultItemList.add(item);
//            }
//
//        }
//        return esResultItemList;
//
//    }
//
//    @Override
//    public List<ESResultItem> searchByMixing(String product, String content) {
//        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
//        if (content != null) {
//            boolBuilder.must(QueryBuilders.matchQuery("content", content));
//            boolBuilder.must(QueryBuilders.wildcardQuery("c_name", "*" + product + "*"));
//        }
//        //增加highlight
//        HighlightBuilder hlBuilder = new HighlightBuilder();
//        hlBuilder.preTags("<tag>");
//        hlBuilder.postTags("</tag>");
//        hlBuilder.field("content");
//
//        SearchRequestBuilder builder = this.client.prepareSearch(this.INDEX)
//                .setTypes(this.TYPE)
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                .setQuery(boolBuilder)
//                .setFrom(0)
//                .setSize(10);
//        if (content != null) {
//            builder.highlighter(hlBuilder);
//        }
//        System.out.println(String.valueOf(builder));
//        SearchResponse response = builder.get();
//        List<ESResultItem> esResultItemList = new ArrayList<>();
//        for (SearchHit searchHit : response.getHits()) {
//            Map<String, Object> map = searchHit.getSourceAsMap();
//            if (content != null) {
//
//                ESResultItem item = new ESResultItem();
//                item.setTitle((String) map.get("number"));
//                item.setHighlights(Arrays.toString(searchHit.getHighlightFields().get("content").getFragments()));
//
//                esResultItemList.add(item);
//            }
//
//        }
//        return esResultItemList;
//    }

    /**
     * 全文检索
     * @param content
     * @return
     */
    @Override
    public List<Map<String, Object>> searchByContent(String content) {
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        if (content != null) {
            boolBuilder.must(QueryBuilders.matchQuery("content", content));
        }
        //增加highlight
        HighlightBuilder hlBuilder = new HighlightBuilder();
        hlBuilder.preTags("<em>");
        hlBuilder.postTags("</em>");
        hlBuilder.field("content");

        SearchRequestBuilder builder = this.client.prepareSearch(this.INDEX)
                .setTypes(this.TYPE)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(boolBuilder)
                .setFrom(0)
                .setSize(100);
        if (content != null) {
            builder.highlighter(hlBuilder);
        }
        System.out.println(String.valueOf(builder));
        SearchResponse response = builder.get();
        List<Map<String, Object>> result = new ArrayList<>();
        for (SearchHit searchHit : response.getHits()) {
            Map<String, Object> map = searchHit.getSourceAsMap();
            if (content != null) {
                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("number", (String) map.get("number"));
                itemMap.put("fileName", map.get("number") + " " + map.get("c_name"));
                List<String> contents = new ArrayList<>();
                for (Text text : searchHit.getHighlightFields().get("content").getFragments()) {
                    contents.add(text.toString());
                }
                itemMap.put("contents", contents);
                result.add(itemMap);
            }
        }
        return result;
    }


}
