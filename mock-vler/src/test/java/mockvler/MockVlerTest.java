package mockvler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MockVlerTest {

  MockVlerController controller = new MockVlerController();

  @Test
  public void validateAddressCount() {

    MockVlerResponse actual = controller.getMockVlerResponse();
    assertThat(actual.count() == 21);
  }
}
