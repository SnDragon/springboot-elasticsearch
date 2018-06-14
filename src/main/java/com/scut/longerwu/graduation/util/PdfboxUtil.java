package com.scut.longerwu.graduation.util;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import com.scut.longerwu.graduation.vo.*;
import org.apache.log4j.*;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfboxUtil {

    private static final Logger LOGGER = Logger.getLogger(PdfboxUtil.class);
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String pdfDir = "I:/桌面/pdf";
        PdfboxUtil pdfutil = new PdfboxUtil();
        String file="I:\\桌面\\LED\\qiyeTxt\\LED 充电式灯具761935_1526115692.txt";
        pdfutil.extractCompany(file);
//        pdfutil.extractRefFiles(file);
//        List<StandardResultItem> itemList=pdfutil.extractRequirements(file);
//        for(StandardResultItem item: itemList){
//            System.out.println(item);
//        }
//        try {
//            File[] files=new File(pdfDir).listFiles();
//            for(File file: files){
//                if(file.getName().endsWith(".pdf")){
//                    String pdfPath=file.getAbsolutePath();
//                    String content = pdfutil.getTextFromPdf(pdfPath);
//                    String txtFilePath=pdfDir+"/"+file.getName().replace(".pdf",".txt");
//                    pdfutil.toTextFile(content, txtFilePath);
//                }
//            }
//            System.out.println("Finished !");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    /**
     * 读取PDF文件的文字内容
     *
     * @param pdfPath
     * @throws Exception
     */
    public static String getTextFromPdf(String pdfPath) throws Exception{
        // 是否排序
        boolean sort = false;
        // 开始提取页数
        int startPage = 1;
        // 结束提取页数
        int endPage = Integer.MAX_VALUE;

        String content = null;
        InputStream input = null;
        File pdfFile = new File(pdfPath);
        PDDocument document = null;
        try {
            input = new FileInputStream(pdfFile);
            // 加载 pdf 文档
            PDFParser parser = new PDFParser(input);
            parser.parse();
            document = parser.getPDDocument();
            // 获取内容信息
            PDFTextStripper pts = new PDFTextStripper();
            pts.setSortByPosition(sort);
            endPage = document.getNumberOfPages();
            System.out.println("Total Page: " + endPage);
            pts.setStartPage(startPage);
            pts.setEndPage(endPage);
            try {
                content = pts.getText(document);
            } catch (Exception e) {
                throw e;
            }
            System.out.println("Get PDF Content ...");
        } catch (Exception e) {
            LOGGER.error("error:"+e.getStackTrace());
        } finally {
            if (null != input)
                input.close();
            if (null != document)
                document.close();
        }

        return content;
    }

    /**
     * 把PDF文件内容写入到txt文件中
     *
     * @param pdfContent
     * @param filePath
     */
    public static void toTextFile(String pdfContent, String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                f.createNewFile();
            }
            System.out.println("Write PDF Content to txt file ...");
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(pdfContent);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<StandardResultItem> extractRequirements(String filePath) throws Exception {
        BufferedReader reader=new BufferedReader(new FileReader(filePath));
        String line=null;
        Pattern pattern=Pattern.compile("^(\\d).*要求");
        Matcher matcher=null;
        List<String> requirementContents=new ArrayList<>();
        //获取指标内容
        while ((line=reader.readLine())!=null){
            matcher=pattern.matcher(line);
            if(matcher.find()){
                System.out.println(line);
                Integer nextIndex=Integer.parseInt(matcher.group(1))+1;
                Pattern nextPattern=Pattern.compile("^"+nextIndex);
                while((line=reader.readLine())!=null){
                    Matcher nextMatcher=nextPattern.matcher(line);
                    if(nextMatcher.find()){
                        break;
                    }else{
                        requirementContents.add(line);
                    }
                }
                break;

            }
        }
        reader.close();
        //提取指标
        Pattern indexPattern=Pattern.compile("^(\\d\\.\\d+) (.*)$");
        StringBuilder sb=new StringBuilder("");
        List<StandardResultItem> itemList=new ArrayList<>();
        String index="";
        String name="";
        Pattern subIndexPattern=Pattern.compile("^\\d(\\.\\d+)* (.*)$");
        Pattern ignorePattern=Pattern.compile("^\\d ");//页码，忽略
        for(String str:requirementContents){
            Matcher indexMatcher=indexPattern.matcher(str);
            if(indexMatcher.find()){
                String content=sb.toString();
                if(!"".equals(content)){
                    StandardResultItem item=new StandardResultItem(name,content,index);
                    itemList.add(item);
                }
                index=indexMatcher.group(1).trim();
                name=indexMatcher.group(2).trim();
                sb=new StringBuilder("");
            }else{
                //忽略水印,页码,页眉
                if(!str.startsWith("备案") && !str.matches("^\\d ") && !str.matches(".*—\\d{4} $")){
                    Matcher subIndexMatcher=subIndexPattern.matcher(str);
                    if(subIndexMatcher.find()){
                        if("".equals(sb.toString()) || sb.lastIndexOf("\n")==sb.length()-1){
                            sb.append(str+"\n");
                        }else{
                            sb.append("\n"+str+"\n");
                        }
                    }else{
                        sb.append(str);
                    }
                }

            }
        }
        StandardResultItem item1=new StandardResultItem(name,sb.toString(),index);
        itemList.add(item1);
        return itemList;
    }

    public static void transformPdfToTxt(String pdfPath,String txtPath){
        try{
            toTextFile(getTextFromPdf(pdfPath),txtPath);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error:"+e.getStackTrace());
        }
    }

    /**
     * 提取规范性引用文件
     * @param filePath  txt文件路径
     * @return  引用的国家标准(强制/推荐)
     */
    public static List<String> extractRefFiles(String filePath) throws Exception{
        BufferedReader reader=new BufferedReader(new FileReader(filePath));
        String line=null;
        Pattern pattern=Pattern.compile("^(\\d).*引用文件");
        Matcher matcher=null;
        List<String> refFiles=new ArrayList<>();
        List<String> refContentLines=new ArrayList<>();
        while((line=reader.readLine())!=null){
            matcher=pattern.matcher(line);
            if(matcher.find()){
                System.out.println(line);
                Integer nextIndex=Integer.parseInt(matcher.group(1))+1;
                Pattern nextPattern=Pattern.compile("^"+nextIndex+" ");
                while((line=reader.readLine())!=null){
                    Matcher nextMatcher=nextPattern.matcher(line);
                    if(nextMatcher.find()){
                        break;
                    }else{
                        refContentLines.add(line);
                    }
                }
                break;
            }
        }
        Pattern filePattern=Pattern.compile("^(GB.*\\d+)  .*要求.*");
        for(String str: refContentLines){
            Matcher fileMatcher=filePattern.matcher(str);
            if(fileMatcher.find()){
                refFiles.add(fileMatcher.group(1));
            }
        }
        return refFiles;
    }

    public static String extractCompany(String filePath){
        String line=null;
        String company="";
        try{
            BufferedReader reader=new BufferedReader(new FileReader(filePath));
            Pattern pattern=Pattern.compile("^(.*)(发布|发 布) *$");
            Matcher matcher=null;
            while ((line=reader.readLine())!=null){
                matcher=pattern.matcher(line);
                if(matcher.find()){
                    company=matcher.group(1);
                    break;
                }
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
        return company.trim();

    }


}
