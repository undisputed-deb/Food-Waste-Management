<template>
  <div class="dashboard-card">
    <div class="card-icon">{{ icon }}</div>
    <div class="card-content">
      <h3>{{ value }}</h3>
      <p>{{ label }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DashboardCard',
  props: {
    icon: {
      type: String,
      required: true
    },
    value: {
      type: [String, Number],
      required: true
    },
    label: {
      type: String,
      required: true
    }
  }
};
</script>

<style scoped>
.dashboard-card {
  background: white;
  padding: 25px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
  display: flex;
  align-items: center;
  gap: 20px;
  transition: transform 0.3s, box-shadow 0.3s;
}

.dashboard-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 20px rgba(0,0,0,0.15);
}

.card-icon {
  font-size: 3em;
}

.card-content h3 {
  font-size: 2.5em;
  color: #667eea;
  margin: 0;
}

.card-content p {
  color: #666;
  margin: 5px 0 0;
  font-size: 1em;
}
</style>