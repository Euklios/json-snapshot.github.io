package io.github.jsonSnapshot;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Separators;

public class CustomDefaultPrettyPrinter extends DefaultPrettyPrinter {
    public CustomDefaultPrettyPrinter(String s) {
        super(s);
    }

    @Override
    public DefaultPrettyPrinter withSeparators(Separators separators) {
        this._separators = separators;
        this._objectFieldValueSeparatorWithSpaces = separators.getObjectFieldValueSeparator() + " ";
        return this;
    }

    @Override
    public DefaultPrettyPrinter createInstance() {
        return new DefaultPrettyPrinter(this);
    }
}
