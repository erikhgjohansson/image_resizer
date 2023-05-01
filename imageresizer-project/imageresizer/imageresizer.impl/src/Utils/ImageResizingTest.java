//package Utils;
//
//import java.awt.image.BufferedImage;
//
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import api.data.ImageResizingLimits;
//import api.data.ResizingMethod;
//
//public class ImageResizingTest {
//
//    @Test
//    public void testScaleImageUp() {
//
//        BufferedImage imageToScale = generateTestImageEqualWidthAndHeight();
//        ResizingMethod method = ResizingMethod.SCALE;
//        int wantedMaxWidth = 10;
//        int wantedMaxHeight = 10;
//        ImageResizingLimits limits = new ImageResizingLimits(wantedMaxWidth, wantedMaxHeight);
//        ImageResizer imageScaler = new ImageResizer(imageToScale, method, limits);
//        BufferedImage scaledImage = imageScaler.getResizedImage();        
//
//        Assert.assertTrue(scaledImage.getWidth() == wantedMaxWidth);
//        Assert.assertTrue(scaledImage.getHeight() == wantedMaxHeight);
//    }
//
//    @Test
//    public void testScaleImageDown() {
//
//        BufferedImage imageToScale = generateTestImageEqualWidthAndHeight();
//        ResizingMethod method = ResizingMethod.SCALE;
//        int wantedMaxWidth = 5;
//        int wantedMaxHeight = 5;
//        ImageResizingLimits limits = new ImageResizingLimits(wantedMaxWidth, wantedMaxHeight);
//        ImageResizer imageScaler = new ImageResizer(imageToScale, method, limits);
//        BufferedImage scaledImage = imageScaler.getResizedImage();        
//
//        Assert.assertTrue(scaledImage.getWidth() == wantedMaxWidth);
//        Assert.assertTrue(scaledImage.getHeight() == wantedMaxHeight);
//    }
//
//    @Test
//    public void testCropImage() {
//
//        BufferedImage imageToScale = generateTestImageGreaterHeight();
//        ResizingMethod method = ResizingMethod.CROP;
//        int wantedMaxWidth = 5;
//        int wantedMaxHeight = 10;
//        ImageResizingLimits limits = new ImageResizingLimits(wantedMaxWidth, wantedMaxHeight);
//        ImageResizer imageScaler = new ImageResizer(imageToScale, method, limits);
//        BufferedImage scaledImage = imageScaler.getResizedImage();        
//
//        Assert.assertTrue(scaledImage.getWidth() == wantedMaxWidth);
//        Assert.assertTrue(scaledImage.getHeight() == wantedMaxHeight);
//    }
//    
//    @Test
//    public void testScaleAndCropImage() {
//
//        System.out.println("testScaleAndCropImage");
//        BufferedImage imageToScale = generateTestImageGreaterHeight();
//        ResizingMethod method = ResizingMethod.SCALE_AND_CROP;
//        int wantedMaxWidth = 5;
//        int wantedMaxHeight = 10;
//        ImageResizingLimits limits = new ImageResizingLimits(wantedMaxWidth, wantedMaxHeight);
//        ImageResizer imageScaler = new ImageResizer(imageToScale, method, limits);
//        BufferedImage scaledImage = imageScaler.getResizedImage();        
//
//        Assert.assertTrue(scaledImage.getWidth() == wantedMaxWidth);
//        Assert.assertTrue(scaledImage.getHeight() == wantedMaxHeight);
//    }
//
//    private BufferedImage generateTestImageGreaterHeight() {
//        return new BufferedImage(10, 20, BufferedImage.TYPE_INT_RGB);
//    }
//
//    private BufferedImage generateTestImageEqualWidthAndHeight() {
//        return new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
//    }
//
//}
