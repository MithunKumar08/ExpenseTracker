import React from 'react'
import {BrowserRouter as Router,Routes,Route} from "react-router-dom";
import ExpenseTrackerPage from './ExpensivePage1';
import TransactionPage from './TransactioPage';
import LoginPage from './Login';
import RegisterPage from './RegisterPage';
import HomePage from './HomePage';
import ExpensivePage1 from './ExpensivePage1';
import Sample from './Sample';

const RouterPage = () => {
  return (
    
        <Routes>
            <Route path="/" element={<RegisterPage />}/>
            <Route path="/home" element={<HomePage />}/>
            <Route path="/login" element={<LoginPage />}/>
            <Route path="/expense" element = {<ExpensivePage1 />}/>
            <Route path="/transaction" element={<TransactionPage/>}/>
            <Route path="/register" element={<RegisterPage/>}/>
            <Route path='/sample' element={<Sample/>}/>
        </Routes>
    
  )
}

export default RouterPage
