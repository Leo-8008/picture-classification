package ch.leo.resnet.resnetapp;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ResNetService {

    public String classify(MultipartFile file)
            throws IOException, TranslateException, ModelNotFoundException, MalformedModelException {

        InputStream is = file.getInputStream();
        Image img = ImageFactory.getInstance().fromInputStream(is);

        Criteria<Image, Classifications> criteria = Criteria.builder()
                .optApplication(Application.CV.IMAGE_CLASSIFICATION)
                .setTypes(Image.class, Classifications.class)
                .optFilter("layers", "18") 
                .optEngine("PyTorch")
                .build();

        try (ZooModel<Image, Classifications> model = ModelZoo.loadModel(criteria);
             Predictor<Image, Classifications> predictor = model.newPredictor()) {

            Classifications result = predictor.predict(img);

            // Top 3 Ergebnisse extrahieren
            List<Classifications.Classification> topK = result.topK(3);
            List<Map<String, Object>> top3List = new ArrayList<>();

            for (Classifications.Classification c : topK) {
                String cleanedClass = c.getClassName().replaceFirst("^[a-z0-9]+\\s+", "");
                double confidence = Math.round(c.getProbability() * 10000.0) / 100.0;

                Map<String, Object> entry = new HashMap<>();
                entry.put("klasse", cleanedClass);
                entry.put("confidence", confidence);
                top3List.add(entry);
            }

            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(top3List);
        }
    }
}
