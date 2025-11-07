import React from 'react';
import { useNavigate } from 'react-router-dom';
import Image1 from '../src/assets/Image1.jpg';
import ApiService from './ApiService';
import './NavbarPage.css';

function HomePage() {
  const navigate = useNavigate();
  const isAuthenticated = ApiService.isAuthenticated();

  const handleImgButton = () => {
    if(isAuthenticated){
    navigate("/expense");
    }else{
      navigate("/login");
    }
  };

  return (
    <div className="home-main">

      {/* Hero Section */}
      <div className="hero-body">
        <div className="hero-text">
          <h1 className="hero-main">
            Track Your <br /> Spending <br /> Effortlessly
          </h1>
          <button className="button-img" onClick={handleImgButton}>
            Manage Your Expense
          </button>
        </div>
        <div className="hero-image">
          <img src={Image1} alt="Expense Tracker" />
        </div>
      </div>
    </div>
  );
}

export default HomePage;
