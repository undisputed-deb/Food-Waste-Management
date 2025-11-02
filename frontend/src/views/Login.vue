<template>
  <div class="winter-night-login">
    <!-- Winter Background Elements -->
    <div class="snow-layer layer1"></div>
    <div class="snow-layer layer2"></div>
    <div class="snow-layer layer3"></div>
    
    <div class="moon"></div>
    
    <div class="trees">
      <div class="tree" v-for="n in 8" :key="'tree-'+n" :style="getTreeStyle(n)"></div>
    </div>
    
    <div class="cabin">
      <div class="cabin-roof"></div>
      <div class="window window1"></div>
      <div class="window window2"></div>
      <div class="door"></div>
      <div class="chimney"></div>
    </div>

    <!-- App Title -->
    <div class="app-title">
      <h1>Food Waste Management App</h1>
      <div class="title-decoration"></div>
    </div>

    <!-- Info Panel with Glassmorphism -->
    <div class="info-panel">
      <h2 class="info-title">
        <span class="title-icon">🌍</span>
        A Planet's Plea
      </h2>
      
      <!-- Animated Paragraphs Container -->
      <div class="paragraphs-container">
        <div 
          v-for="(paragraph, index) in textContent" 
          :key="index"
          class="animated-paragraph"
          :class="{ active: currentParagraph === index }"
          v-html="getAnimatedParagraph(paragraph, index)"
        ></div>
      </div>
      
      <!-- Progress Dots -->
      <div class="progress-dots">
        <div 
          v-for="(_, index) in textContent" 
          :key="index"
          class="dot"
          :class="{ active: currentParagraph === index }"
          @click="currentParagraph = index"
        ></div>
      </div>
    </div>
    
    <!-- Login Card -->
    <div class="login-card">
      <h1 class="title">
        <span class="emoji">♻️</span>
        Welcome Back
      </h1>
      
      <div v-if="error" class="error-message">
        <span class="error-icon">⚠️</span>
        {{ error }}
      </div>
      
      <form @submit.prevent="handleLogin">
        <div class="input-group">
          <label>
            <span class="label-icon">👤</span>
            username
          </label>
          <input 
            v-model="credentials.username" 
            type="text" 
            required
            autocomplete="username"
          />
        </div>
        
        <div class="input-group">
          <label>
            <span class="label-icon">🔒</span>
            Password
          </label>
          <input 
            v-model="credentials.password" 
            type="password" 
            required
            autocomplete="current-password"
          />
        </div>
        
        <div class="options">
          <label class="remember">
            <input type="checkbox" />
            <span>Remember Me</span>
          </label>
          <a href="#" class="forgot">Forget Password</a>
        </div>
        
        <button type="submit" class="login-btn" :disabled="loading">
          <span v-if="!loading">🌱 Log in</span>
          <span v-else class="loading-text">
            <span class="spinner">❄️</span> Signing in...
          </span>
        </button>
      </form>
      
      <p class="register-text">
        Don't have an account?
        <router-link to="/register" class="register-link">
          <span class="link-icon">🏠</span>
          Register
        </router-link>
      </p>
      
      <div class="project-description">
        <p class="tagline">
          <span class="icon">🌲</span>
          <strong>Winter's Sustainable Promise</strong>
        </p>
        <p class="description">
          Embracing sustainability even in the coldest seasons, we work towards a greener future. ❄️✨
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { authAPI } from '@/services/api';

export default {
  name: 'WinterLogin',
  data() {
    return {
      credentials: {
        username: '',
        password: ''
      },
      loading: false,
      error: null,
      currentParagraph: 0,
      textContent: [
        'Globally, about <strong>one-third</strong> of all food produced is lost or wasted. This represents a colossal waste of resources like water, land, and energy used in its production.',
        'When food ends up in landfills, it rots and produces <strong>methane</strong>, a greenhouse gas far more potent than carbon dioxide, significantly contributing to climate change.',
        'By managing food waste, we do more than clean our plates. We reclaim valuable nutrients, reduce our carbon footprint, and take a crucial step towards a sustainable future.',
        'Food waste costs the global economy nearly <strong>$1 trillion</strong> annually. Reducing waste can help fight hunger and improve food security worldwide.',
        'Composting transforms food scraps into <strong>nutrient-rich soil</strong> that helps grow more food, creating a beautiful cycle of sustainability.',
        'Every meal saved from waste helps conserve biodiversity and protects ecosystems from the impacts of excessive agriculture and landfill expansion.'
      ],
      animationInterval: null
    };
  },
  computed: {
    animatedText() {
      let globalDelay = 0;
      return this.textContent.map(p => {
        const words = p.split(/(\s+)/);
        return words.map(word => {
          if (word.trim() === '' || word.includes('<')) {
            return word;
          }
          const letters = word.split('').map(letter => {
            globalDelay += 0.02;
            return `<span style="animation-delay: ${globalDelay}s">${letter}</span>`;
          }).join('');
          return `<div class="word">${letters}</div>`;
        }).join('');
      });
    }
  },
  methods: {
    async handleLogin() {
      this.loading = true;
      this.error = null;
      
      try {
        const response = await authAPI.login(this.credentials);
        
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('username', response.data.username);
        localStorage.setItem('role', response.data.role);
        
        const returnUrl = this.$route.query.returnUrl || '/dashboard';
        this.$router.push(returnUrl);
        
      } catch (error) {
        console.error('Login error:', error);
        this.error = error.response?.data?.error || 'Login failed. Please check your credentials.';
      } finally {
        this.loading = false;
      }
    },
    
    getTreeStyle(n) {
      const sizes = [120, 140, 160, 180];
      const left = (n * 12) + '%';
      const size = sizes[n % sizes.length];
      const delay = n * 0.3;
      
      return {
        '--tree-height': `${size}px`,
        '--tree-width': `${size * 0.6}px`,
        '--tree-left': left,
        '--sway-delay': `${delay}s`
      };
    },
    
    getAnimatedParagraph(text, index) {
      if (this.currentParagraph !== index) return text;
      
      let globalDelay = 0;
      const words = text.split(/(\s+)/);
      return words.map(word => {
        if (word.trim() === '' || word.includes('<')) {
          return word;
        }
        const letters = word.split('').map(letter => {
          globalDelay += 0.02;
          return `<span style="animation-delay: ${globalDelay}s">${letter}</span>`;
        }).join('');
        return `<div class="word">${letters}</div>`;
      }).join('');
    },
    
    startParagraphRotation() {
      this.animationInterval = setInterval(() => {
        this.currentParagraph = (this.currentParagraph + 1) % this.textContent.length;
      }, 6000); // Change paragraph every 6 seconds
    }
  },
  
  mounted() {
    this.startParagraphRotation();
  },
  
  beforeUnmount() {
    if (this.animationInterval) {
      clearInterval(this.animationInterval);
    }
  }
};
</script>

<style scoped>
/* Main Container */
.winter-night-login {
  min-height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  position: relative;
  overflow: hidden;
  background: linear-gradient(135deg, #0c1445 0%, #1a237e 50%, #283593 100%);
}

/* App Title */
.app-title {
  position: absolute;
  top: 40px;
  left: 50%;
  transform: translateX(-50%);
  text-align: center;
  z-index: 10;
  animation: titleSlideDown 1s ease-out 0.3s backwards;
}

.app-title h1 {
  font-size: 2.8rem;
  font-weight: 800;
  color: white;
  text-shadow: 0 2px 20px rgba(0, 0, 0, 0.5);
  margin-bottom: 15px;
  background: linear-gradient(135deg, #a5d6a7, #4caf50);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.title-decoration {
  width: 100px;
  height: 4px;
  background: linear-gradient(90deg, transparent, #a5d6a7, transparent);
  margin: 0 auto;
  border-radius: 2px;
  animation: pulse 2s ease-in-out infinite;
}

@keyframes titleSlideDown {
  from {
    opacity: 0;
    transform: translateX(-50%) translateY(-30px);
  }
  to {
    opacity: 1;
    transform: translateX(-50%) translateY(0);
  }
}

/* Winter Background Elements */
.snow-layer {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  background-image: 
    radial-gradient(4px 4px at 100px 50px, white, transparent),
    radial-gradient(6px 6px at 200px 150px, white, transparent),
    radial-gradient(3px 3px at 300px 250px, white, transparent),
    radial-gradient(4px 4px at 400px 350px, white, transparent),
    radial-gradient(6px 6px at 500px 100px, white, transparent),
    radial-gradient(3px 3px at 50px 200px, white, transparent),
    radial-gradient(4px 4px at 150px 300px, white, transparent),
    radial-gradient(6px 6px at 250px 400px, white, transparent),
    radial-gradient(3px 3px at 350px 500px, white, transparent);
  background-size: 650px 650px;
  animation: snowFall 15s linear infinite;
}

.snow-layer.layer1 {
  opacity: 0.8;
}

.snow-layer.layer2 {
  animation-duration: 20s;
  opacity: 0.6;
  background-size: 600px 600px;
  margin-left: -200px;
}

.snow-layer.layer3 {
  animation-duration: 25s;
  opacity: 0.4;
  background-size: 550px 550px;
  margin-left: 200px;
}

@keyframes snowFall {
  from {
    transform: translateY(-100vh);
  }
  to {
    transform: translateY(100vh);
  }
}

.moon {
  position: absolute;
  top: 10%;
  right: 15%;
  width: 120px;
  height: 120px;
  border-radius: 50%;
  background: radial-gradient(circle at 30% 30%, #f5f5f5, #e0e0e0);
  box-shadow: 0 0 60px rgba(255, 255, 255, 0.8);
  z-index: 1;
  animation: moonGlow 4s ease-in-out infinite;
}

.moon::after {
  content: '';
  position: absolute;
  top: 20%;
  left: 25%;
  width: 25px;
  height: 25px;
  border-radius: 50%;
  background: rgba(200, 200, 200, 0.4);
}

.moon::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 60%;
  width: 15px;
  height: 15px;
  border-radius: 50%;
  background: rgba(200, 200, 200, 0.3);
}

@keyframes moonGlow {
  0%, 100% { 
    box-shadow: 0 0 60px rgba(255, 255, 255, 0.8);
    transform: scale(1);
  }
  50% { 
    box-shadow: 0 0 80px rgba(255, 255, 255, 0.9);
    transform: scale(1.05);
  }
}

.trees {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 40%;
  pointer-events: none;
}

.tree {
  position: absolute;
  bottom: 0;
  left: var(--tree-left, 10%);
  width: var(--tree-width, 120px);
  height: var(--tree-height, 200px);
  animation: treeSway 8s ease-in-out infinite;
  animation-delay: var(--sway-delay, 0s);
}

.tree::before {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 20px;
  height: 60px;
  background: #5d4037;
  z-index: 2;
}

.tree::after {
  content: '';
  position: absolute;
  bottom: 40px;
  left: 50%;
  transform: translateX(-50%);
  width: 0;
  height: 0;
  border-left: calc(var(--tree-width) * 0.5) solid transparent;
  border-right: calc(var(--tree-width) * 0.5) solid transparent;
  border-bottom: calc(var(--tree-height) * 0.5) solid #2e7d32;
  z-index: 1;
}

@keyframes treeSway {
  0%, 100% { transform: translateX(0) rotate(0deg); }
  25% { transform: translateX(-3px) rotate(-0.5deg); }
  75% { transform: translateX(3px) rotate(0.5deg); }
}

.cabin {
  position: absolute;
  bottom: 10%;
  left: 8%;
  width: 200px;
  height: 150px;
  z-index: 2;
  animation: cabinGlow 6s ease-in-out infinite;
}

.cabin-roof {
  position: absolute;
  top: -40px;
  left: 0;
  width: 200px;
  height: 40px;
  background: #4e342e;
  clip-path: polygon(0 100%, 50% 0, 100% 100%);
}

.cabin::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 200px;
  height: 150px;
  background: #5d4037;
  border-radius: 10px 10px 0 0;
}

.window {
  position: absolute;
  top: 30px;
  width: 40px;
  height: 40px;
  background: #ffd54f;
  border-radius: 5px;
  animation: windowGlow 3s ease-in-out infinite;
}

.window1 {
  left: 30px;
}

.window2 {
  right: 30px;
}

.door {
  position: absolute;
  bottom: 0;
  left: 80px;
  width: 40px;
  height: 60px;
  background: #3e2723;
  border-radius: 5px 5px 0 0;
}

.chimney {
  position: absolute;
  top: -60px;
  right: 40px;
  width: 25px;
  height: 40px;
  background: #5d4037;
}

@keyframes cabinGlow {
  0%, 100% { 
    filter: drop-shadow(0 0 10px rgba(255, 213, 79, 0.3));
  }
  50% { 
    filter: drop-shadow(0 0 20px rgba(255, 213, 79, 0.6));
  }
}

@keyframes windowGlow {
  0%, 100% { 
    box-shadow: 0 0 10px #ffd54f;
    opacity: 0.9;
  }
  50% { 
    box-shadow: 0 0 20px #ffd54f;
    opacity: 1;
  }
}

/* Info Panel with Glassmorphism */
.info-panel {
  position: absolute;
  left: 5vw;
  top: 50%;
  transform: translateY(-50%);
  width: 450px;
  color: rgba(255, 255, 255, 0.9);
  z-index: 10;
  padding: 40px 30px;
  background: rgba(255, 255, 255, 0.08);
  backdrop-filter: blur(20px);
  border-radius: 25px;
  border: 1px solid rgba(255, 255, 255, 0.15);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
  animation: panelSlideIn 1s ease-out 0.5s backwards;
}

@keyframes panelSlideIn {
  from {
    opacity: 0;
    transform: translateY(-50%) translateX(-30px);
  }
  to {
    opacity: 1;
    transform: translateY(-50%) translateX(0);
  }
}

.info-title {
  font-size: 2.5em;
  font-weight: 700;
  color: #e8f5e9;
  margin-bottom: 30px;
  display: flex;
  align-items: center;
  gap: 12px;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
}

.title-icon {
  display: inline-block;
  animation: pulse 2.5s ease-in-out infinite;
}

/* Animated Paragraphs Container */
.paragraphs-container {
  position: relative;
  height: 200px;
  overflow: hidden;
  margin-bottom: 30px;
}

.animated-paragraph {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  font-size: 1.1em;
  line-height: 1.7;
  opacity: 0;
  transform: translateY(30px);
  transition: all 0.8s cubic-bezier(0.4, 0, 0.2, 1);
  pointer-events: none;
}

.animated-paragraph.active {
  opacity: 1;
  transform: translateY(0);
  pointer-events: all;
}

.animated-paragraph >>> .word {
  display: inline-block;
}

.animated-paragraph >>> .word span {
  display: inline-block;
  opacity: 0;
  transform: translateY(15px) rotateX(-90deg);
  transform-origin: bottom center;
  animation: revealLetter 0.5s forwards cubic-bezier(0.2, 0.8, 0.2, 1);
}

@keyframes revealLetter {
  to {
    opacity: 1;
    transform: translateY(0) rotateX(0deg);
  }
}

.animated-paragraph >>> strong {
  color: #a5d6a7;
  font-weight: 600;
}

/* Progress Dots */
.progress-dots {
  display: flex;
  justify-content: center;
  gap: 12px;
  margin-top: 20px;
}

.dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.3);
  cursor: pointer;
  transition: all 0.3s ease;
}

.dot.active {
  background: #a5d6a7;
  transform: scale(1.2);
  box-shadow: 0 0 10px rgba(165, 214, 167, 0.5);
}

.dot:hover {
  background: rgba(165, 214, 167, 0.7);
  transform: scale(1.1);
}

/* Login Card */
.login-card {
  position: absolute;
  right: 5vw;
  top: 50%;
  transform: translateY(-50%);
  z-index: 10;
  width: 100%;
  max-width: 450px;
  padding: 50px 40px;
  background: rgba(255, 255, 255, 0.08);
  backdrop-filter: blur(25px);
  border-radius: 30px;
  border: 2px solid rgba(255, 255, 255, 0.15);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.4), inset 0 1px 0 rgba(255, 255, 255, 0.1);
  animation: cardAppear 1s ease-out .5s backwards;
}

@keyframes cardAppear {
  from { 
    opacity: 0; 
    transform: translateY(-50%) translateX(30px); 
  }
  to { 
    opacity: 1; 
    transform: translateY(-50%) translateX(0); 
  }
}

.title { 
  font-size: 2.8em; 
  font-weight: 800; 
  text-align: center; 
  color: white; 
  margin-bottom: 40px; 
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3); 
  display: flex; 
  align-items: center; 
  justify-content: center; 
  gap: 15px; 
}

.emoji { 
  font-size: 1.2em; 
  animation: spin 3s linear infinite; 
}

@keyframes spin { 
  from { transform: rotate(0deg); } 
  to { transform: rotate(360deg); } 
}

.error-message { 
  background: rgba(239, 68, 68, 0.2); 
  border: 1px solid rgba(239, 68, 68, 0.4); 
  color: #fecaca; 
  padding: 12px 16px; 
  border-radius: 12px; 
  margin-bottom: 20px; 
  text-align: center; 
  font-size: 0.95em; 
  backdrop-filter: blur(10px); 
  display: flex; 
  align-items: center; 
  justify-content: center; 
  gap: 8px; 
}

.error-icon { 
  animation: shake 0.5s ease-in-out; 
}

@keyframes shake { 
  0%, 100% { transform: translateX(0); } 
  25% { transform: translateX(-5px); } 
  75% { transform: translateX(5px); } 
}

.input-group { 
  margin-bottom: 30px; 
}

.input-group label { 
  display: flex; 
  align-items: center; 
  gap: 8px; 
  font-size: 1.1em; 
  font-weight: 600; 
  color: white; 
  margin-bottom: 12px; 
  text-shadow: 0 1px 3px rgba(0, 0, 0, 0.3); 
}

.label-icon { 
  font-size: 1.2em; 
}

.input-group input { 
  width: 100%; 
  padding: 14px 16px; 
  background: rgba(255, 255, 255, 0.1); 
  border: 2px solid rgba(255, 255, 255, 0.2); 
  border-radius: 12px; 
  font-size: 1em; 
  color: white; 
  transition: all 0.3s ease; 
  box-sizing: border-box; 
}

.input-group input:focus { 
  outline: none; 
  background: rgba(255, 255, 255, 0.15); 
  border-color: rgba(76, 175, 80, 0.6); 
  box-shadow: 0 0 20px rgba(76, 175, 80, 0.3); 
}

.input-group input::placeholder { 
  color: rgba(255, 255, 255, 0.4); 
}

.input-group input:-webkit-autofill { 
  -webkit-text-fill-color: white; 
  -webkit-box-shadow: 0 0 0 1000px rgba(76, 175, 80, 0.2) inset; 
  transition: background-color 5000s ease-in-out 0s; 
}

.options { 
  display: flex; 
  justify-content: space-between; 
  align-items: center; 
  margin-bottom: 30px; 
}

.remember { 
  display: flex; 
  align-items: center; 
  gap: 8px; 
  cursor: pointer; 
  color: white; 
  font-size: 0.95em; 
}

.remember input[type="checkbox"] { 
  width: 18px; 
  height: 18px; 
  cursor: pointer; 
  accent-color: #4caf50; 
}

.forgot { 
  color: #a5d6a7; 
  text-decoration: none; 
  font-size: 0.95em; 
  font-weight: 600; 
  transition: color 0.3s ease; 
}

.forgot:hover { 
  color: white; 
}

.login-btn { 
  width: 100%; 
  padding: 16px; 
  background: linear-gradient(135deg, #66bb6a, #4caf50); 
  color: white; 
  border: none; 
  border-radius: 50px; 
  font-size: 1.1em; 
  font-weight: 700; 
  cursor: pointer; 
  transition: all 0.3s ease; 
  margin-bottom: 25px; 
  box-shadow: 0 4px 15px rgba(76, 175, 80, 0.4); 
  position: relative; 
  overflow: hidden; 
}

.login-btn::before { 
  content: ''; 
  position: absolute; 
  top: 0; 
  left: -100%; 
  width: 100%; 
  height: 100%; 
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent); 
  transition: left 0.5s; 
}

.login-btn:hover:not(:disabled)::before { 
  left: 100%; 
}

.login-btn:hover:not(:disabled) { 
  transform: translateY(-2px); 
  box-shadow: 0 6px 20px rgba(76, 175, 80, 0.6); 
}

.login-btn:active:not(:disabled) { 
  transform: translateY(0); 
}

.login-btn:disabled { 
  opacity: 0.7; 
  cursor: not-allowed; 
}

.loading-text { 
  display: flex; 
  align-items: center; 
  justify-content: center; 
  gap: 10px; 
}

.spinner { 
  display: inline-block; 
  animation: spin 1s linear infinite; 
}

.register-text { 
  text-align: center; 
  color: white; 
  font-size: 1em; 
}

.register-link { 
  color: #a5d6a7; 
  text-decoration: none; 
  font-weight: 700; 
  transition: color 0.3s ease; 
  margin-left: 4px; 
  display: inline-flex; 
  align-items: center; 
  gap: 4px; 
}

.register-link:hover { 
  color: white; 
}

.link-icon { 
  font-size: 1.1em; 
}

/* Project Description */
.project-description { 
  margin-top: 30px; 
  padding: 20px; 
  background: rgba(76, 175, 80, 0.1); 
  border-radius: 15px; 
  border: 1px solid rgba(76, 175, 80, 0.3); 
}

.tagline { 
  display: flex; 
  align-items: center; 
  justify-content: center; 
  gap: 10px; 
  font-size: 1.1em; 
  color: #a5d6a7; 
  margin-bottom: 10px; 
}

.tagline .icon { 
  font-size: 1.3em; 
  animation: pulse 2s ease-in-out infinite; 
}

@keyframes pulse { 
  0%, 100% { transform: scale(1); } 
  50% { transform: scale(1.1); } 
}

.description { 
  color: rgba(255, 255, 255, 0.85); 
  font-size: 0.9em; 
  line-height: 1.6; 
  text-align: center; 
}

/* Responsive Design */
@media (max-width: 1300px) {
  .winter-night-login {
    flex-direction: column;
    padding: 40px 20px;
    height: auto;
  }
  
  .app-title {
    position: relative;
    top: auto;
    left: auto;
    transform: none;
    margin-bottom: 30px;
  }
  
  .info-panel, .login-card {
    position: relative;
    left: auto;
    top: auto;
    transform: none;
    margin: 20px 0;
    width: 100%;
    max-width: 600px;
  }
  
  .info-panel {
    order: 1;
  }
  
  .login-card {
    order: 2;
  }
  
  .cabin {
    left: 5%;
  }
  
  .moon {
    right: 5%;
  }
}

@media (max-width: 768px) {
  .app-title h1 {
    font-size: 2.2rem;
  }
  
  .title { 
    font-size: 2.5em; 
  }
  
  .info-title { 
    font-size: 2em; 
  }
  
  .info-panel, .login-card {
    padding: 30px 20px;
  }
  
  .paragraphs-container {
    height: 180px;
  }
  
  .cabin {
    width: 150px;
    height: 120px;
    left: 3%;
  }
  
  .cabin-roof {
    width: 150px;
  }
  
  .cabin::before {
    width: 150px;
    height: 120px;
  }
  
  .door {
    left: 55px;
    height: 50px;
  }
  
  .window {
    top: 20px;
    width: 30px;
    height: 30px;
  }
  
  .window1 {
    left: 20px;
  }
  
  .window2 {
    right: 20px;
  }
  
  .moon {
    width: 80px;
    height: 80px;
    right: 8%;
  }
}
</style>