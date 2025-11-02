<template>
  <div class="page">
    <ParallaxBackground />
    
    <!-- Animated Particles Background -->
    <div class="particles-bg">
      <div class="particle" v-for="n in 30" :key="n" :style="getParticleBgStyle(n)"></div>
    </div>
    
    <div class="content">
      <!-- Hero Section with Animated Title -->
      <div class="hero-section">
        <h1 class="animated-title">
          <span class="title-word" v-for="(word, i) in titleWords" :key="i" :style="{animationDelay: `${i * 0.1}s`}">
            {{ word }}
          </span>
        </h1>
        <p class="animated-subtitle">Real-time intelligence • Live tracking • Zero waste future</p>
      </div>
      
      <div v-if="loading" class="loading-container">
        <div class="loader-ring">
          <div class="loader-segment"></div>
          <div class="loader-segment"></div>
          <div class="loader-segment"></div>
          <div class="loader-segment"></div>
        </div>
        <p class="loading-text">Analyzing waste data...</p>
      </div>
      
      <div v-else class="dashboard-layout">
        <!-- Top Stats Cards with Motion -->
        <div class="stats-grid">
          <div 
            class="stat-card primary-card" 
            v-for="(stat, index) in primaryStats" 
            :key="index"
            :style="{ animationDelay: `${index * 0.15}s` }"
          >
            <div class="card-glow"></div>
            <div class="stat-icon-container">
              <div class="icon-pulse"></div>
              <span class="stat-icon">{{ stat.icon }}</span>
            </div>
            <div class="stat-details">
              <p class="stat-label">{{ stat.label }}</p>
              <h2 class="stat-value">
                <span class="number-counter">{{ stat.value }}</span>
                <span class="unit">{{ stat.unit }}</span>
              </h2>
              <div class="stat-change" :class="stat.trend">
                <span class="trend-arrow">{{ stat.trend === 'up' ? '↗' : '→' }}</span>
                <span>{{ stat.change }}</span>
              </div>
            </div>
            <div class="card-decoration">
              <div class="deco-circle"></div>
              <div class="deco-circle"></div>
              <div class="deco-circle"></div>
            </div>
          </div>
        </div>
        
        <!-- Real-time Activity Feed -->
        <div class="activity-section">
          <div class="section-header">
            <h3>
              <span class="pulse-dot"></span>
              Live Activity Feed
            </h3>
          </div>
          <div class="activity-cards">
            <div 
              class="activity-card" 
              v-for="(activity, i) in recentActivities" 
              :key="i"
              :style="{ animationDelay: `${i * 0.1}s` }"
            >
              <div class="activity-icon" :class="activity.type">{{ activity.icon }}</div>
              <div class="activity-content">
                <p class="activity-title">{{ activity.title }}</p>
                <p class="activity-time">{{ activity.time }}</p>
              </div>
              <div class="activity-badge" :class="activity.status">
                {{ activity.statusText }}
              </div>
            </div>
          </div>
        </div>
        
        <!-- Charts Section with Animated Bars -->
        <div class="charts-container">
          <div class="chart-card waste-distribution">
            <div class="chart-header">
              <h3>📊 Waste Distribution</h3>
              <div class="chart-legend">
                <span class="legend-item" v-for="type in wasteByType" :key="type.type">
                  <span class="legend-dot" :style="{ background: type.color }"></span>
                  {{ type.type }}
                </span>
              </div>
            </div>
            <div class="animated-chart">
              <div 
                class="chart-bar" 
                v-for="(item, i) in wasteByType" 
                :key="i"
                :style="{ animationDelay: `${i * 0.2}s` }"
              >
                <div class="bar-label">
                  <span class="bar-emoji">{{ getTypeEmoji(item.type) }}</span>
                  <span class="bar-name">{{ item.type }}</span>
                </div>
                <div class="bar-track">
                  <div 
                    class="bar-fill" 
                    :style="{ 
                      width: `${item.percentage}%`, 
                      background: `linear-gradient(90deg, ${item.color}, ${item.color}dd)`,
                      boxShadow: `0 0 20px ${item.color}80`
                    }"
                  >
                    <span class="bar-value">{{ item.weight.toFixed(1) }} kg</span>
                  </div>
                </div>
                <div class="bar-count">{{ item.count }}</div>
              </div>
            </div>
          </div>
          
          <!-- Environmental Impact with Animated Icons -->
          <div class="chart-card impact-card">
            <div class="chart-header">
              <h3>🌍 Environmental Impact</h3>
              <p class="impact-subtitle">Your contribution today</p>
            </div>
            <div class="impact-grid">
              <div 
                class="impact-item" 
                v-for="(impact, i) in impacts" 
                :key="i"
                :style="{ animationDelay: `${i * 0.15}s` }"
              >
                <div class="impact-icon-wrapper">
                  <span class="impact-icon">{{ impact.icon }}</span>
                  <div class="icon-ring"></div>
                </div>
                <div class="impact-data">
                  <p class="impact-value">{{ impact.value }}</p>
                  <p class="impact-label">{{ impact.label }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <!-- Quick Actions with Hover Effects -->
        <div class="quick-actions">
          <h3 class="section-title">⚡ Quick Actions</h3>
          <div class="actions-grid">
            <router-link 
              v-for="(action, i) in quickActions" 
              :key="i"
              :to="action.path" 
              class="action-button"
              :style="{ animationDelay: `${i * 0.1}s` }"
            >
              <div class="action-bg"></div>
              <span class="action-icon">{{ action.icon }}</span>
              <span class="action-text">{{ action.text }}</span>
              <span class="action-arrow">→</span>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { wasteItemsAPI, donorsAPI, centersAPI } from '@/services/api';
import ParallaxBackground from '@/components/ParallaxBackground.vue';

export default {
  name: 'Dashboard',
  components: {
    ParallaxBackground
  },
  data() {
    return {
      loading: false,
      titleWords: ['🌱', 'Food', 'Waste', 'Intelligence'],
      stats: {
        totalItems: 0,
        totalWeight: 0,
        processedItems: 0,
        pendingItems: 0,
        totalDonors: 0,
        totalCenters: 0,
        totalProcessors: 0
      },
      wasteByType: [],
      quickActions: [
        { icon: '🗑️', text: 'Manage Waste', path: '/waste-items' },
        { icon: '🏢', text: 'Food Donors', path: '/donors' },
        { icon: '📍', text: 'Centers', path: '/centers' },
        { icon: '♻️', text: 'Processors', path: '/processors' }
      ]
    };
  },
  computed: {
    primaryStats() {
      return [
        {
          icon: '🗑️',
          label: 'Waste Reduced',
          value: Math.floor(this.stats.totalWeight),
          unit: 'kg',
          change: '+12% from last week',
          trend: 'up'
        },
        {
          icon: '💰',
          label: 'Cost Savings',
          value: `$${Math.floor(this.stats.totalWeight * 45)}`,
          unit: '',
          change: '+8% from last week',
          trend: 'up'
        },
        {
          icon: '🤝',
          label: 'NGO Partnerships',
          value: this.stats.totalDonors,
          unit: '',
          change: '2 new this month',
          trend: 'up'
        },
        {
          icon: '🌿',
          label: 'Carbon Footprint',
          value: (this.stats.totalWeight * 2.5).toFixed(1),
          unit: 'tons',
          change: '-15% reduction',
          trend: 'down'
        }
      ];
    },
    
    recentActivities() {
      return [
        { 
          icon: '🥬', 
          title: 'Fresh Vegetables - Ready for pickup', 
          time: '2 days', 
          type: 'vegetables',
          status: 'ready',
          statusText: 'Ready'
        },
        { 
          icon: '🍞', 
          title: 'Bread & Pastries - Urgent', 
          time: '1 day', 
          type: 'grains',
          status: 'urgent',
          statusText: 'Urgent'
        },
        { 
          icon: '🥛', 
          title: 'Dairy Products - Ready', 
          time: '3 days', 
          type: 'dairy',
          status: 'ready',
          statusText: 'Ready'
        },
        { 
          icon: '🥕', 
          title: 'Fresh Vegetables - 32 portions', 
          time: '4 hours', 
          type: 'vegetables',
          status: 'urgent',
          statusText: 'Urgent'
        }
      ];
    },
    
    impacts() {
      return [
        { icon: '🍽️', value: Math.floor(this.stats.totalWeight / 0.5), label: 'Meals Saved' },
        { icon: '🌱', value: `${(this.stats.totalWeight * 2.5).toFixed(1)} kg`, label: 'CO₂ Prevented' },
        { icon: '💧', value: Math.floor(this.stats.totalWeight * 170), label: 'Water Saved (L)' },
        { icon: '👥', value: this.stats.totalDonors, label: 'Active Partners' }
      ];
    }
  },
  async mounted() {
    await this.loadStats();
  },
  methods: {
    async loadStats() {
      this.loading = true;
      try {
        const [itemsRes, donorsRes, centersRes] = await Promise.all([
          wasteItemsAPI.getAll(),
          donorsAPI.getAll(),
          centersAPI.getAll()
        ]);
        
        let items = itemsRes.data;
        if (typeof items === 'string') items = JSON.parse(items);
        items = Array.isArray(items) ? items : [];
        
        let donors = donorsRes.data;
        if (typeof donors === 'string') donors = JSON.parse(donors);
        donors = Array.isArray(donors) ? donors : [];
        
        let centers = centersRes.data;
        if (typeof centers === 'string') centers = JSON.parse(centers);
        centers = Array.isArray(centers) ? centers : [];
        
        this.stats.totalItems = items.length;
        this.stats.totalWeight = items.reduce((sum, item) => sum + parseFloat(item.weight || 0), 0);
        this.stats.processedItems = items.filter(item => item.processed).length;
        this.stats.pendingItems = items.filter(item => !item.processed).length;
        this.stats.totalDonors = donors.length;
        this.stats.totalCenters = centers.length;
        this.stats.totalProcessors = centers.length;
        
        const typeData = {};
        items.forEach(item => {
          const type = item.wasteType || 'other';
          if (!typeData[type]) {
            typeData[type] = { count: 0, weight: 0 };
          }
          typeData[type].count++;
          typeData[type].weight += parseFloat(item.weight || 0);
        });
        
        const colors = {
          vegetables: '#4ade80',
          dairy: '#60a5fa',
          grains: '#fbbf24',
          meat: '#f87171',
          other: '#9ca3af'
        };
        
        const maxWeight = Math.max(...Object.values(typeData).map(d => d.weight), 1);
        this.wasteByType = Object.entries(typeData).map(([type, data]) => ({
          type,
          count: data.count,
          weight: data.weight,
          percentage: (data.weight / maxWeight) * 100,
          color: colors[type] || '#9ca3af'
        }));
        
      } catch (error) {
        console.error('Error loading stats:', error);
      } finally {
        this.loading = false;
      }
    },
    
    getTypeEmoji(type) {
      const emojis = {
        vegetables: '🥬',
        dairy: '🥛',
        grains: '🌾',
        meat: '🍖',
        other: '📦'
      };
      return emojis[type] || '📦';
    },
    
    getParticleBgStyle(n) {
      return {
        left: `${Math.random() * 100}%`,
        top: `${Math.random() * 100}%`,
        animationDelay: `${n * 0.2}s`,
        animationDuration: `${15 + Math.random() * 10}s`
      };
    }
  }
};
</script>

<style scoped>
.page {
  min-height: 100vh;
  color: white;
  position: relative;
  overflow-x: hidden;
}

/* Animated Particles Background */
.particles-bg {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 0;
}

.particle {
  position: absolute;
  width: 4px;
  height: 4px;
  background: radial-gradient(circle, rgba(74, 222, 128, 0.8), transparent);
  border-radius: 50%;
  animation: floatParticle 20s infinite ease-in-out;
}

@keyframes floatParticle {
  0%, 100% {
    transform: translateY(0) translateX(0);
    opacity: 0;
  }
  10% {
    opacity: 1;
  }
  90% {
    opacity: 1;
  }
  100% {
    transform: translateY(-100vh) translateX(50px);
    opacity: 0;
  }
}

.content {
  position: relative;
  z-index: 1;
  padding: 60px 30px;
  max-width: 1600px;
  margin: 0 auto;
}

/* Hero Section with Animated Title */
.hero-section {
  text-align: center;
  margin-bottom: 60px;
}

.animated-title {
  font-size: clamp(3em, 7vw, 5.5em);
  font-weight: 900;
  margin: 0 0 20px 0;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 20px;
}

.title-word {
  display: inline-block;
  background: linear-gradient(135deg, #4ade80, #10b981, #4ade80);
  background-size: 200% 200%;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  animation: titleShine 3s ease-in-out infinite, titleBounce 0.8s ease-out backwards;
  text-shadow: 0 0 40px rgba(74, 222, 128, 0.5);
}

@keyframes titleShine {
  0%, 100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

@keyframes titleBounce {
  0% {
    opacity: 0;
    transform: translateY(-50px) scale(0.3);
  }
  50% {
    transform: translateY(0) scale(1.05);
  }
  100% {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.animated-subtitle {
  font-size: clamp(1em, 2vw, 1.3em);
  color: rgba(255, 255, 255, 0.7);
  animation: fadeInUp 1s ease-out 0.5s backwards;
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Loading Animation */
.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 100px 20px;
  gap: 30px;
}

.loader-ring {
  width: 80px;
  height: 80px;
  position: relative;
}

.loader-segment {
  position: absolute;
  width: 100%;
  height: 100%;
  border: 4px solid transparent;
  border-radius: 50%;
  border-top-color: #4ade80;
  animation: rotate 1.5s cubic-bezier(0.5, 0, 0.5, 1) infinite;
}

.loader-segment:nth-child(1) { animation-delay: -0.45s; }
.loader-segment:nth-child(2) { animation-delay: -0.3s; }
.loader-segment:nth-child(3) { animation-delay: -0.15s; }

@keyframes rotate {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.loading-text {
  font-size: 1.2em;
  color: rgba(255, 255, 255, 0.8);
  animation: pulse 2s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 0.6; }
  50% { opacity: 1; }
}

/* Dashboard Layout */
.dashboard-layout {
  display: flex;
  flex-direction: column;
  gap: 50px;
}

/* Stats Grid with Motion */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 30px;
}

.stat-card {
  position: relative;
  padding: 35px;
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(25px);
  border-radius: 25px;
  border: 1px solid rgba(74, 222, 128, 0.2);
  overflow: hidden;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  animation: slideInUp 0.8s ease-out backwards;
  cursor: pointer;
}

@keyframes slideInUp {
  from {
    opacity: 0;
    transform: translateY(60px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.card-glow {
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: radial-gradient(circle, rgba(74, 222, 128, 0.1), transparent 70%);
  animation: glowMove 8s ease-in-out infinite;
}

@keyframes glowMove {
  0%, 100% { transform: translate(0, 0); }
  50% { transform: translate(20px, 20px); }
}

.stat-card:hover {
  transform: translateY(-10px) scale(1.02);
  border-color: rgba(74, 222, 128, 0.5);
  box-shadow: 0 20px 60px rgba(74, 222, 128, 0.3);
}

.stat-icon-container {
  position: relative;
  width: 70px;
  height: 70px;
  margin-bottom: 20px;
}

.icon-pulse {
  position: absolute;
  width: 100%;
  height: 100%;
  background: radial-gradient(circle, rgba(74, 222, 128, 0.3), transparent 70%);
  border-radius: 50%;
  animation: iconPulse 2s ease-in-out infinite;
}

@keyframes iconPulse {
  0%, 100% {
    transform: scale(1);
    opacity: 0.5;
  }
  50% {
    transform: scale(1.3);
    opacity: 0;
  }
}

.stat-icon {
  position: relative;
  font-size: 3em;
  filter: drop-shadow(0 0 20px rgba(74, 222, 128, 0.6));
  animation: iconBounce 3s ease-in-out infinite;
}

@keyframes iconBounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.stat-label {
  font-size: 0.9em;
  color: rgba(255, 255, 255, 0.6);
  text-transform: uppercase;
  letter-spacing: 1px;
  margin: 0 0 10px 0;
}

.stat-value {
  font-size: 2.5em;
  font-weight: 900;
  margin: 0 0 10px 0;
  display: flex;
  align-items: baseline;
  gap: 5px;
}

.number-counter {
  background: linear-gradient(135deg, #fff, #4ade80);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.unit {
  font-size: 0.5em;
  color: rgba(255, 255, 255, 0.6);
}

.stat-change {
  font-size: 0.9em;
  display: flex;
  align-items: center;
  gap: 5px;
}

.stat-change.up {
  color: #4ade80;
}

.stat-change.down {
  color: #f87171;
}

.trend-arrow {
  font-size: 1.2em;
}

.card-decoration {
  position: absolute;
  bottom: 10px;
  right: 10px;
  display: flex;
  gap: 5px;
}

.deco-circle {
  width: 8px;
  height: 8px;
  background: rgba(74, 222, 128, 0.3);
  border-radius: 50%;
  animation: blink 2s ease-in-out infinite;
}

.deco-circle:nth-child(2) { animation-delay: 0.3s; }
.deco-circle:nth-child(3) { animation-delay: 0.6s; }

@keyframes blink {
  0%, 100% { opacity: 0.3; }
  50% { opacity: 1; }
}

/* Activity Feed */
.activity-section {
  animation: slideInUp 0.8s ease-out 0.3s backwards;
}

.section-header {
  margin-bottom: 25px;
}

.section-header h3 {
  font-size: 1.8em;
  font-weight: 700;
  display: flex;
  align-items: center;
  gap: 15px;
}

.pulse-dot {
  width: 12px;
  height: 12px;
  background: #4ade80;
  border-radius: 50%;
  animation: pulseDot 2s ease-in-out infinite;
  box-shadow: 0 0 20px #4ade80;
}

@keyframes pulseDot {
  0%, 100% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.5);
    opacity: 0.5;
  }
}

.activity-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}

.activity-card {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 20px;
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(20px);
  border-radius: 18px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  transition: all 0.3s ease;
  animation: slideInLeft 0.6s ease-out backwards;
}

@keyframes slideInLeft {
  from {
    opacity: 0;
    transform: translateX(-30px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.activity-card:hover {
  transform: translateX(5px);
  border-color: rgba(74, 222, 128, 0.3);
  background: rgba(255, 255, 255, 0.05);
}

.activity-icon {
  font-size: 2.5em;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 15px;
  background: rgba(74, 222, 128, 0.1);
  animation: iconRotate 3s ease-in-out infinite;
}

@keyframes iconRotate {
  0%, 100% { transform: rotate(0deg); }
  50% { transform: rotate(5deg); }
}

.activity-content {
  flex: 1;
}

.activity-title {
  margin: 0 0 5px 0;
  font-weight: 600;
  color: white;
}

.activity-time {
  margin: 0;
  font-size: 0.85em;
  color: rgba(255, 255, 255, 0.5);
}

.activity-badge {
  padding: 6px 14px;
  border-radius: 20px;
  font-size: 0.8em;
  font-weight: 700;
}

.activity-badge.ready {
  background: rgba(74, 222, 128, 0.2);
  color: #4ade80;
}

.activity-badge.urgent {
  background: rgba(239, 68, 68, 0.2);
  color: #f87171;
  animation: urgentBlink 1.5s ease-in-out infinite;
}

@keyframes urgentBlink {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}

/* Charts Section */
.charts-container {
  display: grid;
  grid-template-columns: 1.5fr 1fr;
  gap: 30px;
}

.chart-card {
  padding: 35px;
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(20px);
  border-radius: 25px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  animation: slideInUp 0.8s ease-out 0.5s backwards;
}

.chart-header h3 {
  margin: 0 0 10px 0;
  font-size: 1.5em;
  font-weight: 700;
}

.chart-legend {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  margin-top: 10px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 0.85em;
  color: rgba(255, 255, 255, 0.7);
}

.legend-dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
}

.impact-subtitle {
  margin: 0 0 25px 0;
  color: rgba(255, 255, 255, 0.5);
  font-size: 0.9em;
}

.animated-chart {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 25px;
}

.chart-bar {
  display: grid;
  grid-template-columns: 150px 1fr 60px;
  align-items: center;
  gap: 15px;
  animation: slideInRight 0.8s ease-out backwards;
}

@keyframes slideInRight {
  from {
    opacity: 0;
    transform: translateX(-30px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.bar-label {
  display: flex;
  align-items: center;
  gap: 10px;
}

.bar-emoji {
  font-size: 1.8em;
}

.bar-name {
  font-weight: 600;
  text-transform: capitalize;
}

.bar-track {
  height: 40px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 20px;
  overflow: hidden;
  position: relative;
}

.bar-fill {
  height: 100%;
  border-radius: 20px;
  display: flex;
  align-items: center;
  padding: 0 15px;
  animation: barGrow 1.5s ease-out;
  position: relative;
  overflow: hidden;
}

@keyframes barGrow {
  from {
    width: 0 !important;
  }
}

.bar-fill::after {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  animation: shimmer 2s infinite;
}

@keyframes shimmer {
  0% { left: -100%; }
  100% { left: 100%; }
}

.bar-value {
  font-size: 0.85em;
  font-weight: 700;
  color: white;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
  position: relative;
  z-index: 1;
}

.bar-count {
  font-weight: 700;
  color: rgba(255, 255, 255, 0.8);
  text-align: right;
}

/* Impact Grid */
.impact-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.impact-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 25px;
  background: rgba(255, 255, 255, 0.03);
  border-radius: 18px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  transition: all 0.3s ease;
  animation: scaleIn 0.6s ease-out backwards;
}

@keyframes scaleIn {
  from {
    opacity: 0;
    transform: scale(0.8);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

.impact-item:hover {
  transform: scale(1.05);
  background: rgba(255, 255, 255, 0.05);
  border-color: rgba(74, 222, 128, 0.3);
}

.impact-icon-wrapper {
  position: relative;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.impact-icon {
  font-size: 2.5em;
  position: relative;
  z-index: 2;
  animation: iconFloat 3s ease-in-out infinite;
}

@keyframes iconFloat {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-8px); }
}

.icon-ring {
  position: absolute;
  width: 100%;
  height: 100%;
  border: 2px solid rgba(74, 222, 128, 0.3);
  border-radius: 50%;
  animation: ringPulse 2s ease-in-out infinite;
}

@keyframes ringPulse {
  0%, 100% {
    transform: scale(1);
    opacity: 0.5;
  }
  50% {
    transform: scale(1.3);
    opacity: 0;
  }
}

.impact-data {
  flex: 1;
}

.impact-value {
  margin: 0 0 5px 0;
  font-size: 1.8em;
  font-weight: 800;
  color: #4ade80;
}

.impact-label {
  margin: 0;
  font-size: 0.85em;
  color: rgba(255, 255, 255, 0.6);
}

/* Quick Actions */
.quick-actions {
  animation: slideInUp 0.8s ease-out 0.7s backwards;
}

.section-title {
  font-size: 1.8em;
  font-weight: 700;
  margin: 0 0 25px 0;
}

.actions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 20px;
}

.action-button {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 25px 30px;
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(20px);
  border-radius: 20px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  text-decoration: none;
  color: white;
  overflow: hidden;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  animation: slideInUp 0.6s ease-out backwards;
}

.action-bg {
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(74, 222, 128, 0.1), transparent);
  transition: left 0.5s ease;
}

.action-button:hover .action-bg {
  left: 100%;
}

.action-button:hover {
  transform: translateY(-8px) scale(1.03);
  border-color: rgba(74, 222, 128, 0.5);
  box-shadow: 0 15px 40px rgba(74, 222, 128, 0.3);
}

.action-icon {
  font-size: 2.5em;
  filter: drop-shadow(0 0 15px rgba(74, 222, 128, 0.4));
  animation: iconBounce 3s ease-in-out infinite;
}

.action-text {
  flex: 1;
  font-size: 1.1em;
  font-weight: 600;
  margin: 0 15px;
}

.action-arrow {
  font-size: 1.5em;
  transition: transform 0.3s ease;
}

.action-button:hover .action-arrow {
  transform: translateX(5px);
}

/* Responsive Design */
@media (max-width: 1200px) {
  .charts-container {
    grid-template-columns: 1fr;
  }
  
  .chart-bar {
    grid-template-columns: 120px 1fr 50px;
  }
}

@media (max-width: 768px) {
  .content {
    padding: 40px 20px;
  }
  
  .animated-title {
    font-size: 2.5em;
    gap: 10px;
  }
  
  .stats-grid {
    grid-template-columns: 1fr;
  }
  
  .activity-cards {
    grid-template-columns: 1fr;
  }
  
  .impact-grid {
    grid-template-columns: 1fr;
  }
  
  .chart-bar {
    grid-template-columns: 1fr;
    gap: 10px;
  }
  
  .bar-label {
    justify-content: space-between;
  }
  
  .bar-count {
    order: 3;
  }
  
  .actions-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 480px) {
  .stat-value {
    font-size: 2em;
  }
  
  .impact-value {
    font-size: 1.4em;
  }
  
  .action-icon {
    font-size: 2em;
  }
}
</style>