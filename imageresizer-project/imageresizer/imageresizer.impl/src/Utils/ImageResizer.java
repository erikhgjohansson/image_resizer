package Utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import api.data.ImageCroppingData;
import api.data.ImageResizingLimits;
import api.data.ResizingMethod;

public class ImageResizer {

    private final BufferedImage _imageToResize;
    private final ResizingMethod _resizingMethod;
    private final ImageResizingLimits _limits;

    public ImageResizer(BufferedImage imageToResize, ResizingMethod method, ImageResizingLimits limits) {
        _imageToResize = imageToResize;
        _resizingMethod = method;
        _limits = calculateResizingAdjustedLimits(limits);
    }

    private ImageResizingLimits calculateResizingAdjustedLimits(ImageResizingLimits limits) {
        ImageResizingLimits adjustedLimits;
        if (_imageToResize.getHeight() < _imageToResize.getWidth()) {
            double scale = (double) limits.getMaxHeight() / _imageToResize.getHeight();
            int scaledWidth = (int) Math.round(_imageToResize.getWidth() * scale);
            adjustedLimits = new ImageResizingLimits(scaledWidth, limits.getMaxHeight());
        } else {
            double scale = (double) limits.getMaxWidth() / _imageToResize.getWidth();
            int scaledHeight = (int) Math.round(_imageToResize.getHeight() * scale);
            adjustedLimits = new ImageResizingLimits(limits.getMaxWidth(), scaledHeight);
        }
        return adjustedLimits;
    }

    public BufferedImage getImageToResize() {
        return _imageToResize;
    }

    public ResizingMethod getResizingMethod() {
        return _resizingMethod;
    }

    public ImageResizingLimits getLimits() {
        return _limits;
    }

    public BufferedImage getResizedImage() {
        BufferedImage result = new BufferedImage(_limits.getMaxWidth(), _limits.getMaxHeight(),
                _imageToResize.getType());

        Graphics2D g2d = result.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(_imageToResize, 0, 0, _limits.getMaxWidth(), _limits.getMaxHeight(), null);
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
        return _limits.getMaxHeight() != _limits.getMaxWidth() && !_resizingMethod.equals(ResizingMethod.SCALE);
    }

}
