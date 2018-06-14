package com.scut.longerwu.graduation.dao.impl;

import com.mongodb.*;
import com.mongodb.client.*;
import com.scut.longerwu.graduation.dao.*;
import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.util.*;
import com.scut.longerwu.graduation.vo.*;
import org.apache.log4j.*;
import org.bson.Document;
import org.bson.conversions.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import javax.annotation.*;

import java.util.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

@Repository("standardMongoDao")
public class StandardMongoDaoImpl implements StandardMongoDao {

    @Resource
    private MongoClient mongoClient;

    private MongoCollection<Document> standardCollection;

    private final Logger LOGGER = Logger.getLogger(StandardMongoDaoImpl.class);


    @PostConstruct
    public void init() {
        MongoDatabase database = mongoClient.getDatabase("standard");
        this.standardCollection = database.getCollection("standard_col");
    }

    /**
     * 根据标准名和引用文件找到对应的国家标准
     * @param name          标准名
     * @param refFiles      引用文件
     * @return              对应的国家标准
     */
    @Override
    public SearchResultItem getStandardResultItem(String name, List<String> refFiles) {
        Document doc = standardCollection
                .find(and(in("number", refFiles), regex("requirements.name", name)))
                .projection(fields(include("number", "c_name", "requirements.$")))
                .first();
        SearchResultItem searchResultItem = new SearchResultItem();
        if (doc == null) {
            return null;
        }
        searchResultItem.setNumber(doc.getString("number"));
        searchResultItem.setFileName(doc.getString("c_name"));

        List<StandardResultItem> standards = getStandardResultItemListByDocument(doc);
        searchResultItem.setStandards(standards);
        return searchResultItem;
    }

    /**
     * 根据文档获取指标项列表
     * @param myDoc 指定文档
     * @return  指标项列表
     */
    private List<StandardResultItem> getStandardResultItemListByDocument(Document myDoc) {
        List<Document> documents = (List<Document>) myDoc.get("requirements");
        List<StandardResultItem> standards = new ArrayList<>();
        for (Document doc : documents) {
            String name = doc.getString("name");
            String index = doc.getString("index");
            String content = doc.getString("content");
            StandardResultItem standardResultItem = new StandardResultItem(name, content, index);
            standards.add(standardResultItem);
        }
        return standards;
    }

    @Override
    public List<StandardResultItem> getStandardResultItemListByFileName(String fileName) {
        if (fileName.endsWith("D.txt")) {
            fileName = fileName.substring(0, fileName.length() - 5);
        }
        fileName = fileName.replace("_", "/");
        List<StandardResultItem> itemList = new ArrayList<>();
        Document doc = standardCollection.find(eq("number", fileName)).first();
        if (doc != null) {
            itemList = (List<StandardResultItem>) doc.get("requirements");
        } else {
            LOGGER.warn("doc is null!fileName:" + fileName);
        }
        return itemList;
    }


    /**
     * 插入国家标准
     * @param standardInfo
     * @param standardResultItemList
     */
    @Override
    public void insertStandardDocument(StandardInfo standardInfo, List<StandardResultItem> standardResultItemList) {
        List<Document> standardResultItemDocumentList = new ArrayList<>();
        for (StandardResultItem item : standardResultItemList) {
            standardResultItemDocumentList.add(StandardResultItem.document(item));
        }
        Document document = new Document("number", standardInfo.getNumber())
                .append("c_name", standardInfo.getcName())
                .append("e_name", standardInfo.geteName())
                .append("applicable_range", standardInfo.getApplicableRange())
                .append("scope", standardInfo.getScope())
                .append("ccs", standardInfo.getCcs())
                .append("ics", standardInfo.getIcs())
                .append("status", standardInfo.getStatus())
                .append("publish_date", standardInfo.getPublishDate())
                .append("carry_out_date", standardInfo.getCarryOutDate())
                .append("abolish_date", standardInfo.getAbolishDate())
                .append("ref_standards", Arrays.asList(standardInfo.getRefStandards().split(";")))
                .append("requirements", standardResultItemDocumentList);

        this.standardCollection.insertOne(document);
    }

    /**
     * 根据指标名和产品名返回搜索结果
     * @param keywords  指标名
     * @param product   产品名
     * @return          搜索结果
     */
    @Override
    public List<SearchResultItem> getStandardResultItemListByIndex(String keywords, String product) {
        List<SearchResultItem> searchResultItemList = new ArrayList<>();
        Block<Document> resultBlock = new Block<Document>() {
            @Override
            public void apply(final Document doc) {
                String number = doc.getString("number");
                String cName = doc.getString("c_name");
                List<String> refStandards = (List<String>) doc.get("ref_standards");
                SearchResultItem item = new SearchResultItem();
                item.setNumber(number);
                item.setFileName(number + " " + cName);
                List<Reference> references = new ArrayList<>();
                for (String reference : refStandards) {
                    references.add(new Reference(reference));
                }
                item.setReferences(references);
                List<StandardResultItem> standards=getStandardResultItemListByDocument(doc);
                item.setStandards(standards);
                searchResultItemList.add(item);
            }
        };

        Bson filter = null;
        if (product == null) {
            filter = regex("requirements.name", keywords);
        } else {
            filter = and(regex("c_name", product), regex("requirements.name", keywords));
        }

        standardCollection.find(filter)
                .projection(fields(include("requirements.$", "number", "c_name", "ref_standards")))
                .forEach(resultBlock);
        return searchResultItemList;
    }

    /**
     * 高级搜索
     * @param number            标准号
     * @param cName             中文名
     * @param ics               国际分类号
     * @param ccs               中国分类号
     * @param status            状态
     * @param publishDateStart  发布时间(开始)
     * @param publishDateEnd    发布时间(结束)
     * @param carryOutDateStart 实施时间(开始)
     * @param carryOutDateEnd   实施时间(结束)
     * @param index             章节号
     * @return                  搜索结果
     */
    @Override
    public List<AdvancedSearchResultItem> getAdvancedSearchResult(String number,
                                                                  String cName,
                                                                  String ics,
                                                                  String ccs,
                                                                  String[] status,
                                                                  Long publishDateStart,
                                                                  Long publishDateEnd,
                                                                  Long carryOutDateStart,
                                                                  Long carryOutDateEnd,
                                                                  String index) {
        List<AdvancedSearchResultItem> itemList = new ArrayList<>();
        String[] fieldArr = new String[]{"number", "c_name", "ics", "ccs", "status", "publish_date", "carry_out_date"};
        List<String> fields = new ArrayList<>();
        for (String field : fieldArr) {
            fields.add(field);
        }
        //获取结果集
        Block<Document> resultBlock = new Block<Document>() {
            @Override
            public void apply(final Document doc) {
                System.out.println(doc.toJson());
                AdvancedSearchResultItem item = new AdvancedSearchResultItem();
                item.setNumber(doc.getString("number"));
                item.setcName(doc.getString("c_name"));
                item.setIcs(doc.getString("ics"));
                item.setCcs(doc.getString("ccs"));
                item.setStatus(doc.getString("status"));
                item.setPublishDate(doc.getString("publish_date"));
                item.setCarryOutDate(doc.getString("carry_out_date"));
                if (!StringUtils.isEmpty(index)) {
                    List<Document> documents = (List<Document>) doc.get("requirements");
                    String content = documents.get(0).getString("content");
//                    List<StandardResultItem> standards = (List<StandardResultItem>) doc.get("requirements");
//                    item.setContent(standards.get(0).getContent());
                    item.setContent(content);
                } else {
                    item.setContent("--");
                }
                itemList.add(item);
            }
        };

        //增加过滤条件
        List<Bson> filters = new ArrayList<>();
        if (!StringUtils.isEmpty(number)) {
            filters.add(eq("number", number.trim()));
        }
        if (!StringUtils.isEmpty(cName)) {
            filters.add(regex("c_name", cName));
        }
        if (!StringUtils.isEmpty(ics)) {
            filters.add(eq("ics", ics));
        }
        if (!StringUtils.isEmpty(ccs)) {
            filters.add(eq("ccs", ccs));
        }
        if (status.length != 0) {
            filters.add(in("status", status));
        }
        if (publishDateStart != 0) {
            filters.add(gte("publish_date", DateFormatUtil.formatDate(publishDateStart)));
        }
        if (publishDateEnd != 0) {
            filters.add(lte("publish_date", DateFormatUtil.formatDate(publishDateEnd)));
        }
        if (carryOutDateStart != 0) {
            filters.add(gte("carry_out_date", DateFormatUtil.formatDate(carryOutDateStart)));
        }
        if (carryOutDateEnd != 0) {
            filters.add(lte("carry_out_date", DateFormatUtil.formatDate(carryOutDateEnd)));
        }
        if (!StringUtils.isEmpty(index)) {
            filters.add(eq("requirements.index", index));
            fields.add("requirements.$");
        }


        if (filters.size() > 0) {
            Bson filter = and(filters);
            standardCollection.find(filter)
                    .projection(fields(include(fields)))
                    .forEach(resultBlock);
        } else {
            standardCollection.find()
                    .projection(fields(include(fields)))
                    .forEach(resultBlock);
        }


        return itemList;
    }

    @Override
    public int getCountByNumber(String number) {
        return (int) standardCollection.count(eq("number", number));
    }

    /**
     * 根据产品名和中国标准分类号获取搜索结果
     * @param product       产品名
     * @param icsSet        中国标准分类号
     * @return
     */
    @Override
    public List<SearchResultItem> getStandardResultItemListByProduct(String product, Set<String> icsSet) {
        List<SearchResultItem> searchResultItemList = new ArrayList<>();
        Block<Document> resultBlock = new Block<Document>() {
            @Override
            public void apply(final Document doc) {
                String number = doc.getString("number");
                String cName = doc.getString("c_name");
                List<String> refStandards = (List<String>) doc.get("ref_standards");
                SearchResultItem item = new SearchResultItem();
                item.setNumber(number);
                item.setFileName(number + " " + cName);
                List<Reference> references = new ArrayList<>();
                for (String reference : refStandards) {
                    references.add(new Reference(reference));
                }
                item.setReferences(references);
                List<StandardResultItem> standards = (List<StandardResultItem>) doc.get("requirements");
                item.setStandards(standards);
                searchResultItemList.add(item);

                String ics = doc.getString("ics");
                if (!"".equals(ics.trim())) {
                    icsSet.addAll(Arrays.asList(ics.split(";")));
                }
            }
        };


        standardCollection.find(regex("c_name", product))
                .projection(fields(include("requirements", "number", "c_name", "ref_standards", "ics")))
                .forEach(resultBlock);
        return searchResultItemList;
    }

    /**
     * 根据中国标准分类号获取相关标准
     * @param ics
     * @return
     */
    @Override
    public List<RelatedResultItem> getRelatedResultItemsByIcs(String ics) {
        List<RelatedResultItem> relatedResultItems = new ArrayList<>();
        Block<Document> resultBlock = new Block<Document>() {
            @Override
            public void apply(final Document doc) {
                String number = doc.getString("number");
                String cName = doc.getString("c_name");
                RelatedResultItem item = new RelatedResultItem(number, cName);
                relatedResultItems.add(item);
            }
        };


        standardCollection.find(regex("ics", ics))
                .projection(fields(include("number", "c_name")))
                .forEach(resultBlock);
        return relatedResultItems;
    }

//    @Override
//    public SearchResultItem getStandardResultItemListByIndex2(String keywords) {
//
//        Document doc = standardCollection
//                .find(and(in("number", "GB 24906-2010"), eq("requirements.name", keywords)))
//                .projection(fields(include("number", "c_name", "requirements.$")))
//                .first();
//        SearchResultItem searchResultItem = new SearchResultItem();
//        if (doc == null) {
//            return null;
//        }
//        searchResultItem.setNumber(doc.getString("number"));
//        searchResultItem.setFileName(doc.getString("c_name"));
//
//        List<StandardResultItem> standards = getStandardResultItemListByDocument(doc);
//
//        searchResultItem.setStandards(standards);
//        return searchResultItem;
//    }

//    private static MongoCollection<Document> myCol=null;
//    private static int myCount=0;
//    private static List<Document> documents=new ArrayList<>();
//    @Override
//    public void addStandards(Integer times, Integer type) {
//        myCol=(type==1?standardCollection:notIndexStandardCollection);
//        Block<Document> addBlock = new Block<Document>() {
//            @Override
//            public void apply(Document document) {
//               documents.add(document);
//            }
//        };
//        FindIterable docs=myCol.find();
//        docs.forEach(addBlock);
//        for(int i=0;i<times;i++){
//            System.out.println(documents.size());
//            for(Document doc:documents){
//                doc.remove("_id");
//            }
//            myCol.insertMany(documents);
//        }
//        documents=new ArrayList<>();
//    }

}
