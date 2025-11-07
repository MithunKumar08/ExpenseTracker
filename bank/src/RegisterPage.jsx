import React,{useState} from 'react'
import './RegisterPage.css'
import { useNavigate } from 'react-router-dom';
import ApiService from './ApiService';

const RegisterPage = () => {
    const [formData,setFormData] = useState({
        username: '',
        password: '',
        email: '',
    });
    const navigate = useNavigate();
    const [successMsg,setSuccessMsg] = useState('');
    const [errorMsg,setErrorMsg] = useState('');

    const handleInputChange =(e)=> {
        const {name, value} = e.target;
        setFormData({...formData,[name]: value})
    }


    const handelSubmit= async (e) => {
        e.preventDefault();

        if(formData.username && formData.password && formData.email){
            try{
                const response = await ApiService.registerUser(formData);
                console.log(response);
                
                if(response.status === 201){
                    console.log(response.status)
                    setFormData({
                        username: '',
                        password: '',
                        email: ''
                    })
                     setSuccessMsg("✅  USER REGISTERED SUCCESSFULLY");
                     setTimeout(() => {
                        setSuccessMsg('');
                        navigate('/login');
                     },5000)
                }else if(response.status === 409){
                    setErrorMsg("⚠️ USER ALREADY REGISTERED")
                    setTimeout(() => {
                        setErrorMsg('');
                        navigate('/login')
                    },5000)
                }
            } catch (error){
                setErrorMsg(error.response?.data?.message || error.message )
                setTimeout(() => setErrorMsg(''), 5000);
            }
        }else {
            setErrorMsg('⚠️ Please fill all fields !!')
            setTimeout(()=> {setErrorMsg('')},5000);
        }
    }
  return (
    <>
    <div className='register-container'>
     
    <h1 className='register'>Register</h1>
    <div className="register-page">
        
    <form onSubmit={handelSubmit} className="register-form">

        {successMsg && <p className='success-msg'>{successMsg}</p>}
        {errorMsg && <p className='error-msg'>{errorMsg}</p>}

    <div className="name">
        <label htmlFor="UserName" className='label-css'> Username </label>
        <input type='text'
        name='username' 
        value={formData.username}
        onChange={handleInputChange}
        className='input-css'
        />
    </div>

    <div className="name">
        <label htmlFor="Password" className='label-css'> Password </label>
        <input type='password' 
        name='password'
        value={formData.password}
        onChange={handleInputChange}
        className='input-css'
        />
    </div>

    <div className="name">
        <label htmlFor="Email" className='label-css'> Email </label>
        <input type='email' 
        name='email'
        value={formData.email}
        onChange={handleInputChange}
        className='input-css'
        />
    </div>

    <p className="login-handel">Already Registered ? <a href='/login'> Login</a> </p>

    <button type='submit'> Submit </button>
    </form>
    </div>
    </div>
    </>
  )
}

export default RegisterPage