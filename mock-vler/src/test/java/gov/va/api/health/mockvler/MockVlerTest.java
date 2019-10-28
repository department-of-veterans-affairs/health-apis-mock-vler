package gov.va.api.health.mockvler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MockVlerTest {
  @Autowired MockVlerController controller;

  @Test
  public void addressResponseLazyGetter() {
    assertThat(AddressResponse.builder().build().contacts()).isEmpty();
  }

  @Test
  public void validateAddressCount() {
    assertThat(controller.getAddresses().count()).isEqualTo(30);
  }
}
