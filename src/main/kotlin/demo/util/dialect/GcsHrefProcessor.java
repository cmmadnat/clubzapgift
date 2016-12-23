package demo.util.dialect;

import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractSingleAttributeModifierAttrProcessor;

/**
 * Created by thitiwat on 2/10/16.
 */
public class GcsHrefProcessor extends AbstractSingleAttributeModifierAttrProcessor {

    @Value("${gcs_url}")
    String GCS_URL;

    public GcsHrefProcessor() {
        super("href");
    }




    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    protected String getTargetAttributeName(Arguments arguments, Element element, String s) {
        return "href";
    }

    @Override
    protected String getTargetAttributeValue(Arguments arguments, Element element, String s) {
        return GCS_URL + element.getAttributeValue("href");

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
