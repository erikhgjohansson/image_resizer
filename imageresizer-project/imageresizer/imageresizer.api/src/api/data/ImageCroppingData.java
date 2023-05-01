package api.data;

public class ImageCroppingData {

    private final int _startX;
    private final int _startY;
    private final int _width;
    private final int _height;

    public ImageCroppingData(int startX, int startY, int width, int height) {
        _startX = startX;
        _startY = startY;
        _width = width;
        _height = height;
        
    }

    public int getStartX() {
        return _startX;
    }

    public int getStartY() {
        return _startY;
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    @Override
    public String toString() {
        return "ImageCroppingData [_startX=" + _startX + ", _startY=" + _startY + ", _width=" + _width + ", _height="
                + _height + "]";
    }
    
}
