package me.inqu1sitor.authservice.rabbit;

@FunctionalInterface
public interface Notifier<T> {

    void notifyAbout(T t);
}
