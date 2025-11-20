import React, { useState, useEffect, useRef } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './ExpensivePage1.css';
import ApiService from './ApiService';

const Sample = () => {
    const [totalAmount,setTotalAmount] = useState()
    const [month,setMonth] = useState()
    const [userTransactions,setUserTransactions] = useState([])
    const navigate = useNavigate()
    const location = useLocation();
    const [monthTranData,setmonthTranData]=useState({
      totalIncome:'', totalExpense:'',remainingAmount:''
    });

    const hasAddedTransaction = useRef(false);

    const handleAddTransaction= (e) =>{
        hasAddedTransaction.current = false;
        navigate('/transaction')
    }

    useEffect(()=>{
      const getTranByMonth=async () => {
        console.log("Month Value: "+month);
      const response = await ApiService.getTranByMonth(month);
      setmonthTranData({
        totalIncome: response.totalIncome,
        totalExpense: response.totalExpense,
        remainingAmount: response.remainingAmount
      });
    }
      getTranByMonth();
    },[month])

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
        const response = await ApiService.getUserTransaction(month);
        console.log("Fetched User Transactions:", response);

        setUserTransactions(response);
      } catch (error) {
        console.error("Error fetching user transactions:", error);
      }
    };

    fetchData();
  }, [location.key,month]);


  return (
    <>
    <div className="root">
      <div className="root1">

  <div className="summary-card income">
    <h2 className="costh2">Income</h2>
    <h1 className="costh1">{monthTranData.totalIncome}</h1>
  </div>

  <div className="summary-card expense">
    <h2 className="costh2">Expense</h2>
    <h1 className="costh1">{monthTranData.totalExpense}</h1>
  </div>

  <div className="summary-card remaining">
    <h2 className="costh2">Remaining</h2>
    <h1 className="costh1">{monthTranData.remainingAmount}</h1>
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

    <div className='month-container'>
      <select className='month-select' value={month} onChange={(e)=> setMonth(e.target.value)}>
        <option value={1}>January</option>
        <option value={2}>Febraury</option>
        <option value={3}>March</option>
        <option value={4}>April</option>
        <option value={5}>May</option>
        <option value={6}>June</option>
        <option value={7}>July</option>
        <option value={8}>August</option>
        <option value={9}>September</option>
        <option value={10}>October</option>
        <option value={11}>November</option>
        <option value={12}>December</option>
      </select>
    </div>
    </>
  )
}

export default Sample