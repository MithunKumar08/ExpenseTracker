import React, { useState } from 'react';
import './TransactionPage.css';
import { useNavigate } from 'react-router-dom';
import ApiService from './ApiService';

const TransactionPage = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    type: '',
    category: '',
    amount: '',
    description: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

const handleSubmit = async (e) => {
  e.preventDefault();

  const newTransaction = {
    category: formData.category,   
    amount: parseInt(formData.amount),
    type: formData.type,
    description: formData.description,
  };

  const response = await ApiService.addNewTransaction(newTransaction);
  // navigate back with new transaction
  navigate('/expense', { state: { newTransaction } });
};

  return (
    <div className="App">
      <div className="tran1">
        <h1 className="tranh1">Transaction Details</h1>
      </div>

      <form onSubmit={handleSubmit} className="form-container">
        <div className="form-group">
          <label htmlFor="type">Transaction Type</label>
          <input
            type="text"
            id="type"
            name="type"
            value={formData.type}
            onChange={handleChange}
            placeholder="e.g., Income or Expense"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="category">Category</label>
          <input
            type="text"
            id="category"
            name="category"
            value={formData.category}
            onChange={handleChange}
            placeholder="e.g., Rent, Food, Travel"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="amount">Amount</label>
          <input
            type="number"
            id="amount"
            name="amount"
            value={formData.amount}
            onChange={handleChange}
            placeholder="Enter amount"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="description">Description</label>
          <textarea
            id="description"
            name="description"
            rows="3"
            value={formData.description}
            onChange={handleChange}
            placeholder="Optional description..."
          />
        </div>

        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default TransactionPage;
