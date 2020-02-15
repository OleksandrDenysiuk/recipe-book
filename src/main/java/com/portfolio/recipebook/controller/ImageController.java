package com.portfolio.recipebook.controller;

import com.portfolio.recipebook.model.Recipe;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    @GetMapping("recipe/{id}/image")
    public void renderImageFromDB(@PathVariable("id")Recipe recipe, HttpServletResponse response) throws IOException {

        if (recipe.getImage() != null) {
            byte[] byteArray = new byte[recipe.getImage().length];
            int i = 0;

            for (Byte wrappedByte : recipe.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
