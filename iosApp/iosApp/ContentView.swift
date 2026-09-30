import UIKit
import SwiftUI
import Shared

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Self.Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Self.Context) {}
}

struct ContentView: View {
    @StateObject var appSettings = UiEngineSettingsWrapper()
    @StateObject var exercisesViewModel = ExercisesViewModelWrapper()
    
    var body: some View {
        VStack {
            UIEnginePicker(settings: appSettings)
                .padding(.horizontal, 8)
            
            if appSettings.useComposeUi {
                ComposeView()
                    .ignoresSafeArea([.all], edges: .bottom)
            } else {
                NativeRootView(vm: exercisesViewModel)
            }
        }
    }
}


