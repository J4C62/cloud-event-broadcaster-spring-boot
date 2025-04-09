package com.github.j4c62.infrastructure.deliverer;

import com.github.j4c62.data.Channel;
import com.github.j4c62.delivery.Deliverer;
import com.github.j4c62.delivery.Diffusible;

public class EmailDeliverer implements Deliverer {
  @Override
  public void deliver(Diffusible diffusible) {
    System.out.println("Sending ..." + diffusible);
  }

  @Override
  public Channel getChannel() {
    return Channel.EMAIL;
  }
}
