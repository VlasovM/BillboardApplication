package com.billboardapplication.service;

import com.billboardapplication.api.response.AdvertisementResponse;
import com.billboardapplication.model.entity.Advertisement;
import com.billboardapplication.model.entity.User;
import com.billboardapplication.repository.AdvertisementRepository;
import com.billboardapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.imgscalr.Scalr;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;

    private final static String FIND_BY_NAME = "name";
    private final static String FIND_BY_ID = "id";
    private final static String FIND_BY_CONTACTS = "contacts";

    public List<Advertisement> getAllAdvertisements(String sortType) {
        List<Advertisement> resultList = new ArrayList<>();

        switch (sortType) {
            case (FIND_BY_NAME) -> resultList.addAll(advertisementRepository.findAllByOrderByNameAsc());
            case (FIND_BY_ID) -> resultList.addAll(advertisementRepository.findAllByOrderByIdAsc());
            case (FIND_BY_CONTACTS) -> resultList.addAll(advertisementRepository.findAllByOrderByContactsAsc());
            default -> resultList.addAll(advertisementRepository.findAll());
        }

        return resultList;
    }

    public AdvertisementResponse createNewAdvertisement(String name, String description, MultipartFile photo) {
        Advertisement advertisement = new Advertisement();

        advertisement.setName(name);
        advertisement.setDescription(description);
        advertisement.setContacts(getCurrentUser().getEmail());
        advertisement.setActive((short) 1);
        try {
            advertisement.setPhoto(uploadPhoto(photo));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        advertisementRepository.save(advertisement);

        AdvertisementResponse response = new AdvertisementResponse();
        response.set_successfully(true);
        return response;
    }

    public AdvertisementResponse makeDeal(int advertisementId) {
        AdvertisementResponse advertisementResponse = new AdvertisementResponse();
        Advertisement advertisement = advertisementRepository.findById(advertisementId);
        advertisement.setActive((short) 0);
        return advertisementResponse;
    }

    private User getCurrentUser() {
        String emailUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(emailUser).orElseThrow();
    }

    private Path getPathToFile() {
        String[] foldersName = UUID.randomUUID().toString().split("-");
        return Paths.get("upload/image" + "/" + foldersName[1] + "/" +
                foldersName[2] + "/" + foldersName[3] + "/");
    }

    private String uploadPhoto(MultipartFile photo) throws IOException {
        String imageType = photo.getContentType().split("/")[1];
        BufferedImage image = ImageIO.read(photo.getInputStream());
        int maxPhotoSize = 350; //px
        int height = (int) (Math.round(image.getHeight()) / (image.getWidth() / (double) maxPhotoSize));
        BufferedImage newImage = Scalr.resize(
                image,
                Scalr.Method.AUTOMATIC,
                Scalr.Mode.AUTOMATIC,
                maxPhotoSize,
                height,
                Scalr.OP_ANTIALIAS);

        Path path = getPathToFile();

        if (!new File(path.toString()).exists()) {
            new File(path.toString()).mkdirs();
        }

        String fileType = photo.getOriginalFilename().split("\\.")[1];
        String fileName = RandomString.make(12) + "." + fileType;

        File newFile = new File(path + "/" + fileName);
        ImageIO.write(newImage, imageType, newFile);

        return "/" + newFile.getPath();
    }

}
