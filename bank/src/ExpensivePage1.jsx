import React, { useState, useEffect, useRef } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './ExpensivePage1.css';
import ApiService from './ApiService';

const Sample = () => {
    const [totalAmount,setTotalAmount] = useState()
    const [userTransactions,setUserTransactions] = useState([])
    const navigate = useNavigate()
    const location = useLocation();

    const hasAddedTransaction = useRef(false);

    const handleAddTransaction= (e) =>{
        hasAddedTransaction.current = false;
        navigate('/transaction')
    }

    useEffect(()=>{
      const getData= async ()=> {
      const response = await ApiService.getUserDetails();
      console.log("Total Amount: "+ response.totalAmount)
      setTotalAmount(response.totalAmount);
      }
      getData();
    },location.key)

    useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await ApiService.getUserTransaction();
        console.log("Fetched User Transactions:", response);

        setUserTransactions(response);
      } catch (error) {
        console.error("Error fetching user transactions:", error);
      }
    };

    fetchData();
  }, [location.key]);


  return (
    <>
    <div className="root">
      <div className="root1">
        <div className="remaining">
          <h2 className="costh2">Remaining Amount</h2>
          <h1 className="costh1">{totalAmount}</h1>
        </div>
      </div>

      {userTransactions.map((t,index) => (
         <div key={index} className={`rent ${t.type === 'income' ? 'income-green':'expense-red'}`}>
          <h2 className="rent1">{t.category}</h2>
          <h2 className="rent2">{t.amount}</h2>
        </div>
      ))
      }

      {/* Add Transaction Button */}
      <div onClick={handleAddTransaction} className="form-container">
        <div className="rent">
          <h2 className="rent1">Add Transaction</h2>
          <h2 className="rent2">+</h2>
        </div>
      </div>
    </div>
    </>
  )
}

export default Sample