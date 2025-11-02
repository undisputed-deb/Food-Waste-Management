<template>
  <div class="page">
    <ParallaxBackground />
    
    <div class="content">
      <h1 class="page-title">🗑️ Waste Items</h1>
      
      <button @click="showAddModal = true" class="btn-add">
        <span class="btn-icon">+</span>
        <span>Add Waste Item</span>
      </button>
      
      <div v-if="loading" class="loading-container">
        <div class="loader"></div>
        <p>Loading waste items...</p>
      </div>
      
      <div v-else class="items-grid">
        <div 
          v-for="(item, index) in wasteItems" 
          :key="item.id"
          class="waste-card"
          :style="{ animationDelay: `${index * 0.1}s` }"
        >
          <div class="waste-card-header">
            <span class="waste-type-badge" :class="`type-${item.wasteType}`">
              {{ getTypeEmoji(item.wasteType) }} {{ item.wasteType }}
            </span>
            <span :class="['status-badge', item.processed ? 'processed' : 'pending']">
              {{ item.processed ? '✓ Processed' : '○ Pending' }}
            </span>
          </div>
          
          <div class="waste-card-body">
            <div class="waste-stat">
              <span class="stat-icon">⚖️</span>
              <div>
                <span class="stat-label">Weight</span>
                <span class="stat-value">{{ item.weight }} kg</span>
              </div>
            </div>
            
            <div class="waste-stat">
              <span class="stat-icon">📅</span>
              <div>
                <span class="stat-label">Expiration</span>
                <span class="stat-value">{{ formatDate(item.expirationDate) }}</span>
              </div>
            </div>
            
            <div class="waste-stat">
              <span class="stat-icon">👤</span>
              <div>
                <span class="stat-label">Donor</span>
                <span class="stat-value">{{ item.donor?.name || 'N/A' }}</span>
              </div>
            </div>
          </div>
          
          <div class="waste-card-actions">
            <button @click="editItem(item)" class="btn-edit">
              ✏️ Edit
            </button>
            <button @click="deleteItem(item.id)" class="btn-delete">
              🗑️ Delete
            </button>
          </div>
        </div>
        
        <div v-if="wasteItems.length === 0" class="empty-state">
          <div class="empty-icon">📦</div>
          <h3>No Waste Items</h3>
          <p>Add your first waste item to get started</p>
        </div>
      </div>
    </div>
    
    <!-- Add/Edit Modal -->
    <transition name="modal">
      <div v-if="showAddModal" class="modal" @click="closeModal">
        <div class="modal-content" @click.stop>
          <div class="modal-header">
            <h2>{{ editMode ? 'Edit Waste Item' : 'Add New Waste Item' }}</h2>
            <button @click="closeModal" class="btn-close">×</button>
          </div>
          
          <form @submit.prevent="saveItem">
            <div class="form-group">
              <label>Weight (kg)</label>
              <input 
                v-model.number="formData.weight" 
                type="text"
                inputmode="decimal" 
                placeholder="Enter weight"
                required 
                @input="validateNumberInput"
              />
            </div>
            
            <div class="form-group">
              <label>Waste Type</label>
              <select v-model="formData.wasteType" required>
                <option value="">Select type</option>
                <option value="vegetables">🥬 Vegetables</option>
                <option value="dairy">🥛 Dairy</option>
                <option value="grains">🌾 Grains</option>
                <option value="meat">🍖 Meat</option>
                <option value="other">📦 Other</option>
              </select>
            </div>
            
            <div class="form-group">
              <label>Expiration Date</label>
              <input 
                v-model="formData.expirationDate" 
                type="date" 
                required 
              />
            </div>
            
            <div class="form-group">
              <label>Donor</label>
              <select v-model="formData.donor" required>
                <option value="">Select donor</option>
                <option v-for="donor in donors" :key="donor.id" :value="donor">
                  {{ donor.name }}
                </option>
              </select>
            </div>
            
            <div class="modal-actions">
              <button type="button" @click="closeModal" class="btn-secondary">
                Cancel
              </button>
              <button type="submit" class="btn-primary">
                {{ editMode ? 'Update' : 'Save' }} Item
              </button>
            </div>
          </form>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import { wasteItemsAPI, donorsAPI } from '@/services/api';
import ParallaxBackground from '@/components/ParallaxBackground.vue';

export default {
  name: 'WasteItems',
  components: {
    ParallaxBackground
  },
  data() {
    return {
      wasteItems: [],
      donors: [],
      loading: false,
      showAddModal: false,
      editMode: false,
      formData: {
        id: null,
        weight: '',
        wasteType: '',
        expirationDate: '',
        donor: null,
        processed: false
      }
    };
  },
  async mounted() {
    await this.loadData();
  },
  methods: {
    async loadData() {
      this.loading = true;
      try {
        const [itemsRes, donorsRes] = await Promise.all([
          wasteItemsAPI.getAll(),
          donorsAPI.getAll()
        ]);
        
        let itemsData = itemsRes.data;
        if (typeof itemsData === 'string') {
          itemsData = JSON.parse(itemsData);
        }
        this.wasteItems = Array.isArray(itemsData) ? itemsData : [];
        
        let donorsData = donorsRes.data;
        if (typeof donorsData === 'string') {
          donorsData = JSON.parse(donorsData);
        }
        this.donors = Array.isArray(donorsData) ? donorsData : [];
        
      } catch (error) {
        console.error('Error loading data:', error);
        alert('Error loading data: ' + (error.response?.data?.error || error.message));
      } finally {
        this.loading = false;
      }
    },
    
    handleAddItem() {
      const token = localStorage.getItem('token');
      console.log('🔍 Checking token:', token);
      
      if (!token) {
        console.log('❌ No token found, redirecting to login');
        this.$router.push({ 
          path: '/login', 
          query: { returnUrl: this.$route.path } 
        });
      } else {
        console.log('✅ Token exists, showing modal');
        this.showAddModal = true;
      }
    },
    
    validateNumberInput(event) {
      // Only allow numbers and one decimal point
      const value = event.target.value;
      const regex = /^\d*\.?\d*$/;
      if (!regex.test(value)) {
        event.target.value = value.slice(0, -1);
        this.formData.weight = parseFloat(event.target.value) || '';
      }
    },
    
    handleEditItem(item) {
      const token = localStorage.getItem('token');
      console.log('🔍 Checking token for edit:', token);
      
      if (!token) {
        console.log('❌ No token found, redirecting to login');
        this.$router.push({ 
          path: '/login', 
          query: { returnUrl: this.$route.path } 
        });
      } else {
        console.log('✅ Token exists, showing edit modal');
        this.editMode = true;
        this.formData = { ...item };
        this.showAddModal = true;
      }
    },
    
    async handleDeleteItem(id) {
      const token = localStorage.getItem('token');
      console.log('🔍 Checking token for delete:', token);
      
      if (!token) {
        console.log('❌ No token found, redirecting to login');
        this.$router.push({ 
          path: '/login', 
          query: { returnUrl: this.$route.path } 
        });
        return;
      }
      
      if (confirm('Are you sure you want to delete this item?')) {
        try {
          await wasteItemsAPI.delete(id);
          await this.loadData();
        } catch (error) {
          console.error('Error deleting item:', error);
          alert('Error deleting item: ' + (error.response?.data?.error || error.message));
        }
      }
    },
    
    async saveItem() {
      try {
        if (this.editMode) {
          await wasteItemsAPI.update(this.formData.id, this.formData);
        } else {
          await wasteItemsAPI.create(this.formData);
        }
        await this.loadData();
        this.closeModal();
      } catch (error) {
        console.error('Error saving item:', error);
        alert('Error saving item: ' + (error.response?.data?.error || error.message));
      }
    },
    
    closeModal() {
      this.showAddModal = false;
      this.editMode = false;
      this.formData = {
        id: null,
        weight: '',
        wasteType: '',
        expirationDate: '',
        donor: null,
        processed: false
      };
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
    
    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleDateString('en-US', { 
        month: 'short', 
        day: 'numeric', 
        year: 'numeric' 
      });
    }
  }
};
</script>

<style scoped>
/* CRITICAL: Ensure page has transparent/no background */
.page {
  min-height: 100vh;
  color: white;
  position: relative;
  padding-bottom: 80px;
  background: transparent !important;
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
  background: linear-gradient(135deg, #fff 0%, #fbbf24 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 0 40px rgba(251, 191, 36, 0.3);
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
  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
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
  box-shadow: 0 8px 24px rgba(245, 158, 11, 0.4);
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
  box-shadow: 0 12px 32px rgba(245, 158, 11, 0.6);
}

.btn-add:active {
  transform: translateY(0);
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
  border-top-color: #f59e0b;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.items-grid {
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

.waste-card {
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(20px);
  padding: 25px;
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

.waste-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(90deg, #f59e0b, #d97706, #f59e0b);
  background-size: 200% 100%;
  animation: gradient 3s ease infinite;
  opacity: 0;
  transition: opacity 0.3s;
}

@keyframes gradient {
  0%, 100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

.waste-card:hover::before {
  opacity: 1;
}

.waste-card:hover {
  background: rgba(255, 255, 255, 0.05);
  border-color: rgba(245, 158, 11, 0.3);
  transform: translateY(-8px);
  box-shadow: 0 20px 60px rgba(245, 158, 11, 0.2);
}

.waste-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  gap: 10px;
  flex-wrap: wrap;
}

.waste-type-badge {
  padding: 8px 16px;
  background: rgba(245, 158, 11, 0.15);
  border: 1px solid rgba(245, 158, 11, 0.3);
  border-radius: 20px;
  font-size: 0.9em;
  font-weight: 600;
  color: #fbbf24;
  text-transform: capitalize;
}

.type-vegetables { 
  background: rgba(34, 197, 94, 0.15);
  border-color: rgba(34, 197, 94, 0.3);
  color: #4ade80;
}

.type-dairy { 
  background: rgba(59, 130, 246, 0.15);
  border-color: rgba(59, 130, 246, 0.3);
  color: #60a5fa;
}

.type-grains { 
  background: rgba(251, 191, 36, 0.15);
  border-color: rgba(251, 191, 36, 0.3);
  color: #fbbf24;
}

.type-meat { 
  background: rgba(239, 68, 68, 0.15);
  border-color: rgba(239, 68, 68, 0.3);
  color: #f87171;
}

.type-other { 
  background: rgba(156, 163, 175, 0.15);
  border-color: rgba(156, 163, 175, 0.3);
  color: #9ca3af;
}

.status-badge {
  padding: 6px 14px;
  border-radius: 20px;
  font-size: 0.85em;
  font-weight: 600;
}

.status-badge.processed {
  background: rgba(34, 197, 94, 0.15);
  border: 1px solid rgba(34, 197, 94, 0.3);
  color: #4ade80;
}

.status-badge.pending {
  background: rgba(251, 191, 36, 0.15);
  border: 1px solid rgba(251, 191, 36, 0.3);
  color: #fbbf24;
}

.waste-card-body {
  display: flex;
  flex-direction: column;
  gap: 15px;
  margin-bottom: 20px;
}

.waste-stat {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  background: rgba(255, 255, 255, 0.02);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.05);
}

.stat-icon {
  font-size: 1.8em;
  opacity: 0.8;
}

.waste-stat > div {
  display: flex;
  flex-direction: column;
  gap: 4px;
  flex: 1;
}

.stat-label {
  font-size: 0.8em;
  color: rgba(255, 255, 255, 0.6);
  text-transform: uppercase;
  letter-spacing: 0.5px;
  font-weight: 600;
}

.stat-value {
  font-size: 1.1em;
  font-weight: 700;
  color: #fff;
}

.waste-card-actions {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
  margin-top: 15px;
}

.btn-edit,
.btn-delete {
  padding: 10px;
  border: none;
  border-radius: 12px;
  cursor: pointer;
  font-weight: 600;
  font-size: 0.95em;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
}

.btn-edit {
  background: rgba(59, 130, 246, 0.1);
  color: #60a5fa;
  border: 2px solid rgba(59, 130, 246, 0.3);
}

.btn-edit:hover {
  background: rgba(59, 130, 246, 0.2);
  border-color: rgba(59, 130, 246, 0.5);
  transform: scale(1.02);
}

.btn-delete {
  background: rgba(244, 67, 54, 0.1);
  color: #ff6b6b;
  border: 2px solid rgba(244, 67, 54, 0.3);
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
  max-height: 90vh;
  overflow-y: auto;
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

.form-group input,
.form-group select {
  width: 100%;
  padding: 14px 16px;
  background: rgba(255, 255, 255, 0.05);
  border: 2px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  color: white;
  font-size: 16px;
  transition: all 0.3s ease;
}

.form-group select {
  cursor: pointer;
}

.form-group select option {
  background: #1a1a2e;
  color: white;
  padding: 10px;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #f59e0b;
  background: rgba(255, 255, 255, 0.08);
  box-shadow: 0 0 0 4px rgba(245, 158, 11, 0.1);
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
  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
  color: white;
  box-shadow: 0 8px 24px rgba(245, 158, 11, 0.4);
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 32px rgba(245, 158, 11, 0.6);
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
  
  .items-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .waste-card {
    padding: 20px;
  }
  
  .modal-content {
    padding: 30px 20px;
  }
}
</style>