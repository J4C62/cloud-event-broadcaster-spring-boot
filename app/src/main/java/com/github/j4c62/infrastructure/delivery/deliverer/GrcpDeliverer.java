package com.github.j4c62.infrastructure.delivery.deliverer;

import com.github.j4c62.data.Channel;
import com.github.j4c62.delivery.Deliverer;
import com.github.j4c62.delivery.Diffusible;
import com.github.j4c62.infrastructure.delivery.dto.EventGrcp;
import com.github.j4c62.infrastructure.grcp.EventEmitterGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrcpDeliverer implements Deliverer {

  @GrpcClient("EventEmitter")
  private EventEmitterGrpc.EventEmitterBlockingStub eventEmitterBlockingStub;

  @Override
  public void deliver(Diffusible diffusible) {
    EventGrcp eventGrcp = (EventGrcp) diffusible;
    String message = eventEmitterBlockingStub.emitEvent(eventGrcp.eventRequest()).getMessage();
    System.out.println("Event grcp send " + message);
  }

  @Override
  public Channel getChannel() {
    return Channel.GRCP;
  }
}
