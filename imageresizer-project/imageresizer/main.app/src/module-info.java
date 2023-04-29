module main.app {
    requires imageresizer.api;
    requires imageresizer.impl;
    uses api.services.ImageResizerService;
}