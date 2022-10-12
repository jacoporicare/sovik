//
//  ContentView.swift
//  Sovik
//
//  Created by Jakub Řičař on 04.10.2022.
//

import SwiftUI

struct ContentView: View {
    @State private var owls = 1.0
    @State var lastCoordinateValue: CGFloat = 0.0

    private let maxOwls = 10

    var body: some View {
        GeometryReader { geo in
            let maxValue = geo.size.width
            let scaleFactor = maxValue / Double(maxOwls)

            VStack(spacing: 30) {
                Text("🦉 Sovík 🦉")
                    .font(.system(size: 48, weight: .bold))

                Spacer()

                Text("Kolik 🦉 dáváš?")
                    .font(.system(size: 36))

                HStack(spacing: 0) {
                    ForEach(1 ... maxOwls, id: \.self) { num in
                        VStack(spacing: 4) {
                            Text("🦉")
                                .font(.system(size: 36))
                                .opacity(num > Int(owls) ? 0.3 : 1)

                            Text(num == Int(owls) ? String(Int(num)) : " ")
                                .font(.system(size: 24))
                        }
                    }
                }
                .gesture(
                    DragGesture(minimumDistance: 0)
                        .onChanged { v in
                            if abs(v.translation.width) < 0.1 {
                                self.lastCoordinateValue = v.location.x
                            }

                            if v.translation.width > 0 {
                                let nextCoordinateValue = min(maxValue, self.lastCoordinateValue + v.translation.width)
                                self.owls = max(1, ceil(nextCoordinateValue / scaleFactor))
                            } else {
                                let nextCoordinateValue = max(0.0, self.lastCoordinateValue + v.translation.width)
                                self.owls = max(1, ceil(nextCoordinateValue / scaleFactor))
                            }
                        }
                )

                Spacer()

                ShareLink(item: "Hodnotím \(String(repeating: "🦉", count: Int(owls))) (\(Int(owls))/\(maxOwls)) přes Sovíka.") {
                    Text("Hodnotit")
                }
                .buttonStyle(.borderedProminent)
                .font(.title)
            }
        }
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
