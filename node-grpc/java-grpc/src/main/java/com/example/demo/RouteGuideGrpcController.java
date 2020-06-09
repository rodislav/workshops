package com.example.demo;

import io.grpc.examples.routeguide.RouteGuideGrpc.RouteGuideImplBase;
import io.grpc.examples.routeguide.Point;
import io.grpc.examples.routeguide.Feature;
import io.grpc.examples.routeguide.Rectangle;
import io.grpc.examples.routeguide.RouteNote;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RouteGuideGrpcController extends RouteGuideImplBase {

    @Override
    public void getFeature(Point request, StreamObserver<Feature> responseObserver) {
        super.getFeature(request, responseObserver);
    }

    @Override
    public void listFeatures(Rectangle request, StreamObserver<io.grpc.examples.routeguide.Feature> responseObserver) {
        super.listFeatures(request, responseObserver);
    }

    @Override
    public StreamObserver<Point> recordRoute(StreamObserver<io.grpc.examples.routeguide.RouteSummary> responseObserver) {
        return super.recordRoute(responseObserver);
    }

    @Override
    public StreamObserver<RouteNote> routeChat(StreamObserver<RouteNote> responseObserver) {
        return super.routeChat(responseObserver);
    }
}
