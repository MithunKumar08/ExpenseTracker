import React,{ useState,useEffect } from "react";
import './Login.css'

function Login() {
    const [username,setUsername] = useState('');
    const [password,setPassword] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        alert(`Submit button clicked : ${username} ${password}`);
    
    }

  return (
    <>
    <div className="root1">
    <h1 className="login"> Login </h1>
    <div className="login">
        <form onSubmit={handleSubmit}>

        <div className="user">
        <label htmlFor= "UserName" className="user">Username</label>
        <input type="text" 
               value={username}
               onChange={(e)=> setUsername(e.target.value)} required/>
        </div>

        <div className="pass">
        <label htmlFor="Password" className="pass">Password</label>
        
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)} 
              required
            />
        </div>

        <button type="submit">Submit</button>
        </form>
    </div>
    </div>
    </>
  )
}

export default Login
