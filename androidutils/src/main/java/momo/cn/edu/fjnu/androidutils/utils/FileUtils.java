package momo.cn.edu.fjnu.androidutils.utils;

import android.support.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import momo.cn.edu.fjnu.androidutils.service.ProgressUpdateListener;

/**
 * 文件操作工具
 * @author gaofei
 */

public class FileUtils {

    private FileUtils(){

    }

    /**
     * 获取文件的MD5值
     * @param file
     * @return
     */
    public static String getFileMD5(@NonNull File file) {
        MessageDigest digest;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }

    /**
     * 字节转换成16进制字符串
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * 删除目录或文件
     * @param targetFile
     * @return
     */
    public static boolean deleteFile(@NonNull File targetFile){
        if(targetFile.isFile()){
            return targetFile.delete();
        }
        //目录文件列表
        LinkedList<File> dirFiles = new LinkedList<File>();
        //待删除目录列表
        LinkedList<File> delDirFiles = new LinkedList<File>();
        dirFiles.add(targetFile);
        //被删除的文件列表
        List<String> delFilePaths = new ArrayList<String>();
        //删除文件
        while(!dirFiles.isEmpty()){
            File lastDirFile = dirFiles.removeFirst();
            if(!lastDirFile.canWrite())
                lastDirFile.setWritable(true);
            File[] childFiles = lastDirFile.listFiles();
            //空目录可以直接删除
            if(childFiles == null || childFiles.length == 0)
                lastDirFile.delete();
            else{
                int childLength = childFiles.length;
                int fileCount = 0;
                for(File childFile : childFiles){
                    if(childFile.isFile()){
                        ++fileCount;
                        if(!childFile.canWrite())
                            childFile.setWritable(true);
                        childFile.delete();
                        delFilePaths.add(childFile.getPath());
                    }else{
                        dirFiles.add(childFile);
                    }
                }
                if(fileCount == childLength)
                    lastDirFile.delete();
                else
                    delDirFiles.add(lastDirFile);

            }
        }

        //删除目录
        while(!delDirFiles.isEmpty()){
            delDirFiles.removeLast().delete();
        }
        return !targetFile.exists();

    }

    /**
     * 文件复制操作
     * @param srcFile
     * @param targetFile
     * @return 拷贝结果
     */
    public static int copyFile(@NonNull File srcFile, @NonNull File targetFile, ProgressUpdateListener updateListener){
        long allTotalSize = getAllFilesSize(srcFile);
        long currentProgressSize = 0;
        if(srcFile.isFile()){
            return copyRealFile(srcFile, targetFile, updateListener, allTotalSize, currentProgressSize);
        }else{
            int totalFileCount = getFilesCount(srcFile);
            int copyFileCount = 0;
            LinkedList<File> srcDirFiles = new LinkedList<File>();
            srcDirFiles.add(srcFile);
            String srcParentPath = srcFile.getParentFile().getPath();
            File targetParentFile = targetFile.getParentFile();
            File lastDirFile;
            while(!srcDirFiles.isEmpty()){
                File srcDirFile = srcDirFiles.removeFirst();
                String srcDirPath = srcDirFile.getPath();
                lastDirFile = new File(targetParentFile, srcDirPath.substring(srcParentPath.length() + 1));
                if(lastDirFile.mkdirs())
                    ++copyFileCount;
                File[] childFiles = srcDirFile.listFiles();
                if(childFiles != null && childFiles.length > 0){
                    for(File childFile : childFiles){
                        if(childFile.isFile()){
                            int copyResult = copyRealFile(childFile, new File(lastDirFile, childFile.getName()), updateListener, allTotalSize, currentProgressSize);
                            if(copyResult == 0)
                                ++copyFileCount;
                            currentProgressSize += childFile.length();
                        }else{
                            srcDirFiles.add(childFile);
							/*if(new File(lastDirFile, childFile.getName()).mkdir())
								++copyFileCount;*/
                        }
                    }
                }
            }
            if(totalFileCount == copyFileCount)
                return 0;
            return -1;
        }
    }


    /**
     * 拷贝实际文件
     * @param srcFile
     * @param targetFile
     * @throws Exception
     */
    public static int copyRealFile(File srcFile, File targetFile, ProgressUpdateListener updateListener, long totalSize, long currentProgressSize){
        BufferedInputStream srcInputStream = null;
        BufferedOutputStream targetOutputStream = null;
        try{
            boolean isCreateSuccess = targetFile.createNewFile();
            if(isCreateSuccess){
                if(!targetFile.canWrite()){
                    boolean isWriteable = targetFile.setWritable(true);
                    //目标文件不可写
                    if(!isWriteable)
                        return -1;
                }
            }else{
                //创建文件失败
                return -1;
            }

            if(!srcFile.canRead()){
                //设置可读
                boolean isReadable = srcFile.setReadable(true);
                if(!isReadable)
                    return -1;
            }
            long curretnSize = currentProgressSize;
            srcInputStream = new BufferedInputStream(new FileInputStream(srcFile));
            targetOutputStream = new BufferedOutputStream(new FileOutputStream(targetFile));
            byte[] buffer = new byte[2048];
            int readLength = 0;
            while((readLength = srcInputStream.read(buffer)) > 0){
                curretnSize += readLength;
                targetOutputStream.write(buffer, 0, readLength);
                updateListener.onUpdateProgress((int)(curretnSize * 1.0f / totalSize * 100));

            }
            targetOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(srcInputStream != null){
                try {
                    srcInputStream.close();
                } catch (Exception e) {
                    //no handle
                }
            }

            if(targetOutputStream != null){
                try {
                    targetOutputStream.close();
                } catch (Exception e) {
                    //no handle
                }
            }
        }

        return 0;
    }

    /**
     * 获取某个文件夹下所有的文件个数，包括子文件夹
     * @param targetDirFile
     * @return
     */
    public static int getFilesCount(@NonNull File targetDirFile){
        int fileCount = 0;
        LinkedList<File> dirFiles = new LinkedList<File>();
        dirFiles.add(targetDirFile);
        while(!dirFiles.isEmpty()){
            File dirFile = dirFiles.removeFirst();
            ++fileCount;
            File[] childFiles = dirFile.listFiles();
            if(childFiles != null && childFiles.length > 0){
                for(File childFile : childFiles){
                    if(childFile.isFile())
                        ++fileCount;
                    else
                        dirFiles.add(childFile);
                }
            }
        }
        return fileCount;
    }


    /**
     * 计算所有文件的字节数
     * @param targetFile
     * @return
     */
    public static long getAllFilesSize(File targetFile){
        long total = 0;
        if(targetFile.isFile())
            total += targetFile.length();
        else{
            List<File> dirFiles = new LinkedList<File>();
            dirFiles.add(targetFile);
            while(!dirFiles.isEmpty()){
                File dirFile = dirFiles.remove(0);
                File[] childFiles = dirFile.listFiles();
                if(childFiles != null && childFiles.length > 0){
                    for(File childFile : childFiles){
                        if(childFile.isFile())
                            total += childFile.length();
                        else
                            dirFiles.add(childFile);
                    }
                }
            }
        }
        return total;
    }
}
