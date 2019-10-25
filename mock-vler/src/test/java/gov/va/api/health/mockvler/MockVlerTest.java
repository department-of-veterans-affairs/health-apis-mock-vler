package gov.va.api.health.mockvler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MockVlerTest {

  MockVlerController controller = new MockVlerController();

  @Test
  public void validateAddressCount() {
    AddressResponse actual = controller.getAddresses();
    assertThat(actual.count()).isEqualTo(21);
  }
}
