
module imageresizer.impl {
    requires imageresizer.api;
    requires java.desktop;
    provides api.services.ImageResizerService with impl.ImageResizerServiceImpl;
}