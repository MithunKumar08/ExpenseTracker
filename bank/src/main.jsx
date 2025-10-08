import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import Login from './Login.jsx'
import ExpensivePage1 from './ExpensivePage1.jsx'
import TransactioPage from './TransactioPage.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    {/* <App /> */}
    {/* <Login/> */}
    {/* <ExpensivePage1 /> */}
    <TransactioPage />
  </StrictMode>,
)
