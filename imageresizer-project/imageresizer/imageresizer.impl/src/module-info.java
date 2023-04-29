
module imageresizer.impl {
    requires imageresizer.api;
    provides api.services.ImageResizerService with impl.ImageResizerServiceImpl;
}