package net.fixfixt.fixaide.restful;

import net.fixfixt.fixaide.model.DataDict;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rui Zhou on 2018/5/5.
 */

@RestController
@RequestMapping("/data-dict")
public class DataDictController extends BaseController<DataDict> {


    public DataDictController() {
        super(DataDict.class);
    }

    @Override
    protected DataDict buildModel(String uuid) {
        return new DataDict(uuid);
    }
}
