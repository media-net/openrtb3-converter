package net.media.openrtb25.request;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

/**
 * Created by rajat.go on 19/12/18.
 */

public class Audio {

  @NotEmpty
  private Collection<String> mimes;

  private Integer minduration;

  private Integer maxduration;

  private Collection<Integer> protocols;

  private Integer startdelay;

  private Integer sequence;

  private Collection<Integer> battr;

  private Integer maxextended;

  private Integer minbitrate;

  private Integer maxbitrate;

  private Collection<Integer> delivery;

  private Collection<Banner> companionad;

  private Collection<Integer> api;

  private Collection<Integer> companiontype;

  private Integer maxseq;

  private Integer feed;

  private Integer stitched;

  private Integer nvol;

  private Map<String, Object> ext;

  public @NotEmpty Collection<String> getMimes() {
    return this.mimes;
  }

  public Integer getMinduration() {
    return this.minduration;
  }

  public Integer getMaxduration() {
    return this.maxduration;
  }

  public Collection<Integer> getProtocols() {
    return this.protocols;
  }

  public Integer getStartdelay() {
    return this.startdelay;
  }

  public Integer getSequence() {
    return this.sequence;
  }

  public Collection<Integer> getBattr() {
    return this.battr;
  }

  public Integer getMaxextended() {
    return this.maxextended;
  }

  public Integer getMinbitrate() {
    return this.minbitrate;
  }

  public Integer getMaxbitrate() {
    return this.maxbitrate;
  }

  public Collection<Integer> getDelivery() {
    return this.delivery;
  }

  public Collection<Banner> getCompanionad() {
    return this.companionad;
  }

  public Collection<Integer> getApi() {
    return this.api;
  }

  public Collection<Integer> getCompaniontype() {
    return this.companiontype;
  }

  public Integer getMaxseq() {
    return this.maxseq;
  }

  public Integer getFeed() {
    return this.feed;
  }

  public Integer getStitched() {
    return this.stitched;
  }

  public Integer getNvol() {
    return this.nvol;
  }

  public Map<String, Object> getExt() {
    return this.ext;
  }

  public void setMimes(@NotEmpty Collection<String> mimes) {
    this.mimes = mimes;
  }

  public void setMinduration(Integer minduration) {
    this.minduration = minduration;
  }

  public void setMaxduration(Integer maxduration) {
    this.maxduration = maxduration;
  }

  public void setProtocols(Collection<Integer> protocols) {
    this.protocols = protocols;
  }

  public void setStartdelay(Integer startdelay) {
    this.startdelay = startdelay;
  }

  public void setSequence(Integer sequence) {
    this.sequence = sequence;
  }

  public void setBattr(Collection<Integer> battr) {
    this.battr = battr;
  }

  public void setMaxextended(Integer maxextended) {
    this.maxextended = maxextended;
  }

  public void setMinbitrate(Integer minbitrate) {
    this.minbitrate = minbitrate;
  }

  public void setMaxbitrate(Integer maxbitrate) {
    this.maxbitrate = maxbitrate;
  }

  public void setDelivery(Collection<Integer> delivery) {
    this.delivery = delivery;
  }

  public void setCompanionad(Collection<Banner> companionad) {
    this.companionad = companionad;
  }

  public void setApi(Collection<Integer> api) {
    this.api = api;
  }

  public void setCompaniontype(Collection<Integer> companiontype) {
    this.companiontype = companiontype;
  }

  public void setMaxseq(Integer maxseq) {
    this.maxseq = maxseq;
  }

  public void setFeed(Integer feed) {
    this.feed = feed;
  }

  public void setStitched(Integer stitched) {
    this.stitched = stitched;
  }

  public void setNvol(Integer nvol) {
    this.nvol = nvol;
  }

  public void setExt(Map<String, Object> ext) {
    this.ext = ext;
  }
}
