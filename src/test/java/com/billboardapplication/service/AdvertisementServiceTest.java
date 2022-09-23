package com.billboardapplication.service;

import com.billboardapplication.api.response.AdvertisementResponse;
import com.billboardapplication.model.entity.Advertisement;
import com.billboardapplication.model.entity.User;
import com.billboardapplication.repository.AdvertisementRepository;
import com.billboardapplication.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


class AdvertisementServiceTest {

    AdvertisementRepository mockAdvertisementRepo = Mockito.mock(AdvertisementRepository.class);
    UserRepository mockUserRepo = Mockito.mock(UserRepository.class);
    AdvertisementService underTestService = new AdvertisementService(mockAdvertisementRepo, mockUserRepo);

    private final String PATH_TO_FILE_JPG = "C:\\Users\\79153\\Desktop\\Other\\5.jpg";

    @Test
    @DisplayName("Get all sorting by Name")
    void getAllAdvertisementsByNameTest() {
        List<Advertisement> expected = expectedList();

        List<Advertisement> advertisementList = actualList();
        when(mockAdvertisementRepo.findAllByOrderByNameAsc()).thenReturn(advertisementList);

        List<Advertisement> actual = underTestService.getAllAdvertisements("name");

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Get all sorting by id")
    void getAllAdvertisementByIdTest() {
        List<Advertisement> expected = expectedList();

        List<Advertisement> advertisementList = actualList();
        when(mockAdvertisementRepo.findAllByOrderByIdAsc()).thenReturn(advertisementList);

        List<Advertisement> actual = underTestService.getAllAdvertisements("id");

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Get all sorting by contacts")
    void getAllAdvertisementByContacts() {
        List<Advertisement> expected = expectedList();
        expected.get(0).setContacts("+8915-111");
        expected.get(1).setContacts("+8915-222");

        List<Advertisement> advertisementList = actualList();
        advertisementList.get(0).setContacts("+8915-111");
        advertisementList.get(1).setContacts("+8915-222");
        when(mockAdvertisementRepo.findAllByOrderByContactsAsc()).thenReturn(advertisementList);

        List<Advertisement> actual = underTestService.getAllAdvertisements("contacts");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get all sorting by default")
    void getAllAdvertisementByDefaultTest() {
        List<Advertisement> expected = expectedList();

        List<Advertisement> advertisementList = actualList();
        when(mockAdvertisementRepo.findAll()).thenReturn(advertisementList);

        List<Advertisement> actual = underTestService.getAllAdvertisements("");

        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Make deal with advertisement")
    void makeDealTest() {
        AdvertisementResponse expected = new AdvertisementResponse();
        expected.set_successfully(true);

        Advertisement expectedAdvertisement = expectedList().get(0);
        expectedAdvertisement.setActive((short) 0);

        Advertisement actualAdvertisement = actualList().get(0);
        actualAdvertisement.setActive((short) 1);
        when(mockAdvertisementRepo.findById(actualAdvertisement.getId())).thenReturn(actualAdvertisement);

        AdvertisementResponse actual = underTestService.makeDeal(0);

        assertEquals(expected, actual);
        assertEquals(expectedAdvertisement.getActive(), actualAdvertisement.getActive());

    }

    @Test
    @DisplayName("Create new advertisement")
    void createNewAdvertisementTest() throws IOException {
        AdvertisementResponse expected = new AdvertisementResponse();
        expected.set_successfully(true);

        Advertisement expectedAdvertisement = expectedList().get(0);
        expectedAdvertisement.setPhoto("C:\\Users\\79153\\Desktop\\Other\\5.jpg");

        when(mockAdvertisementRepo.save(expectedAdvertisement)).thenReturn(expectedAdvertisement);

        Advertisement actualAdvertisement = new Advertisement();
        actualAdvertisement.setName("Absolutely nothing");
        actualAdvertisement.setDescription("");

        User user = getUser();

        when(mockUserRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user.getEmail());


        FileInputStream fis = new FileInputStream(PATH_TO_FILE_JPG);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "Photo", "5.jpg", "image/jpeg", fis);

        AdvertisementResponse actual = underTestService.createNewAdvertisement(actualAdvertisement.getName(),
                actualAdvertisement.getDescription(), mockMultipartFile);

        assertEquals(expected, actual);


    }

    private List<Advertisement> expectedList() {
        List<Advertisement> resultList = new ArrayList<>();

        Advertisement advertisementFirst = new Advertisement();
        Advertisement advertisementSecond = new Advertisement();

        advertisementFirst.setName("Absolutely nothing");
        advertisementFirst.setDescription("");
        advertisementFirst.setPhoto("");
        advertisementFirst.setActive((short) 1);
        advertisementFirst.setId(0);

        advertisementSecond.setName("Zealand park");
        advertisementSecond.setDescription("");
        advertisementSecond.setPhoto("");
        advertisementSecond.setActive((short) 1);
        advertisementSecond.setId(1);

        resultList.add(advertisementFirst);
        resultList.add(advertisementSecond);

        return resultList;
    }

    private List<Advertisement> actualList() {

        List<Advertisement> resultList = new ArrayList<>();

        Advertisement advertisementFirst = new Advertisement();
        Advertisement advertisementSecond = new Advertisement();

        advertisementFirst.setName("Absolutely nothing");
        advertisementFirst.setDescription("");
        advertisementFirst.setPhoto("");
        advertisementFirst.setActive((short) 1);
        advertisementFirst.setId(0);

        advertisementSecond.setName("Zealand park");
        advertisementSecond.setDescription("");
        advertisementSecond.setPhoto("");
        advertisementSecond.setActive((short) 1);
        advertisementSecond.setId(1);

        resultList.add(advertisementFirst);
        resultList.add(advertisementSecond);

        return resultList;
    }

    private User getUser() {
        User user = new User();
        user.setName("Maxim");
        user.setEmail("MockEmail@Mock.ru");
        user.setPassword("1234");
        user.setRole("USER");

        return user;
    }

}