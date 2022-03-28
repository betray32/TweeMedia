package cl.twitter.tweemedia.application.usecases;

import cl.twitter.tweemedia.domain.model.GetMedia;
import cl.twitter.tweemedia.domain.model.ProcessedMedia;

public interface TweeMediaUseCase {
    ProcessedMedia getMediaFromProfile(GetMedia media);
}
