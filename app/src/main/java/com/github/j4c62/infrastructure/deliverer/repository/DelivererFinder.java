package com.github.j4c62.infrastructure.deliverer.repository;

import com.github.j4c62.delivery.Deliverer;
import com.github.j4c62.delivery.Diffusible;
import com.github.j4c62.infrastructure.deliverer.EmailDeliverer;
import com.github.j4c62.selector.DelivererSelector;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DelivererFinder implements DelivererSelector {
  @Override
  public List<Deliverer> findDelivers(Diffusible diffusible) {
    return List.of(new EmailDeliverer());
  }
}
