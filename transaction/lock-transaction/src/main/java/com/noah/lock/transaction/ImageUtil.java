package com.noah.lock.transaction;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import de.androidpit.colorthief.ColorThief;
import de.androidpit.colorthief.MMCQ;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.apache.lucene.util.RamUsageEstimator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.*;

import static org.apache.lucene.util.RamUsageEstimator.humanSizeOf;

/**
 * @className: ImageUtil
 * @description: 图片压缩工具类
 * @author: xiaofei
 * @create: 2020年01月15日
 */
@Slf4j
public class ImageUtil {

    public static void main(String[] args) {
        //test();
        //watermark();

        //System.out.println("hello wrold");

        File file = FileUtil.file("/Users/noah/Desktop/idea-temp/shiliang.png");

        StopWatch watch = new StopWatch();

        watch.start("USE ImageIO.read");
        minQuery(file);
        watch.stop();

        watch.start("rightGetHeightAndWidth");
        rightGetHeightAndWidth(file);
        watch.stop();

        System.out.println(watch.prettyPrint());
    }

    @SneakyThrows
    public static void rightGetHeightAndWidth(File file) {

        //获取文件流
        InputStream is = FileUtil.getInputStream(file);

        ImageInputStream stream = ImageIO.createImageInputStream(is);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);

        BufferedImage bi = null;

        if (readers.hasNext()) {

            ImageReader reader = readers.next();
            reader.setInput(stream);

            int height = reader.getHeight(0);
            int width = reader.getWidth(0);

            log.info("height:{},width:{}", height, width);
        }
    }

    @SneakyThrows
    public static void minQuery(File file) {

        //获取文件流
        InputStream is = FileUtil.getInputStream(file);

        //读取图片到内存
        BufferedImage bi = ImageIO.read(is);

        //两种方式获取对象占用内存大小
        //RamUsageCountMemory(is, bi);
        //InstCountMemory(is, bi);

        //获取图片的宽高
        int height = bi.getHeight();
        int width = bi.getWidth();
        log.info("height:{},width:{}", height, width);

        //获取颜色主色调（引入第三方jar包）
        //MMCQ.CMap colors = ColorThief.getColorMap(bi, 5, 10, false);
    }

    /**
     * RamUsageEstimator，获取对象占用内存的大小
     *
     * @param is
     * @param bi
     */
    private static void RamUsageCountMemory(InputStream is, BufferedImage bi) {

        String isLucene = RamUsageEstimator.humanSizeOf(is);
        String biLucene = RamUsageEstimator.humanSizeOf(bi);

        log.info("lucene is isLucene:{},biLucene:{}", isLucene, biLucene);
    }

    /**
     * Instrumentation，获取对象占用内存的大小
     *
     * @param is
     * @param bi
     */
    private static void InstCountMemory(InputStream is, BufferedImage bi) {

        ByteBuddyAgent.install();
        Instrumentation inst = ByteBuddyAgent.getInstrumentation();

        long instIs = inst.getObjectSize(is);
        long instBi = inst.getObjectSize(bi);
        log.info("inst is instIs:{},instBi:{}", instIs, instBi);
    }

    public static void watermark() {
        String path = "/Users/noah/Desktop/Snipaste_2022-08-22_16-12-43.png";
        String pathText = "/Users/noah/Desktop/Snipaste_2022-08-22_16-12-43-3.png";
        File file = FileUtil.file(path);
        File fileText = FileUtil.file(pathText);

        File imageFile = file;
        File destFile = fileText;
        String pressText = "hello world";
        Color color = Color.RED;
        Font font = null;
        int x = 2500 / 2;
        int y = 1500 / 2;
        float alpha = 1.0f;

        ImgUtil.pressText(imageFile, destFile, pressText, color, font, x, y, alpha);

    }

    public static void test() {
        long currentTimeMillis = System.currentTimeMillis();

        ByteBuddyAgent.install();
        Instrumentation inst = ByteBuddyAgent.getInstrumentation();

        //Byte[] bytes = new Byte[1024 * 1024];
        //String s = new String(String.valueOf(bytes));
        //final long ee = inst.getObjectSize(s);
        //
        //BufferedImage read = ImgUtil.read("/Users/noah/Desktop/idea-temp/shiliang.png");
        //final long objectSize = inst.getObjectSize(read);

        //ImgUtil.scale(FileUtil.file("/Users/noah/Desktop/idea-temp/shiliang.png"),
        //        FileUtil.file("/Users/noah/Downloads/萌芽熊DIY素材/aa/0004-1.png"), 0.01f);

        File file = FileUtil.file("/Users/noah/Desktop/idea-temp/shiliang.png");
        BufferedInputStream bufferedInputStream = null;
        bufferedInputStream = FileUtil.getInputStream(file);

        palette(bufferedInputStream, 5, 10, false);
        long l = System.currentTimeMillis() - currentTimeMillis;
        System.out.println(l);
    }

    public static List<String> palette(
            InputStream is, Integer colorCount, Integer quality, boolean ignoreWhite) {
        try {

            Long start = System.currentTimeMillis();

            //增加一个判断，如果图片过大。就不判断主色调了。。但是一个很大的图到了node也是一个蛋疼的事情。
            ImageInputStream stream = ImageIO.createImageInputStream(is);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(stream);
            BufferedImage bi = null;
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                reader.setInput(stream);
                int height = reader.getHeight(0);
                int width = reader.getWidth(0);
                //if (height * width > 5000 * 5000||false){
                //    log.info("震惊，此图像素高达{}X{}, 惹不起，暂时不算主色调了", width, height);
                //    return null;
                //}
                bi = reader.read(0);
            } else {
                throw new IllegalStateException();
            }

            BufferedImage bufferedImage = ImageIO.read(is);
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();

            //BufferedImage bi = ImageIO.read(is);
            MMCQ.CMap colors = ColorThief.getColorMap(bi, colorCount, quality, ignoreWhite);
            List<MMCQ.VBox> vBoxes = Optional.ofNullable(colors).map(
                    x -> (List<MMCQ.VBox>) x.vboxes).orElse(Collections.emptyList());
            if (vBoxes.isEmpty()) {
                log.error("获取不到颜色主色调");
                return null;
            }
            List<String> palette = new ArrayList<>(vBoxes.size());
            for (MMCQ.VBox vBox : vBoxes) {
                int[] rgb = vBox.avg(false);
                String color = RGBUtil.rgbHex(rgb);
                palette.add(color);
            }
            return palette;
        } catch (Throwable e) {
            log.error("获取颜色主色调异常:", e);
            return null;
        }
    }

}
