package tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *@ClassName ImgTool
 *@Description 原生java图片工具类
 *@Author rael
 *@Date 4/21/2023 11:53 AM
 */
public class ImgTool {
    /**
     * 压缩成指定宽高
     *
     * @param img
     * @param width
     * @param height
     * @param out
     * @throws IOException
     */
    public static void compress2WH(File img, int width, int height,
                                   OutputStream out) throws IOException {
        BufferedImage bi = ImageIO.read(img);
        double srcWidth = bi.getWidth(); // 源图宽度
        double srcHeight = bi.getHeight(); // 源图高度

        double scale = 1;

        if (width > 0) {
            scale = width / srcWidth;
        }
        if (height > 0) {
            scale = height / srcHeight;
        }
        if (width > 0 && height > 0) {
            scale = Math.min(height / srcHeight, width / srcWidth);
        }

        compress(img, (int) (srcWidth * scale), (int) (srcHeight * scale), out);

    }

    /**
     * 按照固定宽高原图压缩
     * @param img
     * @param width
     * @param height
     * @param out
     * @throws IOException
     */
    public static void compress(File img, int width, int height,
                                OutputStream out) throws IOException {
        BufferedImage BI = ImageIO.read(img);
        //
        Image image = BI.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        BufferedImage tag = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.setColor(Color.RED);
        g.drawImage(image, 0, 0, null); // 绘制处理后的图
        g.dispose();
        ImageIO.write(tag, "JPEG", out);
    }

    /**
     * 裁剪指定宽高
     *
     * @param srcImageFile
     * @param destWidth
     * @param destHeight
     * @param out
     */
    public static void cutByWH(File srcImageFile, int destWidth,
                               int destHeight, OutputStream out) {
        cutByWHFromXY(srcImageFile, 0, 0, destWidth, destHeight, out);
    }

    /**
     * 从指定位置裁剪指定宽高
     * @param srcImageFile
     * @param x
     * @param y
     * @param destWidth
     * @param destHeight
     * @param out
     */
    public static void cutByWHFromXY(File srcImageFile, int x, int y, int destWidth,
                                     int destHeight, OutputStream out) {
        try {
            Image img;
            ImageFilter cropFilter;
            // 读取源图像
            BufferedImage bi = ImageIO.read(srcImageFile);
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度

            if (srcWidth >= destWidth && srcHeight >= destHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight,
                        Image.SCALE_DEFAULT);

                cropFilter = new CropImageFilter(x, y, destWidth, destHeight);
                img = Toolkit.getDefaultToolkit().createImage(
                        new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(destWidth, destHeight,
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, null); // 绘制截取后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "JPEG", out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        File img = new File("C:\\Users\\mylif\\Desktop\\paixiao.jpg");
        FileOutputStream outputPath = new FileOutputStream("C:\\Users\\mylif\\Desktop\\paoxiao_.jpg");
        //ImgTool.compress(img, 100, 100, outputPath);
        //ImgTool.cutByWH(img, 230, 200, outputPath);
        ImgTool.compress2WH(img, 1000, 1000, outputPath);

    }
}