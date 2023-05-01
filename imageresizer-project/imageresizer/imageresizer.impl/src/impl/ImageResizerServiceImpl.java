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

import Utils.ImageResizer;
import api.data.ImageResizingLimits;
import api.data.ResizingMethod;
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
    public String resizeImage(String path) {
        if (invalidImageFilePath(path)) {
            String errorMessage = "Error invalid file path or file format. Path = "+path;
            System.out.println(errorMessage);
            return errorMessage;
        }

        Path pathImage = Paths.get(path);
        try {
            BufferedImage unresizedImage = ImageIO.read(pathImage.toAbsolutePath().toFile());
            
            ImageResizer resizer = new ImageResizer(
                    unresizedImage, 
                    ResizingMethod.SCALE_AND_CROP, 
                    new ImageResizingLimits(_maxImageSize, _maxImageSize));
            
            BufferedImage resizedImage = resizer.getResizedImage();
            return saveResizedImageToFile(path, pathImage, resizedImage);
            
        } catch (IOException e) {
            String errorMessage = "Error resizing image " + pathImage.getFileName();
            System.out.println(errorMessage);
            e.printStackTrace();
            return errorMessage;
        }
    }

    private String saveResizedImageToFile(String path, Path pathImage, BufferedImage resizedImage) throws IOException {
        String format = path.substring(path.lastIndexOf(".") + 1);
        String newName = pathImage.getFileName().toString().replace("." + format, "_thumb." + format);
        ImageIO.write(resizedImage, format, Paths.get(newName).toFile());
        return Paths.get(newName).toAbsolutePath().toString();
    }

    private boolean invalidImageFilePath(String path) {
        return Objects.isNull(getClass()) || isWrongFileFormat(path);
    }

    private boolean isWrongFileFormat(String path) {
        String format = path.substring(path.lastIndexOf(".") + 1);
        return !_allowedFileFormats.contains(format);
    }

    @Override
    public String resizeVideo(String path) {
        // Intentionally left empty for future work
        System.out.println("Resize video functionality not yet implemented");
        return path;
    }

}
