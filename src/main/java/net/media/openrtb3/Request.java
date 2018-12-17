package net.media.openrtb3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {

  private String id;
  private Integer test;
  private Integer tmax;
  private Integer at;
  private String[] cur;
  private Set<String> seat;
  private Integer wseat;
  private String cdata;
  private Source source;
  private List<Item> item;
  @JsonProperty("package")
  private Integer pack;
  private Context context;
  private Object ext;
}
