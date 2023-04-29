package Utils;

import java.awt.image.BufferedImage;

import api.data.ImageScalingLimits;
import api.data.ScaleMethod;

public class ImageScaler {
    
    private final BufferedImage _imageToScale;
    private final ScaleMethod _scaleMethod;
    private final ImageScalingLimits _limits;

    public ImageScaler(BufferedImage imageToScale, ScaleMethod method, ImageScalingLimits limits) {
        _imageToScale = imageToScale;
        _scaleMethod = method;
        _limits = limits;
    }

    public BufferedImage getScaledImage() {

        return _imageToScale;
    }

    
}
