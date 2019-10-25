package gov.va.api.health.mockvler;

import com.fasterxml.jackson.core.JsonFactory;
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
    List<AddressResponse.Contact> contacts = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    JsonFactory jsonFactory = new JsonFactory();
    for (int i = 0; i < resources.length; i++) {
      AddressResponse currentResponse =
          mapper.readValue(jsonFactory.createParser(resources[i].getFile()), AddressResponse.class);
      for (int j = 0; j < currentResponse.contacts().size(); j++) {
        contacts.add(currentResponse.contacts().get(j));
      }
    }
    return AddressResponse.builder().contacts(contacts).count(contacts.size()).build();
  }
}
