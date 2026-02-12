package imagecpu;

public class Main {
    public static void main(String[] args) {
        String[] images = {
//                "img.jpg"
                "images/1024x768.jpg",
                "images/1280x960.jpg",
                "images/2048x1536.jpg",
//                "images/pic1.png",
//                "images/pic2.png",
//                "images/pic3.jpg",
//                "images/pic4.jpg",
//                "images/pic5.jpg",
                "images/5464x6830.jpg",
                "images/8736x4896.jpg",
                "images/9408x5376.jpg"
        };

        ImageAnalyzer analyzer = ImageAnalyzer.builder()
//                .setFilter(new InversionFilter())
                .setFilter(new BlackWhiteFilter(100))
                .setFormatter(new ImageFormatter())
//                .setOperationDilate(1)
                .setOperationSharpness()
                .setOutputPath("images/results")
                .setTestRuns(1)
                .build();

        analyzer.analyze(images);
    }
}
