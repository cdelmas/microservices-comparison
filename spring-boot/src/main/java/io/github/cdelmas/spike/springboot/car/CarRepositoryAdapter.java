package io.github.cdelmas.spike.springboot.car;

import io.github.cdelmas.spike.common.persistence.InMemoryCarRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepositoryAdapter extends InMemoryCarRepository {

}
