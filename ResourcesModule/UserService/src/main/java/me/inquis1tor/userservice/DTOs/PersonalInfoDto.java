package me.inquis1tor.userservice.DTOs;

public record PersonalInfoDto(
    String name,
    String surname,
    String patronymic,
    String phoneNumber
) {}
