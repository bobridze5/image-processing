package imagecpu;

public class Main {
    public static void main(String[] args) {
        ImageLoader loader = new ImageLoader(128, "images/pic1.png", "images/пик2.png");
        byte[][] images = loader.getBinaryImages();

    }
}
