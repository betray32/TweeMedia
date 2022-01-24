package cl.twitter.TweeMedia.management;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class TwitterInstance {
    public static Twitter getTwitterinstance() {
        Twitter twitter = TwitterFactory.getSingleton();
        return twitter;
    }
}
