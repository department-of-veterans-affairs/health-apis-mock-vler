package mockvler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
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
  private MockVlerResponse buildVlerReponseOffHardCodedDataFiles() {
    File folder = new File("mock-vler/src/main/resources/data");
    File[] listOfFiles = folder.listFiles();

    List<MockVlerResponse.Contacts> contacts = new ArrayList<>();
    MockVlerResponse currentResponse;
    ObjectMapper mapper = new ObjectMapper();
    JsonFactory jsonFactory = new JsonFactory();
    for (int i = 0; i < listOfFiles.length; i++) {
      currentResponse =
          mapper.readValue(jsonFactory.createParser(listOfFiles[i]), MockVlerResponse.class);
      for (int j = 0; j < currentResponse.contacts().size(); j++) {
        contacts.add(currentResponse.contacts().get(j));
      }
    }
    return MockVlerResponse.builder().contacts(contacts).count(contacts.size()).build();
  }

  @GetMapping(
    value = {"direct/addresses"},
    produces = "application/json"
  )
  @ResponseBody
  private MockVlerResponse getMockVlerResponse() {
    return buildVlerReponseOffHardCodedDataFiles();
  }
}
