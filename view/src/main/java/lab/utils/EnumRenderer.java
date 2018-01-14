package lab.utils;

import com.vaadin.ui.renderers.TextRenderer;
import elemental.json.Json;
import elemental.json.JsonValue;


public class EnumRenderer<T extends  Enum> extends TextRenderer {
    @Override
    public JsonValue encode(Object value) {
        if (value == null) {
            return super.encode(null);
        } else {
            return Json.create((new EnumItemCaptionGenerator<>()).apply((Enum) value));
        }
    }
}

