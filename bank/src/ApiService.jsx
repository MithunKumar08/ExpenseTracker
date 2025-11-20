import axios from "axios";

export default class ApiService {
  static BASE_URL = "http://localhost:8080";

  // ✅ Create a single Axios instance
  static axiosInstance = axios.create({
    baseURL: ApiService.BASE_URL,
  });

  // ✅ Attach JWT token to all requests
  static getHeader() {
    const token = localStorage.getItem("token");
    return token
      ? { Authorization: `Bearer ${token}` }
      : {}; // avoid sending undefined header
  }

  static isAuthenticated() {
    const token = localStorage.getItem("token");
    return !!token;
  }

  static logout() {
    localStorage.removeItem("token");
  }

  // ✅ Registration
  static async registerUser(registerData) {
    const response = await ApiService.axiosInstance.post(
      "/auth/v1/register",
      registerData
    );
    console.log("Register response:", response);
    return response.data;
  }

  // ✅ Login
  static async loginUser(loginData) {
    const response = await ApiService.axiosInstance.post(
      "/auth/v1/login",
      loginData
    );
    console.log("Login response:", response);
    return response.data;
  }

  // ✅ Get user transactions
  static async getUserTransaction(month) {
    const response = await ApiService.axiosInstance.get(
      `/transaction/getAllTranMonth/${month}`,
      { headers: this.getHeader() }
    );
    console.log("User ALL Transactions:", response);
    return response.data;
  }

  // ✅ Add new transaction
  static async addNewTransaction(requestData) {
    const response = await ApiService.axiosInstance.post(
      "/transaction/add",
      requestData,
      { headers: this.getHeader() }
    );
    console.log("Add Transaction Response:", response);
    return response.data;
  }

  // ✅ Get user details
  static async getUserDetails() {
    const response = await ApiService.axiosInstance.get(
      "/transaction/getUser",
      { headers: this.getHeader() }
    );
    console.log("User Details:", response);
    return response.data;
  }

  static async getTranByMonth(month){
    const response = await ApiService.axiosInstance.get(
      `/transaction/monthTran/${month}`,
      {headers: this.getHeader()}
    )
    console.log("Response By Month: ",response);
    return response.data;
  }
}

// ✅ Handle expired JWT tokens globally
ApiService.axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      console.warn("JWT expired or unauthorized. Redirecting to login...");
      localStorage.removeItem("token");
      window.location.href = "/login"; // redirect to login page
    }
    return Promise.reject(error);
  }
);
