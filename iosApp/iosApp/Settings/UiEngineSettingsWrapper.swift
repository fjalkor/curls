import UIKit
import SwiftUI
import Shared

class UiEngineSettingsWrapper: ObservableObject {
    @Published var useComposeUi: Bool
    private var job: Kotlinx_coroutines_coreJob?
    
    init() {
        self.useComposeUi = (AppSettings.shared.uiEngineSettings.useComposeUi.value as! KotlinBoolean).boolValue
        self.job = AppSettings.shared.uiEngineSettings.observe { [weak self] newValue in
            DispatchQueue.main.async { self?.useComposeUi = newValue.boolValue }
        }
    }
    
    deinit {
        job?.cancel(cause: nil)
    }
    
    func toggle() {
        AppSettings.shared.uiEngineSettings.setUseComposeUi(newValue: !useComposeUi)
    }
    
    func setUseComposeUi(_ newValue: Bool) {
        AppSettings.shared.uiEngineSettings.setUseComposeUi(newValue: newValue)
    }
}
