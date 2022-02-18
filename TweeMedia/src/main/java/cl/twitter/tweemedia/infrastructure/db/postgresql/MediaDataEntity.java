package cl.twitter.tweemedia.infrastructure.db.postgresql;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "mediadata")
public class MediaDataEntity {
    @Id
    private String transactionId;
    
    private LocalDateTime transactionRequest;
    private String requestedProfile;
    private int getMediaPhoto;
    private int getMediaVideo;
}
