package demo.util.dialect;

import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by thitiwat on 2/10/16.
 */
public class GcsResourceDialect extends AbstractDialect {
    @Autowired
    private GcsHrefProcessor gcsProcessor;
    @Autowired
    GcsSrcProcessor gcsSrcProcessor;
    @Override
    public String getPrefix() {
        return "gcs";
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final HashSet<IProcessor> iProcessors = new HashSet<>();
        iProcessors.add(gcsProcessor);
        iProcessors.add(gcsSrcProcessor);
        return iProcessors;
    }
}
