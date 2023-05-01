package Utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import api.data.ImageCroppingData;
import api.data.ImageResizingLimits;
import api.data.ResizingMethod;
import impl.ImageResizerServiceImpl;

public class ImageResizer {

    private final BufferedImage _imageToResize;
    private final ResizingMethod _resizingMethod;
    private final ImageResizingLimits _limits;
    private static final Logger LOGGER = Logger.getLogger(ImageResizer.class.getName());

    public ImageResizer(BufferedImage imageToResize, ResizingMethod method, ImageResizingLimits limits) {
        _imageToResize = imageToResize;
        _resizingMethod = method;
        _limits = limits;
        initializeLogger();
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
        
        LOGGER.fine("getResizedImage");
        
        if (isScaleImage()) {
            LOGGER.fine("isScaleImage");
            result = scaleImage();
        }
        
        if (isCropImage()) {
            LOGGER.fine("isCropImage");
            return cropImage(result);
        }

        return result;
    }

    private BufferedImage scaleImage() {
        ImageResizingLimits adjustedLimits = calculateScalingLimits();
        
        BufferedImage result = new BufferedImage(adjustedLimits.getMaxWidth(), adjustedLimits.getMaxHeight(),
                _imageToResize.getType());
        
        Graphics2D g2d = result.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(_imageToResize, 0, 0, adjustedLimits.getMaxWidth(), adjustedLimits.getMaxHeight(), null);
        g2d.dispose();
        return result;
    }

    private BufferedImage cropImage(BufferedImage image) {
        if(!canCropImage(image)) {
            LOGGER.warning("Error cropping image, wrong parameters.");
            return image;
        }
        
        ImageCroppingData croppingData = calculateCroppingData(image);
        
        LOGGER.fine("croppingData = " + croppingData);
        
        BufferedImage croppedImage = image.getSubimage(croppingData.getStartX(), croppingData.getStartY(),
                croppingData.getWidth(), croppingData.getHeight());

        return croppedImage;
    }
    
    private ImageResizingLimits calculateScalingLimits() {
        ImageResizingLimits adjustedLimits;
        if (_imageToResize.getHeight() < _imageToResize.getWidth()) {
            double scale = (double) _limits.getMaxHeight() / _imageToResize.getHeight();
            int scaledWidth = (int) Math.round(_imageToResize.getWidth() * scale);
            adjustedLimits = new ImageResizingLimits(scaledWidth, _limits.getMaxHeight());
        } else {
            double scale = (double) _limits.getMaxWidth() / _imageToResize.getWidth();
            int scaledHeight = (int) Math.round(_imageToResize.getHeight() * scale);
            adjustedLimits = new ImageResizingLimits(_limits.getMaxWidth(), scaledHeight);
        }
        return adjustedLimits;
    }

    private boolean canCropImage(BufferedImage image) {
        return image.getHeight() >= _limits.getMaxHeight() && image.getWidth() >= _limits.getMaxWidth();
    }

    private ImageCroppingData calculateCroppingData(BufferedImage image) {
        int startX = 0;
        int startY = 0;
        LOGGER.fine("_limits = " + _limits);
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

    
    private void initializeLogger() {
        try {
            String logName = ImageResizerServiceImpl.class.getName()+".log";
            FileHandler fileHandler = new FileHandler(Paths.get(logName).toAbsolutePath().toString(), true);
            fileHandler.setLevel(Level.ALL);
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
