package gui;

import java.awt.FlowLayout;
import java.util.ServiceLoader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import api.services.ImageResizerService;

public class ImageResizerGui extends JFrame{
    private static final long serialVersionUID = 1L;
    private final JPanel panel;
    private final ImageResizerService _imageResizerService;
    
    public ImageResizerGui() {
        super("ImageResizer");
        _imageResizerService = loadImageResizerService();
//        service.resizeImage(pathImageToScale);
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        
        JLabel label = new JLabel("JFrame By Example");  
        JButton button = new JButton();  
        button.setText("Button");  
        panel.add(label);  
        panel.add(button);  
        this.add(panel);  
        this.setSize(200, 300);  
        this.setLocationRelativeTo(null);  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        this.setVisible(true);  
    }

    private ImageResizerService loadImageResizerService() {
        Iterable<ImageResizerService> services = ServiceLoader.load(ImageResizerService.class);
        ImageResizerService service = services.iterator().next();
        return service;
    }
    
    public void run() {
        
    }
}
