import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class StickerGenerator {

    private static final double NEW_IMAGE_HEIGHT_PERCENTAGE = 1.1;

    private static final String NEW_IMAGE_EXTENSION = "png";

    private String newFilePath;

    private String newFileName;

    private String subtitleText;

    private BufferedImage originalImage;

    public String getNewFilePath() {
        return newFilePath;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public String getSubtitleText() {
        return subtitleText;
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

    public void setSubtitleText(String subtitleText) {
        this.subtitleText = subtitleText;
    }

    private void setOriginalImage(BufferedImage originalImage) {
        this.originalImage = originalImage;
    }

    public StickerGenerator(URL fileUrl, String newFilePath, String newFileName, String subtitleText) {
        try {
            setNewFilePath(newFilePath);
            setNewFileName(newFileName.replace(" ", "_"));
            setOriginalImage(ImageIO.read(fileUrl));
            setSubtitleText(subtitleText);
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
        var newImage = new BufferedImage(
            getOriginalImage().getWidth(),
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
        Font font = new Font(Font.SERIF, Font.BOLD, 60);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.YELLOW);
        var dimensions = calculateCentralizedSubtitle(graphics2D, newImage, font);
        graphics2D.drawString(getSubtitleText(), dimensions[0], dimensions[1]);
    }

    private int[] calculateCentralizedSubtitle(Graphics2D graphics2D, BufferedImage newImage, Font font) {
        FontMetrics metrics = graphics2D.getFontMetrics(font);
        Rectangle rectangle = new Rectangle(
            0,
            getOriginalImage().getHeight(),
            getOriginalImage().getWidth(),
            newImage.getHeight() - getOriginalImage().getHeight());
        int x = rectangle.x + (rectangle.width - metrics.stringWidth(getSubtitleText())) / 2;
        int y = rectangle.y + ((rectangle.height - metrics.getHeight()) / 2) + metrics.getAscent();
        return new int[] {x, y};
    }

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
