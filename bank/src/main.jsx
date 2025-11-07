import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import Navbar from './Navbar'
import RouterPage from './RouterPage'
import { BrowserRouter as Router } from 'react-router-dom'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <Router>
    <Navbar />
    <RouterPage />
    </Router>
  </StrictMode>,
)
