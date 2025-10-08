import React from 'react'
import './TransactionPage.css'

const TransactioPage = () => {

    const handleSubmit=(e)=>{
        console.log("Form Submitted");
    }
  return (
    <>
    <div className="App">
      <div className="tran1">
        <h1 className="tranh1">Transaction Details</h1>
      </div>
      
      <form onSubmit={handleSubmit} className="form-container">
        <div className="form-group">
          <label htmlFor="type">Transaction Type</label>
          <input type="text" id="type" name="type" />
        </div>

        
        <div className="form-group">
          <label htmlFor="category">Category</label>
          <input type="text" id="category" name="category" />
        </div>

        
        <div className="form-group">
          <label htmlFor="amount">Amount</label>
          <input type="number" id="amount" name="amount" />
        </div>

        <div className="form-group">
          <label htmlFor="description">Description</label>
          <textarea id="description" name="description" rows="3" />
        </div>

        <button type="submit">Submit</button>
      </form>
    </div>
    </>
  )
}

export default TransactioPage
