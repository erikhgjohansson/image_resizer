package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ServiceLoader;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import api.data.ImageResizingLimits;
import api.data.ResizingMethod;
import api.services.ImageResizerService;

public class ImageResizerGui extends JFrame {
    private static final String PIXELS_STRING = " pixels";
    private static final String MAXIMUM_HEIGHT_STRING = "Maximum height ";
    private static final String MAXIMUM_WIDTH_STRING = "Maximum width ";
    private static final int MAX_PIXELS = 512;
    private static final int DEFAULT_MAX_PIXELS = 200;
    private static final long serialVersionUID = 1L;
    private final JPanel panel;
    private final ImageResizerService _imageResizerService;

    public ImageResizerGui() {
        super("ImageResizer");
        _imageResizerService = loadImageResizerService();
        // service.resizeImage(pathImageToScale);
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel label = new JLabel("Enter full path to image to be resized");
        JLabel widthLabel = new JLabel(MAXIMUM_WIDTH_STRING + DEFAULT_MAX_PIXELS + PIXELS_STRING);
        JLabel heightLabel = new JLabel(MAXIMUM_HEIGHT_STRING + DEFAULT_MAX_PIXELS + PIXELS_STRING);
        JTextField imagePath = new JTextField();
        imagePath.setText("Absolute path");
        imagePath.setColumns(20);
        JButton button = new JButton();
        button.setText("Resize image");

        JSlider width = new JSlider(0, MAX_PIXELS, DEFAULT_MAX_PIXELS);
        width.setName("Width");
        width.setPaintTicks(true);
        width.setPaintLabels(true);
        width.setMinorTickSpacing(20);
        width.setMajorTickSpacing(100);

        width.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                widthLabel.setText(MAXIMUM_WIDTH_STRING + ((JSlider) e.getSource()).getValue() + PIXELS_STRING);
            }
        });

        JSlider height = new JSlider(0, MAX_PIXELS, DEFAULT_MAX_PIXELS);
        height.setName("height");
        height.setPaintTicks(true);
        height.setPaintLabels(true);
        height.setMinorTickSpacing(20);
        height.setMajorTickSpacing(100);

        height.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                heightLabel.setText(MAXIMUM_HEIGHT_STRING + ((JSlider) e.getSource()).getValue() + PIXELS_STRING);
            }
        });

        JComboBox<ResizingMethod> resizingMethod = new JComboBox<>(ResizingMethod.values());

        JLabel scalingResult = new JLabel("");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = imagePath.getText();
                if (inputText != null && inputText.length() > 0) {

                    ImageResizingLimits limits = new ImageResizingLimits(width.getValue(), height.getValue());
                    ResizingMethod method = (ResizingMethod) resizingMethod.getSelectedItem();
                    String result = _imageResizerService.resizeImage(inputText, limits, method);

                    scalingResult.setText("Resized file saved to " + result);
                } else {
                    label.setText("Please enter a path");
                }
            }
        });

        panel.add(label);
        panel.add(imagePath);
        panel.add(resizingMethod);
        panel.add(button);
        panel.add(heightLabel);
        panel.add(height);
        panel.add(widthLabel);
        panel.add(width);
        panel.add(scalingResult);
        this.add(panel);
        this.setSize(new Dimension(500, 250));
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
