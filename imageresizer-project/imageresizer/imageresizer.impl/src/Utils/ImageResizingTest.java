//package Utils;
//
//import java.awt.image.BufferedImage;
//
//import org.junit.Assert;
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
//        ResizingMethod a = ResizingMethod.SCALE;
//        int wantedMaxWidth = 10;
//        int wantedMaxHeight = 10;
//        ImageResizingLimits b = new ImageResizingLimits(wantedMaxWidth, wantedMaxHeight);
//        ImageResizer imageScaler = new ImageResizer(imageToScale, a, b);
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
//        ResizingMethod a = ResizingMethod.SCALE;
//        int wantedMaxWidth = 5;
//        int wantedMaxHeight = 5;
//        ImageResizingLimits b = new ImageResizingLimits(wantedMaxWidth, wantedMaxHeight);
//        ImageResizer imageScaler = new ImageResizer(imageToScale, a, b);
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
//        ResizingMethod a = ResizingMethod.CROP;
//        int wantedMaxWidth = 5;
//        int wantedMaxHeight = 10;
//        ImageResizingLimits b = new ImageResizingLimits(wantedMaxWidth, wantedMaxHeight);
//        ImageResizer imageScaler = new ImageResizer(imageToScale, a, b);
//        BufferedImage scaledImage = imageScaler.getResizedImage();        
//
//        System.out.println(imageToScale.toString());
//        System.out.println(scaledImage.toString());
//
//        Assert.assertTrue(scaledImage.getWidth() == wantedMaxWidth);
//        Assert.assertTrue(scaledImage.getHeight() == wantedMaxHeight);
//    }
//
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
