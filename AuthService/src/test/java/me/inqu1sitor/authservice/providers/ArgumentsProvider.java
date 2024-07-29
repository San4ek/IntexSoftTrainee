package me.inqu1sitor.authservice.providers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class ArgumentsProvider {

    private static Stream<Arguments> activeNotAdminEntities() {
        return Stream.of(
                Arguments.of(AccountEntityProvider.activeUserEntity(), "active user"),
                Arguments.of(AccountEntityProvider.activeModerEntity(), "active moder")
        );
    }

    private static Stream<Arguments> blockedNotAdminEntities() {
        return Stream.of(
                Arguments.of(AccountEntityProvider.blockedUserEntity(), "blocked user"),
                Arguments.of(AccountEntityProvider.blockedModerEntity(), "blocked moder")
        );
    }

    private static Stream<Arguments> deletedNotAdminEntities() {
        return Stream.of(
                Arguments.of(AccountEntityProvider.deletedUserEntity(), "deleted user"),
                Arguments.of(AccountEntityProvider.deletedModerEntity(), "deleted moder")
        );
    }

    private static Stream<Arguments> notActiveAdminEntities() {
        return Stream.of(
                Arguments.of(AccountEntityProvider.blockedAdminEntity(), "blocked admin"),
                Arguments.of(AccountEntityProvider.deletedAdminEntity(), "deleted admin")
        );
    }

    private static Stream<Arguments> activeAdminEntity() {
        return Stream.of(
                Arguments.of(AccountEntityProvider.activeAdminEntity(), "active admin")
        );
    }
}
