package net.media;

import net.media.converters.Converter;
import net.media.converters.response25toresponse30.Asset24ToAsset30Converter;
import net.media.converters.response25toresponse30.Bid24ToBid30Converter;
import net.media.converters.response25toresponse30.Bid24ToMediaConverter;
import net.media.converters.response25toresponse30.BidResponseToOpenRtbConverter;
import net.media.converters.response25toresponse30.BidResponseToResponseConverter;
import net.media.converters.response25toresponse30.BidToAdConverter;
import net.media.converters.response25toresponse30.BidToAudioConverter;
import net.media.converters.response25toresponse30.BidToAuditConverter;
import net.media.converters.response25toresponse30.BidToDisplayConverter;
import net.media.converters.response25toresponse30.BidToVideoConverter;
import net.media.converters.response25toresponse30.LinkToLinkAssetConverter;
import net.media.converters.response25toresponse30.Native24ToNative30Converter;
import net.media.converters.response25toresponse30.SeatBid24ToSeatBid30Converter;
import net.media.converters.response25toresponse30.SeatBidList24ToSeatBidList30Converter;
import net.media.converters.response30toresponse25.AdToBidConverter;
import net.media.converters.response30toresponse25.Asset30ToAsset24Converter;
import net.media.converters.response30toresponse25.AudioToBidConverter;
import net.media.converters.response30toresponse25.AuditToBidConverter;
import net.media.converters.response30toresponse25.Bid30ToBid24Converter;
import net.media.converters.response30toresponse25.DisplayToBidConverter;
import net.media.converters.response30toresponse25.LinkAssetToLinkConverter;
import net.media.converters.response30toresponse25.MediatoBidConverter;
import net.media.converters.response30toresponse25.Native30ToNative10Converter;
import net.media.converters.response30toresponse25.OpenRtbResponseToBidResponseConverter;
import net.media.converters.response30toresponse25.SeatBid30ToSeatBid24Converter;
import net.media.converters.response30toresponse25.VideoToBidConverter;
import net.media.openrtb25.request.BidRequest;
import net.media.openrtb25.response.BidResponse;
import net.media.openrtb25.response.SeatBid;
import net.media.openrtb25.response.nativeresponse.AssetResponse;
import net.media.openrtb25.response.nativeresponse.Link;
import net.media.openrtb25.response.nativeresponse.NativeResponse;
import net.media.openrtb3.Ad;
import net.media.openrtb3.Audio;
import net.media.openrtb3.Audit;
import net.media.openrtb3.Bid;
import net.media.openrtb3.Display;
import net.media.openrtb3.LinkAsset;
import net.media.openrtb3.Media;
import net.media.openrtb3.Native;
import net.media.openrtb3.OpenRTB;
import net.media.openrtb3.Response;
import net.media.openrtb3.Seatbid;
import net.media.openrtb3.Video;
import net.media.utils.Provider;

import java.util.List;

/**
 * @author shiva.b
 */
public class ConverterPlumber {

  private Provider<Conversion, Converter> converterProvider;

  public ConverterPlumber() {
    converterProvider = new Provider<>(null);
    Converter25To30RequestPlumber converter25To30RequestPlumber = new Converter25To30RequestPlumber();
    Converter30To25RequestPlumber converter30To25RequestPlumber = new Converter30To25RequestPlumber();
    converterProvider.register(new Conversion(BidResponse.class, OpenRTB.class),
      bidResponseToOpenRtb());
    converterProvider.register(new Conversion(BidRequest.class, OpenRTB.class),
      converter25To30RequestPlumber.bidRequestToOpenRtb());
    converterProvider.register(new Conversion(OpenRTB.class, BidResponse.class),
      openRtbToBidResponseConverter());
    converterProvider.register(new Conversion(OpenRTB.class, BidRequest.class),
      converter30To25RequestPlumber.openRtbToBidRequestConverter());
  }


  private Converter<Media, net.media.openrtb25.response.Bid> mediaToBidConverter(){
    Converter<Display, net.media.openrtb25.response.Bid> displayBidConverter  = new DisplayToBidConverter(nativeToNativeResponseConverter());
    Converter<Video, net.media.openrtb25.response.Bid> videoBidConverter =  new VideoToBidConverter();
    Converter<Audio, net.media.openrtb25.response.Bid> audioBidConverter = new AudioToBidConverter();
    Converter<Audit, net.media.openrtb25.response.Bid> auditBidConverter = new AuditToBidConverter();
    Converter<Ad, net.media.openrtb25.response.Bid> adBidConverter = new AdToBidConverter(displayBidConverter,videoBidConverter,audioBidConverter,auditBidConverter);
    return new MediatoBidConverter(adBidConverter);
  }

  public Converter<Native,NativeResponse> nativeToNativeResponseConverter(){
    Converter<LinkAsset,Link> linkAssetLinkConverter = linkAssetToLinkConverter();
    Converter<net.media.openrtb3.Asset,AssetResponse> assetAssetResponseConverter = new Asset30ToAsset24Converter(linkAssetToLinkConverter());
    return new Native30ToNative10Converter(assetAssetResponseConverter,linkAssetLinkConverter);
  }

  public Converter<LinkAsset,Link> linkAssetToLinkConverter(){
    return new LinkAssetToLinkConverter();
  }

  private Converter<OpenRTB, BidResponse> openRtbToBidResponseConverter() {
    Converter<net.media.openrtb3.Bid, net.media.openrtb25.response.Bid> bidBidConverter = new Bid30ToBid24Converter(mediaToBidConverter());
    Converter<Seatbid, SeatBid> seatBid30ToSeatBid24Converter =  new SeatBid30ToSeatBid24Converter(bidBidConverter);
    return new OpenRtbResponseToBidResponseConverter(seatBid30ToSeatBid24Converter);
  }

  private Converter<BidResponse, OpenRTB> bidResponseToOpenRtb() {
    Converter<BidResponse, Response> bidResponseToResponseConverter = bidResponseToResponse();
    Converter<BidResponse, OpenRTB> bidResponseToOpenRtbConverter = new BidResponseToOpenRtbConverter(bidResponseToResponseConverter);

    return bidResponseToOpenRtbConverter;
  }

  private Converter<BidResponse,Response> bidResponseToResponse() {
    Converter<net.media.openrtb25.response.Bid, Bid> bid24ToBid30Converter = bid24ToBid30();
    Converter<SeatBid, Seatbid> seatBid24ToSeatBid30Converter = new SeatBid24ToSeatBid30Converter(bid24ToBid30Converter);
    Converter<List<SeatBid>, List<Seatbid>> seatBidListToSeatBidListConverter = new SeatBidList24ToSeatBidList30Converter(seatBid24ToSeatBid30Converter);
    Converter<BidResponse, Response> bidResponseToResponseConverter = new BidResponseToResponseConverter(seatBidListToSeatBidListConverter);
    return bidResponseToResponseConverter;
  }

  private Converter<net.media.openrtb25.response.Bid, Bid> bid24ToBid30() {
    Converter<net.media.openrtb25.response.Bid, Media> bidMediaConverter = bidToMediaConverter();
    Converter<net.media.openrtb25.response.Bid, Bid> bid24ToBid30Converter = new Bid24ToBid30Converter(bidMediaConverter);
    return bid24ToBid30Converter;
  }

  private Converter<net.media.openrtb25.response.Bid,Media> bidToMediaConverter() {
    Converter<Link, LinkAsset> linkToLinkAssetConverter = new LinkToLinkAssetConverter();
    Converter<AssetResponse, net.media.openrtb3.Asset> assetResponseToAssetConverter = new Asset24ToAsset30Converter(linkToLinkAssetConverter);
    Converter<NativeResponse, Native> nativeResponseNativeConverter = new Native24ToNative30Converter(linkToLinkAssetConverter, assetResponseToAssetConverter);
    Converter<net.media.openrtb25.response.Bid, Display> bidDisplayConverter = new BidToDisplayConverter(nativeResponseNativeConverter);
    Converter<net.media.openrtb25.response.Bid, Audio> bidAudioConverter = new BidToAudioConverter();
    Converter<net.media.openrtb25.response.Bid, Video> bidVideoConverter = new BidToVideoConverter();
    Converter<net.media.openrtb25.response.Bid, Audit> bidAuditConverter = new BidToAuditConverter();
    Converter<net.media.openrtb25.response.Bid, Ad> bidAdConverter = new BidToAdConverter(bidDisplayConverter, bidAudioConverter, bidVideoConverter, bidAuditConverter);
    Converter<net.media.openrtb25.response.Bid, Media> bidMediaConverter = new Bid24ToMediaConverter(bidAdConverter);
    return bidMediaConverter;
  }

  public <U, V> Converter<U, V> getConverter(Class<U> source, Class<V> target) {
    return converterProvider.fetch(new Conversion(source, target));
  }

}
