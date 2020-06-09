package com.example.receiver;

import com.example.receiver.domain.TrafficEvent;
import com.example.receiver.domain.VehicleClass;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
@EnableBinding({Sink.class, TrafficEventReceiver.KStreamSink.class})
public class TrafficEventReceiver {

    @StreamListener(Sink.INPUT) // --> similar to JMS Listener
    public void receiveEvent(TrafficEvent trafficEvent) {
        // System.out.println(trafficEvent);
    }

    @StreamListener
    public void nativeKafkaProcessing(@Input("native-input") KStream<String, TrafficEvent> stream) {
        stream
                .filter((key, value) -> value.getVehicleClass() == VehicleClass.CAR)
                .foreach((key, value) -> System.out.println(value));
    }

    interface KStreamSink {

        @Input("native-input")
        KStream<String, TrafficEvent> input();
    }

}
