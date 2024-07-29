package me.inqu1sitor.authservice.providers;

public interface DtoProvider<T> {

    T correctDto();

    T incorrectDto();
}
