package com.thunisoft.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/11/27.
 */
public class NewFileTest {

    public static void main(String[] args) {
        /*String sourceFilePath = "";
        String targetFilePath = "F:\\export_dadyxt\\xml";
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        File sourceFile = new File(sourceFilePath);
        try {
            fis = new FileInputStream(sourceFile);
        } catch (FileNotFoundException e) {
            System.out.println("源文件不存在");
        }
        byte[] bufs = new byte[1024 * 10];
        bis = new BufferedInputStream(fis, 1024 * 10);
        int read = 0;
        try {
            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                fos.write(bufs, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        String filename = "DA_11_1862000_1.xml";
        int indexOfPoint = filename.indexOf(".");
        String dahjz = filename.substring(indexOfPoint-1, indexOfPoint);
        System.out.println(dahjz);
    }

}