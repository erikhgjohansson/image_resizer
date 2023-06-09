package api.data;

public class ImageResizingLimits {
    
    private final int _maxWidth;
    private final int _maxHeight;

    public ImageResizingLimits(int maxWidth, int maxHeight) {
        _maxWidth = maxWidth;
        _maxHeight = maxHeight;
    }

    public int getMaxWidth() {
        return _maxWidth;
    }

    public int getMaxHeight() {
        return _maxHeight;
    }

    @Override
    public String toString() {
        return "ImageResizingLimits [_maxWidth=" + _maxWidth + ", _maxHeight=" + _maxHeight + "]";
    }
}
