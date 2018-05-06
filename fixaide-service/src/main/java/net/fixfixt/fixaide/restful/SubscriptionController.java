package net.fixfixt.fixaide.restful;

import net.fixfixt.fixaide.model.Subscription;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rui Zhou on 2018/5/5.
 */

@RestController
@RequestMapping("/subscription")
public class SubscriptionController extends BaseController<Subscription> {

    public SubscriptionController() {
        super(Subscription.class);
    }


    @Override
    protected Subscription buildModel(String uuid) {
        return new Subscription(uuid);
    }
}
