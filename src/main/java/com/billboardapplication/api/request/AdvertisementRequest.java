package com.billboardapplication.api.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
public class AdvertisementRequest {

    private String name;

    private String description;

    private String contacts;

    private MultipartFile photo;

}
