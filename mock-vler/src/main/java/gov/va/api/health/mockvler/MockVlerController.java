package gov.va.api.health.mockvler;

import com.fasterxml.jackson.databind.ObjectMapper;
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

  /** Generate and return Mock VLER Response. */
  @GetMapping(value = "direct/addresses", produces = "application/json")
  @ResponseBody
  @SneakyThrows
  public AddressResponse getAddresses() {
    Resource[] resources =
        new PathMatchingResourcePatternResolver().getResources("classpath:data/*.json");
    ObjectMapper mapper = new ObjectMapper();
    List<AddressResponse.Contact> contacts = new ArrayList<>();
    for (int i = 0; i < resources.length; i++) {
      contacts.add(mapper.readValue(resources[i].getFile(), AddressResponse.Contact.class));
    }
    return AddressResponse.builder().contacts(contacts).count(contacts.size()).build();
  }
}
