import main.java.br.com.guelaio.alurastickers.enums.URLS;
import main.java.br.com.guelaio.alurastickers.stickers.Sticker;

public class App {

    public static void main(String[] args) {
        URLS url = URLS.MOCKED_NASA_APOD;
        Sticker sticker = url.getSticker();
        sticker.generateSticker(url);
    }
}
