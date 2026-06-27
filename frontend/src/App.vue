<template>
  <div id="app">
    <nav class="navbar">
      <div class="navbar-brand">
        <span>Agendamento de transferências</span>
      </div>
      <div class="nav-links">
        <router-link to="/agendar" class="nav-link">Agendar</router-link>
        <router-link to="/extrato" class="nav-link">Extrato</router-link>
      </div>
    </nav>
    <main class="container">
      <router-view />
    </main>
  </div>
</template>

<style>
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: Arial, sans-serif;
  background-color: #f0f2f5;
  color: #333;
}

.navbar {
  background-color: #ffffff;
  border-bottom: 1px solid #e0e0e0;
  padding: 0.75rem 2rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.navbar-brand span {
  font-size: 16px;
  font-weight: 600;
  color: #1a73e8;
}

.nav-links {
  display: flex;
  gap: 8px;
}

.nav-link {
  padding: 6px 16px;
  border-radius: 6px;
  font-size: 14px;
  text-decoration: none;
  color: #555;
  transition: background 0.15s;
}

.nav-link:hover {
  background-color: #f0f2f5;
}

.nav-link.router-link-active {
  background-color: #e8f0fe;
  color: #1a73e8;
  font-weight: 500;
}

.container {
  max-width: 900px;
  margin: 2rem auto;
  padding: 0 1rem;
}
</style>