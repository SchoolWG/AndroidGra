# CyberTap Reflex: Wciągająca Gra Zręcznościowa dla Młodzieży

Projekt kompletnej, wciągającej gry zręcznościowej na system Android w istniejącym projekcie `Scroll` (z zachowaniem struktury pakietów `com.kl4tp.scroll`). Gra łączy szybki refleks, system kombosów (combo), dynamicznie zmieniające się cele oraz nowoczesny, młodzieżowy styl wizualny (ciemny motyw / neon).

## User Review Required

> [!IMPORTANT]
> - Gra zostanie zaimplementowana w ramach obecnego projektu (pakiet `com.kl4tp.scroll`), przekształcając `MainActivity` i layout w interaktywną, wciągającą aplikację zręcznościową.
> - Styl wizualny będzie nowoczesny (ciemne tło, neonowe akcenty, efekty wibracji/dźwięku/licznika czasu).

## Open Questions

- Czy preferujesz grę opartą na refleksie (szybkie klikanie w losowo pojawiające się cele), czy może quiz wiedzy / zagadki logiczne? (Wybraliśmy **CyberTap Reflex** jako najbardziej dynamiczną i wciągającą grę zręcznościową dla młodzieży).

## Proposed Changes

### Gra: CyberTap Reflex (Refleks i Kombo)

#### [MODIFY] [activity_main.xml](file:///C:/Users/Wiola/Desktop/szkola/Technik-programista/Programowanie/Aplikacje_mobilne_AndroidStudio/4tp/widzetyScroll/app/src/main/res/layout/activity_main.xml)
- Stworzenie atrakcyjnego interfejsu w stylu cyberpunk/neon:
  - Ekran startowy z tytułem gry i przyciskiem "START GRY".
  - Panel gry: Wynik (Score), Najlepszy wynik (High Score), Licznik czasu (Timer: 30 sekund), Mnożnik Kombo (Combo x1, x2, x3...).
  - Siatka celów (Grid / Przyciski), gdzie losowo pojawia się podświetlony cel ("HIT!"), w który trzeba szybko kliknąć.
  - Ekran końca gry (Game Over) z podsumowaniem i przyciskiem ponownej gry.

#### [MODIFY] [MainActivity.java](file:///C:/Users/Wiola/Desktop/szkola/Technik-programista/Program메이션/Aplikacje_mobilne_AndroidStudio/4tp/widzetyScroll/app/src/main/java/com/kl4tp/scroll/MainActivity.java)
- Logika gry:
  - Odliczanie czasu (`CountDownTimer`).
  - Losowanie aktywnego celu w siatce w regularnych odstępach czasu (trudność wzrasta z czasem).
  - System punktacji i kombosów (trafienie zwiększa combo, spóźnienie lub pudło resetuje combo).
  - Zapisywanie najlepszego wyniku w `SharedPreferences`.
  - Efekty wibracji i animacje powiększenia przy kliknięciu.

#### [MODIFY] [strings.xml / colors.xml / themes.xml](file:///C:/Users/Wiola/Desktop/szkola/Technik-programista/Programowanie/Aplikacje_mobilne_AndroidStudio/4tp/widzetyScroll/app/src/main/res/values)
- Dodanie tekstów w języku polskim oraz nowoczesnej palety kolorów (neon cyan, neon pink, ciemne tło).

## Verification Plan

### Automated Tests
- Kompilacja projektu za pomocą Gradle (`gradle_build`).

### Manual Verification
- Uruchomienie aplikacji na emulatorze / urządzeniu i przetestowanie rozgrywki:
  1. Kliknięcie "Start Gry" uruchamia odliczanie i losowanie celów.
  2. Sprawdzenie reakcji na kliknięcia poprawne i błędne (punkty, kombo).
  3. Sprawdzenie ekranu końca gry po upływie czasu oraz zapisu rekordu (`High Score`).
