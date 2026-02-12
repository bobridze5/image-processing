package imagecpu;

public class Main {
    public static void main(String[] args) {
        String[] images = {
                "images/1024x768.jpg",
                "images/1280x960.jpg",
                "images/2048x1536.jpg",
                "img.jpg"
        };

        ImageAnalyzer analyzer = ImageAnalyzer.builder()
                .setBinarizer(new ImageBinarizer(160))
                .setFormatter(new ImageFormatter(1, 16))
                .setOutputPath("images/results")
                .setTestRuns(3)
                .build();

        analyzer.analyze(images);
    }
}
