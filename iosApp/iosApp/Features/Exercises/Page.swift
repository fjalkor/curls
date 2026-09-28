import Shared
import SwiftUI
import UIKit

struct Page<T: Named>: View {
    let title: String
    let availableOptions: [T]
    let currentSelection: [T]
    var onItemTapped: (T) -> Void = { _ in }
    var goToNextPage: () -> Void = { }

    var body: some View {
        VStack(spacing: 0) {
            Text(title)
                .font(Typography.displaySmall)
                .frame(maxWidth: .infinity, alignment: .leading)

            Spacer().frame(height: 32)

            LazyVGrid(
                columns: [GridItem(.adaptive(minimum: 130))],
                spacing: 16,
            ) {
                ForEach(availableOptions, id: \.name) { item in
                    chip(item)
                }
            }

            Spacer()

            Button(
                action: { goToNextPage() },
                label: { Text("Confirm").frame(maxWidth: .infinity) }
            )
            .disabled(currentSelection.isEmpty)
            .buttonStyle(.borderedProminent)
            .controlSize(.large)
        }
        .frame(maxHeight: .infinity)
        .padding(.horizontal, 8)
    }

    func chip(_ item: T) -> some View {
        let isSelected = currentSelection.contains { $0.name == item.name }
        return Text(item.name)
            .lineLimit(2)
            .multilineTextAlignment(.center)
            .fixedSize(horizontal: false, vertical: true)
            .minimumScaleFactor(0.8)
            .font(Typography.bodyLarge)
            .padding(.vertical, 10)
            .padding(.horizontal, 14)
            .frame(maxWidth: .infinity)
            .background(
                isSelected
                    ? Color.accentColor : Color(.secondarySystemBackground)
            )
            .foregroundStyle(isSelected ? .white : .primary)
            .clipShape(RoundedRectangle(cornerRadius: 10))
            .onTapGesture { onItemTapped(item) }
    }
}
