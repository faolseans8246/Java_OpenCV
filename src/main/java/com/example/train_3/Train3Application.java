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

//Java OpenCV Class qismi
@SpringBootApplication
public class Train3Application extends JFrame {

//    Yordamchi java elementlari
    private JLabel label_1;             // joylashuv nishonlarini ta'minlash
    private JButton button_1;           // Vazifa buttonini qo'shish
    private VideoCapture videoCapture;  // Kamerada video va tasvir olish uchun ishlatiladi
    private Mat matImage;               // OpenCV kutubxonasida ma'lumotlarni saqlash, va ma'lumotlrni matritsa ko'rinishiga o'tkazish

    private boolean click_button_1;     // button harakati bilan ishlash

//  OpenCV bilan ishlash qismi methodi
    public Train3Application() {

//        dizayn UI
        setLayout(null);

        label_1 = new JLabel();                                 // Joylashuv nuqtasini sozlash
        label_1.setBounds(0, 0, 640, 480);      // nishon joylashuv qismi
        add(label_1);                                               // Frame oynasiga nishonni joylash

        // Buttonnni sozlash
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


        /**
         * Bu method bilan ishlashda berilgan oyna yopilganda bajariladigan amallar
         * vazifasi yuklangan
         */
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                videoCapture.release();
                matImage.release();
                System.exit(0);
            }
        });


        //JFrame oynasi vazifalar kiritilgan qism
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


//    Camerani ishga tushirish qismi
    public void startCamera() {

        videoCapture = new VideoCapture(0);     // 0 -indexli camerani ishga tushirish uchun ishlatiladi
        matImage = new Mat();                           // Mat classi shakllantiriladi va undan obekt olingan qismi
        byte[] imageDate;                               // matritsa hosil qilish (byte turli matritsa hosil qilish)

        ImageIcon imageIcon_1;

        /**
         * Berilgan rasmni shakllantirish va uni byte code holatga ko'chirish qismi
         */
        while (true) {

           //  Rasimni matritsada o'qish
            videoCapture.read(matImage);

            // matritsani bayt ko'rinishiga o'tkazish (Mat funksiyani byte[] orqali bayt ko'rinishiga o'tkazadi)
            final MatOfByte matOfByte = new MatOfByte();
            Imgcodecs.imencode(".jpg", matImage, matOfByte);        // matImageni ".jpg" formatga o'tkazib matOfBytega yozadi, mana shu holatda unidan kerakli joylarda foydalanish mumkin

            imageDate = matOfByte.toArray();        //  Byte[] assivga matOfByte array ko'rinishida joylashtirildi

             // Labelga qo'shish
            imageIcon_1 = new ImageIcon(imageDate);
            label_1.setIcon(imageIcon_1);

            /**
             * Button bosilganda bajariladigan vazifalar qismi
             */
            if (click_button_1) {

                String name = JOptionPane.showInputDialog(this, "Rasm nomini kiriting"); // rasm nomini kiritish qismi

                // Jarayonni bajaraish va saqlash qismi
                if (name == null) {
                    name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date()); // nomsiz saqlansa chislo bilan saqlaydi
                }


                String save_path_image = "D:\\ZakazProjects\\Java\\BahodirDomla\\RealProject\\TokenImages\\";
                // Faylga yozish qismi
                Imgcodecs.imwrite(save_path_image + name + ".jpg", matImage);   // olingan rasmni saqlash qismi
                click_button_1 = false;

            }

        }

    }


    /**
     * Berilga qismalr bilan ishlovchi asosiy method
     *
     * @param args
     */
    public static void main(String[] args) {

        SpringApplication.run(Train3Application.class, args);

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);       // OpenCV fayllarini yuklashni taminlaydi

        /**
         * Java dasturlash tilini GUI bilan ishlash jarayonini tashkillashtiradi
         */
        EventQueue.invokeLater(new Runnable() {

            // Berilgan dastur bilan ishlashni ta'minlovchi method
            @Override
            public void run() {
                Train3Application train3Application = new Train3Application();

                // Yuqorida yangi holat bo'lsa tahlil qiladi va ishlatadi
                new Thread(new Runnable() {

                    // Ish methodini qayta ko'rib chiquvchi qism
                    @Override
                    public void run() {
                        train3Application.startCamera();
                    }
                }).start();
            }
        });

    }


}
