package me.inqu1sitor.interfaces;

@FunctionalInterface
public interface UniquenessChecker<T> {
    boolean check(T t);
}
