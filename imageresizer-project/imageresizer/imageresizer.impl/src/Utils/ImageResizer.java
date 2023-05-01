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
        BufferedImage result = _imageToResize;
        
        if (isScaleImage()) {
            result = scaleImage();
            
            
            System.out.println(result);
        }
        
        if (isCropImage()) {
            return cropImage(result);
        }

        return result;
    }

    private BufferedImage scaleImage() {
        BufferedImage result = new BufferedImage(_limits.getMaxWidth(), _limits.getMaxHeight(),
                _imageToResize.getType());
        
        Graphics2D g2d = result.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(_imageToResize, 0, 0, _limits.getMaxWidth(), _limits.getMaxHeight(), null);
        g2d.dispose();
        return result;
    }

    private BufferedImage cropImage(BufferedImage image) {
        ImageCroppingData croppingData = calculateCroppingData(image);
        BufferedImage croppedImage = image.getSubimage(croppingData.getStartX(), croppingData.getStartY(),
                croppingData.getWidth(), croppingData.getHeight());

        return croppedImage;
    }

    private ImageCroppingData calculateCroppingData(BufferedImage image) {
        int startX = 0;
        int startY = 0;
        if (needToCropWidth(image.getWidth())) {
            startX = calculateCroppingStartCoord(image.getWidth(), _limits.getMaxHeight());
        } if (needToCropHeight(image.getHeight())) {
            startY = calculateCroppingStartCoord(image.getHeight(), _limits.getMaxWidth());
        }
        return new ImageCroppingData(startX, startY, _limits.getMaxWidth(), _limits.getMaxHeight());
    }


    private int calculateCroppingStartCoord(int totalSize, int wantedSize) {
        return (int) Math.round((totalSize - wantedSize) * 0.5);
    }

    private boolean needToCropHeight(int height) {
        return height > _limits.getMaxHeight();
    }
    
    private boolean needToCropWidth(int width) {
        return width > _limits.getMaxWidth();
    }

    private boolean isScaleImage() {
        return _resizingMethod.equals(ResizingMethod.SCALE) || _resizingMethod.equals(ResizingMethod.SCALE_AND_CROP);
    }

    private boolean isCropImage() {
        return _resizingMethod.equals(ResizingMethod.CROP) || _resizingMethod.equals(ResizingMethod.SCALE_AND_CROP);
    }

}
