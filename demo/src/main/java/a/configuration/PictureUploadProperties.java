package a.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "upload.properties")
public class PictureUploadProperties {
    private Resource uploadPatch;
    private Resource anonymousPicture;

    public Resource getAnonymousPicture() {
        return anonymousPicture;
    }

    public void setAnonymousPicture(String anonymousPicture) {
        this.anonymousPicture = new DefaultResourceLoader().getResource(anonymousPicture);
    }

    public Resource getUploadPatch() {
        return uploadPatch;
    }

    public void setUploadPatch(String uploadPatch) {
        this.uploadPatch = new DefaultResourceLoader().getResource(uploadPatch);
    }
}
