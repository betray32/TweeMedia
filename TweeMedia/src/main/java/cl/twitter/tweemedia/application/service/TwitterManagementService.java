package cl.twitter.tweemedia.application.service;

import cl.twitter.tweemedia.utils.TweeMediaUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.EntitySupport;
import twitter4j.MediaEntity;
import twitter4j.MediaEntity.Variant;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class TwitterManagementService {

    private static final String PHOTO = "photo";
    private static final String VIDEO = "video";
    private static final String RETRIEVE_INFO_FROM_TWITTER_DETAIL = "Program has failed when retrieve info from twitter, Detail > ";
    private static final String GETTING_DATA_FROM_PROFILE = "Getting data from profile '{}'";
    private static final String ERROR_WHEN_CONNECTING_TO_TWITTER_ABORTING = "Error When connecting to twitter, Aborting...";
    private static final String DATA_OK = "Data OK!";
    private static final String FILTERING_MEDIA_FILES = "Filtering Media Files...";
    private static final String THERE_S_NO_VIDEO_OR_PHOTO_DATA_TO_DOWNLOAD_FROM_REQUESTED_PROFILE = "There's No Video or Photo Data to download from requested Profile";
    private static final String WRITING_IN_DISK = "Writing in disk...";
    private static final String MEDIA_RETRIEVED_SUCCESSFULLY_FROM_REQUESTED_PROFILE = "Media Retrieved Successfully from Requested Profile";
    private static final String DOWNLOADING_VIDEOS = "Downloading Videos";
    private static final String SEPARATOR = "******************";
    private static final String DOWNLOADING_PHOTOS = "Downloading Photos";

    private String responseMessage;

    public boolean saveMedia(Integer photos, Integer videos, String profile, String path, Integer registryCount) {
        ResponseList<Status> mediaList = null;
        try {
            log.info(GETTING_DATA_FROM_PROFILE, profile);
            mediaList = getMediaFromTimeline(profile, registryCount);

            if (mediaList == null) {
                responseMessage = ERROR_WHEN_CONNECTING_TO_TWITTER_ABORTING;
                return false;
            }
        } catch (TwitterException e) {
            responseMessage = ERROR_WHEN_CONNECTING_TO_TWITTER_ABORTING + e.getMessage();
            return false;
        }

        log.info(DATA_OK);
        log.info(FILTERING_MEDIA_FILES);

        List<MediaEntity> lPhotos = new ArrayList<>();
        List<MediaEntity> lVideos = new ArrayList<>();
        mediaFilter(mediaList, lPhotos, lVideos);
        if (NoMediaProfile(lPhotos, lVideos)) {
            responseMessage = THERE_S_NO_VIDEO_OR_PHOTO_DATA_TO_DOWNLOAD_FROM_REQUESTED_PROFILE;
            return false;
        }

        log.info("Photos [" + lPhotos.size() + "] Files");
        log.info("Videos [" + lVideos.size() + "] Files");
        log.info("Total of Media Found [" + (lPhotos.size() + lVideos.size()) + "] Files");
        log.info(WRITING_IN_DISK);

        if (lPhotos.size() > 0)
            savePhotos(photos, path, lPhotos);

        if (lVideos.size() > 0)
            saveVideos(videos, path, lVideos);

        responseMessage = MEDIA_RETRIEVED_SUCCESSFULLY_FROM_REQUESTED_PROFILE;
        log.info(MEDIA_RETRIEVED_SUCCESSFULLY_FROM_REQUESTED_PROFILE);
        return true;
    }

    private boolean NoMediaProfile(List<MediaEntity> lPhotos, List<MediaEntity> lVideos) {
        return lPhotos.isEmpty() && lVideos.isEmpty();
    }

    private void saveVideos(Integer videos, String path, List<MediaEntity> lVideos) {
        if (videos == 1) {
            log.info(SEPARATOR);
            log.info(DOWNLOADING_VIDEOS);
            for (MediaEntity v : lVideos) {
                Variant[] videoVariants = v.getVideoVariants();
                Variant betterQuality = videoVariants[0];
                String urlVideo = betterQuality.getUrl().substring(0, betterQuality.getUrl().indexOf("?"));
                log.info("URL [" + urlVideo + "]");
                TweeMediaUtils.writeMediaIntoFile(urlVideo, path);
            }
            log.info(SEPARATOR);
        }
    }

    private void savePhotos(Integer photos, String path, List<MediaEntity> lPhotos) {
        if (photos == 1) {
            log.info(SEPARATOR);
            log.info(DOWNLOADING_PHOTOS);
            for (MediaEntity f : lPhotos) {
                String urlPhoto = f.getMediaURL();
                log.info("URL [" + urlPhoto + "]");
                TweeMediaUtils.writeMediaIntoFile(urlPhoto, path);
            }
            log.info(SEPARATOR);
        }
    }

    private void mediaFilter(ResponseList<Status> mediaList, List<MediaEntity> lFotos, List<MediaEntity> lVideos) {
        mediaList.stream().map(EntitySupport::getMediaEntities).flatMap(Arrays::stream).forEach(entity -> {
            if (PHOTO.equalsIgnoreCase(entity.getType())) {
                lFotos.add(entity);
                return;
            } else if (VIDEO.equalsIgnoreCase(entity.getType())) {
                lVideos.add(entity);
                return;
            }
        });
    }

    private ResponseList<Status> getMediaFromTimeline(String userId, int registryCount) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        Paging p = new Paging();
        p.setCount(registryCount);
        return twitter.getUserTimeline(userId, p);
    }

}
