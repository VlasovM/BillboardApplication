package com.billboardapplication.service;

import com.billboardapplication.api.response.AdvertisementResponse;
import com.billboardapplication.model.entity.Advertisement;
import com.billboardapplication.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    public List<Advertisement> getAllAdvertisements() {
        return advertisementRepository.findAll();
    }

    public AdvertisementResponse createNewAdvertisement(String name, String description, String photo, String contacts) {
        Advertisement advertisement = new Advertisement();
        advertisement.setName(name);
        advertisement.setPhoto(photo);
        advertisement.setDescription(description);
        advertisement.setContacts(contacts);
        advertisement.setActive((short) 1);

        advertisementRepository.save(advertisement);

        AdvertisementResponse response = new AdvertisementResponse();
        response.setResult(true);
        return response;
    }
}
