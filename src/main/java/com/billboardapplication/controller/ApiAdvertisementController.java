package com.billboardapplication.controller;

import com.billboardapplication.api.response.AdvertisementResponse;
import com.billboardapplication.model.entity.Advertisement;
import com.billboardapplication.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiAdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping("/advertisement")
    public ResponseEntity<List<Advertisement>> getAllAdvertisements(
            @RequestParam(required = false, defaultValue = "byName") String sortType) {
        return ResponseEntity.ok(advertisementService.getAllAdvertisements(sortType));
    }

    @PutMapping("/advertisement")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<AdvertisementResponse> makeDeal(
            @RequestParam int advertisementId) {
        return ResponseEntity.ok(advertisementService.makeDeal(advertisementId));
    }

    @PostMapping(value = "/advertisement")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<AdvertisementResponse> createNewAdvertisement(String name, String description,
                                                                        @RequestParam MultipartFile photo) {
        return ResponseEntity.ok(advertisementService.createNewAdvertisement(name, description, photo));
    }

}
