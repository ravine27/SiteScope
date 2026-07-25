import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 15000,
});

export const auditWebsite = async (url) => {
  try {
    const response = await apiClient.post('/audit', { url });
    return response.data;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(error.response.data.message || 'Audit request failed.');
    } else if (error.request) {
      throw new Error('Backend server is unreachable. Please ensure Spring Boot is running on port 8080.');
    } else {
      throw new Error(error.message || 'An unexpected error occurred.');
    }
  }
};
