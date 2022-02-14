package cl.twitter.tweemedia.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropDefinitions {
    @Value("${tweemedia.photos}")
    public Integer photos;

    @Value("${tweemedia.videos}")
    public Integer videos;

    @Value("${tweemedia.profile}")
    public String profile;

    @Value("${tweemedia.directory}")
    public String directory;

    @Value("${tweemedia.registryCount}")
    public Integer registryCount;
}
