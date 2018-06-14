package com.scut.longerwu.graduation.controller;

import com.scut.longerwu.graduation.enums.*;
import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.service.*;
import com.scut.longerwu.graduation.util.*;
import com.scut.longerwu.graduation.vo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.env.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 文件控制器(上传下载)
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${directory.qiye}")
    private String qiyeDirectory;       //保存企业标准的目录

    @Value("${directory.country}")
    private String countryDirectory;    //保存国家标准的目录

    @Value("${directory.qiyeTxt}")
    private String qiyeTxtDirectory;    //保存企业标准txt格式的目录

    @Resource
    private StandardService standardService;

    /**
     * 上传文件
     * @param multiReq
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Result uploadFile(MultipartHttpServletRequest multiReq,
                                 HttpSession session) {
        // 获取上传文件的路径
        MultipartFile uploadFile = multiReq.getFile("file");
        String originalFilename = uploadFile.getOriginalFilename();
        System.out.println("multiReq.getFile()" + originalFilename);
        // 截取上传文件的后缀
        String uploadFileSuffix = originalFilename.substring(
                originalFilename.lastIndexOf('.') + 1, originalFilename.length());
        if (!"pdf".equals(uploadFileSuffix.toLowerCase())) {
            return new Result(ResultEnum.FILE_FORMAT_ERROR);
        }
        long uploadTime = System.currentTimeMillis();
        long fileSize = uploadFile.getSize();
        String uploadFileName = originalFilename.substring(0,originalFilename.lastIndexOf('.')) + fileSize + "_" + uploadTime / 1000;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fis = (FileInputStream) multiReq.getFile("file").getInputStream();
            fos = new FileOutputStream(new File(qiyeDirectory + uploadFileName
                    + ".")
                    + uploadFileSuffix);
            System.out.println(new File(qiyeDirectory + uploadFileName
                    + ".")
                    + uploadFileSuffix);
            byte[] temp = new byte[1024];
            int len = 0;
            while ((len = fis.read(temp)) != -1) {
                fos.write(temp, 0, len);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(ResultEnum.SERVER_ERROR);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //将pdf解析为txt
        String pdfPath = qiyeDirectory + uploadFileName + ".pdf";
        String txtPath = qiyeTxtDirectory + uploadFileName + ".txt";
        PdfboxUtil.transformPdfToTxt(pdfPath, txtPath);
        String company=PdfboxUtil.extractCompany(txtPath);
        //插入数据库
        Staff staff= (Staff) session.getAttribute("staff");
        String staffName=staff==null?"user1":staff.getName();
        QiyeStandard standard = new QiyeStandard(originalFilename.substring(0,originalFilename.lastIndexOf('.')),
                uploadFileName, fileSize, staffName, uploadTime,company);
        standardService.insertQiyeStandard(standard);
        return new Result(ResultEnum.SUCCESS, standard);
    }

    /**
     * 下载文件
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse res,
                             @RequestParam("fileName") String fileName,
                             @RequestParam(value = "type",defaultValue = "1")Integer type,
                             @RequestParam(value = "originalFileName",required = false)String originalFileName)
                             throws UnsupportedEncodingException {
        String filePath="";
        fileName=fileName.replace("/", "_");//推荐性标准作处理(文件名不能包含/)
        if(type==1){
            filePath=countryDirectory + fileName + "D.PDF";
        }else{
            filePath=qiyeDirectory+fileName+".pdf";
            fileName=originalFileName;
        }
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-streamp");
//        URLEncoder.encode(fileName,"UTF-8");
        fileName=new String(fileName.getBytes(),"ISO-8859-1");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".pdf");
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
            int len = 0;
            while ((len = bis.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

