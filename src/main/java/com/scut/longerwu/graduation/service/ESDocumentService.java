package com.scut.longerwu.graduation.service;

import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.vo.*;
import org.elasticsearch.action.index.*;

import java.io.*;
import java.util.*;

public interface ESDocumentService {

//    IndexResponse addDocument(Document document) throws IOException;

    IndexResponse addStandard(Standard standard) throws IOException;

//    List<ESResultItem> searchByIndex(String keywords);
//
//    List<ESResultItem> searchByMixing(String product, String index);

    List<Map<String,Object>> searchByContent(String keywords);
}
