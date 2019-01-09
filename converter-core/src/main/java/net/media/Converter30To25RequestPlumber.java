package net.media;

import net.media.converters.Converter;
import net.media.converters.request30toRequest25.*;
import net.media.openrtb25.request.*;
import net.media.openrtb25.request.Asset;
import net.media.openrtb3.*;
import net.media.openrtb3.App;
import net.media.openrtb3.Content;
import net.media.openrtb3.Data;
import net.media.openrtb3.Device;
import net.media.openrtb3.Geo;
import net.media.openrtb3.Producer;
import net.media.openrtb3.Publisher;
import net.media.openrtb3.Regs;
import net.media.openrtb3.Segment;
import net.media.openrtb3.Site;
import net.media.openrtb3.Source;
import net.media.openrtb3.User;

public class Converter30To25RequestPlumber {

  public Converter<OpenRTB, BidRequest> openRtbToBidRequestConverter() {
    Converter<Request, BidRequest> requestBidRequestConverter = requestBidRequestConverter();
    return new OpenRtbToBidRequestConverter(requestBidRequestConverter);
  }

  private Converter<Request, BidRequest> requestBidRequestConverter() {
    Converter<User, net.media.openrtb25.request.User> userUserConverter = userUserConverter();
    Converter<Request, net.media.openrtb25.request.User> requestUserConverter = new RequestToUserConverter();
    Converter<App, net.media.openrtb25.request.App> appAppConverter = appAppConverter();
    Converter<Regs, net.media.openrtb25.request.Regs> regsRegsConverter = new RegsToRegsConverter();
    Converter<Site, net.media.openrtb25.request.Site> siteSiteConverter = siteSiteConverter();
    Converter<Device, net.media.openrtb25.request.Device> deviceDeviceConverter = deviceDeviceConverter();
    Converter<Source, net.media.openrtb25.request.Source> sourceSourceConverter = new SourceToSourceConverter();
    Converter<Item, Imp> itemImpConverter = itemToImp();
    return new RequestToBidRequestConverter(userUserConverter, requestUserConverter, appAppConverter, regsRegsConverter,
      siteSiteConverter, deviceDeviceConverter, sourceSourceConverter, itemImpConverter);
  }

  private Converter<User, net.media.openrtb25.request.User> userUserConverter() {
    Converter<Geo, net.media.openrtb25.request.Geo> geoGeoConverter = new GeoToGeoConverter();
    Converter<Data, net.media.openrtb25.request.Data> dataDataConverter = dataDataConverter();
    return new UserToUserConverter(geoGeoConverter, dataDataConverter);
  }

  private Converter<Data, net.media.openrtb25.request.Data> dataDataConverter() {
    Converter<Segment, net.media.openrtb25.request.Segment> segmentSegmentConverter = new SegmentToSegmentConverter();
    return new DataToDataConverter(segmentSegmentConverter);
  }

  private Converter<App, net.media.openrtb25.request.App> appAppConverter() {
    Converter<Publisher, net.media.openrtb25.request.Publisher> publisherPublisherConverter = new PublisherToPublisherConverter();
    Converter<Content, net.media.openrtb25.request.Content> contentContentConverter = contentContentConverter();
    return new AppToAppConverter(publisherPublisherConverter, contentContentConverter);
  }

  private Converter<Content, net.media.openrtb25.request.Content> contentContentConverter() {
    Converter<Producer, net.media.openrtb25.request.Producer> producerProducerConverter = new ProducerToProducerConverter();
    Converter<Data, net.media.openrtb25.request.Data> dataDataConverter = dataDataConverter();
    return new ContentToContentConverter(producerProducerConverter, dataDataConverter);
  }

  private Converter<Site, net.media.openrtb25.request.Site> siteSiteConverter() {
    Converter<Publisher, net.media.openrtb25.request.Publisher> publisherPublisherConverter = new PublisherToPublisherConverter();
    Converter<Content, net.media.openrtb25.request.Content> contentContentConverter = contentContentConverter();
    return new SiteToSiteConverter(publisherPublisherConverter, contentContentConverter);
  }

  private Converter<Device, net.media.openrtb25.request.Device> deviceDeviceConverter() {
    Converter<Geo, net.media.openrtb25.request.Geo> geoGeoConverter = new GeoToGeoConverter();
    return new DeviceToDeviceConverter(geoGeoConverter);
  }

  private Converter<Item, Imp> itemToImp() {
    Converter<DisplayPlacement, net.media.openrtb25.request.Banner> displayPlacementBannerConverter = new
      DisplayPlacementToBannerConverter();
    Converter<DisplayPlacement, net.media.openrtb25.request.Native> displayPlacementNativeConverter =
      displayPlacementToNative();
    Converter<Companion, net.media.openrtb25.request.Banner> companionBannerConverter = new CompanionToBannerConverter
      (displayPlacementBannerConverter);
    Converter<VideoPlacement, net.media.openrtb25.request.Video> videoPlacementVideoConverter = new
      VideoPlacementToVideoConverter(companionBannerConverter);
    Converter<AudioPlacement, net.media.openrtb25.request.Audio> audioPlacementAudioConverter = new
      AudioPlacementToAudioConverter(companionBannerConverter);
    Converter<net.media.openrtb3.Metric, net.media.openrtb25.request.Metric> metricMetricConverter = new net.media.converters
      .request30toRequest25.MetricToMetricConverter();
    Converter<net.media.openrtb3.Deal, net.media.openrtb25.request.Deal> dealDealConverter = new net.media.converters
      .request30toRequest25.DealToDealConverter();
    return new ItemToImpConverter(displayPlacementBannerConverter,
                                  displayPlacementNativeConverter,
                                  videoPlacementVideoConverter,
                                  audioPlacementAudioConverter,
                                  metricMetricConverter,
                                  dealDealConverter);
  }

  private Converter<DisplayPlacement, net.media.openrtb25.request.Native> displayPlacementToNative() {
    Converter<AssetFormat, Asset> assetFormatAssetConverter = new AssetFormatToAssetConverter();
    Converter<NativeFormat, NativeRequestBody> nativeFormatNativeRequestBodyConverter = new
      NativeFormatToNativeRequestBodyConverter(assetFormatAssetConverter);
    return new DisplayPlacementToNativeConverter(nativeFormatNativeRequestBodyConverter);
  }
}
