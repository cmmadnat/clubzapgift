package demo.util.dialect;

import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractSingleAttributeModifierAttrProcessor;

/**
 * Created by thitiwat on 2/10/16.
 */
public class GcsSrcProcessor extends AbstractSingleAttributeModifierAttrProcessor {

    @Value("${gcs_url}")
    String GCS_URL;

    public GcsSrcProcessor() {
        super("src");
    }




    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    protected String getTargetAttributeName(Arguments arguments, Element element, String s) {
        return "src";
    }

    @Override
    protected String getTargetAttributeValue(Arguments arguments, Element element, String s) {
        return GCS_URL + element.getAttributeValue("src");

    }

    @Override
    protected ModificationType getModificationType(Arguments arguments, Element element, String s, String s1) {
        return ModificationType.SUBSTITUTION;
    }

    @Override
    protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String s, String s1) {
        return false;
    }

    @Override
    protected boolean recomputeProcessorsAfterExecution(Arguments arguments, Element element, String s) {
        return false;
    }
}
