package gov.va.api.health.mockvler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public final class AddressResponse {
  List<Contact> contacts;

  int count;

  /** Lazy getter. */
  public List<Contact> contacts() {
    if (contacts == null) {
      contacts = new ArrayList<>();
    }
    return contacts;
  }

  @Data
  @Builder
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
  public static final class Contact {
    @JsonProperty("displayname")
    String displayName;

    @JsonProperty("mail")
    String emailAddress;

    String uid;

    @JsonProperty("givenname")
    String givenName;

    String initials;

    @JsonProperty("sn")
    String surname;

    @JsonProperty("physicaldeliveryofficename")
    String officeCityState;

    @JsonProperty("o")
    String companyName;

    @JsonProperty("departmentnumber")
    String departmentNumber;

    String mobile;

    @JsonProperty("telephonenumber")
    String telephoneNumber;

    String title;

    @JsonProperty("cn")
    String commonName;

    String facility;
  }
}
