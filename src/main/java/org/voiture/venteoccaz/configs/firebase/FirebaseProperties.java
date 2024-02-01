package org.voiture.venteoccaz.configs.firebase;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@Data
@ConfigurationProperties(prefix = "gcp.firebase")
public class FirebaseProperties {
    private Resource serviceAccount;
}