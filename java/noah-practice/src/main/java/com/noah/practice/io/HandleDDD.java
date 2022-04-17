package com.noah.practice.io;

import com.google.common.io.Files;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class HandleDDD {

    public static void main(String[] args) {
        String path = "/Users/noah/Documents/noah/noahGithub/dddTemplate/";
        //String path = "/Users/noah/Desktop/ddd-pre/";
        File files = new File(path);
        doExtract(files);
    }

    /**
     * 递归提取Java文件
     *
     * @param fileOrDirectory
     */
    private static void doExtract(File fileOrDirectory) {

        if (fileOrDirectory.isFile()) {
            return;
        }

        File[] listFiles = fileOrDirectory.listFiles((file) -> {

            //递归处理
            if (file.isDirectory()) {
                return true;
            }

            //判断是否是class文件
            String absolutePath = file.getAbsolutePath();

            if (absolutePath.endsWith("java")) {
                System.out.println(absolutePath);
                try {

                    List<String> readLines = Files.readLines(file, Charset.defaultCharset());
                    StringBuffer newLine = new StringBuffer();

                    for (String line : readLines) {

                        if (line.contains("netease.gcom")) {
                            line = line.replace("netease.gcom", "noah");
                        }

                        newLine.append(line).append(System.getProperty("line.separator"));
                    }

                    Files.write(newLine.toString().getBytes(StandardCharsets.UTF_8), file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return false;
        });

        if (Objects.nonNull(listFiles) && listFiles.length > 0) {
            //递归处理
            for (File f : listFiles) {
                doExtract(f);
            }
        }

    }


}
