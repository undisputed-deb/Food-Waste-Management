import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  },
  transformResponse: [function (data) {
    // Axios default transformResponse
    if (typeof data === 'string') {
      try {
        return JSON.parse(data);
      } catch (e) {
        return data;
      }
    }
    return data;
  }]
});

// Add token to requests
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Handle auth errors
api.interceptors.response.use(
  (response) => {
    console.log('✅ Response:', response.config.url, response.data);
    return response;
  },
  (error) => {
    if (error.response?.status === 401 || error.response?.status === 403) {
      localStorage.removeItem('token');
      localStorage.removeItem('username');
      localStorage.removeItem('role');
      if (window.location.pathname !== '/login') {
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

export const authAPI = {
  login: (credentials) => api.post('/auth/login', credentials),
  register: (userData) => api.post('/auth/register', userData),
  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('role');
  }
};

export const donorsAPI = {
  getAll: () => api.get('/donors'),
  getById: (id) => api.get(`/donors/${id}`),
  create: (donor) => api.post('/donors', donor),
  update: (id, donor) => api.put(`/donors/${id}`, donor),
  delete: (id) => api.delete(`/donors/${id}`)
};

export const wasteItemsAPI = {
  getAll: () => api.get('/waste-items'),
  getById: (id) => api.get(`/waste-items/${id}`),
  getByDonor: (donorId) => api.get(`/waste-items/donor/${donorId}`),
  getByProcessedStatus: (status) => api.get(`/waste-items/processed/${status}`),
  create: (item) => api.post('/waste-items', item),
  update: (id, item) => api.put(`/waste-items/${id}`, item),
  markAsProcessed: (id, processorId) => api.patch(`/waste-items/${id}/process/${processorId}`),
  delete: (id) => api.delete(`/waste-items/${id}`)
};

export const centersAPI = {
  getAll: () => api.get('/collection-centers'),
  getById: (id) => api.get(`/collection-centers/${id}`),
  create: (center) => api.post('/collection-centers', center),
  update: (id, center) => api.put(`/collection-centers/${id}`, center),
  delete: (id) => api.delete(`/collection-centers/${id}`)
};

export const processorsAPI = {
  getAll: () => api.get('/processors'),
  getById: (id) => api.get(`/processors/${id}`),
  create: (processor) => api.post('/processors', processor),
  update: (id, processor) => api.put(`/processors/${id}`, processor),
  delete: (id) => api.delete(`/processors/${id}`)
};

export default api;