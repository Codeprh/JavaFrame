package com.noah.guava.other;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.List;

public class GetFileName {

    public static void main1(String[] args) {
        //String folderPath = "/Volumes/data/macdata/a-极客时间/051-099/88-高并发系统设计40问/"; // 替换为实际的文件夹路径
        String folderPath = "/Users/noah/Desktop/noah-up-3/"; // 替换为实际的文件夹路径

        List<File> folderList = FileUtil.loopFiles(folderPath, file -> file.isDirectory());

        for (File folder : folderList) {
            System.out.println("Folder name: " + folder.getName());
        }
    }

    public static void main22(String[] args) {
        //String folderPath = "/Users/noah/Desktop/noah-up-3/"; // 替换为实际的文件夹路径
        String folderPath = "/Volumes/data/macdata/a-极客时间/"; // 替换为实际的文件夹路径

        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        System.out.println("Folder name: " + file.getName());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        //String folderPath = "your_folder_path"; // 替换为实际的文件夹路径
        //String folderPath = "/Users/noah/Desktop/noah-up-3/";
        String folderPath = "/Volumes/data/macdata/a-极客时间/";
        int maxDepth = 2; // 设置递归深度

        traverseFolders(folderPath, maxDepth, 0);
    }

    private static void traverseFolders(String folderPath, int maxDepth, int currentDepth) {
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            System.out.println("Folder name: " + folder.getName());

            if (currentDepth < maxDepth) {
                File[] files = folder.listFiles();

                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            traverseFolders(file.getAbsolutePath(), maxDepth, currentDepth + 1);
                        }
                    }
                }
            }
        }
    }


}
