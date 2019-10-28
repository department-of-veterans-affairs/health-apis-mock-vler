package gov.va.api.health.mockvler;

import gov.va.api.health.autoconfig.configuration.JacksonConfig;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockVlerController {
  private static final AddressResponse ADDRESSES = loadAddresses();

  @SneakyThrows
  private static AddressResponse loadAddresses() {
    Resource[] resources =
        new PathMatchingResourcePatternResolver().getResources("classpath*:data/*.json");
    List<AddressResponse.Contact> contacts = new ArrayList<>();
    for (Resource resource : resources) {
      try (InputStream inputStream = resource.getInputStream()) {
        contacts.add(
            JacksonConfig.createMapper().readValue(inputStream, AddressResponse.Contact.class));
      }
    }
    return AddressResponse.builder().contacts(contacts).count(contacts.size()).build();
  }

  /** Return Mock VLER Response. */
  @ResponseBody
  @SneakyThrows
  @GetMapping(value = "direct/addresses", produces = "application/json")
  public AddressResponse getAddresses() {
    return ADDRESSES;
  }
}
