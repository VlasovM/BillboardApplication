package com.billboardapplication.service;

import com.billboardapplication.api.response.AdvertisementCommentResponse;
import com.billboardapplication.api.response.AdvertisementResponse;
import com.billboardapplication.model.entity.Advertisement;
import com.billboardapplication.model.entity.AdvertisementComment;
import com.billboardapplication.model.entity.User;
import com.billboardapplication.repository.AdvertisementCommentRepository;
import com.billboardapplication.repository.AdvertisementRepository;
import com.billboardapplication.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CommentServiceTest {

    UserRepository mockUserRepo = Mockito.mock(UserRepository.class);
    AdvertisementRepository mockAdvertisementRepo = Mockito.mock(AdvertisementRepository.class);
    AdvertisementCommentRepository mockAdvertisementCommentRepo = Mockito.mock(AdvertisementCommentRepository.class);
    CommentService underTestService = new CommentService(
            mockAdvertisementRepo, mockUserRepo, mockAdvertisementCommentRepo);


    @Test
    @DisplayName("Set comment if parentId = null")
    void setCommentToAdvertisementParentIdNullTest() {
        AdvertisementCommentResponse expected = new AdvertisementCommentResponse();
        expected.setCommentId(1);

        Advertisement advertisement = getAdvertisement();
        when(mockAdvertisementRepo.findById(advertisement.getId())).thenReturn(advertisement);

        AdvertisementComment advertisementComment = getAdvertisementComment();
        when(mockAdvertisementCommentRepo.findTopByOrderById()).thenReturn(advertisementComment);

        User user = getUser();
        when(mockUserRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user.getEmail());

        AdvertisementCommentResponse actual =
                underTestService.setCommentToAdvertisement(null, 1, "");

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Set comment if parentId is not null")
    void setCommentToAdvertisementParentIdNotNullTest() {
        AdvertisementCommentResponse expected = new AdvertisementCommentResponse();
        expected.setCommentId(1);

        Advertisement advertisement = getAdvertisement();
        when(mockAdvertisementRepo.findById(advertisement.getId())).thenReturn(advertisement);

        AdvertisementComment advertisementComment = getAdvertisementComment();
        advertisementComment.setParentId("1");
        when(mockAdvertisementCommentRepo.findTopByOrderById()).thenReturn(advertisementComment);

        User user = getUser();
        when(mockUserRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user.getEmail());

        AdvertisementCommentResponse actual =
                underTestService.setCommentToAdvertisement("1", 1, "");

        assertEquals(expected, actual);

    }

    private Advertisement getAdvertisement() {
        Advertisement advertisement = new Advertisement();

        advertisement.setId(1);
        advertisement.setPhoto(null);
        advertisement.setName("Some");
        advertisement.setActive((short) 1);
        advertisement.setDescription("");

        return advertisement;
    }

    private User getUser() {
        User user = new User();
        user.setName("Maxim");
        user.setEmail("MockEmail@Mock.ru");
        user.setPassword("1234");
        user.setRole("USER");

        return user;
    }

    private AdvertisementComment getAdvertisementComment() {
        AdvertisementComment advertisementComment = new AdvertisementComment();

        advertisementComment.setAdvertisement(getAdvertisement());
        advertisementComment.setUser(getUser());
        advertisementComment.setText("");
        advertisementComment.setId(1);
        advertisementComment.setParentId(null);

        return advertisementComment;
    }

}