package lab.utils;

import com.vaadin.ui.renderers.TextRenderer;
import elemental.json.Json;
import elemental.json.JsonValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class LocalDateRenderer extends TextRenderer {
    private final String pattern;
    
    public LocalDateRenderer(String pattern) {
        super("");
        this.pattern = pattern;
    }
    
    public LocalDateRenderer(String nullRepresentation, String pattern) {
        super(nullRepresentation);
        this.pattern = pattern;
    }
    
    @Override
    public JsonValue encode(Object value) {
        LocalDate localDate = (LocalDate) value;
        if (value == null) {
            return super.encode(null);
        } else {
            return Json.create(localDate.format(DateTimeFormatter.ofPattern(pattern)));
        }
    }
}