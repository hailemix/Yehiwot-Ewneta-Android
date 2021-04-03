package afcethiopia.lifetruths

class BannerImpl :BannerIdCode {
    override  fun getBannerId(): String {
        return "ca-app-pub-3940256099942544/6300978111" //Test Banner Ad
    }
}
interface BannerIdCode {
       fun getBannerId(): String
}