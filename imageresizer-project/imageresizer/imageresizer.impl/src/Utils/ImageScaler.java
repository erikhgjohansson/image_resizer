package Utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import api.data.ImageCroppingData;
import api.data.ImageScalingLimits;
import api.data.ScaleMethod;

public class ImageScaler {

    private final BufferedImage _imageToScale;
    private final ScaleMethod _scaleMethod;
    private final ImageScalingLimits _limits;

    public ImageScaler(BufferedImage imageToScale, ScaleMethod method, ImageScalingLimits limits) {
        _imageToScale = imageToScale;
        _scaleMethod = method;
        _limits = calculateScalingAdjustedLimits(limits);
    }

    private ImageScalingLimits calculateScalingAdjustedLimits(ImageScalingLimits limits) {
        ImageScalingLimits adjustedLimits;
        if (_imageToScale.getHeight() < _imageToScale.getWidth()) {
            double scale = (double) limits.getMaxHeight() / _imageToScale.getHeight();
            int scaledWidth = (int) Math.round(_imageToScale.getWidth() * scale);
            adjustedLimits = new ImageScalingLimits(scaledWidth, limits.getMaxHeight());
        } else {
            double scale = (double) limits.getMaxWidth() / _imageToScale.getWidth();
            int scaledHeight = (int) Math.round(_imageToScale.getHeight() * scale);
            adjustedLimits = new ImageScalingLimits(limits.getMaxWidth(), scaledHeight);
        }
        return adjustedLimits;
    }

    public BufferedImage getImageToScale() {
        return _imageToScale;
    }

    public ScaleMethod getScaleMethod() {
        return _scaleMethod;
    }

    public ImageScalingLimits getLimits() {
        return _limits;
    }

    public BufferedImage getScaledImage() {
        BufferedImage result = new BufferedImage(_limits.getMaxWidth(), _limits.getMaxHeight(),
                _imageToScale.getType());

        Graphics2D g2d = result.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(_imageToScale, 0, 0, _limits.getMaxWidth(), _limits.getMaxHeight(), null);
        g2d.dispose();

        if (isCropImage()) {
            return cropImage(result);
        }

        return result;
    }

    private BufferedImage cropImage(BufferedImage image) {
        ImageCroppingData croppingData;
        if (needToCropWidth()) {
            int startX = calculateCroppingStartCoord(image.getWidth(), _limits.getMaxHeight());
            int startY = 0;
            croppingData = new ImageCroppingData(startX, startY, _limits.getMaxHeight(), _limits.getMaxHeight());
        } else {
            int startY = calculateCroppingStartCoord(image.getHeight(), _limits.getMaxWidth());
            int startX = 0;
            croppingData = new ImageCroppingData(startX, startY, _limits.getMaxWidth(), _limits.getMaxWidth());
        }
        BufferedImage croppedImage = image.getSubimage(croppingData.getStartX(), croppingData.getStartY(),
                croppingData.getWidth(), croppingData.getHeight());

        return croppedImage;
    }

    private int calculateCroppingStartCoord(int totalSize, int wantedSize) {
        return (int) Math.round((totalSize - wantedSize) * 0.5);
    }

    private boolean needToCropWidth() {
        return _limits.getMaxHeight() < _limits.getMaxWidth();
    }

    private boolean isCropImage() {
        return _limits.getMaxHeight() != _limits.getMaxWidth() && !_scaleMethod.equals(ScaleMethod.SCALE);
    }

}
