package cl.twitter.tweemedia.application.service;

import cl.twitter.tweemedia.utils.TweeMediaUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
public class TwitterManagementService {

    private static final String PHOTO = "photo";
    private static final String VIDEO = "video";

    public boolean saveMedia(Integer photos, Integer videos, String profile, String path, Integer registryCount) {
        
        ResponseList<Status> mediaList = null;

        try {
            mediaList = getMediaFromTimeline(profile, registryCount);
        } catch (TwitterException e) {
            System.err.println("Program has failed when retrieve info from twitter, Detail > " + e.getMessage());
        }

        if (mediaList == null) {
            System.err.println("Error When connecting to twitter, Aborting...");
            return false;
        }

        System.out.println("Data OK!");
        System.out.println("Filtering Media Files...");

        List<MediaEntity> lPhotos = new ArrayList<>();
        List<MediaEntity> lVideos = new ArrayList<>();
        mediaFilter(mediaList, lPhotos, lVideos);

        System.out.println("Photos [" + lPhotos.size() + "] Files");
        System.out.println("Videos [" + lVideos.size() + "] Files");
        System.out.println("Total of Media Found [" + (lPhotos.size() + lVideos.size()) + "] Files");
        System.out.println("Writing in disk...");

        savePhotos(photos, path, lPhotos);
        saveVideos(videos, path, lVideos);
        return true;
    }

    private void saveVideos(Integer videos, String path, List<MediaEntity> lVideos) {
        if (videos == 1) {
            System.out.println("******************");
            System.out.println("Downloading Videos");
            for (MediaEntity v : lVideos) {
                Variant[] videoVariants = v.getVideoVariants();
                Variant betterQuality = videoVariants[0];
                String urlVideo = betterQuality.getUrl().substring(0, betterQuality.getUrl().indexOf("?"));
                System.out.println("URL [" + urlVideo + "]");
                TweeMediaUtils.writeMediaIntoFile(urlVideo, path);
            }
            System.out.println("******************");
        }
    }

    private void savePhotos(Integer photos, String path, List<MediaEntity> lPhotos) {
        if (photos == 1) {
            System.out.println("******************");
            System.out.println("Downloading Photos");
            for (MediaEntity f : lPhotos) {
                String urlFoto = f.getMediaURL();
                System.out.println("URL [" + urlFoto + "]");
                TweeMediaUtils.writeMediaIntoFile(urlFoto, path);
            }
            System.out.println("******************");
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
