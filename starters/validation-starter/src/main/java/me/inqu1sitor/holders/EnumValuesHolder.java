package me.inqu1sitor.holders;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class EnumValuesHolder<T extends Enum<T>> {

    public final Set<String> values;

    public EnumValuesHolder(Class<T> clazz) {
        values = Arrays.stream(clazz.getEnumConstants()).map(Enum::name).collect(Collectors.toSet());
    }

    public boolean contains(String value) {
        return values.contains(value);
    }
}
