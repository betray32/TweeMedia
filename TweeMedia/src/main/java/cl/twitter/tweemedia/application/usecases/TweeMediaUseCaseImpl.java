package cl.twitter.tweemedia.application.usecases;

import cl.twitter.tweemedia.domain.model.GetMedia;
import cl.twitter.tweemedia.domain.model.ProcessedMedia;
import org.springframework.stereotype.Component;

@Component
public class TweeMediaUseCaseImpl implements TweeMediaUseCase {
    @Override
    public ProcessedMedia getMediaFromProfile(GetMedia media) {
        return null;
    }
}
