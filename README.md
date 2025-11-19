# Offline_Voice-Activated_Group-Quiz-App
Traditional group quiz games suffer from two main flaws: reliance on the internet and difficulty in scorekeeping/cheating prevention. This application is a fully offline Android quiz game designed specifically for competitive group settings (parties, gatherings, etc.) where integrity and high energy are key.


✨ Key Features
1.Strictly Offline Multiplayer: The entire application runs without any internet connection. All game resources (images, audio, database) are bundled, eliminating the possibility of players cheating via online searches.

2.Voice-Activated Input System: To maintain a high-energy, shout-out-loud environment while ensuring fair play, we use a custom speech detection mechanism(from Android Voice recognition module).
  -Players must use a specific wake word (e.g., "Movie," "Hero") immediately before their answer. Only answers prefixed by this word are registered by the app. This prevents background chatter from interfering.
  -A "Push-to-Talk" mode is available for smaller groups.

3.Team-Based Scoring: Two teams compete in a race to a target score (e.g., 20 or 30 points).
  -Scoring Logic: Correct answer = +1 point. Incorrect answer = -1 point. The correct answer is displayed immediately after a miss.
  -Real-time scoreboard is displayed throughout the game.

 Architecture & Status:
Platform: Android (APK available for download).
Language: Kotlin.
Development Tools: Android Studio.
Development Assistance: Developed with coding support from Claude AI.
Assets: All game assets (posters, songs, sounds) are sourced from open-source platforms.
(Source: 
Posters:minimalmovieposters.in
        wirally.com/
        Gemini image creation
Sound effects : Pixabay.com/
Songs/Dialogue's : Youtube

 Current State (Prototype: Movies Theme):
The current prototype is fully functional and includes the following categories under the "Movies" theme (specifically Tollywood cinema):
    -Guess the Movie by its Poster.
    -Identify the Movie/Hero based on a short song clip.
    -Identify the Movie/Hero based on a short dialogue clip.
    -Name Movies featuring a specific Hero/Heroine (photo displayed).

💡 Future Roadmap
This is an ongoing project. Future updates will focus on:
    -Expanding the database of movies and heroes.
    -Introducing new themes (e.g., Sports, Politics, Science).
    -Developing more diverse and complex quiz categories.
