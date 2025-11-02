<template>
  <nav class="navbar">
    <div class="nav-brand">
      <h2>🌱 Food Waste Management</h2>
    </div>
    
    <div class="nav-links">
      <router-link to="/dashboard">Dashboard</router-link>
      <router-link to="/donors">Donors</router-link>
      <router-link to="/waste-items">Waste Items</router-link>
      <router-link to="/centers">Centers</router-link>
      <router-link to="/processors">Processors</router-link>
    </div>
    
    <div class="nav-user">
      <span class="username">{{ username }}</span>
      <button @click="handleLogout" class="btn-logout">Logout</button>
    </div>
  </nav>
</template>

<script>
import { authAPI } from '@/services/api';

export default {
  name: 'Navbar',
  data() {
    return {
      username: localStorage.getItem('username') || 'User'
    };
  },
  methods: {
    handleLogout() {
      authAPI.logout();
      this.$router.push('/login');
    }
  }
};
</script>

<style scoped>
.navbar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 15px 30px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.nav-brand h2 {
  color: white;
  margin: 0;
  font-size: 1.5em;
}

.nav-links {
  display: flex;
  gap: 25px;
}

.nav-links a {
  color: white;
  text-decoration: none;
  font-weight: 600;
  padding: 8px 16px;
  border-radius: 6px;
  transition: background 0.3s;
}

.nav-links a:hover {
  background: rgba(255,255,255,0.2);
}

.nav-links a.router-link-active {
  background: rgba(255,255,255,0.3);
}

.nav-user {
  display: flex;
  align-items: center;
  gap: 15px;
}

.username {
  color: white;
  font-weight: 600;
}

.btn-logout {
  padding: 8px 16px;
  background: rgba(255,255,255,0.2);
  color: white;
  border: 2px solid white;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
  transition: all 0.3s;
}

.btn-logout:hover {
  background: white;
  color: #667eea;
}
</style>