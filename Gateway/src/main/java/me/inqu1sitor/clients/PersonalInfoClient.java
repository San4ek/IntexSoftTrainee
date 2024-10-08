package me.inqu1sitor.clients;

import me.inqu1sitor.config.FeignClientConfig;
import me.inqu1sitor.dtos.PersonalInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "personalinfos",
        url = "${remote-services.user-service.url}" + "/api/personal-infos",
        configuration = FeignClientConfig.class
)
public interface PersonalInfoClient {

    @PutMapping
    ResponseEntity<Object> updatePersonalInfo(@RequestBody PersonalInfoDto dto);
}
