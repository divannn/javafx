package webmap;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class GoogleMapDemo extends JFrame {

    private WebEngine webEngine;

    public GoogleMapDemo() {
        setTitle(GoogleMapDemo.class.getSimpleName());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JFXPanel fxPanel = new JFXPanel();
        getContentPane().add(createToolBar(), BorderLayout.NORTH);
        getContentPane().add(fxPanel, BorderLayout.CENTER);
        setVisible(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fxPanel.setScene(createScene());
            }
        });
    }

    private Scene createScene() {
        WebView webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("googlemap.html").toString());
        BorderPane root = new BorderPane();
        root.getStyleClass().add("map");
        root.setCenter(webView);
        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(
                GoogleMapDemo.class.getResource("WebMap.css").toExternalForm());
        return scene;
    }

    private void navigate(String str) {
        webEngine.executeScript("document.goToLocation(\"" + str + "\")");
    }

    private void road() {
        webEngine.executeScript("document.setMapTypeRoad()");
    }

    private void satellite() {
        webEngine.executeScript("document.setMapTypeSatellite()");
    }

    private void hybrid() {
        webEngine.executeScript("document.setMapTypeHybrid()");
    }

    private void terrain() {
        webEngine.executeScript("document.setMapTypeTerrain()");
    }

    private void zoomOut() {
        webEngine.executeScript("document.zoomOut()");
    }

    private void resetZoom() {
        webEngine.executeScript("document.resetZoom()");
    }

    private void zoomIn() {
        webEngine.executeScript("document.zoomIn()");
    }

    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    static {
        // use system proxy settings when standalone application
        System.setProperty("java.net.useSystemProxies", "true");
    }

    public static void main(String[] args) {
        runSwing();
        //runSWT();
    }

    private static void runSwing() {
        new GoogleMapDemo();
    }

    private JPanel createToolBar() {
        JPanel r = new JPanel(new BorderLayout());
        r.add(createLocationPanel(), BorderLayout.WEST);
        r.add(createTypePanel(), BorderLayout.EAST);
        return r;
    }

    private JPanel createLocationPanel() {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JTextField tf = new JTextField();
        tf.setColumns(30);
        tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        navigate(tf.getText().trim());
                    }
                });
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        final JButton zoomIn = new JButton("+");
        final JButton zoomOut = new JButton("-");
        final JButton resetZoom = new JButton("0");
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                final JButton s = (JButton) e.getSource();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (s == zoomIn) {
                            zoomIn();
                        } else if (s == zoomOut) {
                            zoomOut();
                        } else {
                            resetZoom();
                        }
                    }
                });
            }
        };
        zoomIn.addActionListener(al);
        zoomOut.addActionListener(al);
        resetZoom.addActionListener(al);

        result.add(new JLabel("Location:"));
        result.add(tf);
        result.add(zoomIn);
        result.add(resetZoom);
        result.add(zoomOut);
        return result;
    }

    private JPanel createTypePanel() {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ButtonGroup typeGroup = new ButtonGroup();
        final JToggleButton road = new JToggleButton("Road");
        road.setSelected(true);
        final JToggleButton satellite = new JToggleButton("Satellite");
        final JToggleButton hybrid = new JToggleButton("Hybrid");
        JToggleButton terrain = new JToggleButton("Terrain");
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                final JToggleButton s = (JToggleButton) e.getSource();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (s == road) {
                            road();
                        } else if (s == satellite) {
                            satellite();
                        } else if (s == hybrid) {
                            hybrid();
                        } else {
                            terrain();
                        }
                    }
                });
            }
        };
        road.addActionListener(al);
        satellite.addActionListener(al);
        hybrid.addActionListener(al);
        terrain.addActionListener(al);

        typeGroup.add(road);
        typeGroup.add(satellite);
        typeGroup.add(hybrid);
        typeGroup.add(terrain);
        result.add(road);
        result.add(satellite);
        result.add(hybrid);
        result.add(terrain);
        return result;
    }

//	private static void runSWT() {
//		Display display = new Display();
//		Shell shell = new Shell(display);
//		shell.setText(GoogleMapDemo.class.getSimpleName());
//		createContents(shell, display);
//		shell.setSize(800, 600);
//		shell.setLocation(200, 200);
//		shell.open();
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
//		display.dispose();
//	}
//
//	private static void createContents(final Shell shell,
//			final Display display) {
//		shell.setLayout(new FillLayout());
//		org.eclipse.swt.widgets.Composite composite = new org.eclipse.swt.widgets.Composite(
//				shell, SWT.EMBEDDED | SWT.NO_BACKGROUND);
//		Frame frame = SWT_AWT.new_Frame(composite);
//		final JFXPanel fxPanel = new JFXPanel();
//		frame.add(fxPanel);
//		Platform.runLater(new Runnable() {
//			@Override
//			public void run() {
//				fxPanel.setScene(new GoogleMapDemo().createScene());
//			}
//		});
//	}


}