import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class StickerGenerator {

    private static final double NEW_PORTRAIT_IMAGE_HEIGHT_PERCENTAGE = 1.1;

    private static final double NEW_LANDSCAPE_IMAGE_HEIGHT_PERCENTAGE = 1.3;

    private static final String NEW_IMAGE_EXTENSION = "png";

    public static void generateSticker(URL fileUrl, String newFilePath, String newFileName, String subtitleText) {
        try {
            createSticker(ImageIO.read(fileUrl), newFilePath, newFileName.replace(" ", "_"), subtitleText);
        } catch (Exception e) {
            System.out.println("StickerGenerator Exception: ".concat(e.getMessage()));
        }
    }

    private static void createSticker(BufferedImage originalImage,
                                      String newFilePath,
                                      String newFileName,
                                      String subtitleText) {

        var newImage = new BufferedImage(originalImage.getWidth(),
            calculateNewImageHeight(originalImage.getHeight(), originalImage.getWidth()),
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
        var font = scaleFontToFit(originalImage.getHeight(),
            originalImage.getWidth(),
            graphics2D,
            new Font(Font.SERIF, Font.BOLD, 400),
            subtitleText);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.YELLOW);
        var dimensions = calculateCentralizedSubtitle(originalImage.getHeight(),
            originalImage.getWidth(),
            graphics2D.getFontMetrics(font),
            subtitleText);
        graphics2D.drawString(subtitleText, dimensions[0], dimensions[1]);
    }

    public static Font scaleFontToFit(int height,
                                      int width,
                                      Graphics graphics,
                                      Font originalFont,
                                      String subtitleText) {
        int referenceValue = Math.min(width, height);
        float fontSize = originalFont.getSize();
        float fontWidth = graphics.getFontMetrics(originalFont).stringWidth(subtitleText);
        if (fontWidth <= referenceValue) {
            return originalFont;
        }
        fontSize = ((float) referenceValue / fontWidth) * fontSize;
        return originalFont.deriveFont(fontSize);
    }

    private static int[] calculateCentralizedSubtitle(int originalImageHight,
                                                      int originalImageWidth,
                                                      FontMetrics metrics,
                                                      String subtitleText) {
        int x = 0;
        int y = 0;
        Rectangle rectangle = new Rectangle(0,
            originalImageHight,
            originalImageWidth,
            Double.valueOf(originalImageHight * (getNewImageHight(originalImageHight, originalImageWidth) - 1))
                .intValue());
        x = rectangle.x +
            calculateRectangleNewX(originalImageHight, originalImageWidth, rectangle, metrics, subtitleText);
        y = rectangle.y +
            calculateRectangleNewY(originalImageHight, originalImageWidth, rectangle, metrics, subtitleText);
        return new int[] {x, y};
    }

    private static int calculateRectangleNewX(int originalImageHight,
                                              int originalImageWidth,
                                              Rectangle rectangle,
                                              FontMetrics metrics,
                                              String subtitleText) {
        if (originalImageHight > originalImageWidth) {
            return (rectangle.width - metrics.stringWidth(subtitleText)) / 2;
        }
        return ((rectangle.height - metrics.getHeight()) / 2) + metrics.getAscent();
    }

    private static int calculateRectangleNewY(int originalImageHight,
                                              int originalImageWidth,
                                              Rectangle rectangle,
                                              FontMetrics metrics,
                                              String subtitleText) {
        if (originalImageHight > originalImageWidth) {
            return ((rectangle.height - metrics.getHeight()) / 2) + metrics.getAscent();
        }
        return (rectangle.width - metrics.stringWidth(subtitleText)) / 2;
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

    private static int calculateNewImageHeight(int originalImageHeight, int originalImageWidth) {
        return Double.valueOf(originalImageHeight * getNewImageHight(originalImageHeight, originalImageWidth))
            .intValue();
    }

    private static double getNewImageHight(int originalImageHeight, int originalImageWidth) {
        return originalImageHeight < originalImageWidth ?
            NEW_LANDSCAPE_IMAGE_HEIGHT_PERCENTAGE :
            NEW_PORTRAIT_IMAGE_HEIGHT_PERCENTAGE;
    }
}
