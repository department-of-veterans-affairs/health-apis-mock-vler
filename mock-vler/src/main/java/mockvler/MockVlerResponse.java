package mockvler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonAutoDetect(
  fieldVisibility = JsonAutoDetect.Visibility.ANY,
  isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
public final class MockVlerResponse {
  List<Contacts> contacts;

  int count;

  /** Lazy getter. */
  public List<Contacts> contacts() {
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
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static final class Contacts {

    @JsonProperty("displayname")
    String displayName;

    String mail;

    String uid;

    @JsonProperty("givenname")
    String givenName;

    String initials;

    String sn;

    @JsonProperty("physicaldeliveryofficename")
    String physicalDeliveryOfficeName;

    @JsonProperty("o")
    String organization;

    @JsonProperty("departmentnumber")
    String departmentNumber;

    String mobile;

    @JsonProperty("telephonenumber")
    String telephoneNumber;

    String title;

    String cn;

    String facility;
  }
}
