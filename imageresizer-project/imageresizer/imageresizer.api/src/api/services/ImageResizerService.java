package api.services;

import api.data.ImageResizingLimits;
import api.data.ResizingMethod;

public interface ImageResizerService {
    public String resizeImage(String path, ImageResizingLimits limits, ResizingMethod method);
    
    public String resizeVideo(String path);
}
