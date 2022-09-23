package com.billboardapplication.api.request;

import lombok.Data;

@Data
public class CommentRequest {

    private String parentId;

    private String text;

    private int advertisementId;

}
