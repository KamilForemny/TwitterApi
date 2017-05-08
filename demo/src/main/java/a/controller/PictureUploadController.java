package a.controller;

import a.configuration.PictureUploadProperties;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@SessionAttributes("picturePath")
public class PictureUploadController {
    public static final Resource PICTURES_DIR = new FileSystemResource("./pictures");
    private final Resource picturesDir;
    private final Resource anonymousPicture;

    @Autowired
    public PictureUploadController(PictureUploadProperties uploadProperties){
        this.picturesDir=uploadProperties.getUploadPatch();
        this.anonymousPicture=uploadProperties.getAnonymousPicture();
    }

    @RequestMapping("upload")
    public String uploadPage(){
        return "/uploadPage";
    }

    @RequestMapping(value = "/profile", params = {"upload"},method = RequestMethod.POST)
    public String onUpload(MultipartFile file, RedirectAttributes redirectAttributes, Model model) throws IOException {
        if(file.isEmpty() || !isImage(file)){
            redirectAttributes.addFlashAttribute("error","niewwasciwy plik");
            return "redirect:/profilePage";
        }
        Resource picResource = copyFileToPictures(file);
        model.addAttribute("picturePath",picResource);

        //copyFileToPictures(file);
        return "/profilePage";

    }

    @RequestMapping(value = "/uploadedPicture")
    public void getUploadedPicture(HttpServletResponse response, @ModelAttribute("picturePath")Path picturePath) throws IOException {
        //ClassPathResource classPathResource = new ClassPathResource("/images/anonymous.png");
        response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath.toString()));
        Files.copy(picturePath,response.getOutputStream());
    }

    private Resource copyFileToPictures(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("pic",fileExtension,picturesDir.getFile());
        try (InputStream in = file.getInputStream();
             OutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in,out);
        }
        return new FileSystemResource(tempFile);
    }

    @ModelAttribute
    public Resource picturePath(){
        return anonymousPicture;
    }



    public static String getFileExtension(String name){
        return name.substring(name.lastIndexOf("."));
    }

    public boolean isImage(MultipartFile file){
        return file.getContentType().startsWith("image");
    }
}
