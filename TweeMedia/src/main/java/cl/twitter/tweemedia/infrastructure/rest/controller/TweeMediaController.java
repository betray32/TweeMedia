package cl.twitter.tweemedia.infrastructure.rest.controller;

import cl.twitter.tweemedia.application.usecases.TweeMediaUseCase;
import cl.twitter.tweemedia.domain.model.GetMedia;
import cl.twitter.tweemedia.domain.model.ProcessedMedia;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TweeMediaController {

    private final TweeMediaUseCase tweeMediaUseCase;

    @PostMapping(path = "/GetMedia", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessedMedia> getMediaFromProfile(@RequestBody GetMedia media) {
        return ResponseEntity.ok().body(tweeMediaUseCase.getMediaFromProfile(media));
    }

}
