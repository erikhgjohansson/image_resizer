module imageresizer.mmi {
    requires imageresizer.api;
    requires imageresizer.impl;
    requires java.desktop;
    uses api.services.ImageResizerService;
    exports gui;
}