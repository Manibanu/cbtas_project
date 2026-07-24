import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { api } from '../api.js'
import { getStudent } from '../App.jsx'

const DIFFICULTIES = ['EASY', 'MEDIUM', 'HARD']

export default function Dashboard() {
  const navigate = useNavigate()
  const student = getStudent()

  const [companies, setCompanies] = useState([])
  const [technologies, setTechnologies] = useState([])
  const [form, setForm] = useState({
    companyId: '',
    technologyId: '',
    difficulty: 'EASY',
    totalQuestions: 5,
  })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const [loadError, setLoadError] = useState('')

  useEffect(() => {
    async function load() {
      try {
        const [c, t] = await Promise.all([api.getCompanies(), api.getTechnologies()])
        setCompanies(c)
        setTechnologies(t)
        if (c.length) setForm((f) => ({ ...f, companyId: c[0].id }))
        if (t.length) setForm((f) => ({ ...f, technologyId: t[0].id }))
      } catch (err) {
        setLoadError(
          'Could not load companies/technologies. Check that /company/all and /technology/all exist on your backend.'
        )
      }
    }
    load()
  }, [])

  function logout() {
    localStorage.removeItem('cbtas_student')
    navigate('/login')
  }

  async function handleStart(e) {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      const test = await api.startTest({
        student: { id: student.id },
        company: { id: Number(form.companyId) },
        technology: { id: Number(form.technologyId) },
        difficulty: form.difficulty,
        totalQuestions: Number(form.totalQuestions),
      })
      navigate(`/test/${test.id}`)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="page-wide">
      <div className="topbar">
        <div className="who">
          logged in as <span>{student?.name}</span>
        </div>
        <div className="topbar-actions">
          <button onClick={() => navigate('/history')}>history</button>
          <button onClick={logout}>log out</button>
        </div>
      </div>

      <div className="page" style={{ padding: 0 }}>
        <div className="term">
          <div className="term-bar">
            <span className="term-dot red" />
            <span className="term-dot yellow" />
            <span className="term-dot green" />
            <span className="term-title">cbtas — new test</span>
          </div>
          <div className="term-body">
            <div className="prompt">configure</div>
            <h1>Start a practice test</h1>
            <p className="subtitle">Pick a company, technology, and difficulty.</p>

            {loadError && <div className="error-box">{loadError}</div>}
            {error && <div className="error-box">{error}</div>}

            <form onSubmit={handleStart}>
              <div className="field">
                <label>company</label>
                <select value={form.companyId} onChange={(e) => setForm((f) => ({ ...f, companyId: e.target.value }))}>
                  {companies.map((c) => (
                    <option key={c.id} value={c.id}>{c.companyName}</option>
                  ))}
                </select>
              </div>
              <div className="field">
                <label>technology</label>
                <select value={form.technologyId} onChange={(e) => setForm((f) => ({ ...f, technologyId: e.target.value }))}>
                  {technologies.map((t) => (
                    <option key={t.id} value={t.id}>{t.technologyName}</option>
                  ))}
                </select>
              </div>
              <div className="field">
                <label>difficulty</label>
                <select value={form.difficulty} onChange={(e) => setForm((f) => ({ ...f, difficulty: e.target.value }))}>
                  {DIFFICULTIES.map((d) => (
                    <option key={d} value={d}>{d}</option>
                  ))}
                </select>
              </div>
              <div className="field">
                <label>number of questions</label>
                <input
                  type="number"
                  min="1"
                  max="50"
                  value={form.totalQuestions}
                  onChange={(e) => setForm((f) => ({ ...f, totalQuestions: e.target.value }))}
                />
              </div>
              <button className="btn btn-primary" disabled={loading || !form.companyId || !form.technologyId}>
                {loading ? 'starting…' : 'Start test'}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  )
}