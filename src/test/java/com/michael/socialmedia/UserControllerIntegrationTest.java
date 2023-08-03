package com.michael.socialmedia;

import com.michael.socialmedia.controller.UserController;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
public class UserControllerIntegrationTest {

    @Autowired
    private   MockMvc mockMvc;


    @Test
    public void testUploadProfilePic() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "multipartFile", // name of the file parameter in your controller method
                "test-image.jpg", // original filename
                "image/jpeg", // content type
                "Test image content".getBytes() // content as bytes
        );
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload/profile/image").file(file))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Profile image uploaded successfully."));


    }




}
