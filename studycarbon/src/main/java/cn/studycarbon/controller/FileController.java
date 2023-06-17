package cn.studycarbon.controller;

import cn.studycarbon.domain.Catalog;
import cn.studycarbon.util.FastDFSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/files")
public class FileController {

    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FastDFSUtils fastDFSUtils;

    @GetMapping("/uploadUI")
    public String uploadUI(Model model) {
        logger.info("get => /files/uploadUI");
        return "/upload";
    }

    @PostMapping("/uploadFile")
    public String uploadFile() {
        logger.info("post => /files/uploadFiles");
        //logger.info(" upload file filename = " + file.getName() + "file size:"+file.getSize());
        //return fastDFSUtils.uploadFile(file);
        return "";
    }
}
