package net.fixfixt.fixaide.repository;

import net.fixfixt.fixaide.model.DataDict;
import net.fixfixt.fixaide.model.Subscription;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Rui Zhou on 2018/5/6.
 */
public interface SubscriptionRepository extends CrudRepository<Subscription, String> {
}
