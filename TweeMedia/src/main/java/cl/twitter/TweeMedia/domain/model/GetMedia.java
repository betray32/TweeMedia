package cl.twitter.tweemedia.domain.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetMedia {
    private String twitterProfile;
    private int registryCount;
    private int getPhotos;
    private int getVideos;
}
