package gov.va.api.health.mockvler.api.swaggerexamples;

import static java.util.Arrays.asList;

import gov.va.api.health.mockvler.api.VlerResponse;
import gov.va.api.health.mockvler.api.VlerResponse.Contacts;
import lombok.experimental.UtilityClass;

@UtilityClass
class SwaggerMockVlerResponse {
  static final VlerResponse SWAGGER_EXAMPLE_VLER_RESPONSE =
      VlerResponse.builder()
          .contacts(
              asList(
                  Contacts.builder()
                      .displayName("Naranji, Maryam MN")
                      .mail("maryam.naranji@test2.direct.va.gov")
                      .uid("maryam.naranji")
                      .givenName("Maryam")
                      .initials("MN")
                      .sn("Naranji")
                      .physicalDeliveryOfficeName("Ashburn, VA")
                      .mobile("123-456-1879")
                      .telephoneNumber("123-456-1879")
                      .title("Engineer")
                      .cn("Maryam Naranji")
                      .facility("Test Facility")
                      .build(),
                  Contacts.builder()
                      .displayName("Kalahasti, Venkata")
                      .mail("venkata.kalahasti@test2.direct.va.gov")
                      .uid("venkata.kalahasti")
                      .givenName("Venkata")
                      .sn("Kalahasti")
                      .cn("Venkata Kalahasti")
                      .facility("Test Facility")
                      .build()))
          .build();
}
