 import { useState } from 'react'
import { useNavigate, Link, useLocation } from 'react-router-dom'
import { api } from '../api.js'

export default function Login() {
  const navigate = useNavigate()
  const location = useLocation()
  const [form, setForm] = useState({ email: '', password: '' })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  function update(field) {
    return (e) => setForm((f) => ({ ...f, [field]: e.target.value }))
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      const student = await api.loginStudent(form.email, form.password)
      localStorage.setItem('cbtas_student', JSON.stringify(student))
      navigate('/dashboard')
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="page">
      <div className="term">
        <div className="term-bar">
          <span className="term-dot red" />
          <span className="term-dot yellow" />
          <span className="term-dot green" />
          <span className="term-title">cbtas — login</span>
        </div>
        <div className="term-body">
          <div className="prompt">authenticate</div>
          <h1>Welcome back</h1>
          <p className="subtitle">
            {location.state?.justRegistered
              ? 'Account created — log in to continue.'
              : 'Log in to start a practice test.'}
          </p>

          {error && <div className="error-box">{error}</div>}

          <form onSubmit={handleSubmit}>
            <div className="field">
              <label>email</label>
              <input required type="email" value={form.email} onChange={update('email')} placeholder="you@college.edu" />
            </div>
            <div className="field">
              <label>password</label>
              <input required type="password" value={form.password} onChange={update('password')} placeholder="••••••••" />
            </div>
            <button className="btn btn-primary" disabled={loading}>
              {loading ? 'logging in…' : 'Log in'}
            </button>
          </form>

          <div className="link-row">
            Need an account? <Link to="/register"><button type="button">Register</button></Link>
          </div>
        </div>
      </div>
    </div>
  )
}