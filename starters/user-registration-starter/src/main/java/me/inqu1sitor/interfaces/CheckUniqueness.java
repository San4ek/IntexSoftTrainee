package me.inqu1sitor.interfaces;

@FunctionalInterface
public interface CheckUniqueness<T> {
    boolean check(T t);
}
