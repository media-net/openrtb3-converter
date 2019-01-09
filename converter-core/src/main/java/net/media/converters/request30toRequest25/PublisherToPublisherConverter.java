package net.media.converters.request30toRequest25;

import net.media.config.Config;
import net.media.converters.Converter;
import net.media.openrtb3.Publisher;

import java.util.HashMap;
import java.util.Map;

public class PublisherToPublisherConverter implements Converter<Publisher, net.media.openrtb25.request.Publisher> {
  @Override
  public net.media.openrtb25.request.Publisher map(Publisher source, Config config) {
    if ( source == null ) {
      return null;
    }

    net.media.openrtb25.request.Publisher publisher1 = new net.media.openrtb25.request.Publisher();

    inhance( source, publisher1, config );

    return publisher1;
  }

  @Override
  public void inhance(Publisher source, net.media.openrtb25.request.Publisher target, Config config) {
    if(source == null)
      return;
    target.setId( source.getId() );
    target.setName( source.getName() );
    target.setCat( source.getCat() );
    target.setDomain( source.getDomain() );
    Map<String, Object> map = source.getExt();
    if ( map != null ) {
      target.setExt( new HashMap<String, Object>( map ) );
    }
  }
}
