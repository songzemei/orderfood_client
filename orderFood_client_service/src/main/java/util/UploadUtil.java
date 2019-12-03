package util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UploadUtil {
    /**
     * 上传文件到本项目
     * @param file 文件解析器解析出的文件
     * @param file1 上传到的文件
     * @throws IOException
     */
    public static void uploadToSelf(MultipartFile file, File file1) throws IOException {
        file.transferTo(file1);
    }

    /**
     * 上传文件到图片服务器
     * @param file 文件解析器解析出的文件
     * @param path 图片服务器的上传路径
     * @param fileName 文件名
     * @throws IOException
     */
    public static void uploadToImgServer(MultipartFile file, String path, String fileName) throws IOException {
        Client client = Client.create();
        WebResource resource = client.resource(path+fileName);
        resource.put(file.getBytes());
    }
}
