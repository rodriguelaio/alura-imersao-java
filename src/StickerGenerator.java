import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class StickerGenerator {

    private static final double NEW_IMAGE_HEIGHT_PERCENTAGE = 1.2;

    private static final String NEW_IMAGE_EXTENSION = "png";

    private String newFilePath;

    private String newFileName;

    private BufferedImage originalImage;

    public String getNewFilePath() {
        return newFilePath;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public BufferedImage getOriginalImage() {
        return originalImage;
    }

    private void setNewFilePath(String newFilePath) {
        this.newFilePath = newFilePath;
    }

    private void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    private void setOriginalImage(BufferedImage originalImage) {
        this.originalImage = originalImage;
    }

    public StickerGenerator(URL fileUrl, String newFilePath, String newFileName) {
        try {
            setNewFilePath(newFilePath);
            setNewFileName(newFileName.replace(" ", "_"));
            setOriginalImage(ImageIO.read(fileUrl));
        } catch (Exception e) {
            System.out.println("StickerGenerator Exception: ".concat(e.getMessage()));
        }
    }

    public StickerGenerator(String filePath, String newFilePath, String newFileName) {
        setNewFilePath(newFilePath);
        setNewFileName(newFileName.replace(" ", "_"));
        prepareFile(new File(filePath));
    }

    public StickerGenerator(File filePath, String newFilePath, String newFileName) {
        setNewFilePath(newFilePath);
        setNewFileName(newFileName.replace(" ", "_"));
        prepareFile(filePath);
    }

    private void prepareFile(File filePath) {
        try {
            validateFilePath(filePath);
            setOriginalImage(ImageIO.read(filePath));
        } catch (Exception e) {
            System.out.println("StickerGenerator Exception: ".concat(e.getMessage()));
        }
    }

    public void createSticker() {
        var newImage = new BufferedImage(getOriginalImage().getWidth(),
            calculateNewImageHeight(getOriginalImage().getHeight()),
            BufferedImage.TRANSLUCENT);
        drawNewImage(newImage);
    }

    private void drawNewImage(BufferedImage newImage) {
        Graphics2D graphics2D = (Graphics2D) newImage.getGraphics();
        graphics2D.drawImage(getOriginalImage(), 0, 0, null);
        printSubtitle(graphics2D, newImage);
        createNewImageFile(newImage);
    }

    private void printSubtitle(Graphics2D graphics2D, BufferedImage newImage) {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 60);
        FontMetrics metrics = graphics2D.getFontMetrics(font);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString("guelaio",
            newImage.getWidth() / 2,
            Double.valueOf(getOriginalImage().getHeight() * 1.1).intValue());
    }

//    private void seila(Graphics2D graphics2D) {
//        FontRenderContext context = graphics2D.getFontRenderContext();
//        Font font = new Font("Arial", Font.BOLD, 48);
//        TextLayout txt = new TextLayout("guelaio", font, context);
//    }
//
//    private void drawCenteredString(Graphics2D g, String text, Rectangle rect, Font font) {
//        // Get the FontMetrics
//        FontMetrics metrics = g.getFontMetrics(font);
//        // Determine the X coordinate for the text
//        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
//        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
//        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
//        // Set the font
//        g.setFont(font);
//        // Draw the String
//        g.drawString(text, x, y);
//    }

    private void createNewImageFile(BufferedImage newImage) {
        try {
            File newImageFilePath =
                new File(getNewFilePath().concat(getNewFileName()).concat(".").concat(NEW_IMAGE_EXTENSION));

            validateFilePath(newImageFilePath.getParentFile());

            ImageIO.write(newImage, NEW_IMAGE_EXTENSION, newImageFilePath);
        } catch (Exception e) {
            System.out.println("StickerGenerator Exception: ".concat(e.getMessage()));
        }
    }

    private void validateFilePath(File filePath) {
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
    }

    private int calculateNewImageHeight(int oldBufferedImageHeight) {
        return Double.valueOf(oldBufferedImageHeight * NEW_IMAGE_HEIGHT_PERCENTAGE).intValue();
    }
}
