package me.inquis1tor.userservice.DTOs;

public record PersonalInfoDTO(
    String name,
    String surname,
    String patronymic,
    String phoneNumber
) {}
