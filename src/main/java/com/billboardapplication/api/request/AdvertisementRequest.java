package com.billboardapplication.api.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AdvertisementRequest {

    private String name;

    private String description;

    private String contacts;

    private String photo;

}
