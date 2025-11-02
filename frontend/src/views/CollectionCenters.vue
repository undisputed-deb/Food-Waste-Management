<template>
  <div class="page">
    <ParallaxBackground />
    
    <div class="content">
      <h1 class="page-title">📍 Collection Centers</h1>
      
      <button @click="handleAddCenter" class="btn-add">
        <span class="btn-icon">+</span>
        <span>Add Center</span>
      </button>
      
      <div v-if="loading" class="loading-container">
        <div class="loader"></div>
        <p>Loading centers...</p>
      </div>
      
      <div v-else class="grid">
        <div 
          v-for="(center, index) in centers" 
          :key="center.id" 
          class="card"
          :style="{ animationDelay: `${index * 0.1}s` }"
        >
          <div class="card-header">
            <h3>{{ center.location }}</h3>
            <div class="capacity-badge">
              {{ Math.round((center.currentCapacity / center.maxCapacity) * 100) }}%
            </div>
          </div>
          
          <div class="capacity-bar">
            <div 
              class="capacity-fill" 
              :style="{ width: `${(center.currentCapacity / center.maxCapacity) * 100}%` }"
            ></div>
          </div>
          
          <div class="card-stats">
            <div class="stat">
              <span class="stat-label">Current</span>
              <span class="stat-value">{{ center.currentCapacity }} kg</span>
            </div>
            <div class="stat">
              <span class="stat-label">Max Capacity</span>
              <span class="stat-value">{{ center.maxCapacity }} kg</span>
            </div>
          </div>
          
          <button @click="handleDeleteCenter(center.id)" class="btn-delete">
            <span>🗑️</span> Delete
          </button>
        </div>
        
        <div v-if="centers.length === 0" class="empty-state">
          <div class="empty-icon">📦</div>
          <h3>No Collection Centers</h3>
          <p>Add your first collection center to get started</p>
        </div>
      </div>
    </div>
    
    <transition name="modal">
      <div v-if="showModal" class="modal" @click="closeModal">
        <div class="modal-content" @click.stop>
          <div class="modal-header">
            <h2>Add Collection Center</h2>
            <button @click="closeModal" class="btn-close">×</button>
          </div>
          
          <form @submit.prevent="saveCenter">
            <div class="form-group">
              <label>Location</label>
              <input 
                v-model="form.location" 
                type="text" 
                placeholder="Enter location name" 
                required 
              />
            </div>
            
            <div class="form-group">
              <label>Max Capacity (kg)</label>
              <input 
                v-model.number="form.maxCapacity" 
                type="text" 
                inputmode="numeric"
                placeholder="Enter max capacity" 
                required 
              />
            </div>
            
            <div class="form-group">
              <label>Current Capacity (kg)</label>
              <input 
                v-model.number="form.currentCapacity" 
                type="text" 
                inputmode="numeric"
                placeholder="Enter current capacity" 
              />
            </div>
            
            <div class="modal-actions">
              <button type="button" @click="closeModal" class="btn-secondary">
                Cancel
              </button>
              <button type="submit" class="btn-primary">
                Save Center
              </button>
            </div>
          </form>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import { centersAPI } from '@/services/api';
import ParallaxBackground from '@/components/ParallaxBackground.vue';

export default {
  name: 'CollectionCenters',
  components: {
    ParallaxBackground
  },
  data() {
    return {
      centers: [],
      loading: false,
      showModal: false,
      form: { 
        location: '', 
        maxCapacity: '', 
        currentCapacity: 0 
      }
    };
  },
  async mounted() {
    await this.loadCenters();
  },
  methods: {
    async loadCenters() {
      this.loading = true;
      try {
        const response = await centersAPI.getAll();
        let centersData = response.data;
        if (typeof centersData === 'string') {
          centersData = JSON.parse(centersData);
        }
        this.centers = Array.isArray(centersData) ? centersData : [];
      } catch (error) {
        console.error('Error loading centers:', error);
        alert('Error loading centers');
      } finally {
        this.loading = false;
      }
    },
    
    handleAddCenter() {
      const token = localStorage.getItem('token');
      if (!token) {
        this.$router.push({ path: '/login', query: { returnUrl: '/centers' } });
      } else {
        this.showModal = true;
      }
    },
    
    async handleDeleteCenter(id) {
      const token = localStorage.getItem('token');
      if (!token) {
        this.$router.push({ path: '/login', query: { returnUrl: '/centers' } });
        return;
      }
      
      if (confirm('Delete this center?')) {
        try {
          await centersAPI.delete(id);
          await this.loadCenters();
        } catch (error) {
          console.error('Error deleting center:', error);
          alert('Error deleting center');
        }
      }
    },
    
    async saveCenter() {
      try {
        await centersAPI.create(this.form);
        await this.loadCenters();
        this.closeModal();
      } catch (error) {
        console.error('Error saving center:', error);
        alert('Error saving center');
      }
    },
    
    closeModal() {
      this.showModal = false;
      this.form = { location: '', maxCapacity: '', currentCapacity: 0 };
    }
  }
};
</script>

<style scoped>
.page {
  min-height: 100vh;
  color: white;
  position: relative;
  padding-bottom: 80px;
}

.content {
  position: relative;
  z-index: 1;
  padding: 80px 20px 40px;
  max-width: 1400px;
  margin: 0 auto;
}

.page-title {
  font-size: clamp(2.5em, 5vw, 4em);
  margin-bottom: 40px;
  font-weight: 800;
  background: linear-gradient(135deg, #fff 0%, #a78bfa 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 0 40px rgba(167, 139, 250, 0.3);
  animation: fadeInDown 0.8s ease-out;
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.btn-add {
  padding: 16px 32px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 16px;
  font-size: 1.1em;
  font-weight: 700;
  cursor: pointer;
  margin-bottom: 40px;
  display: inline-flex;
  align-items: center;
  gap: 10px;
  transition: all 0.3s ease;
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.4);
  position: relative;
  overflow: hidden;
}

.btn-add::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255,255,255,0.2), transparent);
  transition: left 0.5s;
}

.btn-add:hover::before {
  left: 100%;
}

.btn-add:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 32px rgba(102, 126, 234, 0.6);
}

.btn-icon {
  font-size: 1.5em;
  line-height: 1;
}

.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 20px;
  gap: 20px;
}

.loader {
  width: 50px;
  height: 50px;
  border: 4px solid rgba(255, 255, 255, 0.1);
  border-top-color: #667eea;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 30px;
  animation: fadeIn 0.6s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.card {
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(20px);
  padding: 30px;
  border-radius: 24px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
  animation: slideUp 0.6s ease-out backwards;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(90deg, #667eea, #764ba2, #667eea);
  background-size: 200% 100%;
  animation: gradient 3s ease infinite;
  opacity: 0;
  transition: opacity 0.3s;
}

@keyframes gradient {
  0%, 100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

.card:hover::before {
  opacity: 1;
}

.card:hover {
  background: rgba(255, 255, 255, 0.05);
  border-color: rgba(102, 126, 234, 0.3);
  transform: translateY(-8px);
  box-shadow: 0 20px 60px rgba(102, 126, 234, 0.2);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.card h3 {
  margin: 0;
  font-size: 1.6em;
  font-weight: 700;
  color: #fff;
}

.capacity-badge {
  padding: 6px 14px;
  background: rgba(102, 126, 234, 0.2);
  border: 1px solid rgba(102, 126, 234, 0.4);
  border-radius: 20px;
  font-size: 0.9em;
  font-weight: 600;
  color: #a78bfa;
}

.capacity-bar {
  width: 100%;
  height: 8px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 10px;
  overflow: hidden;
  margin-bottom: 20px;
}

.capacity-fill {
  height: 100%;
  background: linear-gradient(90deg, #667eea, #764ba2);
  border-radius: 10px;
  transition: width 0.6s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: 0 0 10px rgba(102, 126, 234, 0.5);
}

.card-stats {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 15px;
  margin-bottom: 20px;
}

.stat {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.stat-label {
  font-size: 0.85em;
  color: rgba(255, 255, 255, 0.6);
  text-transform: uppercase;
  letter-spacing: 0.5px;
  font-weight: 600;
}

.stat-value {
  font-size: 1.2em;
  font-weight: 700;
  color: #fff;
}

.btn-delete {
  width: 100%;
  margin-top: 15px;
  padding: 12px;
  background: rgba(244, 67, 54, 0.1);
  color: #ff6b6b;
  border: 2px solid rgba(244, 67, 54, 0.3);
  border-radius: 12px;
  cursor: pointer;
  font-weight: 600;
  font-size: 1em;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.btn-delete:hover {
  background: rgba(244, 67, 54, 0.2);
  border-color: rgba(244, 67, 54, 0.5);
  transform: scale(1.02);
}

.empty-state {
  grid-column: 1 / -1;
  text-align: center;
  padding: 80px 20px;
  animation: fadeIn 0.6s ease-out;
}

.empty-icon {
  font-size: 5em;
  margin-bottom: 20px;
  opacity: 0.5;
}

.empty-state h3 {
  font-size: 1.8em;
  margin-bottom: 10px;
  color: rgba(255, 255, 255, 0.9);
}

.empty-state p {
  color: rgba(255, 255, 255, 0.5);
  font-size: 1.1em;
}

/* Modal Styles */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.85);
  backdrop-filter: blur(10px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 20px;
}

.modal-content {
  background: rgba(26, 26, 46, 0.95);
  backdrop-filter: blur(20px);
  padding: 40px;
  border-radius: 24px;
  width: 100%;
  max-width: 500px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.5);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.modal-header h2 {
  margin: 0;
  font-size: 1.8em;
  font-weight: 700;
  color: #fff;
}

.btn-close {
  width: 40px;
  height: 40px;
  border: none;
  background: rgba(255, 255, 255, 0.05);
  color: white;
  font-size: 2em;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  line-height: 1;
}

.btn-close:hover {
  background: rgba(244, 67, 54, 0.2);
  transform: rotate(90deg);
}

.form-group {
  margin-bottom: 24px;
}

.form-group label {
  display: block;
  margin-bottom: 10px;
  font-weight: 600;
  color: rgba(255, 255, 255, 0.9);
  font-size: 0.95em;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.form-group input {
  width: 100%;
  padding: 14px 16px;
  background: rgba(255, 255, 255, 0.05);
  border: 2px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  color: white;
  font-size: 16px;
  transition: all 0.3s ease;
}

.form-group input:focus {
  outline: none;
  border-color: #667eea;
  background: rgba(255, 255, 255, 0.08);
  box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
}

.form-group input::placeholder {
  color: rgba(255, 255, 255, 0.3);
}

.modal-actions {
  display: flex;
  gap: 12px;
  margin-top: 32px;
}

.btn-secondary,
.btn-primary {
  flex: 1;
  padding: 14px 24px;
  border: none;
  border-radius: 12px;
  font-weight: 700;
  font-size: 1em;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-secondary {
  background: rgba(255, 255, 255, 0.05);
  color: white;
  border: 2px solid rgba(255, 255, 255, 0.1);
}

.btn-secondary:hover {
  background: rgba(255, 255, 255, 0.08);
  border-color: rgba(255, 255, 255, 0.2);
}

.btn-primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.4);
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 32px rgba(102, 126, 234, 0.6);
}

/* Modal Transitions */
.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.3s ease;
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}

.modal-enter-active .modal-content,
.modal-leave-active .modal-content {
  transition: transform 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.modal-enter-from .modal-content,
.modal-leave-to .modal-content {
  transform: scale(0.9) translateY(20px);
}

/* Responsive */
@media (max-width: 768px) {
  .content {
    padding: 60px 15px 40px;
  }
  
  .grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .card {
    padding: 24px;
  }
  
  .modal-content {
    padding: 30px 20px;
  }
}
</style>