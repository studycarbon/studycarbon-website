package cn.studycarbon.util;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

// 参考：https://blog.csdn.net/jun8148/article/details/102840412
@Component
public class FastDFSUtils {
    // fastdfs客户端
    private FastFileStorageClient fastFileStorageClient;

    public String uploadFile(byte[] bytes, long fileSize, String extension) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        StorePath storePath = fastFileStorageClient.uploadFile(byteArrayInputStream, fileSize, extension, null);
        return storePath.getFullPath();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return  storePath.getFullPath();
    }

    public String uploadFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(),
                FilenameUtils.getExtension(file.getName()), null);
        return  storePath.getFullPath();
    }

    public String uploadFile(InputStream inputStream, long size, String fileName) {
        StorePath storePath = fastFileStorageClient.uploadFile(inputStream, size, fileName, null);
        return storePath.getFullPath();
    }

    public byte[] downloadFile(String fileUrl) {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/")+1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        return fastFileStorageClient.downloadFile(group, path, downloadByteArray);
    }

    public void deleteFile(String fileUrl) {
        if(StringUtils.isBlank(fileUrl)) {
            return;
        }

        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
