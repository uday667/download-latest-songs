const resultGrid = document.getElementById('resultGrid');
const trendingGrid = document.getElementById('trendingGrid');
const latestGrid = document.getElementById('latestGrid');
const resultsMeta = document.getElementById('resultsMeta');
const searchInput = document.getElementById('searchInput');
const searchBtn = document.getElementById('searchBtn');
const moodChips = document.querySelectorAll('.chip');
const playlistName = document.getElementById('playlistName');
const createPlaylistBtn = document.getElementById('createPlaylistBtn');
const exportPlaylistBtn = document.getElementById('exportPlaylistBtn');
const playlistList = document.getElementById('playlistList');

function getPlaylists() { return JSON.parse(localStorage.getItem('tt_playlists') || '[]'); }
function setPlaylists(items) { localStorage.setItem('tt_playlists', JSON.stringify(items)); }

async function fetchJson(url) {
  const response = await fetch(url);
  if (!response.ok) throw new Error(`Request failed: ${response.status}`);
  return response.json();
}

async function requestDownloadLink(songId) {
  const data = await fetchJson(`/api/music/${songId}/download`);
  window.open(data.legalLink, '_blank');
}

function renderSongCards(container, songs) {
  if (!songs.length) {
    container.innerHTML = '<p>No songs found.</p>';
    return;
  }

  container.innerHTML = songs.map(song => `
    <article class="song-card">
      <strong>${song.title}</strong>
      <p>${song.artist} · ${song.album} (${song.year})</p>
      <span class="tag">${song.source}</span>
      <div class="song-actions">
        <button onclick="window.open('${song.streamUrl}', '_blank')">Play</button>
        <button class="secondary" onclick="requestDownloadLink(${song.id})">Legal Download Link</button>
        <button onclick="addSongToPlaylist('${song.title.replace(/'/g, '')}', '${song.artist.replace(/'/g, '')}')">Add to playlist</button>
      </div>
    </article>
  `).join('');
}

function renderPlaylists() {
  const playlists = getPlaylists();
  if (!playlists.length) {
    playlistList.innerHTML = '<p>No playlists yet.</p>';
    return;
  }

  playlistList.innerHTML = playlists.map(list => `
    <article class="playlist-card">
      <strong>${list.name}</strong>
      <p>${list.songs.length} songs</p>
      <ul>${list.songs.map(song => `<li>${song}</li>`).join('')}</ul>
    </article>
  `).join('');
}

window.addSongToPlaylist = (title, artist) => {
  const playlists = getPlaylists();
  if (!playlists.length) {
    alert('Create a playlist first.');
    return;
  }

  playlists[0].songs.push(`${title} - ${artist}`);
  setPlaylists(playlists);
  renderPlaylists();
};

window.requestDownloadLink = (songId) => {
  requestDownloadLink(songId).catch(() => alert('Unable to open legal provider link right now.'));
};

function createPlaylist() {
  const name = (playlistName.value || '').trim();
  if (!name) return;

  const playlists = getPlaylists();
  playlists.push({ name, songs: [] });
  setPlaylists(playlists);
  playlistName.value = '';
  renderPlaylists();
}

function exportPlaylists() {
  const data = JSON.stringify(getPlaylists(), null, 2);
  const blob = new Blob([data], { type: 'application/json' });
  const url = URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = 'playlists.json';
  a.click();
  URL.revokeObjectURL(url);
}

async function runSearch() {
  const raw = searchInput.value || '';
  const q = encodeURIComponent(raw);
  const data = await fetchJson(`/api/music/search?q=${q}`);
  resultsMeta.textContent = `Found ${data.length} songs for "${raw || 'all'}"`;
  renderSongCards(resultGrid, data);
}

async function loadHomeSections() {
  const [trending, latest] = await Promise.all([
    fetchJson('/api/music/trending'),
    fetchJson('/api/music/latest')
  ]);

  renderSongCards(trendingGrid, trending);
  renderSongCards(latestGrid, latest);
  renderSongCards(resultGrid, trending);
  resultsMeta.textContent = `Showing ${trending.length} trending songs`;
  renderPlaylists();
}

searchBtn?.addEventListener('click', () => runSearch().catch(console.error));
searchInput?.addEventListener('keydown', (e) => {
  if (e.key === 'Enter') runSearch().catch(console.error);
});

moodChips.forEach(chip => {
  chip.addEventListener('click', async () => {
    const mood = encodeURIComponent(chip.innerText);
    const response = await fetchJson(`/api/music/recommendations?mood=${mood}`);
    resultsMeta.textContent = `Mood: ${chip.innerText} · ${response.songs.length} songs`;
    renderSongCards(resultGrid, response.songs || []);
  });
});

createPlaylistBtn?.addEventListener('click', createPlaylist);
exportPlaylistBtn?.addEventListener('click', exportPlaylists);

loadHomeSections().catch(console.error);
