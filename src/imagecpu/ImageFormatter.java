package imagecpu;

import java.util.Arrays;

public class ImageFormatter {
    private final int step;

    public ImageFormatter() {
        this(1);
    }

    public ImageFormatter(int step) {
        validateStep(step);
        this.step = step;
    }

    public void dilate(byte[] binaryImage, int width, int height) {
        byte[] source = binaryImage.clone();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (source[y * width + x] == 1) {
                    for (int dy = -step; dy <= step; dy++) {
                        for (int dx = -step; dx <= step; dx++) {
                            int nx = x + dx;
                            int ny = y + dy;

                            if (nx < 0 || nx >= width || ny < 0 || ny >= height) {
                                continue;
                            }

                            binaryImage[ny * width + nx] = 1;

                        }
                    }
                }

            }
        }

//        return binaryImage;
    }


    private void validateStep(int step) {
        if (step < 1 || step > 3) {
            throw new IllegalArgumentException("Шаг должен быть 1, 2 или  3");
        }
    }


}
