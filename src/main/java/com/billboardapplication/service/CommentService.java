package com.billboardapplication.service;

import com.billboardapplication.api.response.AdvertisementCommentResponse;
import com.billboardapplication.model.entity.Advertisement;
import com.billboardapplication.model.entity.AdvertisementComment;
import com.billboardapplication.model.entity.User;
import com.billboardapplication.repository.AdvertisementCommentRepository;
import com.billboardapplication.repository.AdvertisementRepository;
import com.billboardapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final AdvertisementCommentRepository advertisementCommentRepository;


    public AdvertisementCommentResponse setCommentToAdvertisement(String parentId, int advertisementId, String text) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId);
        User user = getCurrentUser();

        AdvertisementComment advertisementComment = new AdvertisementComment();
        advertisementComment.setAdvertisement(advertisement);
        advertisementComment.setText(text);
        advertisementComment.setUser(user);
        AdvertisementCommentResponse response = new AdvertisementCommentResponse();
        if (parentId == null) {
            advertisementComment.setParentId(null);
            advertisementCommentRepository.save(advertisementComment);
            response.setCommentId(advertisementCommentRepository.findTopByOrderById().getId());
            return response;
        }

        advertisementComment.setParentId(parentId);
        advertisementCommentRepository.save(advertisementComment);

        response.setCommentId(advertisementCommentRepository.findTopByOrderById().getId());
        return response;

    }

    private User getCurrentUser() {
        String emailUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(emailUser).orElseThrow();
    }

}
