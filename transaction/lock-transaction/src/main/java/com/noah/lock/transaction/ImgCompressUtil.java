//package com.noah.lock.transaction;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//
///**
// * 图片压缩并保存
// *
// * @author jingxue.chen
// */
//public class ImgCompressUtil {
//
//
//    public static void main(String[] args) {
//        try {
//            //ImgCompressUtil.compressFileName("D:\\logs\\0003.jpg", 100, 100, "D:\\logs", "0003-6.jpg");
//            ImgCompressUtil.compressFileName("/Users/noah/Downloads/萌芽熊DIY素材/电话1.png", 100, 100, "/Users/noah/Downloads/萌芽熊DIY素材/aa/", "0003-6.jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 根据图片的本地路径压缩图片并保存
//     *
//     * @param fileUrl     图片存放全路径【D:\\logs\\0003.jpg】
//     * @param width       保存宽度
//     * @param height      保存高度
//     * @param saveAddress 保存路径【D:\\logs】
//     * @param fileName    保存文件名【0003.jpg】
//     * @throws IOException
//     */
//    public static void compressFileName(String fileUrl, int width, int height, String saveAddress, String fileName) throws IOException {
//        File file = new File(fileUrl);// 读入文件
//        Image img = ImageIO.read(file);      // 构造Image对象
//        int imgWidth = img.getWidth(null);    // 得到源图宽
//        int imgHeight = img.getHeight(null);  // 得到源图长
//        if (imgWidth / imgHeight > width / height) {
//            int h = (int) (imgHeight * width / imgWidth);
//            resize(img, width, h, saveAddress, fileName);
//        } else {
//            int w = (int) (imgWidth * height / imgHeight);
//            resize(img, w, height, saveAddress, fileName);
//        }
//    }
//
//    /**
//     * 根据图片的本地路径压缩图片并保存
//     *
//     * @param inputStream 图片文件的文件流
//     * @param width       保存宽度
//     * @param height      保存高度
//     * @param saveAddress 保存路径【D:\\logs】
//     * @param fileName    保存文件名【0003.jpg】
//     * @throws IOException
//     */
//    public static void compressFileName(InputStream inputStream, int width, int height, String saveAddress, String fileName) throws IOException {
//        Image img = ImageIO.read(inputStream);      // 构造Image对象
//        int imgWidth = img.getWidth(null);    // 得到源图宽
//        int imgHeight = img.getHeight(null);  // 得到源图长
//        if (imgWidth / imgHeight > width / height) {
//            int h = (int) (imgHeight * width / imgWidth);
//            resize(img, width, h, saveAddress, fileName);
//        } else {
//            int w = (int) (imgWidth * height / imgHeight);
//            resize(img, w, height, saveAddress, fileName);
//        }
//    }
//
//    /**
//     * 强制压缩/放大图片到固定的大小
//     *
//     * @param img
//     * @param w           int 新宽度
//     * @param h           int 新高度
//     * @param saveAddress 文件保存地址
//     * @param fileName    文件保存名称
//     */
//    private static void resize(Image img, int newWidth, int newHeight, String saveAddress, String fileName) throws IOException {
//        BufferedImage newBufImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
//        newBufImg.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
//        FileOutputStream output = new FileOutputStream(saveAddress + File.separator + fileName);
//        // JPEGImageEncoder可适用于其他图片类型的转换
//        //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
//        //encoder.encode(newBufImg);
//        output.close();
//    }
//}