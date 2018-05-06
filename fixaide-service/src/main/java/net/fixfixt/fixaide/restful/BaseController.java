package net.fixfixt.fixaide.restful;

import net.fixfixt.fixaide.api.CrudServiceGrpc.CrudServiceBlockingStub;
import net.fixfixt.fixaide.api.EnvelopeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Rui Zhou on 2018/5/5.
 */

public abstract class BaseController<T> {

    @Autowired
    private EnvelopeHelper envelopeHelper;

    @Autowired
    private CrudServiceBlockingStub stub;

    private Class<T> modelClass;

    public BaseController(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    @PostConstruct
    private void postConstruct() {
    }

    protected abstract T buildModel(String uuid);

    @GetMapping()
    public List<T> findAll() {
        return envelopeHelper.callList(null, stub::findAll);
    }

    @GetMapping("/{uuid}")
    public T read(@PathVariable String uuid) {
        return (T) envelopeHelper.call(buildModel(uuid), stub::read);
    }

    @PostMapping()
    public T create(@RequestBody T payload) {
        return (T) envelopeHelper.call(payload, stub::create);
    }

    @DeleteMapping()
    public T delete(@RequestBody T payload) {
        return (T) envelopeHelper.call(payload, stub::delete);
    }

}
