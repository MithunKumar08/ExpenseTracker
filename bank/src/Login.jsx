import React,{ useState,useEffect } from "react";
import { Link, useNavigate } from 'react-router-dom'
import './Login.css'
import ApiService from "./ApiService";

function Login() {
    const [formData,setformData] = useState({
      username: '',
      password:''
    });
    const [errorMsg,setErrorMsg] = useState('')
    const [successMsg,setSuccessMsg] = useState('')
    const navigate = useNavigate('')

    const handleRegisterClick=()=>{
      navigate("/register")
    }

    const handleInputChange = (e) => {
      const {name, value} = e.target;
      setformData({...formData,[name]:value})
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        if(formData.username && formData.password){
        
        try{
        const response = await ApiService.loginUser(formData);
        console.log(response)

          if(response.statusCode === 200){
            localStorage.setItem('token',response.token)
            setformData({
              username: '',
              password:''
            })
            setSuccessMsg('✅ LOGGED IN SUCCESSFULLY')
            setTimeout(() => {
              setSuccessMsg('');
              navigate('/home')
            },3000)
            
          }else if(response.statusCode === 404){
                    setErrorMsg("⚠️ USER DOES NOT EXIST")
                    setTimeout(() => {
                        setErrorMsg('');
                        navigate('/login')
                    },5000)
                }
        }catch(error){
          setErrorMsg(error.response?.data?.message || error.message)
          setTimeout(() => {setErrorMsg('')},5000)
        }

        }else{
          setErrorMsg('⚠️ Please fill all fields !!')
          setTimeout(() => {setErrorMsg('')},5000)
        }
    
    }

  return (
    <>
    <div className="login-root1">
    <h1 className="login"> Login </h1>
    <div className="login">

      {successMsg && <p className='success-msg'>{successMsg}</p>}
      {errorMsg && <p className='error-msg'>{errorMsg}</p>}

        <form onSubmit={handleSubmit}>

        <div className="user">
        <label htmlFor= "UserName" className="user">Username</label>
        <input type="text" 
               name = 'username'
               value={formData.username}
               onChange={handleInputChange}/>
        </div>

        <div className="pass">
        <label htmlFor="Password" className="pass">Password</label>
        
            <input
              type="password"
              name ='password'
              value={formData.password}
              onChange={handleInputChange} 
            />
        </div>
        <p className="register-p">Don't have Account? <div onClick={handleRegisterClick} className="register-link">Register</div></p>
        
        <button type="submit">Submit</button>
        </form>

        
    </div>
    </div>
    </>
  )
}

export default Login
