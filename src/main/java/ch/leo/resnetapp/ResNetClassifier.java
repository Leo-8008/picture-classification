package main.java.ch.leo.resnetapp;

import ai.djl.Application;
import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;

import java.io.IOException;
import java.nio.file.Paths;

public class ResNetClassifier {

    public static void main(String[] args) throws IOException, TranslateException {
        // Bild laden
        Image img = ImageFactory.getInstance().fromFile(Paths.get("path/to/your/image.jpg"));

        // Kriterien für das Modell festlegen
        Criteria<Image, Classifications> criteria = Criteria.builder()
                .optApplication(Application.CV.IMAGE_CLASSIFICATION)
                .setTypes(Image.class, Classifications.class)
                .optFilter("layers", "18") // ResNet18
                .optEngine("PyTorch") // PyTorch-Engine verwenden
                .build();

        // Modell laden und Vorhersage treffen
        try (ZooModel<Image, Classifications> model = ModelZoo.loadModel(criteria);
             Predictor<Image, Classifications> predictor = model.newPredictor()) {

            Classifications classifications = predictor.predict(img);
            System.out.println(classifications);
        }
    }
}

