package imagecpu;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ImageLoader loader = new ImageLoader(128, "images/pic1.png", "images/пик2.png");
        List<BinaryImageData> imageData = loader.getImageData();
        ImageFormatter imageFormatter = new ImageFormatter();

        for (int i = 0; i < imageData.size(); i++) {
            BinaryImageData data = imageData.get(i);
            byte[] newImage = imageFormatter.dilate(data.pixels(), data.height(), data.width());

        }


    }
}
