import React,{useState,useEffect} from 'react';
import './ExpensivePage1.css'

const ExpensivePage1 = () => {
    const [cost,setCost] = useState(30000);

    const handleTrans=(e)=>{
        alert("clicked");
    }

  return (
    <>
    <div className='root'>
    <div className='root1'>
      <div className='remaining'>
        <h2 className='costh2'>Remaining Amount</h2>
        <h1 className='costh1'>{cost}</h1>
      </div>
    </div>

    <div className="rent">
        <h2 className="rent1">Rent Paid</h2>
        <h2 className="rent2">6500</h2>
    </div>
    <form onClick={handleTrans} className='form-contaier'>
    <div className="rent">
        <h2 className="rent1">Add Transaction</h2>
        <h2 className="rent2">+</h2>
    </div>
    </form>
    </div>
    </>
  )
}

export default ExpensivePage1
