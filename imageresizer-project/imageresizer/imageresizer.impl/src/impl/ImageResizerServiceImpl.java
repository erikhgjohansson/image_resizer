package impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import Utils.ImageResizer;
import api.data.ImageResizingLimits;
import api.data.ResizingMethod;
import api.services.ImageResizerService;

public class ImageResizerServiceImpl implements ImageResizerService {

    private static final Logger LOGGER = Logger.getLogger(ImageResizerServiceImpl.class.getName());

    private final Set<String> _allowedFileFormats;

    public ImageResizerServiceImpl() {
        _allowedFileFormats = configureAllowedFileFormats();
        initializeLogger();
    }

    private void initializeLogger() {
        try {
            String logName = ImageResizerServiceImpl.class.getName() + ".log";
            FileHandler fileHandler = new FileHandler(Paths.get(logName).toAbsolutePath().toString(), true);
            fileHandler.setLevel(Level.ALL);
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> configureAllowedFileFormats() {
        String[] readerFormatNames = ImageIO.getReaderFormatNames();
        String[] writerFormatNames = ImageIO.getWriterFormatNames();

        List<String> readerFormatList = Arrays.asList(readerFormatNames);

        return Arrays.stream(writerFormatNames).filter(s -> readerFormatList.contains(s)).collect(Collectors.toSet());
    }

    @Override
    public String resizeImage(String path, ImageResizingLimits limits, ResizingMethod method) {
        LOGGER.log(Level.INFO, "Resizing image found at " + path);
        if (invalidImageFilePath(path)) {
            String errorMessage = "Error invalid file path or file format. Path = " + path;
            LOGGER.log(Level.WARNING, errorMessage);
            return errorMessage;
        }

        Path pathImage = Paths.get(path);
        try {
            BufferedImage unresizedImage = ImageIO.read(pathImage.toAbsolutePath().toFile());
            LOGGER.log(Level.INFO, "Unaltered image has [pixels] height:" + unresizedImage.getHeight() + ", width "
                    + unresizedImage.getWidth());
            ImageResizer resizer = new ImageResizer(unresizedImage, method, limits);
            BufferedImage resizedImage = resizer.getResizedImage();
            LOGGER.log(Level.INFO, "Resized image has [pixels] height:" + unresizedImage.getHeight() + ", width "
                    + unresizedImage.getWidth());
            return saveResizedImageToFile(path, pathImage, resizedImage);

        } catch (IOException e) {
            String errorMessage = "Error resizing image " + pathImage.getFileName();
            LOGGER.log(Level.WARNING, errorMessage + e);
            return errorMessage;
        }
    }

    private String saveResizedImageToFile(String path, Path pathImage, BufferedImage resizedImage) throws IOException {
        String format = path.substring(path.lastIndexOf(".") + 1);
        String newName = pathImage.getFileName().toString().replace("." + format, "_thumb." + format);
        boolean isWrittenToFile = ImageIO.write(resizedImage, format, Paths.get(newName).toFile());
        String resizedImagePath = Paths.get(newName).toAbsolutePath().toString();
        String saveFileMessage = isWrittenToFile ? "SUCCESSFULLY saved resized image to "
                : "FAILED to save resized image to ";
        LOGGER.info(saveFileMessage + resizedImagePath);
        return resizedImagePath;
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
        String notYetImplementedMessage = "Resize video functionality not yet implemented";
        LOGGER.log(Level.INFO, notYetImplementedMessage);
        return notYetImplementedMessage;
    }

}
