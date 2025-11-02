<template>
  <div class="winter-register-container">
    <div class="snowflakes layer1"></div>
    <div class="snowflakes layer2"></div>
    <div class="background-elements">
      <div class="cabin"></div>
      <div class="trees"></div>
    </div>

    <div class="content-wrapper">
      <div class="left-panel">
        <div class="plea-content">
          <h2 class="plea-title">Food Waste Management</h2>
          <p class="plea-text line-1">Every crumb, every peel, every forgotten fruit holds a story. A potential for new life.</p>
          <p class="plea-text line-2">Here, we don't see waste. We see nourishment for the earth, a promise returned to the soil.</p>
          <p class="plea-text line-3">By registering, you become a vital link in the chain. A whisper of change that grows into a roar of renewal.</p>
          <p class="plea-text line-4">You're not just signing up; you're joining a movement. A community dedicated to healing our planet, one mindful choice at a time.</p>
          <p class="plea-text line-5">Your journey starts now. Turn intention into action and let your legacy be one of restoration and care.</p>
        </div>
      </div>
      
      <div class="right-panel">
        <div class="glass-card">
          <h1 class="title">Create Account</h1>
          
          <div v-if="error" class="error-message">
            {{ error }}
          </div>
          
          <div v-if="success" class="success-message">
            {{ success }}
          </div>
          
          <form @submit.prevent="handleRegister">
            <div class="input-group">
              <label>Username</label>
              <input 
                v-model="userData.username" 
                type="text" 
                required
                autocomplete="username"
              />
            </div>
            
            <div class="input-group">
              <label>Email</label>
              <input 
                v-model="userData.email" 
                type="email" 
                required
                autocomplete="email"
              />
            </div>
            
            <div class="input-group">
              <label>Password</label>
              <input 
                v-model="userData.password" 
                type="password" 
                required
                autocomplete="new-password"
              />
            </div>
            
            <button type="submit" class="register-btn" :disabled="loading">
              {{ loading ? 'Creating Account...' : 'Create Account' }}
            </button>
          </form>
          
          <p class="login-text">
            Already a member? <router-link to="/login" class="login-link">Login Here</router-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// Import your actual API service, just like in your login component
import { authAPI } from '@/services/api';

export default {
  name: 'Register',
  data() {
    return {
      userData: {
        username: '',
        email: '',
        password: ''
        // --- "role" was removed from here ---
      },
      loading: false,
      error: null,
      success: null
    };
  },
  methods: {
    async handleRegister() {
      this.loading = true;
      this.error = null;
      this.success = null;
      
      try {
        // Use your real authAPI now
        await authAPI.register(this.userData);
        
        this.success = 'Registration successful! Redirecting to login...';
        
        setTimeout(() => {
          this.$router.push('/login');
        }, 2000);
        
      } catch (error) {
        console.error('Registration error:', error);
        this.error = error.response?.data?.error || 'Registration failed. Please try again.';
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
/* All your styles remain the same */
/* --- Base & Background --- */
.winter-register-container {
  min-height: 100vh;
  position: relative;
  overflow: hidden;
  background: linear-gradient(170deg, #0d123a, #1a225a, #4a2a6c, #7e3a8c, #4a2a6c, #1a225a);
  background-size: 400% 400%;
  animation: rapid-color-shift 8s ease infinite;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
}

@keyframes rapid-color-shift {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

.content-wrapper {
  display: flex;
  width: 100%;
  max-width: 1200px;
  min-height: 700px;
  z-index: 2;
}

/* --- Snowfall Animation --- */
@keyframes snowfall {
  0% { transform: translateY(-100vh); }
  100% { transform: translateY(100vh); }
}

.snowflakes {
  position: absolute;
  top: -100vh;
  left: 0;
  width: 100%;
  height: 100vh;
  background-repeat: repeat;
  pointer-events: none;
}
.layer1 {
  background-image: radial-gradient(4px 4px at 10% 30%, white, transparent),
                    radial-gradient(3px 3px at 50% 60%, white, transparent),
                    radial-gradient(2px 2px at 90% 80%, white, transparent);
  background-size: 50vw 50vh;
  animation: snowfall 15s linear infinite;
  z-index: 1;
  opacity: 0.8;
}
.layer2 {
  background-image: radial-gradient(2px 2px at 25% 15%, white, transparent),
                    radial-gradient(2px 2px at 75% 45%, white, transparent),
                    radial-gradient(3px 3px at 85% 95%, white, transparent);
  background-size: 75vw 75vh;
  animation: snowfall 25s linear infinite;
  z-index: 1;
  opacity: 0.6;
}


/* --- Left Panel: Planet's Plea (3D EFFECT) --- */
.left-panel {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
  position: relative;
  /* Establishes the 3D space for child elements */
  perspective: 800px; 
}

.plea-content {
  max-width: 400px;
  color: #e9d5ff;
  text-shadow: 0 2px 8px rgba(0, 0, 0, 0.5);
  position: relative;
}

.plea-title, .plea-text {
  opacity: 0; /* Start invisible */
  transform-origin: top center; /* Animation rotates around the top edge */
}

.plea-title {
  font-size: 3em;
  font-weight: 700;
  margin-bottom: 2rem;
  color: #fff;
  animation: text-3d-flip-down 1.2s forwards cubic-bezier(0.25, 0.46, 0.45, 0.94);
  animation-delay: 0.5s;
}

.plea-text {
  font-size: 1.1em;
  line-height: 1.7;
  margin-bottom: 1.5rem;
  animation: text-3d-flip-down 1.2s forwards cubic-bezier(0.25, 0.46, 0.45, 0.94);
}

/* Staggered animation delays for each paragraph */
.line-1 { animation-delay: 1.5s; }
.line-2 { animation-delay: 2.5s; }
.line-3 { animation-delay: 3.5s; }
.line-4 { animation-delay: 4.5s; }
.line-5 { animation-delay: 5.5s; }

/* MODIFIED: Keyframes for the 3D "flip down" effect */
@keyframes text-3d-flip-down {
  from {
    opacity: 0;
    transform: rotateX(-90deg);
  }
  to {
    opacity: 1;
    transform: rotateX(0deg);
  }
}

/* --- Right Panel & Glass Card --- */
.right-panel {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}

.glass-card {
  position: relative;
  width: 100%;
  max-width: 450px;
  padding: 50px 40px;
  background: rgba(26, 34, 90, 0.25);
  backdrop-filter: blur(15px);
  border-radius: 30px;
  border: 2px solid rgba(255, 255, 255, 0.15);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.4);
  animation: card-fade-in 1s ease-out forwards;
  opacity: 0;
}

@keyframes card-fade-in {
  from { opacity: 0; transform: translateX(30px); }
  to { opacity: 1; transform: translateX(0); }
}

.title {
  font-size: 2.8em;
  font-weight: 800;
  text-align: center;
  color: white;
  margin-bottom: 40px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
}

/* --- Messages & Form Elements --- */
.error-message, .success-message {
  padding: 12px 16px;
  border-radius: 12px;
  margin-bottom: 20px;
  text-align: center;
  font-size: 0.95em;
  backdrop-filter: blur(5px);
}

.error-message {
  background: rgba(239, 68, 68, 0.2);
  border: 1px solid rgba(239, 68, 68, 0.4);
  color: #fecaca;
}

.success-message {
  background: rgba(34, 197, 94, 0.2);
  border: 1px solid rgba(34, 197, 94, 0.4);
  color: #bbf7d0;
}

.input-group {
  margin-bottom: 30px;
}

.input-group label {
  display: block;
  font-size: 1.1em;
  color: #d1c4e9;
  margin-bottom: 12px;
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
}

.input-group input,
.input-group select {
  width: 100%;
  padding: 14px 0;
  background: transparent;
  border: none;
  border-bottom: 2px solid rgba(255, 255, 255, 0.2);
  font-size: 1em;
  color: white;
  transition: all 0.3s ease;
}

.input-group select {
  cursor: pointer;
  appearance: none;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='white' d='M6 9L1 4h10z'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 0 center;
}

.input-group select option {
  background: #1a225a;
  color: white;
}

.input-group input:focus,
.input-group select:focus {
  outline: none;
  border-bottom-color: #e9d5ff;
}

.input-group input:-webkit-autofill {
  -webkit-text-fill-color: white;
  -webkit-box-shadow: 0 0 0 1000px rgba(74, 42, 108, 0.5) inset;
  transition: background-color 5000s ease-in-out 0s;
}

.register-btn {
  width: 100%;
  padding: 16px;
  background: linear-gradient(90deg, #f3e8ff, #e9d5ff);
  color: #4a2a6c;
  border: none;
  border-radius: 50px;
  font-size: 1.1em;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-bottom: 25px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.register-btn:hover:not(:disabled) {
  transform: translateY(-3px);
  box-shadow: 0 6px 20px rgba(233, 213, 255, 0.3);
}

.register-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.login-text {
  text-align: center;
  color: #d1c4e9;
  font-size: 1em;
}

.login-link {
  color: #fff;
  text-decoration: none;
  font-weight: 700;
  margin-left: 4px;
}

.login-link:hover {
  text-decoration: underline;
}

/* --- Responsive Design --- */
@media (max-width: 900px) {
  .content-wrapper {
    flex-direction: column;
    min-height: auto;
    max-width: 500px;
  }
  
  .left-panel {
    display: none; /* Hide the plea text on smaller screens to focus on the form */
  }

  .right-panel {
    width: 100%;
  }

  @keyframes card-fade-in {
    from { opacity: 0; transform: translateY(30px); }
    to { opacity: 1; transform: translateY(0); }
  }
}
</style>