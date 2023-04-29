package impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import Utils.ImageScaler;
import api.data.ImageScalingLimits;
import api.data.ScaleMethod;
import api.services.ImageResizerService;

public class ImageResizerServiceImpl implements ImageResizerService {

    private static final int DEFAULT_MAX_IMAGE_SIZE = 512;
    private final int _maxImageSize;
    private final Set<String> _allowedFileFormats;

    public ImageResizerServiceImpl() {
        this(DEFAULT_MAX_IMAGE_SIZE);
    }

    public ImageResizerServiceImpl(int maxImageSize) {
        _maxImageSize = maxImageSize;
        _allowedFileFormats = configureAllowedFileFormats();
    }

    private Set<String> configureAllowedFileFormats() {
        String[] readerFormatNames = ImageIO.getReaderFormatNames();
        String[] writerFormatNames = ImageIO.getWriterFormatNames();

        List<String> readerFormatList = Arrays.asList(readerFormatNames);

        return Arrays.stream(writerFormatNames).filter(s -> readerFormatList.contains(s)).collect(Collectors.toSet());
    }

    @Override
    public void resizeImage(String path) {
        if (invalidImageFilePath(path)) {
            System.out.println("Error invalid file path or file format.");
            return;
        }

        Path pathImage = Paths.get(path);
        try {
            BufferedImage unscaledImage = ImageIO.read(pathImage.toAbsolutePath().toFile());
            
            ImageScaler scaler = new ImageScaler(
                    unscaledImage, 
                    ScaleMethod.SCALE_AND_CROP, 
                    new ImageScalingLimits(_maxImageSize, _maxImageSize));
            
            BufferedImage scaledImage = scaler.getScaledImage();
            saveScaledImageToFile(path, pathImage, scaledImage);
            
        } catch (IOException e) {
            System.out.println("Error resizing image " + pathImage.getFileName());
            e.printStackTrace();
            return;
        }
    }

    private void saveScaledImageToFile(String path, Path pathImage, BufferedImage scaledImage) throws IOException {
        String format = path.substring(path.lastIndexOf(".") + 1);
        String newName = pathImage.getFileName().toString().replace("." + format, "_thumb." + format);
        ImageIO.write(scaledImage, format, Paths.get(newName).toFile());
    }

    private boolean invalidImageFilePath(String path) {
        return Objects.isNull(getClass()) || isWrongFileFormat(path);
    }

    private boolean isWrongFileFormat(String path) {
        String format = path.substring(path.lastIndexOf(".") + 1);
        return !_allowedFileFormats.contains(format);
    }

    @Override
    public void resizeVideo(String path) {
        // Intentionally left empty for future work
        System.out.println("Resize video functionality not yet implemented");
    }

}
