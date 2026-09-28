import SwiftUI
import Shared

class ExercisesViewModelWrapper: ObservableObject {
    @Published var uiState: ExercisesScreenUiState
    private var job: Kotlinx_coroutines_coreJob?
    private let viewModel: ExercisesViewModel
    
    init() {
        self.viewModel = KoinHelper().getExercisesViewModel()
        self.uiState = viewModel.uiState.value as! ExercisesScreenUiState
        self.job = viewModel.observeState { [weak self] newState in
            DispatchQueue.main.async { self?.uiState = newState }
        }
    }
    
    deinit {
        job?.cancel(cause: nil)
    }
    
    func onCategoryItemTapped(_ item: Category_) {
        viewModel.onCategoryTapped(category: item)
    }
    
    func onEquipmentItemTapped(_ item: Equipment_) {
        viewModel.onEquipmentTapped(equipment: item)
    }

    func onUpdatePage(_ newPage: Int32) {
        viewModel.updatePage(newPage: Int32(newPage))
    }
}
