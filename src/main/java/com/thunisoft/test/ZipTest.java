package com.thunisoft.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/11/25.
 */
public class ZipTest {

    public static void main(String[] args) {
        String sourceFilePath = "F:\\export_dadyxt\\xml";
        String zipFilePath = "F:\\export_dadyxt";
        int exportCount = 1000;
        String fyid = "1800";
        System.out.println("start: " + new Date());
        fileToZip(sourceFilePath, zipFilePath, exportCount, fyid);
        System.out.println("end: " + new Date());
    }

    /**
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
     *
     * @param sourceFilePath:待压缩的文件路径
     * @param zipFilePath:压缩后存放路径
     * @return
     */
    public static boolean fileToZip(String sourceFilePath, String zipFilePath, int exportCount, String fyid) {
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        String zipName = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");

        if (sourceFile.exists() == false) {
            System.out.println("待压缩的文件目录：" + sourceFilePath + "不存在.");
        } else {
            File[] sourceFiles = sourceFile.listFiles();
            if (null == sourceFiles || sourceFiles.length < 1) {
                System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
            } else {
                List<File> list = new ArrayList<File>();
                for (File file : sourceFiles) {
                    list.add(file);
                }
                int subCount = list.size() / exportCount;
                boolean exactDivide = list.size() % exportCount == 0;
                String fzid = "";
                int fzCount = 1;
                String dateId = "";
                for (int i = 0; i < subCount; i++) {
                    //Calendar cal = Calendar.getInstance();
                    // 此为 标准V1.2的zip命名规范
                    //String zipName = "DA_" + fyid + "_" + sf.format(new Date()) + "_01_" + cal.get(Calendar.YEAR) + "_" + dahjz;
                    if (dateId.equals(sf.format(new Date()))) {
                        fzCount++;
                    } else {
                        dateId = sf.format(new Date());
                        fzCount = 1;
                    }
                    if (fzCount < 10) {
                        fzid = "0" + fzCount;
                    } else {
                        fzid = fzCount + "";
                    }
                    zipName = "DA_" + fyid + "_" + dateId + "_" + fzid;
                    File zipFile = new File(zipFilePath + "/" + zipName + ".zip");
                    compress(list.subList(exportCount * i, exportCount * (i + 1)), zipFile);
                }
                if (!exactDivide) {
                    if (dateId.equals(sf.format(new Date()))) {
                        fzCount++;
                    } else {
                        dateId = sf.format(new Date());
                        fzCount = 1;
                    }
                    if (fzCount < 10) {
                        fzid = "0" + fzCount;
                    } else {
                        fzid = fzCount + "";
                    }
                    zipName = "DA_" + fyid + "_" + dateId + "_" + fzid;
                    File zipFile = new File(zipFilePath + "/" + zipName + ".zip");
                    compress(list.subList(exportCount * subCount, list.size()), zipFile);
                }
                flag = true;
            }
        }
        return flag;
    }

    private static void compress(List<File> list, File zipFile) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(new BufferedOutputStream(fos));
            byte[] bufs = new byte[1024 * 10];
            for (int i = 0; i < list.size(); i++) {
                // 创建ZIP实体，并添加进压缩包
                ZipEntry zipEntry = new ZipEntry(list.get(i).getName());
                zos.putNextEntry(zipEntry);
                // 读取待压缩的文件并写进压缩包里
                fis = new FileInputStream(list.get(i));
                bis = new BufferedInputStream(fis, 1024 * 10);
                int read = 0;
                while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                    zos.write(bufs, 0, read);
                }
                fis.close();
                // list.get(i).delete();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if (null != bis)
                    bis.close();
                if (null != zos)
                    zos.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 递归文件夹下的文件压缩
     *
     * @param f
     * @param baseDir
     * @param zos
     */
    public static void compress(File f, String baseDir, ZipOutputStream zos) {
        if (!f.exists()) {
            System.out.println("待压缩的文件目录或文件" + f.getName() + "不存在");
            return;
        }

        File[] fs = f.listFiles();
        BufferedInputStream bis = null;
        byte[] bufs = new byte[1024 * 10];
        FileInputStream fis = null;

        try {
            for (int i = 0; i < fs.length; i++) {
                String fName = fs[i].getName();
                System.out.println("压缩：" + baseDir + fName);
                if (fs[i].isFile()) {
                    ZipEntry zipEntry = new ZipEntry(baseDir + fName);//
                    zos.putNextEntry(zipEntry);
                    // 读取待压缩的文件并写进压缩包里
                    fis = new FileInputStream(fs[i]);
                    bis = new BufferedInputStream(fis, 1024 * 10);
                    int read = 0;
                    while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                        zos.write(bufs, 0, read);
                    }
                    // 如果需要删除源文件，则需要执行下面2句
                    // fis.close();
                    // fs[i].delete();
                } else if (fs[i].isDirectory()) {
                    compress(fs[i], baseDir + fName + "/", zos);
                }
            } // end for
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if (null != bis)
                    bis.close();
                // if(null!=zos)
                // zos.close();
                if (null != fis)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
