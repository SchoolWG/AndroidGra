# Walkthrough - CyberTap Reflex Game

Zrealizowałem pełną, wciągającą grę zręcznościową dla młodzieży o nazwie **CyberTap Reflex Challenge** w ramach obecnego projektu Androida. Gra oferuje dynamiczną rozgrywkę zręcznościową, system kombosów oraz nowoczesny wygląd w stylu cyberpunk / neon.

## Zmiany w Projektach

### Konfiguracja i Zasoby
- **[MODIFY] [strings.xml](file:///C:/Users/Wiola/Desktop/szkola/Technik-programista/Programowanie/Aplikacje_mobilne_AndroidStudio/4tp/widzetyScroll/app/src/main/res/values/strings.xml)**: Dodano polskie tłumaczenia i etykiety gry (tytuł, wynik, czas, kombo, przyciski startu).
- **[MODIFY] [colors.xml](file:///C:/Users/Wiola/Desktop/szkola/Technik-programista/Programowanie/Aplikacje_mobilne_AndroidStudio/4tp/widzetyScroll/app/src/main/res/values/colors.xml)**: Dodano paletę kolorów cyberpunkowych (neon cyan, neon pink, głęboki granat tła, żółty akcent).

### Interfejs Użytkownika (UI)
- **[MODIFY] [activity_main.xml](file:///C:/Users/Wiola/Desktop/szkola/Technik-programista/Programowanie/Aplikacje_mobilne_AndroidStudio/4tp/widzetyScroll/app/src/main/res/layout/activity_main.xml)**:
  - Ekran startowy z neonowym tytułem, podtytułem, rekordem i przyciskiem startu.
  - Ekran rozgrywki (HUD z wynikiem, licznikiem czasu 30s i mnożnikiem kombosów) oraz siatka 3x3 celów zręcznościowych.
  - Ekran końca gry (Game Over) z podsumowaniem wyniku, informacją o nowym rekordzie oraz przyciskiem powtórki.

### Logika Gry
- **[MODIFY] [MainActivity.java](file:///C:/Users/Wiola/Desktop/szkola/Technik-programista/Programowanie/Aplikacje_mobilne_AndroidStudio/4tp/widzetyScroll/app/src/main/java/com/kl4tp/scroll/MainActivity.java)**:
  - Zarządzanie stanami ekranów (Menu -> Gra -> Koniec gry).
  - Licznik czasu (`CountDownTimer`) na 30 sekund.
  - Dynamiczny spawner celów (`Handler`) z przyspieszaniem częstotliwości pojawiania się celów w miarę wzrostu wyniku.
  - System kombosów (`COMBO x1` do `x10`), zwiększający zdobywane punkty za celne trafienia.
  - Trwały zapis najlepszego wyniku (`High Score`) za pomocą `SharedPreferences`.

## Rezultaty Weryfikacji

### Automatyczna Weryfikacja
- Kompilacja projektu zakończona pomyślnie (`BUILD SUCCESSFUL` dla `:app:assembleDebug`).
