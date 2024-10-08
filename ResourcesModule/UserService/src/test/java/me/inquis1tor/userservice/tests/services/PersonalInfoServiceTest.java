package me.inquis1tor.userservice.tests.services;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.dtos.PersonalInfoDto;
import me.inquis1tor.userservice.entities.PersonalInfoEntity;
import me.inquis1tor.userservice.mappers.PersonalInfoMapper;
import me.inquis1tor.userservice.providers.AccountEntityProvider;
import me.inquis1tor.userservice.providers.DtoProvider;
import me.inquis1tor.userservice.providers.PersonalInfoEntityProvider;
import me.inquis1tor.userservice.repositories.AccountRepository;
import me.inquis1tor.userservice.repositories.PersonalInfoRepository;
import me.inquis1tor.userservice.services.PersonalInfoService;
import me.inquis1tor.userservice.utils.LoggedAccountDetailsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

@SpringBootTest
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class PersonalInfoServiceTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    private final PersonalInfoService personalInfoService;
    private final PersonalInfoRepository personalInfoRepository;
    private final PersonalInfoEntityProvider personalInfoEntityProvider;
    private final AccountEntityProvider accountEntityProvider;
    private final DtoProvider<PersonalInfoDto> personalInfoDtoProvider;
    private final AccountRepository accountRepository;
    private final PersonalInfoMapper mapper;

    @MockBean
    private LoggedAccountDetailsProvider holder;

    @Test
    @DisplayName("updatePersonalInfo with active user entity in db")
    void updatePersonalInfoWithCorrectEntityInDb_EqualsExpected() {
        UUID accountId = UUID.randomUUID();
        Mockito.doReturn(accountId).when(holder).getAccountId();
        accountRepository.save(accountEntityProvider.activeUserEntity(accountId.toString()));
        PersonalInfoEntity personalInfoEntity = personalInfoEntityProvider.correctEntity(accountId.toString());
        personalInfoRepository.save(personalInfoEntity);
        PersonalInfoDto personalInfoDto = personalInfoDtoProvider.correctDto();
        personalInfoService.updatePersonalInfo(personalInfoDto);
        personalInfoEntity = mapper.dtoToPersonalInfo(personalInfoDto);
        personalInfoEntity.setId(accountId);
        Assertions.assertEquals(personalInfoEntity, personalInfoRepository.findById(accountId).get());
    }
}
