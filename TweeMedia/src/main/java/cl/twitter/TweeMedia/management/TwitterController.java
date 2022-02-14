package cl.twitter.tweemedia.management;

public interface TwitterController {
    public boolean saveMedia(Integer photos, Integer videos, String profile, String path, Integer registryCount);
}
