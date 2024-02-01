package com.example.train_3.Analyses;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class ImageAnalysisService {

    public byte[] analyzeImage(byte[] imageData) {

        // Rasmni OpenCV orqali tahlil qilish
        CascadeClassifier faceCascade = new CascadeClassifier();
        faceCascade.load("haarcascade_frontalface_default.xml"); // Haqiqiy manzilni o'zgartiring

        Mat image = Imgcodecs.imdecode(new MatOfByte(imageData), Imgcodecs.IMREAD_UNCHANGED);
        MatOfRect faceDetections = new MatOfRect();
        faceCascade.detectMultiScale(image, faceDetections, 1.1, 3, 0, new Size(30, 30), new Size());

        // Tahlil natijasini rasmga chizish
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 2);
        }

        // Natijani byte massiviga aylantirish
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", image, matOfByte);
        byte[] resultImageData = matOfByte.toArray();

        return resultImageData;
    }

}
