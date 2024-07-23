package me.inquis1tor.userservice.providers;

public interface DtoProvider<T> {

    T correctDto();

    T incorrectDto();
}
