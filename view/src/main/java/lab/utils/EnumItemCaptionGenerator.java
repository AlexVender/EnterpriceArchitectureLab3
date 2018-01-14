package lab.utils;

import com.vaadin.ui.ItemCaptionGenerator;

import java.util.Locale;


public class EnumItemCaptionGenerator<T extends Enum> implements ItemCaptionGenerator<T> {
    private Locale locale;
    
    public EnumItemCaptionGenerator() {
        this.locale = Locale.getDefault();
    }
    
    public EnumItemCaptionGenerator(Locale locale) {
        this.locale = locale;
    }
    
    @Override
    public String apply(T item) {
        if (locale == null) {
            Locale locale = Locale.getDefault();
        }
    
        String enumString = item.toString();
        if (enumString.equals(item.name())) {
            String result = enumString.substring(0, 1).toUpperCase(locale);
            result += enumString.substring(1).toLowerCase(locale).replace('_', ' ');
            return result;
        } else {
            return enumString;
        }
    }
}
