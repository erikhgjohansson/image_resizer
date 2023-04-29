package api.data;

public class ImageScalingLimits {
    
    private final int _maxWidth;
    private final int _maxHeight;

    public ImageScalingLimits(int maxWidth, int maxHeight) {
        _maxWidth = maxWidth;
        _maxHeight = maxHeight;
    }

    public int get_maxWidth() {
        return _maxWidth;
    }

    public int get_maxHeight() {
        return _maxHeight;
    }

    @Override
    public String toString() {
        return "ImageScalingLimits [_maxWidth=" + _maxWidth + ", _maxHeight=" + _maxHeight + "]";
    }
}
