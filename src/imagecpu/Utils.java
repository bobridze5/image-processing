package imagecpu;

public final class Utils {
    public static byte[][] deepCopy(byte[][] source) {
        if (source == null) return null;

        int height = source.length;
        byte[][] result = new byte[height][];
        for (int i = 0; i < height; i++) {
            result[i] = source[i].clone();
        }

        return result;
    }

    public static ImageData deepCopy(ImageData data) {
        int h = data.getHeight();
        int w = data.getWidth();

        byte[][] rCopy = deepCopy(data.getRed());

        if (data.isColor()) {
            byte[][] gCopy = deepCopy(data.getGreen());
            byte[][] bCopy = deepCopy(data.getBlue());
            return new ImageData(rCopy, gCopy, bCopy, w, h);
        } else {
            return new ImageData(rCopy, w, h);
        }
    }
}
