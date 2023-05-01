
module imageresizer.impl {
    requires imageresizer.api;
    requires java.desktop;
//    requires junit;
    provides api.services.ImageResizerService with impl.ImageResizerServiceImpl;
}