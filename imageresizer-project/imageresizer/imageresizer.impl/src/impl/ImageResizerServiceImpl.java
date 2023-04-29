package impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import api.services.ImageResizerService;

public class ImageResizerServiceImpl implements ImageResizerService{
    
    private static final int DEFAULT_MAX_IMAGE_SIZE = 512;
    private final int _maxImageSize;

    public ImageResizerServiceImpl() {
        this(DEFAULT_MAX_IMAGE_SIZE);
    }
    
    public ImageResizerServiceImpl(int maxImageSize) {
        _maxImageSize = maxImageSize;
    }
    

    @Override
    public void resizeImage(String path) {
        Path pathImage = Paths.get(path);
        try {
            BufferedImage unscaledImage = ImageIO.read(pathImage.toAbsolutePath().toFile());
            
            if(imageNotValid(unscaledImage)) {
                System.out.println("IMAGE NOT VALID");
                return;
            }
            
            if(isImageScalable(unscaledImage)) {
                scaleImage(unscaledImage);
                return;
            }
            
            cropImage(unscaledImage);
            
        } catch (IOException e) {
            System.out.println("Error resizing image "+pathImage.getFileName());
            e.printStackTrace();
            return;
        }
    }

    private boolean imageNotValid(BufferedImage image) {
        // TODO Auto-generated method stub
        return false;
    }

    private void cropImage(BufferedImage image) {
        // TODO Auto-generated method stub
        
    }

    private void scaleImage(BufferedImage image) {
        // TODO Auto-generated method stub
        
    }

    private boolean isImageScalable(BufferedImage image) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void resizeVideo(String path) {
        //Intentionally left empty for future work
        System.out.println("Resize video functionality not yet implemented");
    }

}
