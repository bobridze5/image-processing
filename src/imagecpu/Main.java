package imagecpu;

public class Main {
    public static void main(String[] args) {
        String[] images = {
                "images/1024x768.jpg",
                "images/1280x960.jpg",
                "images/2048x1536.jpg",
                "images/5464x6830.jpg",
                "images/8736x4896.jpg",
                "images/9408x5376.jpg"
        };

        ImageAnalyzer analyzer = ImageAnalyzer.builder()
                .setFilter(new InversionFilter())
                .setFormatter(new ImageFormatter(6))
                .setOperationDilate(1)
                .setOutputPath("images/results")
                .setTestRuns(1)
                .build();

        analyzer.analyze(images);
    }
}
