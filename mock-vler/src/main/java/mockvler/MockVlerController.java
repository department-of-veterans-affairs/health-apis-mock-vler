package mockvler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.va.api.health.mockvler.api.VlerResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockVlerController {

  @SneakyThrows
  private VlerResponse buildVlerReponseOffHardCodedDataFiles() {
    File folder = new File("mock-vler/src/main/resources/data");
    File[] listOfFiles = folder.listFiles();

    List<VlerResponse.Contacts> contacts = new ArrayList<>();
    VlerResponse currentResponse;
    ObjectMapper mapper = new ObjectMapper();
    JsonFactory jsonFactory = new JsonFactory();
    for (int i = 0; i < listOfFiles.length; i++) {
      currentResponse =
          mapper.readValue(jsonFactory.createParser(listOfFiles[i]), VlerResponse.class);
      for (int j = 0; j < currentResponse.contacts().size(); j++) {
        contacts.add(currentResponse.contacts().get(j));
      }
    }
    return VlerResponse.builder().contacts(contacts).count(contacts.size()).build();
  }

  @GetMapping(
    value = {"direct/addresses"},
    produces = "application/json"
  )
  @ResponseBody
  private VlerResponse getMockVlerResponse() {
    return buildVlerReponseOffHardCodedDataFiles();
  }
}
