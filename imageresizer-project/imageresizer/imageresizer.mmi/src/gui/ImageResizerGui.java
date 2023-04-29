package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ServiceLoader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import api.services.ImageResizerService;

public class ImageResizerGui extends JFrame{
    private static final long serialVersionUID = 1L;
    private final JPanel panel;
    private final ImageResizerService _imageResizerService;
    private JTextField _textField;
    
    public ImageResizerGui() {
        super("ImageResizer");
        _imageResizerService = loadImageResizerService();
//        service.resizeImage(pathImageToScale);
        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        
        JLabel label = new JLabel("Enter full path to image to be resized");
        _textField = new JTextField();
        _textField.setText("Absolute path");
        _textField.setColumns(20);
        JButton button = new JButton();  
        button.setText("Resize image");  
        
        JLabel scalingResult = new JLabel("");
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String inputText = _textField.getText();
               if(inputText != null && inputText.length() > 0){
                   
                   String result = _imageResizerService.resizeImage(inputText);
                   
                   
                   scalingResult.setText("Resized file saved to "+result);
               }else {
                  label.setText("Please enter a path");
               }
            }
         });
        
        panel.add(label);
        panel.add(_textField);
        panel.add(button);  
        panel.add(scalingResult);  
        this.add(panel);  
        this.setSize(new Dimension(700, 300));
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
