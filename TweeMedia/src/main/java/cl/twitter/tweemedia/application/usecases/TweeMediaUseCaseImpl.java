package cl.twitter.tweemedia.application.usecases;

import cl.twitter.tweemedia.application.service.TwitterManagementService;
import cl.twitter.tweemedia.domain.model.GetMedia;
import cl.twitter.tweemedia.domain.model.ProcessedMedia;
import cl.twitter.tweemedia.infrastructure.db.postgresql.MediaDataEntity;
import cl.twitter.tweemedia.infrastructure.db.postgresql.MediaDataRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TweeMediaUseCaseImpl implements TweeMediaUseCase {

    private static final String OK = "OK";
    private static final String ERROR = "Error";
    private static final String ERROR_WHEN_EXPORTING_DATA_FROM_PROFILE_DETAIL = "Error when exporting data from profile, Detail > ";

    @Value("${tweemedia.directory}")
    public String directory;

    private final MediaDataRepository mediaDataRepository;
    private final TwitterManagementService twitterManagementService;

    @Override
    public ProcessedMedia getMediaFromProfile(GetMedia media) {

        try {
            twitterManagementService.saveMedia(media.getGetPhotos(), media.getGetVideos(), media.getTwitterProfile(), directory, media.getRegistryCount());
            mediaDataRepository.save(buildMediaDataEntity(media));
            return buildProcessedMedia(true);
        } catch (Exception e) {
            log.error(ERROR_WHEN_EXPORTING_DATA_FROM_PROFILE_DETAIL, e);
        } finally {
            return buildProcessedMedia(false);
        }
    }

    private ProcessedMedia buildProcessedMedia(boolean result) {
        return ProcessedMedia.builder()
                .dataDownloaded(result)
                .message(result ? OK : ERROR).build();
    }

    private MediaDataEntity buildMediaDataEntity(GetMedia media) {
        return MediaDataEntity.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionRequest(LocalDateTime.now())
                .requestedProfile(media.getTwitterProfile())
                .getMediaPhoto(media.getGetPhotos())
                .getMediaVideo(media.getGetVideos()).build();

    }
}
