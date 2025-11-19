import SwiftUI
import GoogleMaps

@main
struct iOSApp: App {

    init() {
        GMSServices.provideAPIKey("AIzaSyDfMAUvlHfnm-UTPJx3I2gYSzKM2GInZfI")
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}