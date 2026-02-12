package imagecpu;

public class ImageData {
    private final byte[][] red;
    private final byte[][] green;
    private final byte[][] blue;
    private final int width;
    private final int height;
    private final boolean isColor;

    public ImageData(byte[][] pixels, int width, int height) {
        this.red = pixels;
        this.green = null;
        this.blue = null;
        this.width = width;
        this.height = height;
        this.isColor = false;
    }

    public ImageData(byte[][] red, byte[][] green, byte[][] blue, int width, int height) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.width = width;
        this.height = height;
        this.isColor = true;
    }

    public boolean isColor() {
        return isColor;
    }

    public byte[][] getRed() {
        return red;
    }

    public byte[][] getGreen() {
        return green;
    }

    public byte[][] getBlue() {
        return blue;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
