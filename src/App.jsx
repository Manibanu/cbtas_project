import { Routes, Route, Navigate } from 'react-router-dom'
import Register from './Pages/Register.jsx'
import Login from './Pages/Login.jsx'
import Dashboard from './Pages/Dashboard.jsx'
import TestPage from './Pages/TestPage.jsx'
import ResultPage from './Pages/ResultPage.jsx'
import History from './Pages/History.jsx'

function getStudent() {
  const raw = localStorage.getItem('cbtas_student')
  return raw ? JSON.parse(raw) : null
}

function Protected({ children }) {
  const student = getStudent()
  if (!student) return <Navigate to="/login" replace />
  return children
}

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/login" replace />} />
      <Route path="/register" element={<Register />} />
      <Route path="/login" element={<Login />} />
      <Route path="/dashboard" element={<Protected><Dashboard /></Protected>} />
      <Route path="/test/:testId" element={<Protected><TestPage /></Protected>} />
      <Route path="/result/:resultId" element={<Protected><ResultPage /></Protected>} />
      <Route path="/history" element={<Protected><History /></Protected>} />
      <Route path="*" element={<Navigate to="/login" replace />} />
    </Routes>
  )
}

export { getStudent }