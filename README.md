# Recipe Discovery App (Coding Challenge)

This is the Android app I built for the challenge, using Kotlin and Jetpack Compose. You can browse recipes, tap on each one to see its ingredients and cooking steps and apply multi-criteria filters: dietary restrictions, maximum servings, ingredients to include or exclude and a search by cooking instructions.

---

## Setup Instructions

### Prerequisites
- **Android Studio**: Android Studio Ladybug / Meerkat (2024.2+) or newer with support for Android Gradle Plugin 9.x.
- **JDK**: Java 11 or higher
- **Min SDK**: `minSdk = 26` (Android 8.0 Oreo)
- **Target SDK**: `targetSdk = 37`

### Building and Running
1. Clone the repository and open the project in Android Studio;
2. Allow Gradle to sync dependencies (`libs.versions.toml`);
3. Run the app directly from Android Studio onto an emulator or physical device running API 26 or higher, or build via command line:
   ```bash
   ./gradlew assembleDebug
   ```

---

## High-Level Architecture Overview

I organized the app following Clean Architecture, with Unidirectional Data Flow (MVI-style state handling) and dependency injection with Hilt:

```
UI Layer (Jetpack Compose)
  └── RecipeScreen / RecipeFilterBottomSheet
  └── RecipeViewModel (StateFlow<RecipeUiState>, RecipeIntent)
        │
Domain Layer (Pure Kotlin)
  └── RecipeRepository (Interface)
  └── Domain Models: Recipe, DietaryAttribute, RecipeFilter
        │
Data Layer
  └── RecipeRepositoryImpl
  └── RecipeApi (Retrofit contract) / MockApiService (JSON parser + latency simulator)
  └── DTOs & Mappers: RecipeResponse, RecipeResponseMapper
```

---

## Key Design Decisions

### 1. Expandable recipe cards instead of a details screen
To keep things simple for the exercise, I decided that tapping a recipe expands its card in place to show the details, instead of navigating to a separate screen with the Navigation library. This way the user doesn't lose their scroll position, the filters stay within reach, and it's easy to compare recipes without jumping back and forth between screens.

### 2. Filters

**Where to find:** I put all the filter options together in a single `ModalBottomSheet`, so the user has one place to set everything up.

**Draft before applying:** While the user is adjusting the filters, I keep the changes in a local `filterDraft`. Nothing is applied until they tap "Filter recipes" and then all the criteria are sent at once. I did this to avoid firing a new query every time a single option changes.

**Clear all:** There is a "Clear all" button that resets the draft and runs a fresh query.

**Filtering happens on the "server":** I simulated the filtering inside `MockApiService` instead of doing it in the ViewModel. In a real product, filtering would happen on the backend, since it can be heavy work to do on the device.

**All filters are combined (AND):** Every filter narrows the results. If the user selects several filters, they'll only see recipes that match all of them.

**Servings work as a maximum:** The rule is `recipe.numberOfServings <= servings`. I assumed that someone filtering by servings wants recipes that make up to that amount, not exactly that number.

**Include uses `all`, exclude uses `any`:** If the user wants to include "garlic" and "onion", I understand they want recipes that have both. If they exclude "egg", any recipe that has egg is removed. The match checks whether the term appears inside the normalized ingredient text.

### 3. Accent and case-insensitive search (`normalizeForSearch`)
Search terms and ingredient filters go through a function which removes accents, lowercases the text and collapses repeated spaces. So searching for "saute" finds "sauté", and "creme" finds "crème".

### 4. A dedicated screen for every state
Instead of showing just a generic progress bar, I created a styled composable for each state:
- `RecipesLoadingState`: an illustration with friendly copy while the data loads (I simulated a 2-second delay).
- `RecipesErrorState`: an error illustration with a "Try again" button.
- `RecipesEmptyState`: shown when no recipe matches the filters, with an option to reset them.

---

## Assumptions and Trade-offs

### Data source
I bundled the data as a local JSON file (`res/raw/recipes.json`) behind a Retrofit interface. This keeps the app fully self-contained and reproducible, with no dependency on third-party APIs or keys that can expire. The trade-off is that the data is static unless I bundle a new file.

### Dependency injection
I started with manual DI, but since I had time left, I migrated to **Hilt**. I thought this would be appreciated in the exercise and in a real project I'd consider it a must, because it makes testing easier and brings more cohesion and less coupling to the code.

### Dietary attributes come from the data, not from an enum
I kept the dietary attributes, including their color fields in the JSON and read them from the mocked endpoint. The filter chips are built dynamically from the recipes that are available and they're styled with the colors that come from the data.

The benefit is that if this data came from a real API, a new dietary attribute would show up in the app right away, with its own colors, and I wouldn't need to recompile the app and wait for users to update it. If I had parsed them into an enum, every new attribute would require a new release and an app update before users could see it.

### State persistence
I keep the expanded cards in `RecipeUiState`. This survives screen rotation through the `ViewModel`, but not process death. For a browsing session, I thought this was enough and avoided adding a database just for that.

### Pagination
I didn't add pagination. Using a paging library would add a lot of complexity to the exercise and with this dataset size the effort wasn't worth it.

### Offline caching
I didn't add offline support. Right now the app reads the JSON again on every fetch. In a real world scenario, good options would be to add a in-memory cache or add local database with a cache-first repository, which would enable offline support and search indexing.

---

## Testing

I wrote unit tests for the most important business logic and the presentation layer:
- **`RecipeViewModelTest`**: covers intent handling, UI state transitions (loading, success, error) and filter dispatching.
- **`RecipeRepositoryImplTest`**: checks data mapping, error wrapping with `Result` and the repository contract with `RecipeApi`.

To run the unit tests from the command line:
```bash
./gradlew testDebugUnitTest
```

---

## Limitations

- **Only 50 recipes:** The app currently lists 50 recipes, which is all the bundled JSON has. Since everything is loaded in a single request, it works fine at this size, but it wouldn't scale to a real catalog without pagination and a real backend.
- **Ingredient matching is a simple "contains":** Because I match by checking whether the term appears inside the normalized ingredient text, excluding "egg" would also remove recipes with "eggplant". There's no word-boundary check, synonym handling, or plural/singular handling.
- **Data is not remembered:** Data lives in the ViewModel, so they're lost if the app is closed or the process is killed.
- **Dietary chips depend on the loaded recipes:** Since I build them from the recipes available, an attribute that no recipe has won't show up as an option.
- **English only:** All the texts are in English, and there's no localization.

---
