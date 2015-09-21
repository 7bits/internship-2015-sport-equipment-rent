package it.sevenbits.web.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * Created by awemath on 9/21/15.
 */
@Service
@ConfigurationProperties(prefix = "assets")
public class AssetsResolver {
    private String version;

    public String getVersionedResourceUrl(final String filePath) {
        final String filePathVersioned = filePath.replace("#version", version);
        return filePathVersioned;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}