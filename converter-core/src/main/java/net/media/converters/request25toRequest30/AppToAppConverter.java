package net.media.converters.request25toRequest30;

import lombok.AllArgsConstructor;
import net.media.OpenRtbConverterException;
import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb25.request.App;
import net.media.openrtb25.request.Content;
import net.media.openrtb25.request.Publisher;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class AppToAppConverter implements Converter<App, net.media.openrtb3.App> {

  private Converter<Publisher, net.media.openrtb3.Publisher> publisherPublisherConverter;
  private Converter<Content, net.media.openrtb3.Content> contentContentConverter;

  @Override
  public net.media.openrtb3.App map(App source, Config config) throws OpenRtbConverterException {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb3.App app1 = new net.media.openrtb3.App();

    inhance( source, app1, config );

    return app1;
  }

  @Override
  public void inhance(App source, net.media.openrtb3.App target, Config config) throws OpenRtbConverterException {
    if(source == null)
      return;
    target.setPrivpolicy( source.getPrivacypolicy() );
    target.setSectcat( source.getSectioncat() );
    target.setPub( publisherPublisherConverter.map( source.getPublisher(), config ) );
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setContent( contentContentConverter.map( source.getContent(), config ) );
    target.setDomain( source.getDomain() );
    target.setCat( source.getCat() );
    target.setPagecat( source.getPagecat() );
    target.setKeywords( source.getKeywords() );
    target.setBundle( source.getBundle() );
    target.setStoreurl( source.getStoreurl() );
    target.setVer( source.getVer() );
    target.setPaid( source.getPaid() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }

    if(source.getExt() == null)
      return;
    target.setCattax((Integer) source.getExt().get("cattax"));
    target.setStoreid((String) source.getExt().get("storeid"));
    target.getExt().remove("cattax");
    target.getExt().remove("storeid");

  }
}
