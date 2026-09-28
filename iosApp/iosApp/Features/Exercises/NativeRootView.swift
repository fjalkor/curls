import Shared
import SwiftUI
import UIKit

struct NativeRootView: View {
    @ObservedObject var vm: ExercisesViewModelWrapper

    var body: some View {
        TabView(
            selection: Binding(
                get: { vm.uiState.currentPage },
                set: { vm.onUpdatePage($0) },
            )
        ) {
            Page(
                title: "What equipment do you have?",
                availableOptions: vm.uiState.availableEquipment,
                currentSelection: vm.uiState.selectedEquipment,
                onItemTapped: { vm.onEquipmentItemTapped($0) },
                goToNextPage: { vm.onUpdatePage(1) },
            ).tag(Int32(0))

            Page(
                title: "What do you want to train?",
                availableOptions: vm.uiState.availableCategories,
                currentSelection: vm.uiState.selectedCategories,
                onItemTapped: { vm.onCategoryItemTapped($0) },
                goToNextPage: { vm.onUpdatePage(2) },
            ).tag(Int32(1))
            
            ScrollView {
                LazyVStack {
                    Text("Count: \(vm.uiState.exercises.count)")
                    ForEach(vm.uiState.exercises, id: \.id) { item in
                        Text(item.translation.name)
                    }
                }
            }
            .tag(Int32(2))
        }
        .tabViewStyle(.page(indexDisplayMode: .never))
    }
}



private let debugEquipment = [
    Equipment_(name: "Barbell"),
    Equipment_(name: "Bench"),
    Equipment_(name: "Cable machine"),
    Equipment_(name: "Dumbbell"),
    Equipment_(name: "Gym mat"),
    Equipment_(name: "Incline bench"),
    Equipment_(name: "Kettlebell"),
    Equipment_(name: "Pull-up bar"),
    Equipment_(name: "Resistance band"),
    Equipment_(name: "SZ-Bar"),
    Equipment_(name: "Swiss Ball"),
    Equipment_(name: "none (bodyweight exercise)"),
]

#Preview {
    Page(
        title: "What equipment do you have?",
        availableOptions: debugEquipment,
        currentSelection: [],
    )
}
