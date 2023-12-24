package com.example.train_3;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class Train3Application extends JFrame {

//    Camera screen
    private JLabel label_1;
    private JButton button_1;
    private VideoCapture videoCapture;
    private Mat matImage;

    private boolean click_button_1;


    public Train3Application() {

//        dizayn UI
        setLayout(null);

        label_1 = new JLabel();
        label_1.setBounds(0, 0, 640, 480);
        add(label_1);

        button_1 = new JButton("Rasmga olish");
        button_1.setBounds(225, 480, 180, 40);
        add(button_1);

//        Buttonning vazifa qismi (harakatda bajaradigan qismi)
        button_1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                click_button_1 = true;

            }
        });

//
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                videoCapture.release();
                matImage.release();
                System.exit(0);
            }
        });


        setTitle("OpenCV oynasida camera bilan ishlash");
        setSize(new Dimension(640, 560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(true);

        String icon_path = "D:\\MyPractPrograms\\OpenCV\\Images\\Face_od_two.png";
        ImageIcon imageIcon = new ImageIcon(icon_path);
        setIconImage(imageIcon.getImage());

    }

//    create camera

    public void startCamera() {

        videoCapture = new VideoCapture(0);
        matImage = new Mat();
        byte[] imageDate;

        ImageIcon imageIcon_1;

        while (true) {

//            Rasimni matritsada o'qish
            videoCapture.read(matImage);

//            matritsani bayt ko'rinishiga o'tkazish
            final MatOfByte matOfByte = new MatOfByte();
            Imgcodecs.imencode(".jpg", matImage, matOfByte);

            imageDate = matOfByte.toArray();

//            Labelga qo'shish
            imageIcon_1 = new ImageIcon(imageDate);
            label_1.setIcon(imageIcon_1);

            if (click_button_1) {

                String name = JOptionPane.showInputDialog(this, "Rasm nomini kiriting");

//                Jarayonni bajaraish va saqlash qismi
                if (name == null) {
                    name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date());
                }

                // Faylga yozish qismi
                Imgcodecs.imwrite("myImage/" + name + ".jpg", matImage);
                click_button_1 = false;

            }

        }

    }

    public static void main(String[] args) {

        SpringApplication.run(Train3Application.class, args);

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Train3Application train3Application = new Train3Application();

                // Start camera in thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        train3Application.startCamera();
                    }
                }).start();
            }
        });

    }

}
