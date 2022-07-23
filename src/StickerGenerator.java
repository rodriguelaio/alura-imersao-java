import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class StickerGenerator {

    private static final double NEW_IMAGE_HEIGHT_PERCENTAGE = 1.1;

    private static final String NEW_IMAGE_EXTENSION = "png";

    public static void generateSticker(URL fileUrl, String newFilePath, String newFileName, String subtitleText) {
        try {
            createSticker(ImageIO.read(fileUrl), newFilePath, newFileName.replace(" ", "_"), subtitleText);
        } catch (Exception e) {
            System.out.println("StickerGenerator Exception: ".concat(e.getMessage()));
        }
    }

    public static void generateSticker(String filePath, String newFilePath, String newFileName, String subtitleText) {
        BufferedImage bufferedImage;
        if ((bufferedImage = generateBufferedImageByFile(new File(filePath))) == null) {
            return;
        }
        createSticker(bufferedImage, newFilePath, newFileName.replace(" ", "_"), subtitleText);
    }

    public static void generateSticker(File file, String newFilePath, String newFileName, String subtitleText) {
        BufferedImage bufferedImage;
        if ((bufferedImage = generateBufferedImageByFile(file)) == null) {
            return;
        }
        createSticker(bufferedImage, newFilePath, newFileName.replace(" ", "_"), subtitleText);
    }

    private static BufferedImage generateBufferedImageByFile(File filePath) {
        try {
            validateFilePath(filePath);
            return ImageIO.read(filePath);
        } catch (Exception e) {
            System.out.println("generateBufferedImageByFile Exception: ".concat(e.getMessage()));
            return null;
        }
    }

    private static void createSticker(BufferedImage originalImage,
                                      String newFilePath,
                                      String newFileName,
                                      String subtitleText) {

        var newImage = new BufferedImage(originalImage.getWidth(),
            calculateNewImageHeight(originalImage.getHeight()),
            BufferedImage.TRANSLUCENT);
        drawNewImage(originalImage, newImage, newFilePath, newFileName, subtitleText);
    }

    private static void drawNewImage(BufferedImage originalImage,
                                     BufferedImage newImage,
                                     String newFilePath,
                                     String newFileName,
                                     String subtitleText) {

        var graphics2D = (Graphics2D) newImage.getGraphics();
        graphics2D.drawImage(originalImage, 0, 0, null);
        printSubtitle(originalImage, graphics2D, subtitleText);
        createNewImageFile(newImage, newFilePath, newFileName);
    }

    private static void printSubtitle(BufferedImage originalImage, Graphics2D graphics2D, String subtitleText) {
        var font =
            scaleFontToFit(originalImage.getWidth(), graphics2D, new Font(Font.SERIF, Font.BOLD, 400), subtitleText);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.YELLOW);
        var dimensions = calculateCentralizedSubtitle(originalImage, graphics2D.getFontMetrics(font), subtitleText);
        graphics2D.drawString(subtitleText, dimensions[0], dimensions[1]);
    }

    public static Font scaleFontToFit(int width, Graphics graphics, Font originalFont, String subtitleText) {
        float fontSize = originalFont.getSize();
        float fontWidth = graphics.getFontMetrics(originalFont).stringWidth(subtitleText);
        if (fontWidth <= width) {
            return originalFont;
        }
        fontSize = ((float) width / fontWidth) * fontSize;
        return originalFont.deriveFont(fontSize);
    }

    private static int[] calculateCentralizedSubtitle(BufferedImage originalImage,
                                                      FontMetrics metrics,
                                                      String subtitleText) {
        int x = 0;
        int y = 0;
        Rectangle rectangle = new Rectangle(0,
            originalImage.getHeight(),
            originalImage.getWidth(),
            Double.valueOf(originalImage.getHeight() * (NEW_IMAGE_HEIGHT_PERCENTAGE - 1)).intValue());
        x = rectangle.x + (rectangle.width - metrics.stringWidth(subtitleText)) / 2;
        y = rectangle.y + ((rectangle.height - metrics.getHeight()) / 2) + metrics.getAscent();
        return new int[] {x, y};
    }

    private static void createNewImageFile(BufferedImage newImage, String newFilePath, String newFileName) {
        try {
            File newImageFilePath = new File(newFilePath.concat(newFileName).concat(".").concat(NEW_IMAGE_EXTENSION));
            validateFilePath(newImageFilePath.getParentFile());
            ImageIO.write(newImage, NEW_IMAGE_EXTENSION, newImageFilePath);
        } catch (Exception e) {
            System.out.println("createNewImageFile Exception: ".concat(e.getMessage()));
        }
    }

    private static void validateFilePath(File filePath) {
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
    }

    private static int calculateNewImageHeight(int oldBufferedImageHeight) {
        return Double.valueOf(oldBufferedImageHeight * NEW_IMAGE_HEIGHT_PERCENTAGE).intValue();
    }
}
