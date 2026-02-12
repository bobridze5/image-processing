package imagecpu;

public class InversionFilter extends BaseFilter {
    @Override
    protected byte handleIntensity(int intensity) {
        return (byte) (255 - intensity);
    }
}
