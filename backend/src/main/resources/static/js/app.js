const catalog = [
  { id: 1, title: 'Samajavaragamana', artist: 'Sid Sriram', source: 'Spotify', mood: 'Romantic' },
  { id: 2, title: 'Butta Bomma', artist: 'Armaan Malik', source: 'YouTube', mood: 'Happy' },
  { id: 3, title: 'Ramuloo Ramulaa', artist: 'Anurag Kulkarni', source: 'YouTube', mood: 'Party' },
  { id: 4, title: 'Inkem Inkem', artist: 'Sid Sriram', source: 'Spotify', mood: 'Chill' },
  { id: 5, title: 'Seeti Maar', artist: 'Jaspreet Jasz', source: 'Spotify', mood: 'Workout' },
  { id: 6, title: 'Priyathama Priyathama', artist: 'Chinmayi', source: 'YouTube', mood: 'Focus' }
];

const searchInput = document.getElementById('searchInput');
const searchBtn = document.getElementById('searchBtn');
const resultGrid = document.getElementById('resultGrid');
const favoriteGrid = document.getElementById('favoriteGrid');
const moodChips = document.querySelectorAll('.chip');

function getFavorites() {
  return JSON.parse(localStorage.getItem('tt_favorites') || '[]');
}

function setFavorites(favs) {
  localStorage.setItem('tt_favorites', JSON.stringify(favs));
}

function addToFavorites(id) {
  const favs = getFavorites();
  if (!favs.includes(id)) {
    favs.push(id);
    setFavorites(favs);
  }
  renderFavorites();
}

function renderSongs(container, songs, showFavoriteButton = true) {
  if (!songs.length) {
    container.innerHTML = '<p>No songs found.</p>';
    return;
  }

  container.innerHTML = songs.map(song => `
    <article class="song-card">
      <strong>${song.title}</strong>
      <p>${song.artist}</p>
      <span class="tag">${song.source}</span>
      ${showFavoriteButton ? `<button onclick="addToFavorites(${song.id})">❤ Add favorite</button>` : ''}
    </article>
  `).join('');
}

function renderFavorites() {
  const favs = getFavorites();
  const favoriteSongs = catalog.filter(song => favs.includes(song.id));
  renderSongs(favoriteGrid, favoriteSongs, false);
}

function runSearch() {
  const q = (searchInput.value || '').toLowerCase();
  const filtered = catalog.filter(song =>
    song.title.toLowerCase().includes(q) || song.artist.toLowerCase().includes(q)
  );
  renderSongs(resultGrid, filtered);
}

searchBtn?.addEventListener('click', runSearch);
searchInput?.addEventListener('keydown', (e) => {
  if (e.key === 'Enter') runSearch();
});

moodChips.forEach(chip => {
  chip.addEventListener('click', () => {
    const mood = chip.innerText;
    const filtered = catalog.filter(song => song.mood === mood);
    renderSongs(resultGrid, filtered);
  });
});

window.addToFavorites = addToFavorites;
renderSongs(resultGrid, catalog);
renderFavorites();
