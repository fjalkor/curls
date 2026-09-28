import UIKit
import SwiftUI
import Shared

struct UIEnginePicker: View {
    @ObservedObject var settings: UiEngineSettingsWrapper
    private let options: [String] = ["Compose", "SwiftUI"]
    
    init(settings: UiEngineSettingsWrapper) {
        self.settings = settings
    }
    
    var body: some View {
        Picker("UI Engine", selection: Binding(
            get: { settings.useComposeUi ? options[0] : options[1] },
            set: { settings.setUseComposeUi($0 == options.first) },
        )) {
            ForEach(options, id: \.self) { option in
                Text(option)
            }
        }
        .pickerStyle(.segmented)
    }
}
