 import { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { api } from '../api.js'

export default function Register() {
  const navigate = useNavigate()
  const [form, setForm] = useState({ name: '', email: '', password: '', college: '' })
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
      await api.registerStudent(form)
      navigate('/login', { state: { justRegistered: true } })
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
          <span className="term-title">cbtas — register</span>
        </div>
        <div className="term-body">
          <div className="prompt">create-account</div>
          <h1>Set up your account</h1>
          <p className="subtitle">Practice company-specific technical assessments.</p>

          {error && <div className="error-box">{error}</div>}

          <form onSubmit={handleSubmit}>
            <div className="field">
              <label>full name</label>
              <input required value={form.name} onChange={update('name')} placeholder="Bhanu R B" />
            </div>
            <div className="field">
              <label>email</label>
              <input required type="email" value={form.email} onChange={update('email')} placeholder="you@college.edu" />
            </div>
            <div className="field">
              <label>password</label>
              <input required type="password" value={form.password} onChange={update('password')} placeholder="••••••••" />
            </div>
            <div className="field">
              <label>college</label>
              <input value={form.college} onChange={update('college')} placeholder="METS" />
            </div>
            <button className="btn btn-primary" disabled={loading}>
              {loading ? 'creating account…' : 'Create account'}
            </button>
          </form>

          <div className="link-row">
            Already have an account? <Link to="/login"><button type="button">Log in</button></Link>
          </div>
        </div>
      </div>
    </div>
  )
}