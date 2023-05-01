
module imageresizer.impl {
    requires imageresizer.api;
    requires java.desktop;
//    requires junit;
    requires java.logging;
    provides api.services.ImageResizerService with impl.ImageResizerServiceImpl;
}