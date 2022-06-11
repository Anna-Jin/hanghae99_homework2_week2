package com.homework.homework_week2.common;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileManagerService {

    public final static String FILE_UPLOAD_PATH = "/Users/jin-yujin/Desktop/yujin/sparta/week5~7/homework_week2/image/";

    public String savaFile(String email, MultipartFile file) {
        String directoryName = email + "_" + System.currentTimeMillis() + "/";
        String filePath = FILE_UPLOAD_PATH + directoryName;

        // 디렉토리 생성
        File directory = new File(filePath);
        if (directory.mkdir() == false) {
            return null; // 디렉토리 생성 시 실패하면 null을 리턴
        }

        // 파일 업로드: byte 단위로 업로드한다.
        try {
            byte[] bytes = file.getBytes();

            //파일명 암호화
            String origName = new String(file.getOriginalFilename().getBytes("8859_1"),"UTF-8");
            String ext = origName.substring(origName.lastIndexOf(".")); // 확장자
            String saveFileName = getUuid() + ext;

            Path path = Paths.get(filePath + saveFileName);
            Files.write(path, bytes);

            return "/images/" + directoryName + saveFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public void deleteFile(String imagePath) {
        Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));

        if (Files.exists(path)) {
            try {
                Files.delete(path);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        path = path.getParent();
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
