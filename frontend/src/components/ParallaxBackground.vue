<template>
  <div class="parallax-container">
    <div ref="layer1" class="parallax-layer layer-1"></div>
    <div ref="layer2" class="parallax-layer layer-2"></div>
    <div ref="layer3" class="parallax-layer layer-3"></div>
    <div ref="layer4" class="parallax-layer layer-4"></div>
    <div class="parallax-overlay"></div>
  </div>
</template>

<script>
import { ref, onMounted, onUnmounted } from 'vue';
import { gsap } from 'gsap';
import { ScrollTrigger } from 'gsap/ScrollTrigger';

gsap.registerPlugin(ScrollTrigger);

export default {
  name: 'ParallaxBackground',
  setup() {
    const layer1 = ref(null);
    const layer2 = ref(null);
    const layer3 = ref(null);
    const layer4 = ref(null);
    
    let scrollTriggerInstance = null;
    
    onMounted(() => {
      // Create parallax effect with different speeds for each layer
      scrollTriggerInstance = ScrollTrigger.create({
        trigger: 'body',
        start: 'top top',
        end: 'bottom bottom',
        scrub: true,
        onUpdate: (self) => {
          const progress = self.progress;
          
          // Layer 1 - Slowest (deepest)
          gsap.to(layer1.value, {
            y: progress * 150,
            duration: 0
          });
          
          // Layer 2 - Slow
          gsap.to(layer2.value, {
            y: progress * 300,
            duration: 0
          });
          
          // Layer 3 - Medium
          gsap.to(layer3.value, {
            y: progress * 450,
            duration: 0
          });
          
          // Layer 4 - Fastest (closest)
          gsap.to(layer4.value, {
            y: progress * 600,
            duration: 0
          });
        }
      });
      
      // Animate layers on load
      gsap.from([layer1.value, layer2.value, layer3.value, layer4.value], {
        opacity: 0,
        duration: 1.5,
        stagger: 0.2,
        ease: 'power2.out'
      });
    });
    
    onUnmounted(() => {
      if (scrollTriggerInstance) {
        scrollTriggerInstance.kill();
      }
    });
    
    return {
      layer1,
      layer2,
      layer3,
      layer4
    };
  }
};
</script>

<style scoped>
.parallax-container {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  overflow: hidden;
  z-index: -1;
  background: #0a0a0f;
}

.parallax-layer {
  position: absolute;
  width: 100%;
  height: 120vh;
  top: -10vh;
  left: 0;
  will-change: transform;
}

/* Layer 1 - Deepest - Dark purple nebula */
.layer-1 {
  background: 
    radial-gradient(circle at 20% 30%, rgba(75, 0, 130, 0.3) 0%, transparent 50%),
    radial-gradient(circle at 80% 70%, rgba(138, 43, 226, 0.2) 0%, transparent 50%);
  filter: blur(80px);
  opacity: 0.6;
}

/* Layer 2 - Deep - Blue/purple gradient */
.layer-2 {
  background: 
    radial-gradient(circle at 60% 40%, rgba(102, 126, 234, 0.25) 0%, transparent 50%),
    radial-gradient(circle at 30% 80%, rgba(118, 75, 162, 0.25) 0%, transparent 50%);
  filter: blur(60px);
  opacity: 0.7;
}

/* Layer 3 - Medium - Violet accents */
.layer-3 {
  background: 
    radial-gradient(circle at 70% 20%, rgba(147, 51, 234, 0.2) 0%, transparent 40%),
    radial-gradient(circle at 40% 60%, rgba(79, 70, 229, 0.2) 0%, transparent 40%),
    radial-gradient(circle at 90% 90%, rgba(168, 85, 247, 0.15) 0%, transparent 35%);
  filter: blur(40px);
  opacity: 0.8;
}

/* Layer 4 - Closest - Bright accent spots */
.layer-4 {
  background: 
    radial-gradient(circle at 50% 50%, rgba(167, 139, 250, 0.15) 0%, transparent 30%),
    radial-gradient(circle at 85% 15%, rgba(129, 140, 248, 0.12) 0%, transparent 25%),
    radial-gradient(circle at 15% 85%, rgba(196, 181, 253, 0.1) 0%, transparent 25%);
  filter: blur(20px);
  opacity: 0.9;
}

/* Overlay with grain texture for depth */
.parallax-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: 
    repeating-linear-gradient(
      0deg,
      transparent,
      transparent 2px,
      rgba(255, 255, 255, 0.01) 2px,
      rgba(255, 255, 255, 0.01) 4px
    );
  opacity: 0.5;
  pointer-events: none;
}

/* Mobile optimization */
@media (max-width: 768px) {
  .parallax-layer {
    height: 110vh;
  }
  
  .layer-1, .layer-2 {
    filter: blur(50px);
  }
  
  .layer-3 {
    filter: blur(30px);
  }
  
  .layer-4 {
    filter: blur(15px);
  }
}

/* Reduce motion for accessibility */
@media (prefers-reduced-motion: reduce) {
  .parallax-layer {
    transform: none !important;
  }
}
</style>