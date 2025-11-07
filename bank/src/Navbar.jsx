import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import './Navbar.css';
import ApiService from './ApiService';

const Navbar = () => {

  const isAuthenticated = ApiService.isAuthenticated();
  const navigate = useNavigate();

  const handleLogout=(e) => {
    const isLogout = window.confirm("Are you sure you want to LOGOUT");
    if(isLogout){
      ApiService.logout();
      navigate("/home")
    }
  }
  return (
    <nav className="nav-container">
      <NavLink to="/home" className="nav-logo">Spendly</NavLink>
      <div className="nav-links">

        <NavLink to="/home" className={({ isActive }) => (isActive ? "nav-link active" : "nav-link")}> Home</NavLink>

        {!isAuthenticated && <NavLink to="/login" className={({ isActive }) => (isActive ? "nav-link active" : "nav-link")}>Login</NavLink>}

        {!isAuthenticated && <NavLink to="/register" className={({ isActive }) => (isActive ? "nav-link active" : "nav-link")}>Register</NavLink>}

        {isAuthenticated &&<button onClick={handleLogout} className={({isActive}) => (isActive ? "nav-link active":"nav-link")}> Logout</button>}

      </div>
    </nav>
  );
};

export default Navbar;
