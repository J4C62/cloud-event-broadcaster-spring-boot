package com.github.j4c62.infrastructure.deliverer.repository;

import com.github.j4c62.delivery.Deliverer;
import com.github.j4c62.delivery.Diffusible;
import com.github.j4c62.selector.DelivererSelector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DelivererFinder implements DelivererSelector {
  private final List<Deliverer> deliverers;

  @Override
  public List<Deliverer> findDelivers(Diffusible diffusible) {
    return deliverers.stream()
        .filter(d -> d.getChannel().equals(diffusible.getChannel()))
        .toList();

  }
}
