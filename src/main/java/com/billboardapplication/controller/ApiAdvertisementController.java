package com.billboardapplication.controller;

import com.billboardapplication.api.request.AdvertisementRequest;
import com.billboardapplication.api.response.AdvertisementResponse;
import com.billboardapplication.model.entity.Advertisement;
import com.billboardapplication.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/")
public class ApiAdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping("advertisement")
    public ResponseEntity<List<Advertisement>> getAllAdvertisements() {
        return ResponseEntity.ok(advertisementService.getAllAdvertisements());
    }

    @PostMapping(value = "advertisement")
    public ResponseEntity<AdvertisementResponse> createNewAdvertisement(@RequestBody AdvertisementRequest request) {
        return ResponseEntity.ok(advertisementService.createNewAdvertisement(
                request.getName(),
                request.getDescription(),
                request.getPhoto(),
                request.getContacts()));
    }

}
