package de.htwg.stratego.aview;

import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.IncomingConnection;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.*;
import akka.japi.function.Function;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import de.htwg.stratego.controller.ISingelDeviceStrategoController;

import java.util.concurrent.CompletionStage;

public class StrategoService {

    private ActorSystem system = ActorSystem.create();
    private final Materializer materializer = ActorMaterializer.create(system);

    private ISingelDeviceStrategoController sc;

    public StrategoService(ISingelDeviceStrategoController sc) {
        this.sc = sc;

        Source<IncomingConnection, CompletionStage<ServerBinding>> serverSource =
                Http.get(system).bind(ConnectHttp.toHost("localhost", 8080), materializer);

        final Function<HttpRequest, HttpResponse> requestHandler =
                new Function<HttpRequest, HttpResponse>() {
                    private final HttpResponse NOT_FOUND =
                            HttpResponse.create()
                                    .withStatus(404)
                                    .withEntity("Unknown resource!");


                    @Override
                    public HttpResponse apply(HttpRequest request) throws Exception {
                        Uri uri = request.getUri();
                        if (request.method() == HttpMethods.GET) {
                            if (uri.path().equals("/stratego")) {
                                return HttpResponse.create()
                                        .withEntity(sc.toJson());
                            } else if (uri.path().equals("/stratego/add")) {
                                int x = Integer.valueOf(uri.query().get("x").get());
                                int y = Integer.valueOf(uri.query().get("y").get());
                                int rank = Integer.valueOf(uri.query().get("rank").get());

                                sc.add(x, y, rank);

                                return HttpResponse.create()
                                        .withEntity(sc.getStatusString());
                            } else if (uri.path().equals("/stratego/swap")) {
                                int x1 = Integer.valueOf(uri.query().get("x1").get());
                                int y1 = Integer.valueOf(uri.query().get("y1").get());
                                int x2 = Integer.valueOf(uri.query().get("x2").get());
                                int y2 = Integer.valueOf(uri.query().get("y2").get());

                                sc.swap(x1, y1, x2, y2);

                                return HttpResponse.create()
                                        .withEntity(sc.getStatusString());
                            } else if (uri.path().equals("/stratego/remove")) {
                                int x = Integer.valueOf(uri.query().get("x").get());
                                int y = Integer.valueOf(uri.query().get("y").get());

                                sc.remove(x, y);

                                return HttpResponse.create()
                                        .withEntity(sc.getStatusString());
                            } else if (uri.path().equals("/stratego/move")) {
                                int x1 = Integer.valueOf(uri.query().get("x1").get());
                                int y1 = Integer.valueOf(uri.query().get("y1").get());
                                int x2 = Integer.valueOf(uri.query().get("x2").get());
                                int y2 = Integer.valueOf(uri.query().get("y2").get());

                                sc.move(x1, y1, x2, y2);

                                return HttpResponse.create()
                                        .withEntity(sc.getStatusString());
                            } else if (uri.path().equals("/stratego/finish")) {
                                sc.finish();

                                return HttpResponse.create()
                                        .withEntity(sc.getStatusString());
                            } else {
                                return NOT_FOUND;
                            }
                        } else {
                            return NOT_FOUND;
                        }
                    }
                };

        CompletionStage<ServerBinding> serverBindingFuture =
                serverSource.to(Sink.foreach(connection -> {
                    System.out.println("Accepted new connection from " + connection.remoteAddress());

                    connection.handleWithSyncHandler(requestHandler, materializer);
                    // this is equivalent to
                    //connection.handleWith(Flow.of(HttpRequest.class).map(requestHandler), materializer);
                })).run(materializer);
    }

}
