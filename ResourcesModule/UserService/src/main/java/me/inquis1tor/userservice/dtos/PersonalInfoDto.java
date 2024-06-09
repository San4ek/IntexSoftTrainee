package me.inquis1tor.userservice.dtos;

public record PersonalInfoDto(
    String name,
    String surname,
    String patronymic,
    String phoneNumber
) {}
