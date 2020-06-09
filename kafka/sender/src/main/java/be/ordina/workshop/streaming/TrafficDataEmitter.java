package be.ordina.workshop.streaming;

import be.ordina.workshop.streaming.domain.TrafficEvent;
import generated.traffic.Miv;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.util.List;
import java.util.function.Function;

/**
 * @author Tim Ysewyn
 */
@Component
@EnableBinding({Source.class})
public class TrafficDataEmitter {

    private final TrafficDataConverter trafficDataConverter = new TrafficDataConverter();
    private final TrafficDataRetriever trafficDataRetriever;

    private final Source source;

    public TrafficDataEmitter(TrafficDataRetriever trafficDataRetriever,
                              @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") Source source) {
        this.trafficDataRetriever = trafficDataRetriever;
        this.source = source;
    }

    @Scheduled(fixedRate = 60_000L)
    public void retrieveAndSendEvents() {
        this.getTrafficDataEventsAsList().forEach(te ->
                this.source.output().send(
                        MessageBuilder.withPayload(te).build()));
    }

    private List<TrafficEvent> getTrafficDataEventsAsList() {
        return this.getTrafficDataEvents().collectList().block();
    }

    private Flux<TrafficEvent> getTrafficDataEvents() {
        return this.trafficDataRetriever.getTrafficData()
                .map(Miv::getMeetpunt)
                .flatMapIterable(Function.identity())
                .flatMap(meetpunt ->
                        Flux.fromStream(meetpunt.getMeetdata().stream()
                                .map(meetdata -> Tuples.of(meetpunt, meetdata))))
                .map(trafficDataConverter::convertToTrafficEvent);
    }

}
