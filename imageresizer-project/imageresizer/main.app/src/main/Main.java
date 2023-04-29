package main;

import java.util.ServiceLoader;

import api.services.ImageResizerService;

public class Main {

    public static void main(String[] args) {
        Iterable<ImageResizerService> services = ServiceLoader.load(ImageResizerService.class);
        ImageResizerService service = services.iterator().next();
        service.resizeImage("hej");
    }

}
