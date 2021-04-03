package afcethiopia.lifetruths

class BannerImpl :BannerIdCode {
    override  fun getBannerId(): String {
        return "ca-app-pub-9156727777369518/8629842401" //Read Banner Ad
    }
}
interface BannerIdCode {
       fun getBannerId(): String
}