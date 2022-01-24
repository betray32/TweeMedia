package cl.twitter.TweeMedia.management;

import org.springframework.stereotype.Service;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Service
public class TwitterFunctions {
    public ResponseList<Status> getMediaFromTimeline(String userId, int registryCount) throws TwitterException {
        Twitter twitter = TwitterInstance.getTwitterinstance();
        Paging p = new Paging();
        p.setCount(registryCount);
        return twitter.getUserTimeline(userId, p);
    }
}
