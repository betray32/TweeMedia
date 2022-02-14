package cl.twitter.tweemedia.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessedMedia {
    private boolean dataDownloaded;
    private String message;
}
