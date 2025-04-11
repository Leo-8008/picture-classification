package ch.leo.resnet.resnetapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class ResNetController {

    @Autowired
    private ResNetService resNetService;

    @PostMapping("/analyze")
    public String analyze(@RequestParam("image") MultipartFile image) throws Exception {
        return resNetService.classify(image);
    }

    @GetMapping("/ping")
    public String ping() {
        return "ResNet App läuft!";
    }
}
